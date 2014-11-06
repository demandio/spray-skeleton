package com.zipfworks.skeleton.spray.util

import com.zipfworks.skeleton.spray.Controller
import spray.http._
import spray.http.HttpHeaders._
import spray.routing.Directives
import spray.http.SomeOrigins

trait CORSDirectives {
  this: Directives =>

  private final val allowedMethods = Seq[HttpMethod](
    HttpMethods.GET,
    HttpMethods.POST,
    HttpMethods.PUT,
    HttpMethods.DELETE,
    HttpMethods.OPTIONS
  )

  private def allowedOrigins(clientOrigin: String) = {
    val originsSeq = Seq(Controller.SERVER_CORS_ORIGINS.find(_ == clientOrigin)).flatten.toSeq
    SomeOrigins(originsSeq.map(HttpOrigin.apply))
  }

  private def corsHeaders(clientOrigin: String) = List[HttpHeader](
    `Access-Control-Allow-Origin`(allowedOrigins(clientOrigin)),
    `Access-Control-Allow-Headers`(Controller.SERVER_CORS_HEADERS),
    `Access-Control-Allow-Methods`(allowedMethods),
    `Access-Control-Allow-Credentials`(allow = true)
  )

  val respondWithCORS = optionalHeaderValueByName("Origin").flatMap({
    case Some(origin) => respondWithHeaders(corsHeaders(origin))
    case None         => noop
  })

}


