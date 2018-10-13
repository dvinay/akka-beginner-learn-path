package com.fuppino.akka.lifecycle.learning

import akka.actor.{Actor, ActorSystem, Props}

object PreStartExample {
  def main(args: Array[String]): Unit = {
    var system = ActorSystem("ActorSystem")
    var actor = system.actorOf(Props[MyActor1], "MyActor")
    actor !  "message from main"
    system.terminate()
  }
}
class MyActor1 extends Actor {
  override def receive: Receive = {
    case msg:String =>
      println(" Inside myActor actor : "+ msg)
  }

  override def preStart(): Unit = {
    println(" preStart method is called " )
  }
}