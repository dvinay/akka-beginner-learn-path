package com.fuppino.akka.communication.learning

import akka.actor.{Actor, ActorSystem, Props}
import akka.util.Timeout
import akka.pattern.ask
import scala.concurrent.duration._
import scala.concurrent.Await

object AskExample1 {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("ActorSystem")
    val actor = system.actorOf(Props[MyActor1], "MyActor")
    implicit val timeout = Timeout(10 seconds)
    val future = (actor ?  "Hello Akka")
    val result = Await.result(future, timeout.duration)
    println("result : "+ result)
    system.terminate()
  }
}
class MyActor1 extends Actor {
  override def receive: Receive = {
    case msg:String =>
      println(" Data from main class : "+ msg)
      println(" sender : "+ sender())
      // not sending anything
  }
}
