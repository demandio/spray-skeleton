package com.zipfworks.skeleton.spray.routes.api.users

import com.zipfworks.skeleton.spray.Controller
import com.zipfworks.skeleton.spray.datastore.models.users.User
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
      getUsers.map(users =>
        Map("users" -> JsArray(users.map(_.toJson): _*))
      )
    }

    def getUsers: Future[Seq[User]] = {
      //make sure limit > 0 and skip > 0
      val limit = Math.max(rum.limit, 0)
      val skip = Math.max(rum.page, 0) * limit

      val findCMD = find().skip(skip).limit(limit).asList
      Controller.MONGODB.Users.execute(findCMD)
    }

  }

}
