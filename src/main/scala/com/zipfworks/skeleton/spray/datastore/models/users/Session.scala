package com.zipfworks.skeleton.spray.datastore.models.users

import java.util.UUID

import com.zipfworks.sprongo.ExtendedJsonProtocol
import com.zipfworks.sprongo.macros.ExtendedMacroHandlers
import org.joda.time.DateTime
import reactivemongo.bson.Macros
import spray.json.DefaultJsonProtocol

case class Session(
  id: UUID = UUID.randomUUID(),
  expiresOn: DateTime
)

object Session extends ExtendedMacroHandlers with ExtendedJsonProtocol {
  implicit val sessionHandler = Macros.handler[Session]
  implicit val sessionJS = jsonFormat2(Session.apply)
}

