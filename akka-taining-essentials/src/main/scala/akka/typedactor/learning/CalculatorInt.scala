package akka.typedactor.learning

import akka.actor.{ActorSystem, TypedActor, TypedProps}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

trait CalculatorInt { // This interface act as a contract
  def add(first: Int, second: Int): Future[Int]
  def subtract(first: Int, second: Int): Future[Int]
  def incrementCount(): Unit
  def incrementAndReturn(): Option[Int]
}

class Calculator extends CalculatorInt { // This become an actor
  var counter: Int = 0
  import akka.actor.TypedActor.dispatcher

  def add(first: Int, second: Int): Future[Int] =
    Future successful first + second

  def subtract(first: Int, second: Int): Future[Int] =
    Future successful first - second

  def incrementCount(): Unit = counter += 1

  def incrementAndReturn(): Option[Int] = {
    counter += 1
    Some(counter)
  }

}
class Application {
  def main(args: Array[String]): Unit = {
    val _system = ActorSystem("TypedActorsExample")
    val calculator1: CalculatorInt = TypedActor(_system).typedActorOf(TypedProps[Calculator]())
    calculator1.incrementCount() // function invoke

    // like ask - expecting asynch response
    val future = calculator1.add(14,6);
    val result = Await.result(future, 5 second);

    //Method invocation in a blocking way
    val response = calculator1.incrementAndReturn()
  }
}