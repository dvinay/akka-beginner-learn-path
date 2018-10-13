package com.fuppino.akka.pros.learning

import akka.actor.{Actor, ActorSystem, Props}

object PropsExample {
  def main(args: Array[String]): Unit = {
    var system = ActorSystem("ActorSystem")
    var actor = system.actorOf(Props[MyPropsActor], "MyPropsActor")
    actor ! "Hello Akka"
    system.terminate()
  }
}
class MyPropsActor extends Actor {
  override def receive: Receive = {
    case msg:String =>
      println(" Data from main class : "+ msg)
      println(" props details  : ")
      println(" name : "+self.path.name)
      println(" address : "+self.path.address)
      println(" parent : "+self.path.parent)
      println(" root : "+self.path.root)
  }
}
