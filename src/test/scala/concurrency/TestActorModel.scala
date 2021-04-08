package concurrency

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

// An actor can be interpreted as an object which is able to run asynchronously
// When using actors, scala will start a thread pool to execute actor.
// An actor can be run by only one thread at once.
class Boat extends Actor {
  var touche: Boolean = false
  def receive: Receive = {
    case "shoot" => touche = true
    case _ =>
  }
}

class SomeActor extends Actor {
  var count: Integer = 0

  def receive: Receive = {
    case incrementQt: Integer => 
      count = count + incrementQt
      println(s"I've counted $count")
    case _ => println("I'm lost.")
  }    
}


// If we want to use an actor with attribute, simply add it when declaring your class
class ActorWithAttribute(name: String) extends Actor {

  def receive: Receive = {
    case "hi" => println(s"hello, my name is $name")
    case "context" => println(s"$self is my context")
    case _ => println("hello")
  }
}

// When using actor with attr., we need to declare our actor like that :
// system.actorOf(Props(new ActorWithAttribute("tom")), name="myActor")
// But we tend to prefer the use of an object companion like the following

object ActorWithAttribute {
  def props(name: String): Props = Props(new ActorWithAttribute(name))
}
// then call is with system.actorOf(ActorWithAttribute.props("tom"))

// Stateless Actor with multiples handlers
class StatelessActor extends Actor {

  // default receive function to receiveA
  def receive: Receive = receiveA

  def receiveA: Receive = {
    // route receive function to receiveB
    case "toggle" => context.become(receiveB)
    // stackHandler won't replace existing handler but instead it will stack receiveB on top of receiveA
    case "stackHandler" => context.become(receiveB, discardOld = false)
    case "output" => println("receiveA")
  }

  def receiveB: Receive = {
    // route receive function to receiveA
    case "toggle" => context.become(receiveA)
    // when used with receiveA:stackHandler, the receive stack will pop our func and return its prior state (receiveA).
    case "stackHandler" => context.unbecome()
    case "output" => println("receiveB")
  }
}


class SimpleActorWithLogger extends Actor with ActorLogging {
  override def receive: Receive = {
    case message: String => log.info(s"Got $message")
    case _ =>
  }
}

class TestActorModel extends AnyFlatSpec with should.Matchers {
  "Basic Actor" should "be able to receive message" in {
    val system: ActorSystem = ActorSystem("Boat")
    val my_boat_actor = system.actorOf(Props[Boat](), name="boat")
    my_boat_actor ! "shoot"
  }

  "Using and Actor System" should "allow to run async" in {
    val system: ActorSystem = ActorSystem("MyActorSystem")
    println(system.name)
    
    val actor = system.actorOf(Props[SomeActor](), name="counter")
    actor ! 5
    actor ! 10
    // Messages can be of any type
    // a) must be immutable
    // b) must be serializable
  }

  "each actor as an ActorContext" should "provide information to the system and the actor itself" in {
    val system: ActorSystem = ActorSystem("mySystem")
    val myActor = system.actorOf(ActorWithAttribute.props("tom"), "tomActor")

    myActor ! "context"
  }
}
