package com.fuppino.akka.lifecycle.learning

import akka.actor.{Actor, ActorSystem, Props}

object PostStartExample {
  def main(args: Array[String]): Unit = {
    var system = ActorSystem("ActorSystem")
    var actor = system.actorOf(Props[MyActor2], "MyActor")
    actor !  "message from main"
    //system.terminate()
    system.stop(actor)
  }
}
class MyActor2 extends Actor {
  override def receive: Receive = {
    case msg:String =>
      println(" Inside myActor actor : "+ msg)
  }

  override def postStop() : Unit = {    // Overriding postStop method
    println(" postStop method is called")
  }
}