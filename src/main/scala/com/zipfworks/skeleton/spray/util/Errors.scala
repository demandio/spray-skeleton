package com.zipfworks.skeleton.spray.util

import spray.json.JsString

object Errors {

  lazy val EMAIL_ALREADY_EXISTS = "email" -> JsString("another user already has that email")
  lazy val EMAIL_INVALID_FORMAT = "email" -> JsString("needs to be a valid email")
  lazy val EMAIL_NOT_FOUND      = "email" -> JsString("email was not found")

  lazy val PASSWORD_TOO_WEAK = "password" -> JsString("does not meet password requirements")
  lazy val PASSWORD_INVALID  = "password" -> JsString("password is invalid")

}
