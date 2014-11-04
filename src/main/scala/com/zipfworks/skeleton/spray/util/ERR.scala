package com.zipfworks.skeleton.spray.util

import spray.http.{StatusCode, StatusCodes}
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.json._

class ERR(code: StatusCode, errors: Seq[(String, JsValue)]) extends Exception {

  override def getMessage: String = {
    errors.map({case (key, value) => key + ":" + value}).mkString("|")
  }

  def error: (StatusCode, Map[String, JsValue]) = {
    val body = JsObject(Map(
      "error" -> JsBoolean(x = true),
      "code"  -> JsNumber(code.intValue)
    ) ++ errors)

    (code, Map("meta" -> body))
  }

  def complete: spray.routing.Route = spray.routing.Directives.complete(error)
}

object ERR {

  def badRequest(errors: (String, JsValue)*): ERR = new ERR(StatusCodes.BadRequest, errors)
  def notFound(errors: (String, JsValue)*): ERR = new ERR(StatusCodes.NotFound, errors)
  def internalServerError(errors: (String, JsValue)*): ERR = new ERR(StatusCodes.InternalServerError, errors)

  def apply(code: StatusCode, errors: (String, JsValue)*): ERR = new ERR(code, errors)
  def apply(e: Throwable): ERR = e match {
    case e: ERR => e
    case _ => new ERR(StatusCodes.InternalServerError, Seq(
      "exception"  -> JsString(e.getMessage),
      "stackTrace" -> JsArray(e.getCause.getStackTrace.map(e => JsString(e.toString)): _*)
    ))
  }
}

