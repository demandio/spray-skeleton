package com.zipfworks.skeleton.spray

import com.typesafe.config.ConfigFactory
import scala.collection.JavaConverters._


/**********************************************************************************
 * System Configuration
 * Contains any configuration constants used in this app.
 *
 * Its best not to read external environment variables so we collect everything
 * that we need in the app here.
 *
 * Note that things declared here are instantiated FIRST
 *
 * In keeping with the GLOBAL_VARIABLE convention, things that are accessible
 * globally will be ALL_CAPS
 *********************************************************************************/
trait SystemConfig {

  val LOADED_CONFIG = sys.env.getOrElse("ENVIRONMENT", "LOCAL").toLowerCase match {
    case "production" => ConfigFactory.load.getConfig("production")
    case "staging"    => ConfigFactory.load.getConfig("staging")
    case "local"      => ConfigFactory.load.getConfig("local")
    case _            => ConfigFactory.load.getConfig("local") //we'll default to 'local'
  }

  val ENVIRONMENT   = LOADED_CONFIG.getString("environment")
  val IS_PRODUCTION = ENVIRONMENT == "PRODUCTION"
  val DB_URLS       = LOADED_CONFIG.getStringList("db.urls").asScala

  val SERVER_PORT = LOADED_CONFIG.getInt("webserver.port")
  val SERVER_BIND_TIMEOUT = LOADED_CONFIG.getInt("webserver.bindTimeout")

}
