## AKKA ToolKit - Learning ##

### prerequisite ###
- Basic knowledge on Scala
- Basic knowledge on SBT

### What is Akka ###
- Akka is a tool kit and a open-source library written in Scala to create concurrent, distributed and fault-tolerant applications
- By using akka tool kit you can create application which are responsive, concurrent, distributed, event driven and fault-tolerant applications
- Akka can integrate to any JVM based language

### How does Akka works ###
- It implements Actor Based Model.
- Actor is an entity which communicates to other actor by message passing
- Actor has it's own state and behavior, like an object in OOP. 
- The Actor Model provides a higher level of abstraction for writing concurrent and distributed applications. 
- It helps to developer to deals with explicit locking and thread management. Akka makes it easier to write correct concurrent and parallel application.

### How to create a Sample Akka project ###
- Create a SBT project
- add library dependencies in build.sbt file
```
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion
)
```
- create a simple scala object file and create main method
```
    def main(args: Array[String]): Unit = {
        
    }
```
- create a actor by extending akka actor class, you have to override **receive** method from Actor trait like an abstract method in java
```
class MyActor extends Actor {
    override def receive: Receive = ???
}
```
- implement **receive** method in the Actor class, if actor receives String message print **Welcome to Actor example**, else print **Unknown message**
- This method is called each time a message is received by the actor
- The receive method does pattern matching on the received message and decide what to do.
```
class MyActor extends Actor {
    override def receive: Receive = {
        case msg : String => println(s"Welcome to Actor example ${msg}")
        case _ => println(s"Unknown message")
    }
}
```
- create an actor system - actor system is like a container for actors, it use factory pattern to create an actor
``` 
    val actorSystem = ActorSystem("ActorSystem"); 
```
- create an actor using actorsystem reference 
- to create an actor you can use method called actorOf, actor system create an actor and give the ref of actor to object
- actor system takes the Props object and name of the actor to create actor
```
    var actor = actorSystem.actorOf(Props[MyActor],"MyActor")
```
- actor is like a event handle component, we have to send a data to actor like indication to do a work
- sending message to actor is two types
    - tell ! => no reponse return (fire and forgot)
    - ask ? => waiting for reponse from actor
- actors contain mail box to keep the messages in the sequence
- we have to send the message/event in a known format to an actor using ask or tell operation 
```
    actor ! "Sample"
    actor ! 12.34
```
- once actor did it work we can close the complete actor system using terminate method
Note: if you don't terminate the actor system. it will be keep live
```
    actorSystem.terminate()
```
- In the above example, we have created an actor **MyActor** by extending Actor trait and overriding receive method.
- You must provide match cases for all received messages. In case, if there is unknown message, you need to provide a default case as we did in above example.
[For reference](https://github.com/dvinay/akka-beginner-learn-path/tree/master/akka-training-demo)

### What is Akka ActorSystem ###
- The ActorSystem is a root actor in actors structure, it's like a container for actors
- An ActorSystem is a hierarchical group of actors which share common configuration, e.g. dispatchers, deployments, remote capabilities and addresses.
- ActorSystem is the entry point for creating or looking up actors. 
- It is an abstract class which extends to ActorRefFactory trait.
- ActorSystem provides an actorOf() method which is used to create actor instance.

### Akka ActorSystem Components ###
- AKKa ActorSystem contains 6 components
    - Dead Letter Office
        - Messages which cannot be delivered will be delivered to an actor called deadLetters.
        - The main use of this facility is for debugging purpose, especially if a message sent by an actor does not arrive consistently. You can implement this by importing akka.actor.DeadLatter package.
    - User Guardian Actor
        - It is parent actor of actors created by user by using ActorSystem.
        - This special guardian is used to achieve an orderly shut-down sequence where logging remains active while all normal actors terminated. 
        - It monitors all user created actors.
    - System Guardian Actor
        - This actor works same as user guardian actor except that it works for system actors. 
        - The system guardian monitors the user guardian and initiate its own shut-down upon reception of the Terminated message.
    - Scheduler
        - Scheduler is a trait and extends to AnyRef. 
        - It is used to handle scheduled tasks. It provides the facility to schedule messages. 
        - You can schedule sending of messages and execution of tasks. It creates new instance for each ActorSystem for scheduling tasks to happen at specific time.
        - It returns a cancellable reference so that you can cancel the execution of the scheduled operation by calling cancel method on this reference object.
        - You can implement Scheduler by importing akka.actor.Scheduler package.
    - Event System
        - The Event System also known as eventStream.
        - It is a main event bus for each ActorSystem. It is used to carry log messages and dead latters. 
        - You can also used it to publish messages across entire ActorSystem. 
        - You can get eventStream reference by calling actorSystemRef.eventStream() method.
    - Configuration
        - ActorSystem provides a configuration component which is used to configure application. 
        - You can access configurations from your actor system
 
### AKKA Props class ###
- Props is a configuration class which is used to specify options while creating an actor. 
- It is immutable, so it is thread-safe and shareable.
- You can implement Props by importing akka.actor.Props package.
- You can create actor by passing a Props instance into the actorOf() factory method which is available in ActorSystem and ActorContext. 
- The actorOf() method returns an instance of ActorRef, it's a ref object for the actor. 
- This instance is immutable and has one to one relationship with the actor it represents. 
- ActorRef is also serializable so that you can serialize it.
