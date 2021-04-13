package akkaActor.streams

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Sink, Source}


object Concepts extends App {
  implicit val system: ActorSystem = ActorSystem("mySystem")

  // define source
  val source: Source[Int, NotUsed] = Source(1 to 20)

  // define sink
  val sink = Sink.foreach[Int](println)

  // register our stream
  source
    .to(sink)
    .run()

  val flow = Flow[Int].map(x => x * x)

  source
    .via(flow)
    .to(sink)
    .run()

}
