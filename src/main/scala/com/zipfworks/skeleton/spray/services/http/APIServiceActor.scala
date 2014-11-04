package com.zipfworks.skeleton.spray.services.http

import spray.routing.{HttpServiceActor, Route}
import akka.actor.{Props, ActorLogging}

object APIServiceActor {
  def props(route: Route): Props = Props(classOf[APIServiceActor], route)
}

class APIServiceActor(route: Route) extends HttpServiceActor with ActorLogging {
  override def receive: Receive = runRoute(route)
}
