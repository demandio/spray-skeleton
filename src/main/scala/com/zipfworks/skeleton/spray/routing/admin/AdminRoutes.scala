package com.zipfworks.skeleton.spray.routing.admin

import akka.actor.ActorRefFactory
import com.zipfworks.skeleton.spray.AppPackage
import com.zipfworks.skeleton.spray.services.RandomDataGenerator
import com.zipfworks.skeleton.spray.utils.ExtendedDirectives
import spray.routing.Route

class AdminRoutes(implicit App: AppPackage) extends ExtendedDirectives(App) {

  implicit val actorRefFactory: ActorRefFactory = App.System

  private val generateRandomData: Route = (
    get &
    path("gen-random-data") &
    parameters('count.?(10))
  ){ (count) => complete { RandomDataGenerator.generate(count, App.Databases); "OK" }}

  val routes: Route = pathPrefix("admin"){
    generateRandomData
  }
}
