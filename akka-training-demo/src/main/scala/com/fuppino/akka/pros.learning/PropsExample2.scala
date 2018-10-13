package com.fuppino.akka.pros.learning

import akka.actor.{Actor, ActorSystem, Props}

object PropsExample2 {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("ActorSystem")
    val prop1 = Props[MyPropsActor]
    val actor1 = system.actorOf(prop1)
    actor1 ! "Hello Akka - actor1"

    val actor2 = system.actorOf(Props[MyPropsActor],"Actor2")
    actor2 ! "Hello Akka - actor2"

    val actor3 = system.actorOf(Props(classOf[MyPropsActor]),"Actor3")
    actor3 ! "Hello Akka - actor3"

    val actor4 = system.actorOf(Props(new MyPropsActor),"Actor4")
    actor4 ! "Hello Akka - actor3"

    system.terminate()
  }
}
class MyPropsActor2 extends Actor {
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
