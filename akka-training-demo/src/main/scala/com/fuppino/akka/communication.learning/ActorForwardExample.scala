package com.fuppino.akka.communication.learning

import akka.actor.{Actor,ActorSystem, Props};

class ActorForwardExample extends Actor{
  def receive = {
    case message:String => println("Message received from "+sender().path.name+" : "+message);
      val child = context.actorOf(Props[Actor2],"ChildActor");
      println("message forwarded to child Actor");
      child ! message ;    // Message forwarded to child actor

    case _ => println("Unknown message");
  }
}

class Actor2 extends Actor{
  def receive ={
    case message:String => println("Message received from "+sender().path.name+" : "+message);
    case _ => println("Unknown message");
  }
}


object ActorForwardExample{
  def main(args:Array[String]){
    val actorSystem = ActorSystem("ActorSystem");
    val actor = actorSystem.actorOf(Props[ActorForwardExample], "RootActor");
    actor ! "Hello"
  }
}
