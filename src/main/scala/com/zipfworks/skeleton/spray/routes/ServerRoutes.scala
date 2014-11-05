package com.zipfworks.skeleton.spray.routes

import com.zipfworks.skeleton.spray.routes.api.APIRoutes
import com.zipfworks.skeleton.spray.routes.web.WebRoutes
import spray.routing.{Directives, Route}
import scala.concurrent.ExecutionContext

class ServerRoutes(implicit ec: ExecutionContext) extends Directives {

  val routes: Route = {
    new APIRoutes().routes ~
    new WebRoutes().routes
  }

}
