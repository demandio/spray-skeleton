package com.zipfworks.skeleton.spray.datastore.models

import java.util.UUID
import com.zipfworks.sprongo.macros.ExtendedMacroHandlers
import reactivemongo.bson.Macros


case class Auth(
  token: String,
  userID: String,
  appDes: String
)

object Auth {
  implicit val authHandler = Macros.handler[Auth]
}


case class User(
  email: String,
  auth: List[Auth],
  _id: UUID = UUID.randomUUID()
)

object User extends ExtendedMacroHandlers {
  implicit val userHandler = Macros.handler[User]
}

