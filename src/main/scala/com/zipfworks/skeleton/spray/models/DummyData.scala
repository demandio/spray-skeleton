package com.zipfworks.skeleton.spray.models

import java.util.UUID

import com.zipfworks.sprongo.ExtendedJsonProtocol._
import com.zipfworks.sprongo.Model
import org.joda.time.DateTime

import scala.util.Random

case class DummyData(
  data: String = Random.alphanumeric.take(100).toList.mkString,
  createdOn: DateTime = DateTime.now,
  id: String = UUID.randomUUID().toString
) extends Model

object DummyData {
  implicit val dummyDataJsFormat = jsonFormat3(DummyData.apply)
}
