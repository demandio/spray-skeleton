package com.zipfworks.skeleton.spray.routes.users

import com.zipfworks.skeleton.spray.datastore.models.users.User
import com.zipfworks.skeleton.spray.util.{ERR, UserAuthenticator, ExtendedDirectives}
import spray.json._
import spray.routing.Route
import scala.concurrent.ExecutionContext

class UserRoutes(implicit ec: ExecutionContext) extends ExtendedDirectives {

  private val read_users: Route = (
    get
      & pathEnd
      & ReadUsersModel.params
  ){(rum: ReadUsersModel) => {
    completeMap(rum.getResponse)
  }}

  private val read_self: Route = (
    get
      & path("self")
      & requiredAuth
  ){(user: User) => {
    complete(Map("users" -> JsArray(user.toJson)))
  }}

  private val create_user: Route = (
    post
      & path("register")
      & entity(as[CreateUsersModel])
  ){(cum: CreateUsersModel) => {
    completeRoute {
      for {
        user <- cum.create
        sess <- user.getSession
      } yield{
        (UserAuthenticator.setSessionCookie(sess) & complete){
          Map("users" -> JsArray(user.toJson))
        }
      }
    }
  }}

  val routes: Route = pathPrefix("users"){
    read_users ~        //GET   /users
    read_self ~         //GET   /users/self
    create_user         //POST  /users/register
  }

}

