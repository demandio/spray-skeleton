package com.zipfworks.skeleton.spray.utils

import akka.actor.{Props, Actor}
import spray.routing._

class BasicHttpService(route: Route) extends Actor with HttpServiceBase {
  override def receive: Receive = runRoute(route)
}

object BasicHttpService {
  def props(route: Route): Props = Props(classOf[BasicHttpService], route)
}
