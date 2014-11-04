package com.zipfworks.skeleton.spray.routes

import spray.routing.{Route, Directives}

class SampleRoute extends Directives {

  //root
  val routes: Route = get {
    complete("Hello World!")
  }

}
