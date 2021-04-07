package concurrency

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import akka.actor.{Actor, ActorSystem, Props}

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
  def props(name: String) = Props(new ActorWithAttribute(name))
}

// then call is with system.actorOf(ActorWithAttribute.props("tom"))

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
