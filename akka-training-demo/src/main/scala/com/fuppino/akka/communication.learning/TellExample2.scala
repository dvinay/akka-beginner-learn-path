package com.fuppino.akka.communication.learning

import akka.actor.{Actor, ActorSystem, Props}

object TellExample2 {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("ActorSystem")
    val actor = system.actorOf(Props[RootActor], "RootActor")
    actor !  "Hello Akka"
    system.terminate()
  }
}
class RootActor extends Actor {
  override def receive: Receive = {
    case msg:String =>
      println(" Inside root actor - msg : "+ msg)
      println(" Inside root actor - sender : "+ sender())
      val childActor = context.actorOf(Props[ChildActor],"ChildActor")
      childActor ! "message from root"
  }
}
class ChildActor extends Actor {
  override def receive: Receive = {
    case msg:String =>
      println(" Inside child actor : "+ msg)
      println(" Inside child actor - sender : "+ sender())
  }
}
