package akkaRouter

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.routing.RoundRobinPool

object AutomaticRouter extends App {
  import manualRouter.Slave
  val system = ActorSystem("MySystem")

  val poolMaster = system.actorOf(RoundRobinPool(5).props(Props[Slave]), "simplePool")
  for (index <- 1 to 10) {
    poolMaster ! s"$index Hello, world!"
  }

}
