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
[ref](https://github.com/dvinay/akka-beginner-learn-path/commit/ffd9a82b1683b31058900c4a2d452d2c0fe640d5#diff-68f8e00b1e6e805bad13dd74712f1d82)
```
case class PING()
case class PONG()
 class PingPongActor extends Actor {
  import context._
  var count = 0
  def receive: Receive = {
    case PING =>
      println("PING")
      count = count + 1
      Thread.sleep(100)
      self ! PONG
      become {
        case PONG =>
          println("PONG")
          count = count + 1
          Thread.sleep(100)
          self ! PING
          unbecome()
      }
      if(count > 10) context.stop(self)
  }
}
```

### Actors Type ###
- Untype Actor
    - Untyped actors respond to messages sent
- Type Actor
    - typed actors respond to method calls
    - A typed actor has two parts—a publicly defined interface, and secondly, an implementation of the interface.
    - Calls to the publicly defined interface are delegated asynchronously to the private instance of the implementation
    - The public interface of typed actor provides the service contract that bridges the Actor Model to the object-oriented paradigm
- Active Object Pattern
    - The Active Object Design pattern decouples method execution from method invocation, which reside in their own threads of control. 
    - The goal is to introduce concurrency and fault tolerance, by using asynchronous method invocation and a scheduler for handling requests.
    - The Active Object pattern uses the proxy pattern (interface) to separate the interface and implementation of the object.
![AKKA Typed Actor](https://github.com/dvinay/akka-beginner-learn-path/blob/master/resources/typed%20actor%20flow.png)

#### How to create a typed Actor ####
- create a trait
```
trait CalculatorInt {
    def add(first: Int, second: Int): Future[Int]
    def subtract(first: Int, second: Int): Future[Int]
    def incrementCount(): Unit
    def incrementAndReturn(): Option[Int]
}
```
- create implementation for trait
```
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
```
- Creating an actor and invoking using Actor Object pattern
```
    val _system = ActorSystem("TypedActorsExample")
    val calculator1: CalculatorInt = TypedActor(_system).typedActorOf(TypedProps[Calculator]())
```
- Invoke any method in the Typed Actor
```
    calculator1.incrementCount() // function invoke

    // like ask - expecting asynch response
    val future = calculator1.add(14,6);
    val result = Await.result(future, 5 second);

    //Method invocation in a blocking way
    val response = calculator1.incrementAndReturn()
```
- to stop a typed actor, can send Stop or PoisonPill method on the TypedActor extension and passing the reference of the dynamic proxy instance
```
    //To shut down the typed actor, call the stop method
    TypedActor(system).stop(calculator1)
    
    //Other way to stop the actor is invoke the Poisonpill method
    TypedActor(system).poisonPill(calculator1)
```
- typed actors, additional hooks can be implemented by making the implementation class implement additional interfaces. These interfaces can be overridden to initialize resources on actor start and clean up resources on actor stop.
```
    class Calculator extends CalculatorInt with PreStart 
                        with PostStop {
        import TypedActor.context
        val log = Logging(context.system, TypedActor.self.getClass())
        def preStart(): Unit = {
             log.info ("Actor Started")
        }
    
        def postStop(): Unit = {
             log.info ("Actor Stopped")
        }
    }
```
- to provide an arbitrary message handling in typed actor
- Typed actors can implement the akka.actor.TypedActor.Receiver interface in order to process messages coming to them. 
- Now, the typed actor can handle the arbitrary messages in addition to the method calls. 
- Adding this interface is useful when the typed actor is managing standard child actors and it wants to be notified of their termination (DeathWatch).
```
class Calculator extends CalculatorInt {
    import TypedActor.context
    val log = Logging(context.system, TypedActor.self.getClass())
    def onReceive(message: Any, sender: ActorRef): Unit = {
        log.info("Message received->{}", message)
    }
}
```
- to use tell or ask approach, ActorRef object is required. In typed actor we can get ActorRef using getActorRefFor()
```
val _system = ActorSystem("TypedActorsExample")

val calculator: CalculatorInt =
TypedActor(_system).typedActorOf(TypedProps[Calculator]())
    
//Get access to the ActorRef
val calActor:ActorRef = TypedActor(_system)
                      .getActorRefFor(calculator)

//pass a message 
calActor.tell("Hi there")
```  
- To supervise the typed actor
```
class Calculator extends CalculatorInt with Supervisor {

  def supervisorStrategy(): SupervisorStrategy = 
    OneForOneStrategy(maxNrOfRetries = 10, 
           withinTimeRange = 10 seconds) {
    case _: ArithmeticException => Resume
    case _: IllegalArgumentException => Restart
    case _: NullPointerException => Stop
    case _: Exception => Escalate
  }
}
```
- To create a child actor in the typed actor
```
import TypedActor.context
//create a child actor under the Typed Actor context
val childActor:ActorRef = context.actorOf(Props[ChildActor],name="childActor")
```

#### Dispatchers and Routers ####
- Dispatcher 
    - Dispatcher is the engine that powers the Akka application, control the flow of execution.
    - The dispatchers run on their threads; they dispatch the actors and messages from the attached mailbox and allocate on heap to the executor threads.
    - Based on the dispatching policy, dispatchers will route the incoming message or request to the business process.
    - dispatchers are based on the Java Executor framework. 
    - It is based on the producer–consumer model, meaning the act of task submission (producer) is decoupled from the act of task execution (consumer). The threads that submit tasks are different from the threads that execute the tasks.
- Routers
    - route incoming messages to outbound actors.

- Dispatchers are 4 types

    - Dispatcher
        - This is the default dispatcher used by the Akka application in case there is nothing defined.
        - This is an event-based dispatcher that binds a set of actors to a thread pool backed up by a BlockingQueue method.
        - Every actor is backed by its own mailbox
        - The dispatcher can be shared with any number of actors
        - The dispatcher can be backed by either thread pool or fork join pool
        - The dispatcher is optimized for non-blocking code
        
    - Pinned dispatcher
        - This dispatcher provides a single, dedicated thread (pinned) for each actor. 
        - This dispatcher is useful when the actors are doing I/O operations or performing long-running calculations. 
        - The dispatcher will deallocate the thread attached to the actor after a configurable period of inactivity.
        - Every actor is backed by its own mailbox.
        - A dedicated thread for each actor implies that this dispatcher cannot be shared with any other actors.
        - The dispatcher is backed by the thread pool executor.
        - The dispatcher is optimized for blocking operations. For example, if the code is making I/O calls or database calls, then such actors will wait until the task is finished. For such blocking operation, the pinned dispatcher performs better than the default dispatcher.
        
    - Balancing dispatcher
        - It is an event-based dispatcher that tries to redistribute work from busy actors and allocate it to idle ones. 
        - Redistribution of tasks can only work if all actors are of the same type (requirement).
        - This task redistribution is similar to the work-stealing technique
        - There is only one mailbox for all actors
        - The dispatcher can be shared only with actors of the same type
        - The dispatcher can be backed by a either thread pool or fork join pool
        
    - Calling thread dispatcher
        - The calling thread dispatcher is primarily used for testing. 
        - This dispatcher runs the task execution on the current thread only. 
        - It does not create any new threads and provides a deterministic execution order.
        - Every actor is backed by its own mailbox
        - The dispatcher can be shared with any number of actors
        - The dispatcher is backed by the calling thread
 
- Mailbox are 4 types
    - Unbounded mailbox
    - Bounded mailbox
    - Unbounded priority mailbox
    - Bounded priority mailbox
![Mail box types](https://github.com/dvinay/akka-beginner-learn-path/blob/master/resources/mail%20box%20types.png)

- Akka supports two Executor contexts
    - Thread pool executor: 
        - is to create a pool of worker threads. Tasks are assigned to the pool using a queue. If the number of tasks exceeds the number of threads, then the tasks are queued up until a thread in the pool is available. Worker threads minimize the overhead of allocation/deallocation of threads.
    - Fork join executor: 
        - This is based on the premise of divide-and-conquer. The idea is to divide a large task into smaller tasks whose solution can then be combined for the final answer. The tasks need to be independent to be able run in parallel.

- To configure Thread pool executor we need to pass min, max and factors for threads count
```
# Configuration for the thread pool
  thread-pool-executor {
    # minimum number of threads 
    core-pool-size-min = 2
    # available processors * factor
    core-pool-size-factor = 2.0
    # maximum number of threads 
    core-pool-size-max = 10
  }
```
- To configure Fork-Join executor we need to pass min, max and factors for threads count
```
# Configuration for the fork join pool
  fork-join-executor {
    # Min number of threads 
    parallelism-min = 2
    # available processors * factor
    parallelism-factor = 2.0
    # Max number of threads 
    parallelism-max = 10
}
```
- To configure the dispatcher, we need to pass following conf in application.conf file
    - Negative (or zero) implies usage of an unbounded mailbox (default). A positive number implies bounded mailbox and with the specified size.
    - Bounded or unbounded mailbox used if nothing is specified (dependent on mailbox capacity) or FQCN of the mailbox implementation
```
my-dispatcher {

  type = Dispatcher

  executor = "fork-join-executor"
  
  fork-join-executor {
    parallelism-min = 2
    parallelism-factor = 2.0
    parallelism-max = 10
  }
  throughput = 100

  mailbox-capacity = -1

  mailbox-type =""
}
```
- pinned dispatcher can be configured in application.conf file by using following configuration
```
my-dispatcher {

  type = Dispatcher

  executor = "fork-join-executor"
  
  fork-join-executor {
    parallelism-min = 2
    parallelism-factor = 2.0
    parallelism-max = 10
  }
  throughput = 100

  mailbox-capacity = -1

  mailbox-type =""
}
```
- To load the application.conf dispatcher to the application, we have to pass along with Props
```
val _system = ActorSystem("dispatcher", ConfigFactory.load().getConfig("MyDispatcherExample"))

val actor = _system.actorOf(Props[MsgEchoActor].withDispatcher("my-dispatcher"))
```
### Routers ###
- the router actors are of a special type—RouterActorRef. 
- RouterActorRef does not make use of the store-and-forward mechanism. Instead, routers dispatch the incoming messages directly to the routee's mailboxes and avoid the router's mailbox.
- Routers are  5 basic types
    - Round robin router: 
        - It routes the incoming messages in a circular order to all its routees
    - Random router: 
        - It randomly selects a routee and routes the message to the same
    - Smallest mailbox router: 
        -It identifies the actor with the least number of messages in its mailbox and routes the message to the same
    - Broadcast router: 
        - It forwards the same message to all the routees
    - Scatter gather first completed router: 
        - It forwards the message to all its routees as a future, then whichever routee actor responds back, it takes the results and sends them back to the caller
        
#### Routers config in application code ####
- e.g for RoundRobinRouter
```
val router = system.actorOf(Props[MyActor].withRouter(RoundRobinRouter(nrOfInstances = 5)) , name = "myRouterActor")
```
- Here MyActor router acts like a supervisors for the Routees.
- Note: ScatterGatherFirstCompletedRouter requires the timeout parameter while creating router actor

#### Routers config in application.conf file ####
- We can configure the routers using application.conf
```
MyRouterExample{
    akka.actor.deployment {
      /myRandomRouterActor {
        router = random
        nr-of-instances = 5
      }
    }
}
```
- To load the routers information from the application.conf file 
```
val _system = ActorSystem.create("RandomRouterExample", ConfigFactory.load()
            .getConfig("MyRouterExample"))

val randomRouter = _system.actorOf(Props[MsgEchoActor].withRouter(FromConfig()), name = "myRandomRouterActor")
```

#### Routers config in application code - In distributed application ####
- In case, in a distributed application. we have to configure the routers by adding address
```
val addresses = Seq( Address("akka", "remotesys", "host1", 1234),Address("akka", "remotesys", "host2", 1234))
  
val routerRemote = system.actorOf(Props[MyEchoActor].withRouter(
  RemoteRouterConfig(RoundRobinRouter(5), addresses)))
```
#### Routers config in application.conf file - In distributed application ####
```
MyRouterExample{
    akka.actor.deployment {
      /myRandomRouterActor  {
        router = round-robin
        nr-of-instances = 5
        target {
              nodes = ["akka://app@192.168.0.5:2552", "akka://app@192.168.0.6:2552"]
            }
      }
    }
}
```
- To load the routers information from the application.conf file 
```
val _system = ActorSystem.create("RandomRouterExample", ConfigFactory.load()
            .getConfig("MyRouterExample"))

val randomRouter = _system.actorOf(Props[MsgEchoActor].withRouter(FromConfig()), name = "myRandomRouterActor")
```

#### Dynamically resizing routers ####
#### Routers config in application code ####
- In case, in a distributed application. we have to configure the routers by adding address
```
val resizer = DefaultResizer(lowerBound = 2, upperBound = 15)

val randomRouter = system.actorOf(Props[MsgEchoActor].withRouter(
  RandomRouter (resizer = Some(resizer))))
```
#### Routers config in application.conf file ####
```
MyRouterExample{
    akka.actor.deployment {
      /myRandomRouterActor  {
        router = round-robin
        nr-of-instances = 5
        resizer {
          lower-bound = 2
          upper-bound = 15
        }
      }
    }
}
```
- To load the routers information from the application.conf file 
```
val _system = ActorSystem.create("RandomRouterExample", ConfigFactory.load()
            .getConfig("MyRouterExample"))

val randomRouter = _system.actorOf(Props[MsgEchoActor].withRouter(FromConfig()), name = "myRandomRouterActor")
```

- Note: Akka allows creation of **custom router** using **RouterConfig interface**

### Supervision and Monitoring ###
- To help manage the fault tolerance and manage the actors, Akka provides a concept called supervisors.
- Akka by default provides a parental supervisor – "user". 
- This parental supervisor creates the rest of the actors and the actor hierarchy.
- When the supervisor is informed of the failure of a Subordinate actor, 4 possible choices for the supervisor
  - Restart the Subordinate actor means kill the current actor instance and instantiate a new actor instance.
  - Resume the Subordinate actor means the actor keeps its current state and goes back to its current state as though nothing has happened.
  - Terminate the Subordinate actor permanently.
  - Escalate the failure to its own supervisor.

  
#### Supervision strategies ####
- One-For-One strategy
    - strategy means the supervision strategy is applied only to the failed child
    - default strategy if a strategy is not defined explicitly
    - akka.actor.OneForOneStrategy
    - OneForOneStrategy(maxNrOfRetries: int, withinTimeRange: Duration, decider: Decider)
```
override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 10 seconds) {
    case _: ArithmeticException => Resume
    case _: NullPointerException => Restart
    case _: IllegalArgumentException => Stop
    case _: Exception => Escalate
}
```
[ref](https://github.com/dvinay/akka-beginner-learn-path/tree/master/akka-taining-essentials/src/main/scala/akka/supervisor/learning/oneforonestrategy)
- All-For-One strategy
    - the supervision strategy is applied to all the actor siblings as well
    - akka.actor.AllForOneStrategy
    - AllForOneStrategy(maxNrOfRetries: int, withinTimeRange: Duration, decider: Decider)
```
override val supervisorStrategy = OneForAllStrategy(maxNrOfRetries = 10, withinTimeRange = 10 seconds) {
    case _: ArithmeticException => Resume
    case _: NullPointerException => Restart
    case _: IllegalArgumentException => Stop
    case _: Exception => Escalate
}
```
[ref](https://github.com/dvinay/akka-beginner-learn-path/tree/master/akka-taining-essentials/src/main/scala/akka/supervisor/learning/allforonestrategy)

- maxNrOfRetries: This defines the number of times an actor is allowed to be restarted before it is assumed to be dead. 
    A negative number implies no limits.
- withinTimeRange: This defines the duration of the time window for maxNrOfRetries. 
    The value Duration.Inf means no window defined.
- decider: This is the function defined where the Throwable are mapped to the directives that allow us to specify the actions resume(), restart(), stop(), or escalate()

#### LifeCycle Monitoring ####

To do:
























