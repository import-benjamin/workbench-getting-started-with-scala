
package concurrency

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

object Echo {
  case class Ping(message: String, response: ActorRef[Pong])
  case class Pong(message: String)

  def apply(): Behavior[Ping] = Behaviors.receiveMessage {
    case Ping(m, replyTo) =>
      replyTo ! Pong(m)
      Behaviors.same
  }
}

class TestProperlyActor extends AnyFlatSpec with Matchers with BeforeAndAfterAll{
  val testKit: ActorTestKit = ActorTestKit()
  override def afterAll(): Unit = testKit.shutdownTestKit()

  "When using a probe" should "allow to run tests properly and expect responses from actor" in {
    val pinger = testKit.spawn(Echo(), "ping")
    val probe = testKit.createTestProbe[Echo.Pong]()
    pinger ! Echo.Ping("hello", probe.ref)
    probe.expectMessage(Echo.Pong("hello"))
  }
}

