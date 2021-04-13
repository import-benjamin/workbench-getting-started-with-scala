package akkaActor.router

import akka.actor.{Actor, ActorLogging, Props}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}

object manualRouter extends App {

  class Master extends Actor {

    private val slaves: IndexedSeq[ActorRefRoutee] = for (index <- 1 to 5) yield {
      val slave = context.actorOf(Props[Slave], s"slave$index")
      context.watch(slave)
      ActorRefRoutee(slave)
    }

    private val router = Router(RoundRobinRoutingLogic(), slaves)

    override def receive: Receive = {
      case message => router.route(message, sender())
    }
  }

  class Slave extends Actor with ActorLogging {
    override def receive: Receive = {
      case message => log.info(s"Receive $message")
    }
  }


}
