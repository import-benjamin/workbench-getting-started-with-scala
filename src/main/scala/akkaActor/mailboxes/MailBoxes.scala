package akkaActor.mailboxes

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.dispatch.{PriorityGenerator, UnboundedPriorityMailbox}
import com.typesafe.config.Config

object MailBoxes extends App {

  val system = ActorSystem("MailBoxExample")

  class SimpleActor extends Actor with ActorLogging {
    override def receive: Receive = {
      case message => log.info(message.toString)
    }
  }


  class CustomMailBox(settings: ActorSystem.Settings, config: Config) extends UnboundedPriorityMailbox(
    PriorityGenerator {
      case message: String if message.startsWith("URGENT") => 0
      case _: String => 1
      case _ => 2
    }
  )


  val actor = system.actorOf(Props[SimpleActor]().withDispatcher("my-dispatcher"))
  actor ! "General Kenobi, you are a bold one."
  actor ! 5
  actor ! "URGENT Hello, there!"

}
