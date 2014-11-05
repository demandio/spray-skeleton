package com.zipfworks.skeleton.spray.routes.api

import com.zipfworks.skeleton.spray.routes.api.users.UserRoutes
import spray.routing.{Directives, Route}

import scala.concurrent.ExecutionContext

class APIRoutes(implicit ec: ExecutionContext) extends Directives {

  val routes: Route = pathPrefix("api"){
    new UserRoutes().routes
  }

}
