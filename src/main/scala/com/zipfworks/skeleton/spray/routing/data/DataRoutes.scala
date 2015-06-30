package com.zipfworks.skeleton.spray.routing.data

import com.zipfworks.skeleton.spray.AppPackage
import com.zipfworks.skeleton.spray.utils.ExtendedDirectives
import spray.routing.Route

class DataRoutes(implicit App: AppPackage) extends ExtendedDirectives(App) {

  private val listRoute: Route = (
    get &
    pathEnd &
    DataListRequest.params
  ){ (request) => request.getResponse }

  val routes: Route = pathPrefix("data"){
    listRoute
  }

}
