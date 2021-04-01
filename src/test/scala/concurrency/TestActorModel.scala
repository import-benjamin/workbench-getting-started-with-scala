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

class TestActorModel extends AnyFlatSpec with should.Matchers {
  "Basic Actor" should "be able to receive message" in {
    val system: ActorSystem = ActorSystem("Boat")
    val my_boat_actor = system.actorOf(Props[Boat], name="boat")
    my_boat_actor ! "shoot"
  }

  "Using and Actor System" should "allow to run async" in {
    val system: ActorSystem = ActorSystem("MyActorSystem")
    println(system.name)
    
    val actor = system.actorOf(Props[SomeActor], name="counter")
    actor ! 5
    actor ! 10

  }
}
