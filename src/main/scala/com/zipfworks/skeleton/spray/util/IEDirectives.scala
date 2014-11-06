package com.zipfworks.skeleton.spray.util

import com.zipfworks.skeleton.spray.Controller
import spray.http.CacheDirectives.{`max-age`, `must-revalidate`, `no-cache`, `public`}
import spray.http.HttpHeaders.`Cache-Control`
import spray.routing.{Route, Directives, Directive0}
import spray.http.MediaTypes.`text/html`

trait IEDirectives {
  this: Directives =>

  private final val defaultMaxAge = 604800

  //default to NOT CACHE the routes
  val nocache: Directive0 =
    respondWithSingletonHeader(`Cache-Control`(`no-cache`, `must-revalidate`, `max-age`(0)))

  //XDReceiver
  val xdReceiverRoute: Route = (
    get
      & path("xdreceiver.html")
      & respondWithHeader(`Cache-Control`(`public`, `max-age`(defaultMaxAge)))
      & respondWithMediaType(`text/html`)
      & complete
  ){
    s"""<?xml version="1.0" encoding="UTF-8"?>
      |<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
      |<html>
      |  <body>
      |    <script>document.domain='${Controller.SERVER_XDRECEIVER}'</script>
      |  </body>
      |</html>""".stripMargin
  }

}
