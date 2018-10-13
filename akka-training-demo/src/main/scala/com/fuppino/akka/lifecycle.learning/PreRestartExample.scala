package com.fuppino.akka.lifecycle.learning

import akka.actor.{Actor, ActorSystem, Props}

object PreRestartExample {
  def main(args: Array[String]): Unit = {
    var system = ActorSystem("ActorSystem")
    var actor = system.actorOf(Props[MyActor3], "MyActor")
    actor !  "message from main"
    //system.terminate()
  }
}
class MyActor3 extends Actor {
  override def receive: Receive = {
    case msg:String =>
      println(" Inside myActor actor : "+ msg)
      var a:Int =  10/0;      // ArithmethicException occurred
  }

  override def preRestart(reason:Throwable, message: Option[Any]){    // Overriding preRestart method
    println("preRestart method is called");
    println("Reason: "+reason)
  }
}