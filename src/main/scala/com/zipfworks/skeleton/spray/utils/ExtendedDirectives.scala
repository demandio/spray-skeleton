package com.zipfworks.skeleton.spray.utils

import com.zipfworks.skeleton.spray.AppPackage
import spray.http.CacheDirectives._
import spray.http.HttpHeaders.`Cache-Control`
import spray.http._
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.json.JsObject
import spray.routing._

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.implicitConversions
import scala.util.{Failure, Success}

abstract class ExtendedDirectives(App: AppPackage) extends Directives {
  import App.System.dispatcher

  lazy val IdOrSlug: PathMatcher1[String] = PathMatcher("[a-z0-9\\-]+".r)
  lazy val IdOrUsername: PathMatcher1[String] = PathMatcher("(?!^self$)[A-Za-z0-9\\-]+".r)

  def respondWithCacheControl(maxAge: FiniteDuration = 1.hour, sMaxAge: FiniteDuration = 0.seconds): Directive0 = {
    respondWithHeader(`Cache-Control`(`public`, `max-age`(maxAge.toSeconds), `s-maxage`(sMaxAge.toSeconds)))
  }

  implicit def completeFuture(f: Future[Any]): Route = onComplete(f){
    case Success(js: JsObject) => complete(js)
    case Success((js: JsObject, cookie: HttpCookie)) => (setCookie(cookie) & complete)(js)
    case Success(err: ERROR)   => complete(err.code, err.body)
    case Success(unknown)      =>
      println(unknown)
      complete {val e = ERROR.internalServerError(Errors.UNKNOWN_RESPONSE_TYPE); (e.code, e.body)}
    case Failure(err)          => complete {val e = ERROR(err); (e.code, e.body)}
  }

}
