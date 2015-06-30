package com.zipfworks.skeleton.spray

import akka.actor.ActorSystem

case class AppPackage(
  Config: AppConfig,
  System: ActorSystem,
  Databases: AppDatabases,
  Services: AppServices
)
