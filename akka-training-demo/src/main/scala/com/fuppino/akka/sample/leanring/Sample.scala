package com.fuppino.akka.sample.leanring

import akka.actor.Actor;          // Importing actor trait
import akka.actor.ActorSystem;
import akka.actor.Props;

object Sample {
  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem("ActorSystem");
    var actor = actorSystem.actorOf(Props[MyActor],"MyActor")
    actor ! "Sample"
    actor ! 12.34
    actorSystem.terminate()
  }
}
class MyActor extends Actor {
  override def receive: Receive = {
    case msg : String => println(s"Welcome to Actor example ${msg}")
    case _ => println(s"Unknown message")
  }
}


