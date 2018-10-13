package com.fuppino.akka.communication.learning

import akka.actor.{Actor, ActorSystem, Props};

class ActorReplyExample extends Actor{
  def receive = {
    case message:String => println("Message recieved from "+sender.path.name+" massage: "+message);
      val child = context.actorOf(Props[ActorChildReplyExample],"ActorChild");
      child ! "Hello Child"
    case message:Int => println("Message recieved from "+sender.path.name+" massage: "+message);
  }
}


class ActorChildReplyExample extends Actor{
  def receive ={
    case message:String => println("Message recieved from "+sender.path.name+" massage: "+message);
      println("Replying to "+sender().path.name);
      sender()! 234;
  }
}

object ActorReplyExample{
  def main(args:Array[String]){
    val actorSystem = ActorSystem("ActorSystem");
    val actor = actorSystem.actorOf(Props[ActorReplyExample], "RootActor");
    actor ! "Hello";
  }
}  