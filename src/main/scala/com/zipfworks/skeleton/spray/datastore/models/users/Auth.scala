package com.zipfworks.skeleton.spray.datastore.models.users

import reactivemongo.bson.Macros
import spray.json.DefaultJsonProtocol

case class Auth(
  token: String,
  userID: String,
  appDes: String
)

object Auth extends DefaultJsonProtocol {
  implicit val authHandler = Macros.handler[Auth]
  implicit val authJS = jsonFormat3(Auth.apply)
}
