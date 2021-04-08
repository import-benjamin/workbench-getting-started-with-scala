package concurrency

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class DummyActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case message: String => log.debug("Got {}", message)
    case _ =>
  }
}

class ConfigureActor extends AnyFlatSpec with should.Matchers {
  "Using string config for actor" should "be allowed by ConfigFactory" in {
    val stringConfiguration: String =
      """
        | akka {
        |   loglevel = "DEBUG"
        | }
        |""".stripMargin

    val akkaConfiguration: Config = ConfigFactory.parseString(stringConfiguration)
    val system: ActorSystem = ActorSystem("dummySystem", ConfigFactory.load(akkaConfiguration))

    val actor: ActorRef = system.actorOf(Props[DummyActor](), "dummyActor")
    actor ! "Hello, there"


  }
}
