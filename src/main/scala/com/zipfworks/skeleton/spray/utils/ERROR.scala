package com.zipfworks.skeleton.spray.utils

import spray.http.{StatusCodes, StatusCode}
import spray.json._
import DefaultJsonProtocol._

case class ERROR(code: StatusCode, errors: Map[String, JsValue]) extends Exception {

  def body: JsObject = JsObject(
    "isError" -> JsBoolean(x = true),
    "code"    -> JsNumber(code.intValue),
    "errors"  -> JsObject(errors)
  )

}

object ERROR {
  def apply(str: String): ERROR = {
    val fields = str.parseJson.asJsObject.fields
    val code = StatusCode.int2StatusCode(fields("code").convertTo[Int])
    val errors = fields("errors").convertTo[Map[String, JsValue]]
    ERROR(code, errors)
  }

  def apply(t: Throwable): ERROR = t match {
    case err: ERROR => err
    case err: Throwable =>
      ERROR(
        code   = StatusCodes.InternalServerError,
        errors = Map(t.getMessage -> JsArray(t.getStackTrace.map(e => JsString(e.toString)).toVector))
      )
  }

  def internalServerError(errors: Seq[(String, JsValue)]): ERROR = ERROR(StatusCodes.InternalServerError, errors.toMap)
  def internalServerError(error: (String, JsValue)): ERROR = {
    println(error)
    ERROR(StatusCodes.InternalServerError, Map(error))
  }

  def badRequest(errors: Seq[(String, JsValue)]): ERROR = ERROR(StatusCodes.BadRequest, errors.toMap)
  def badRequest(error: (String, JsValue)): ERROR = ERROR(StatusCodes.BadRequest, Map(error))

  def notFound(errors: Seq[(String, JsValue)]): ERROR = ERROR(StatusCodes.NotFound, errors.toMap)
  def notFound(error: (String, JsValue)): ERROR = ERROR(StatusCodes.NotFound, Map(error))
}

object Errors {
  val UNKNOWN_RESPONSE_TYPE = "unknown-response-type" -> JsString("unknown reponse type could not be marshalled")
}

