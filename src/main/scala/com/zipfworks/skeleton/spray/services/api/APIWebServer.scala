package com.zipfworks.template.spray.services.api

import com.typesafe.config.Config
import akka.util.Timeout
import scala.concurrent.duration._
import akka.io.IO
import spray.can.Http
import akka.actor.{ActorRef, ActorSystem}
import akka.pattern.ask
import com.zipfworks.template.spray.routes.SampleRoute
import spray.routing.Route

class APIWebServer(config: Config, system: ActorSystem) {

  private val port = config.getInt("webserver.port")
  private implicit val bindTimeout: Timeout = config.getInt("webserver.bindTimeout").second

  val apiRoutes: Route = new SampleRoute().routes
  val apiServiceActor: ActorRef = system.actorOf(APIServiceActor.props(apiRoutes))
  val apiServer = IO(Http)(system).ask(Http.Bind(apiServiceActor, "0.0.0.0", port = port))

}
