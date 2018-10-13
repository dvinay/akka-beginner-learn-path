package com.fuppino.akka.communication.learning

import akka.util.Timeout
import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import scala.concurrent.duration._
import scala.concurrent.Await

object AskExample2 {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("ActorSystem")
    val actor = system.actorOf(Props[MyActor2], "MyActor")
    implicit val timeout = Timeout(10 seconds)
    val future = actor ?  "Hello Akka"
    val result = Await.result(future, timeout.duration)
    println(" result : "+ result)
    //system.terminate()
  }
}
class MyActor2 extends Actor {
  override def receive: Receive = {
    case msg:String =>
      println(" Data from main class : "+ msg)
      println(" sender : "+ sender())
      sender() ! "Hello, I got your message."      // Replying message
  }
}

