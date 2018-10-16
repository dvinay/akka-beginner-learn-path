package akka.supervisor.learning.oneforonestrategy

import akka.actor.SupervisorStrategy._
import akka.actor.{Actor, ActorLogging, ActorSystem, OneForOneStrategy, Props}
import akka.util.Timeout._
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._

class SupervisorActor extends Actor with ActorLogging {


  val childActor = context.actorOf(Props[WorkerActor], name = "workerActor")

  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 10 seconds) {

    case _: ArithmeticException => Resume
    case _: NullPointerException => Restart
    case _: IllegalArgumentException => Stop
    case _: Exception => Escalate
  }

  def receive = {
    case result: Result =>
      childActor.tell(result, sender)
    case msg: Object =>
      childActor ! msg
  }
}

object SupervisorActor {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("faultTolerance")
    val log = system.log
    val originalValue: Int = 0

    implicit val timeout = Timeout(5 seconds)

    val supervisor = system.actorOf(Props[SupervisorActor], name = "supervisor")

    // No Exception case
    log.info("Sending value 8, no exceptions should be thrown! ")
    var mesg: Int = 8
    supervisor ! mesg
    var future = (supervisor ? new Result).mapTo[Int]
    var result = Await.result(future, timeout.duration)
    log.info("Value Received-> {}", result)


    //ArithmeticException => resume => gives previous result = 8
    log.info("Sending value -8, ArithmeticException should be thrown! Our Supervisor strategy says resume!")
    mesg = -8
    supervisor ! mesg
    future = (supervisor ? new Result).mapTo[Int]
    result = Await.result(future, timeout.duration)
    log.info("Value Received-> {}", result)


    //NullPointerException => restart => restart value 0 will be return
    log.info("Sending value null, NullPointerException should be thrown! Our Supervisor strategy says restart !")
    supervisor ! new NullPointerException
    future = (supervisor ? new Result).mapTo[Int]
    result = Await.result(future, timeout.duration)
    log.info("Value Received-> {}", result)


    // IllegalArgumentException => stop the actor
    log.info("Sending value \"String\", IllegalArgumentException should be thrown! Our Supervisor strategy says Stop !")
    supervisor ? "Do Something"
  }
}