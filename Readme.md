# AKKA ToolKit - Learning #

## AKKA Beginners Learning ##

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
- receive method is a partial function in scala 
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
[ref](https://github.com/dvinay/akka-beginner-learn-path/commit/ccc462be18bae362b725ddd075e8b7fa6cc0a0bb)

### AKKA Child Actors creation ###
- Akka provides facility to create child actor. 
- You can create child actor by using implicit context reference. ActorSystem is used to create root-level or top-level actor.
- Akka provides you context so that you can create child actor also.
[ref](https://github.com/dvinay/akka-beginner-learn-path/commit/7be056047875683cf140c65797a92adc888c34bd)

### AKKA Actor life cycle ###
- Akka provides life cycle methods for Actor. we can override and provide specific implementation accordingly.
    - preStart()
        - It is invoked right after the starting of Actor and when an Actor is first created. In case of restart, it is called by postRestart() method.
    - postStop()
        - After stopping an actor, postStop() method is called. 
        - It is an asynchronous method. This method is used to release resources after stopping the Actor. 
        - It may be used for deregistering this Actor. 
        - Messages sent to a stopped actor will be redirected to the deadLetters of the ActorSystem.
    - preRestart(reason: Throwable, message: Option[Any])
        - Actor may be restarted in case an exception is thrown. 
        - When an actor is restarted, preRestart() method is invoked. 
        - The preRestart() method is called with the exception that caused the restart. By default it disposes of all children Actors and then calls postStop() method.
    - postRestart(reason: Throwable)
        - This method is invoked right after restarting of newly created Actor.
        - It is used to allow reinitialization after an Actor crash due to exception.
  
### AKKA Actor Communication ###
- tell (!)
    - It is used to send a message asynchronously, It does not wait and block thread for a message. 
    - If this method is invoked from within an Actor, the sending actor reference will be implicitly passed along with the message.
    - If this method is invoked from an instance that is not an Actor, the sender will be deadLetters actor reference by default.  
- ask (?)
    - ask is a pattern and involves Actors as well as Futures. 
    - future is a data structure used to retrieve the result of some concurrent operation.
    - Ask is used to sends a message asynchronously and it returns a Future which represents a possible reply. 
    - If the actor does not reply and complete the future, it will expire after the timeout period. 
    - After timeout period, it throws an TimeoutException.
- Reply
    - sender ! message
    - sender information can get by using context
    - if message has received from non-actor class or object, reply will send to DeadLetterOffice   
- Forward
    - actor.forward(message)
    - the incoming messages will get forwarded to the target actors. 
    - it is very important that the original sender reference is maintained and passed on to the target actors.

### How to stop AKKA Actor and ActorSystem ###
- we can stop akka parent actor using actorSystem.stop(actor) method by passing Root ActorRef object
- we can stop akka child actor using context.stop(childActor) method by passing child ActorRef object
- we can stop akka actor system using actorSystem.terminate() method, it will terminate all actors

## AKKA - Intermediate Learning ##

- Akka was originally created by Jonas Bonér and is currently available as part of the open source Typesafe Stack
- In the Actor Model, all objects are modeled as independent, computational entities that only respond to the messages received
- Actors change their state only when they receive a stimulus in the form of a message. So unlike the object-oriented world where the objects are executed sequentially, the actors execute concurrently.
- Akka actor system provides - Concurrency, Scalability, Fault tolerance, Event-driven architecture, Transaction support, Location transparency.

### Actor System & Actor ###
- The actor system is the container that manages the actor behavior, lifecycle, hierarchy, and configuration among other things. The actor system provides the structure to manage the application.
- Actor is modeled as the object that encapsulates state and behavior. All the messages intended for the actors are parked in a queue and actors process the messages from that queue.
- Akka provides multiple mailbox implementations. The mailboxes can be bounded or unbounded. 
- A bounded mailbox limits the number of messages that can be queued in the mailbox, meaning it has a defined or fixed capacity for holding the messages.
- akka actor location is transparent using ActorRef class
- Akka uses the URL convention to locate the actors. The default values are akka://hostname/ or akka://hostname:2552/ depending upon whether the application uses remote actors or not, to identify the application.

![AKKA system](https://github.com/dvinay/akka-beginner-learn-path/blob/master/resources/akka-actor%20system.png)

### Actor modules ###
- akka-actor => Standard actors, untyped actors
- akka-remote => Remote actors
- akka-slf4j => Simple Logging Facade for Java (SLF4J) event-handler listener for logging with SLF4J
- akka-testkit => Testing toolkit for actors
- akka-kernel => Microkernel for running a bare-bones mini application server
- akka-<storage-system>-mailbox => File-based Akka durable mailboxes
- akka-transactor => software transactional memory (STM) support
- akka-agent => STM agent support
- akka-dataflow => Oz-style dataflow concurrency support
- akka-camel => Apache Camel support
- akka-osgi => OSGI deployment support
- Akka-zeromq => ZeroMQ support

##### Sample Map reduce application #####
[ref] (https://github.com/dvinay/akka-beginner-learn-path/commit/cff22721bceb857b2b891543807620a5056a3f8d)

### Actor life cycle ###
- An actor's lifecycle broadly consists of three phases
    - Actor is initialized and started
    - Actor receives and processes messages by executing a specific behavior
    - Actor stops itself when it receives a termination message

### ActorSystem & Actor stop/Shutdown steps ###
- Actor stop takes the following steps, when actor receive a stop signal
    - Actor stops processing the mailbox messages.
    - Actor sends the STOP signal to all the children.
    - Actor waits for termination message from all its children.
    - Actor starts the self-termination process that involves the following:
        - Invoking the postStop() method
        - Dumping the attached mailbox
        - Publishing the terminated message on DeathWatch
        - Informing the supervisor about self-termination
- When the actor system calls the shutdown() method, this technique shuts down all the actors and the actor system.
- By sending a PoisonPill message to an actor—PoisonPill is like any message that goes and sits in the mailbox of the actor. A PoisonPill message is processed by initiating the shutdown of the actor.
- By calling context.stop(self) for stopping itself and calling context.stop(child) to stop the child actors.

- PoisonPill is a asynchronous way to shutdown the actor
- kill is a synchronous way. 
    - The killed actor sends ActorKilledException to its parent. 

```
    //first option of shutting down the actors by shutting down the ActorSystem
    system.shutdown()
    
    //second option of shutting down the actor by sending a poisonPill message
    actor ! PoisonPill
    
    //third option of shutting down the actor
    context.stop(self)
    //or
    context.stop(childActorRef)
    
    // Kill the actor, synchronous way
    actor ! Kill
```

### ActorSystem & Actor HotSwap ###
- HotSwap an actor's message loop functionality at runtime.
