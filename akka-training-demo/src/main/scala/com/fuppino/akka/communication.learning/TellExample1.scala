package com.fuppino.akka.communication.learning

import akka.actor.{Actor, ActorSystem, Props}

object TellExample1 {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("ActorSystem")
    val actor = system.actorOf(Props[MyActor1], "MyActor")
    actor !  "Hello Akka"
    system.terminate()
  }
}
class MyActor1 extends Actor {
  override def receive: Receive = {
    case msg:String =>
      println(" Data from main class : "+ msg);
      println(" sender : "+ sender());
  }
}
