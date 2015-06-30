package com.zipfworks.skeleton.spray.routing

import com.zipfworks.skeleton.spray.AppPackage
import com.zipfworks.skeleton.spray.routing.admin.AdminRoutes
import com.zipfworks.skeleton.spray.routing.data.DataRoutes
import com.zipfworks.skeleton.spray.utils.ExtendedDirectives
import spray.routing.Route

class DummyRoutes(implicit App: AppPackage) extends ExtendedDirectives(App) {

  val routes: Route = {
    new AdminRoutes().routes ~
    new DataRoutes().routes ~
    pathPrefix("dummy"){
      (get & pathEnd & complete)("Hello World!")
    }
  }

}
