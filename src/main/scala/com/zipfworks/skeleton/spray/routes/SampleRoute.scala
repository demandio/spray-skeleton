package com.zipfworks.skeleton.spray.routes

import com.zipfworks.skeleton.spray.routes.users.UserRoutes
import spray.routing.{Route, Directives}

import scala.concurrent.ExecutionContext

class SampleRoute(implicit ec: ExecutionContext) extends Directives {

  //root
  val routes: Route = {
    new UserRoutes().routes
  }

}
