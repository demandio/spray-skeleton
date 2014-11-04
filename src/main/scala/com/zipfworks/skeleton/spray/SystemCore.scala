package com.zipfworks.skeleton.spray

import akka.actor.ActorSystem
import com.zipfworks.skeleton.spray.datastore.MongoDB

/**********************************************************************************
 * System Core
 * Contains the ActorSystem and Database Driver instantiation
 *
 * Note that things declared here are instantiated AFTER SystemConfig
 *
 * In keeping with the GLOBAL_VARIABLE convention, things that are accessible
 * globally will be ALL_CAPS
 *********************************************************************************/
trait SystemCore {
  this: SystemConfig =>

  implicit lazy val SYSTEM  = ActorSystem("ss-api", LOADED_CONFIG)
  implicit lazy val MONGODB = new MongoDB(DB_URLS, "spray-skeleton")

}
