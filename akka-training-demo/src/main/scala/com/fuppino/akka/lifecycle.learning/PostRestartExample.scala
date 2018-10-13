package com.fuppino.akka.lifecycle.learning

import akka.actor.{Actor, ActorSystem, Props}

object PostRestartExample {
  def main(args: Array[String]): Unit = {
    var system = ActorSystem("ActorSystem")
    var actor = system.actorOf(Props[MyActor4], "MyActor")
    actor !  "message from main"
    //system.terminate()
  }
}
class MyActor4 extends Actor {
  override def receive: Receive = {
    case msg:String =>
      println(" Inside myActor actor : "+ msg)
      var a:Int =  10/0;      // ArithmethicException occurred
  }

  override def postRestart(reason:Throwable){    // Overriding preRestart method
    println("postRestart method is called");
    println("Reason: "+reason)
  }
}