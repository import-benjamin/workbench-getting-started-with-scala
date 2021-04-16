package akkaActor.http


import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._

import scala.concurrent.ExecutionContextExecutor

object AkkaAPI extends App {
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "serverSystem")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  val routes =
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Hello, world</h1>"))
      }
    }

  val serverSource = Http()(system).newServerAt("localhost", 8080).bind(routes)
}
