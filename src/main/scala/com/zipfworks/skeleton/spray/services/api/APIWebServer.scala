package com.zipfworks.skeleton.spray.services.api

import akka.util.Timeout
import scala.concurrent.duration._
import akka.io.IO
import spray.can.Http
import akka.actor.{ActorRef, ActorSystem}
import akka.pattern.ask
import spray.routing.Route

/**********************************************************************************
 * Web Server
 * contains routing and the actor servicing http requests
 *********************************************************************************/
class APIWebServer(port: Int, timeout: Int, routes: Route)(implicit system: ActorSystem) {

  private implicit val bindTimeout: Timeout = timeout.second
  val apiServiceActor: ActorRef = system.actorOf(APIServiceActor.props(routes))
  val apiServer = IO(Http)(system).ask(Http.Bind(apiServiceActor, "0.0.0.0", port = port))

}
