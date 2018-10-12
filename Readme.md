## AKKA ToolKit - Learning ##

### prerequisite ###
- Basic knowledge on Scala
- Basic knowledge on SBT

### What is Akka ###
- Akka is a tool kit and a open-source library written in Scala to create concurrent, distributed and fault-tolerant applications
- By using akka tool kit you can create application which are responsive, concurrent, distributed, event driven and fault-tolerant applications

### How does Akka works ###
- It implements Actor Based Model. 
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
```
    actorSystem.terminate()
```