package com.zipfworks.skeleton.spray.routes.users

import com.zipfworks.skeleton.spray.util.ExtendedDirectives
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

  private val create_user: Route = (
    post
      & path("register")
      & entity(as[CreateUsersModel])
  ){(cum: CreateUsersModel) => {
    completeMap(cum.getResponse)
  }}

  val routes: Route = pathPrefix("users"){
    read_users ~        //GET  /users
    create_user         //POST /users/register
  }

}

