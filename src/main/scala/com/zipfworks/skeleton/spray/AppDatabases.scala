package com.zipfworks.skeleton.spray

import akka.actor.ActorSystem
import com.zipfworks.sprongo.driver.{DBBuilder, SprongoDriver}

case class AppDatabases(nodes: Seq[String])(implicit system: ActorSystem){
  implicit private val sDriver = SprongoDriver(nodes)

  val DummyDB = new DBBuilder {
    override def driver: SprongoDriver = sDriver
    override def dbName: String = "dummy"
  }

}
