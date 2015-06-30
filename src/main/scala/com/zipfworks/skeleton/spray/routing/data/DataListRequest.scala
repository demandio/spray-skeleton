package com.zipfworks.skeleton.spray.routing.data

import com.zipfworks.skeleton.spray.AppPackage
import com.zipfworks.sprongo.SprongoDSL
import spray.json.DefaultJsonProtocol._
import spray.json._
import spray.routing.Directive1
import spray.routing.directives.ParameterDirectives._
import scala.concurrent.Future

case class DataListRequest(
  page: Int,
  limit: Int
)

object DataListRequest {
  val params: Directive1[DataListRequest] = parameters(
    'page.?(0),
    'limit.?(10)
  ).as(DataListRequest.apply _)

  implicit class DataList(request: DataListRequest)(implicit App: AppPackage){

    def getResponse: Future[JsObject] = {
      import App.System.dispatcher
      val limit = math.max(request.limit, 0)
      val skip = math.max(request.page, 0) * limit
      App.Databases.DummyDB.Data.exec(SprongoDSL.read().limit(limit).skip(skip).asList).map(data => {
        JsObject("data" -> data.toJson)
      })
    }
  }
}
