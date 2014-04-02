package com.zipfworks.template.spray

import com.typesafe.config.ConfigFactory

trait SystemConfig {

  val loadedConfig = System.getenv("ENVIRONMENT") match {
    case "production" => ConfigFactory.load.getConfig("production")
    case "staging" => ConfigFactory.load.getConfig("staging")
    case "local" => ConfigFactory.load.getConfig("local")
    case _ => ConfigFactory.load.getConfig("")
  }

}
