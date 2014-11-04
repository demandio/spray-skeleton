package com.zipfworks.skeleton.spray.util

import spray.json.JsString

object Errors {

  lazy val EMAIL_ALREADY_EXISTS = "email" -> JsString("another user already has that email")
  lazy val EMAIL_INVALID_FORMAT = "email" -> JsString("needs to be a valid email")
  lazy val PASSWORD_TOO_WEAK = "password" -> JsString("does not meet password requirements")

}
