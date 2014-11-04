package com.zipfworks.skeleton.spray.datastore

import akka.actor.ActorSystem
import com.zipfworks.skeleton.spray.datastore.models.users.User
import com.zipfworks.sprongo.macros.{MacroDAO, MacroDB}

class MongoDB(db_urls: Seq[String], db_name: String)(implicit sys: ActorSystem)
  extends MacroDB(nodes = db_urls, db = db_name){

  object Users extends MacroDAO[User]("users")

}

