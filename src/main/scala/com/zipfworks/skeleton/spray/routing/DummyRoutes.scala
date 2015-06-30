package com.zipfworks.skeleton.spray.routing

import com.zipfworks.skeleton.spray.AppPackage
import com.zipfworks.skeleton.spray.utils.ExtendedDirectives
import spray.routing.Route

class DummyRoutes(implicit App: AppPackage) extends ExtendedDirectives(App) {

  val routes: Route = pathPrefix("dummy"){
    (get & pathEnd & complete)("Hello World!")
  }

}
