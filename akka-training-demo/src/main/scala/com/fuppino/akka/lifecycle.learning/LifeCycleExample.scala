package com.fuppino.akka.lifecycle.learning

import akka.actor.{Actor, ActorSystem, Props}

object LifeCycleExample {
  def main(args: Array[String]): Unit = {
    var system = ActorSystem("ActorSystem")
    var actor = system.actorOf(Props[MyActor5], "MyActor")
    actor !  "message from main"
    //system.terminate()
  }
}
class MyActor5 extends Actor {
  override def receive: Receive = {
    case msg:String =>
      println(" Inside myActor actor : "+ msg)
      var a:Int =  10/0;      // ArithmethicException occurred
  }
  override def preStart(){
    super.preStart();
    println("preStart method is called");
  }
  override def postStop(){
    super.postStop();
    println("postStop method is called");
  }
  override def preRestart(reason:Throwable, message: Option[Any]){
    super.preRestart(reason, message);
    println("preRestart method is called");
    println("Reason: "+reason);
  }
  override def postRestart(reason:Throwable){
    super.postRestart(reason);
    println("postRestart is called");
    println("Reason: "+reason);
  }
}