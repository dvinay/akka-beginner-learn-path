package com.fuppino.akka.childactor.learning

import akka.actor.{Actor, ActorSystem, Props}

object ChildActorExample {
  def main(args: Array[String]): Unit = {
    var system = ActorSystem("ActorSystem")
    var actor = system.actorOf(Props[RootActor], "RootActor")
    actor !  "message from main"
    system.terminate()
  }
}
class RootActor extends Actor {
  override def receive: Receive = {
    case msg:String =>
      println(" Inside root actor : "+ msg)
      val childActor = context.actorOf(Props[ChildActor],"ChildActor")
      childActor ! "message from root"
  }
}
class ChildActor extends Actor {
  override def receive: Receive = {
    case msg:String =>
      println(" Inside child actor : "+ msg)
  }
}