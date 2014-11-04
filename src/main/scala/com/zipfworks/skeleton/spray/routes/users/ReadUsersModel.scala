package com.zipfworks.skeleton.spray.routes.users

import com.zipfworks.skeleton.spray.Controller
import reactivemongo.bson.BSONDocument
import spray.json._
import spray.routing.{Directives, Directive1}
import scala.concurrent.{ExecutionContext, Future}
import com.zipfworks.sprongo.macros.SprongoDSL._

case class ReadUsersModel(
  page: Int,
  limit: Int
)

object ReadUsersModel extends Directives {
  val params: Directive1[ReadUsersModel] = parameters(
    'page.?(0),
    'limit.?(10)
  ).as(ReadUsersModel.apply _)

  implicit class RUMPimps(rum: ReadUsersModel)(implicit ec: ExecutionContext){

    def getResponse: Future[Map[String, JsValue]] = {
      Controller.MONGODB.Users.execute(
        find(BSONDocument()).skip(rum.page * rum.limit).limit(rum.limit).asList
      ).map(users =>
        Map("users" -> JsArray(users.map(_.toJson): _*))
      )
    }

  }

}
