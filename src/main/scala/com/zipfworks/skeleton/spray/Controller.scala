package com.zipfworks.skeleton.spray

import akka.actor.ActorSystem
import akka.io.IO
import com.zipfworks.skeleton.spray.routing.DummyRoutes
import com.zipfworks.skeleton.spray.utils.BasicHttpService
import spray.can.Http

object Controller extends App {

  private val config = AppConfig()
  private val system = ActorSystem("spray-skeleton", config.CONFIG)
  private val databases = AppDatabases(config.MONGO_NODES)(system)
  private val services = AppServices(config, databases)(system)

  private implicit val appPackage = AppPackage(config, system, databases, services)

  private val routing = new DummyRoutes().routes
  private val httpService = system.actorOf(BasicHttpService.props(routing), "http-service")
  IO(Http)(system) ! Http.Bind(httpService, config.HTTP_IFACE, config.HTTP_PORT)

}
