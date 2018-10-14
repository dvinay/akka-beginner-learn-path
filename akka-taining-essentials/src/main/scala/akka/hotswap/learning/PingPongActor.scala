package akka.hotswap.learning

import akka.actor.{Actor, ActorSystem, Props}

case class PING()
case class PONG()

class PingPongActor extends Actor {
  import context._
  var count = 0
  def receive: Receive = {
    case PING =>
      println("PING")
      count = count + 1
      Thread.sleep(100)
      self ! PONG
      become {
        case PONG =>
          println("PONG")
          count = count + 1
          Thread.sleep(100)
          self ! PING
          unbecome()
      }
      if(count > 10) context.stop(self)
  }
}

object PingPongActor {

  def main(args: Array[String]): Unit = {
    val _system = ActorSystem("BecomeUnbecome")
    val pingPongActor = _system.actorOf(Props[PingPongActor])
    pingPongActor ! PING
    Thread.sleep(2000)
    _system.terminate()
  }
}