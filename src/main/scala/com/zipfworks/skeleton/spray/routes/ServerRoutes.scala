package com.zipfworks.skeleton.spray.routes

import com.zipfworks.skeleton.spray.routes.api.APIRoutes
import com.zipfworks.skeleton.spray.routes.web.WebRoutes
import com.zipfworks.skeleton.spray.util.ExtendedDirectives
import spray.routing.{Directives, Route}
import scala.concurrent.ExecutionContext

class ServerRoutes(implicit ec: ExecutionContext) extends ExtendedDirectives {

  val routes: Route = nocache {
    new APIRoutes().routes ~
    new WebRoutes().routes
  }

}
