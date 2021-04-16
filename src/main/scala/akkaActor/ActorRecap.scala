package akkaActor

import akka.actor.{Actor, ActorLogging, ActorSystem, PoisonPill, Props, Stash}

object ActorRecap extends App {

  class SimpleActor extends Actor with ActorLogging {
    override def receive: Receive = {
      case stringMessage: String => log.info(s"$self got $stringMessage")
    }
  }

  object StateFullActorMessages {
    case class StashMessage(message: String)
    case class SwitchState()
    case class BlankMessage()
  }

  class StateFullActor extends Actor with ActorLogging with Stash {
    import akkaActor.ActorRecap.StateFullActorMessages.{StashMessage, SwitchState}

    override def receive: Receive = stateA

    def stateA: Receive = {
      case StashMessage(_) => stash()
      case SwitchState() =>
        unstashAll()
        context.become(stateB)
      case _ => log.info("I'm in state A")
    }

    def stateB: Receive = {
      case SwitchState() => context.unbecome()
      case StashMessage(message) => log.info(s"$message")
      case message: String => log.info(s"$self got $message")
      case _ => log.info("I'm in state B")
    }
  }

  import akkaActor.ActorRecap.StateFullActorMessages.{StashMessage, SwitchState, BlankMessage}
  val system = ActorSystem("ActorRecap")
  val stateActor = system.actorOf(Props[StateFullActor](), name = "stateActor")

  stateActor ! StashMessage("Bonjour acteur")
  stateActor ! StashMessage("Sauvegarde ce message")
  stateActor ! SwitchState()
  stateActor ! BlankMessage()
  stateActor ! "Hello actor"
  stateActor ! SwitchState()
  stateActor ! BlankMessage()
  stateActor ! PoisonPill

  system.terminate()
}
