package com.zipfworks.skeleton.spray.utils

import com.zipfworks.skeleton.spray.models.DummyData
import com.zipfworks.sprongo.driver.{SprongoDriver, DBBuilder}

class DummyDriver(implicit sDriver: SprongoDriver) extends DBBuilder{
  override def dbName: String = "dummy"
  override def driver: SprongoDriver = sDriver

  val Data = getCollection[DummyData]("data")
}
