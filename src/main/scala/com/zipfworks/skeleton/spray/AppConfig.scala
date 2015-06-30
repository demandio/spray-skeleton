package com.zipfworks.skeleton.spray

import com.typesafe.config.{ConfigFactory, Config}
import scala.collection.JavaConversions._

case class AppConfig(
  ENVIRONMENT: String = sys.env.getOrElse("ENVIRONMENT", "local").toLowerCase
){

  val IS_PRODUCTION = ENVIRONMENT == "production"

  val CONFIG: Config = ENVIRONMENT match {
    case "production" => ConfigFactory.load.getConfig("production")
    case "staging"    => ConfigFactory.load.getConfig("staging")
    case _            => ConfigFactory.load.getConfig("local")
  }

  val HTTP_PORT = CONFIG.getInt("http.port")
  val HTTP_IFACE = CONFIG.getString("http.iface")

  val MONGO_NODES = CONFIG.getStringList("mongo.nodes").toList

}
