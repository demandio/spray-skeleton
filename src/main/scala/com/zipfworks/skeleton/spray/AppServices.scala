package com.zipfworks.skeleton.spray

import akka.actor.ActorSystem

case class AppServices(config: AppConfig, databases: AppDatabases)(implicit system: ActorSystem){


}
