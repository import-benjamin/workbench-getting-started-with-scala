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

class TestActorModel extends AnyFlatSpec with should.Matchers {
  "Basic Actor" should "be able to receive message" in {
    val system: ActorSystem = ActorSystem("Boat")
    val my_boat_actor = system.actorOf(Props[Boat], name="boat")
    my_boat_actor ! "shoot"
  }
}
