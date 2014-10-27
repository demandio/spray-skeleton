package com.zipfworks.skeleton.spray

import com.typesafe.config.ConfigFactory

trait SystemConfig {

  val loadedConfig = sys.env.getOrElse("ENVIRONMENT", "LOCAL").toLowerCase match {
    case "production" => ConfigFactory.load.getConfig("production")
    case "staging" => ConfigFactory.load.getConfig("staging")
    case "local" => ConfigFactory.load.getConfig("local")
    case _ => ConfigFactory.load.getConfig("")
  }

}
