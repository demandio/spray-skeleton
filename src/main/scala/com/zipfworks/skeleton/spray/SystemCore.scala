package com.zipfworks.skeleton.spray

import akka.actor.ActorSystem

trait SystemCore {
  this: SystemConfig =>

  implicit lazy val system = ActorSystem("ss-api", loadedConfig)

}
