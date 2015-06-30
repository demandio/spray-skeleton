package com.zipfworks.skeleton.spray

import akka.actor.ActorSystem
import com.zipfworks.skeleton.spray.utils.DummyDriver
import com.zipfworks.sprongo.driver.SprongoDriver

case class AppDatabases(nodes: Seq[String])(implicit system: ActorSystem){
  implicit private val sDriver = SprongoDriver(nodes)

  val DummyDB = new DummyDriver()

}
