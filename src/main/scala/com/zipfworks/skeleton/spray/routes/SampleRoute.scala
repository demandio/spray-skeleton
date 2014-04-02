package com.zipfworks.template.spray.routes

import spray.routing.{Route, Directives}

class SampleRoute extends Directives {

  val routes: Route = get {
    complete("Hello World!")
  }

}
