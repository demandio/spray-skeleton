package com.zipfworks.skeleton.spray.routes

import com.zipfworks.skeleton.spray.routes.api.APIRoutes
import com.zipfworks.skeleton.spray.routes.web.WebRoutes
import com.zipfworks.skeleton.spray.util.{IEDirectives, CORSDirectives}
import spray.routing.{Directives, Route}
import scala.concurrent.ExecutionContext

class ServerRoutes(implicit ec: ExecutionContext)
  extends Directives
  with CORSDirectives
  with IEDirectives
{
  val routes: Route =
    xdReceiverRoute ~
    nocache {
      cors {
        new APIRoutes().routes ~
        new WebRoutes().routes
      }
    }
}
