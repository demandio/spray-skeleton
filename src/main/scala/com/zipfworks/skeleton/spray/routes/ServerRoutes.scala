package com.zipfworks.skeleton.spray.routes

import com.zipfworks.skeleton.spray.routes.api.APIRoutes
import com.zipfworks.skeleton.spray.routes.web.WebRoutes
import com.zipfworks.skeleton.spray.util.{CacheDirectives, XDReceiver, CORSDirectives}
import spray.http.StatusCodes
import spray.routing.{Directives, Route}
import scala.concurrent.ExecutionContext

class ServerRoutes(implicit ec: ExecutionContext)
  extends Directives
  with CORSDirectives
  with CacheDirectives
  with XDReceiver
{
  val routes: Route =
    xdReceiverRoute ~
    (nocache & respondWithCORS){
      (options & complete(StatusCodes.NoContent)) ~   //OPT   ---       pre-flight requests
      new APIRoutes().routes ~                        //---   /api
      new WebRoutes().routes                          //---   /web
    }
}
