package com.zipfworks.skeleton.spray.routes.api.users

import com.zipfworks.skeleton.spray.datastore.models.users.User
import com.zipfworks.skeleton.spray.util.{ERR, UserAuthenticator, ExtendedDirectives}
import spray.http.HttpCookie
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

  private val logout_user: Route = (
    get
      & path("logout")
      & requiredAuth
      & cookie(UserAuthenticator.sess_cookie_name)
  ){(user: User, sess: HttpCookie) => {
    completeRoute {
      user.delSession(sess.content).map(_ => {
        (UserAuthenticator.delSessionCookie & complete){
          "logged out"
        }
      })
    }
  }}

  private val login_user: Route = (
    post
      & path("login")
      & entity(as[LoginUserModel])
  ){(lum: LoginUserModel) => {
    completeRoute {
      for {
        user <- lum.login
        sess <- user.getSession
      } yield {
        (UserAuthenticator.setSessionCookie(sess) & complete){
          Map("users" -> JsArray(user.toJson))
        }
      }
    }
  }}

  val routes: Route = pathPrefix("users"){
    read_users ~        //GET   /users
    read_self ~         //GET   /users/self
    logout_user ~       //GET   /users/logout
    login_user ~        //POST  /users/login
    create_user         //POST  /users/register
  }

}

