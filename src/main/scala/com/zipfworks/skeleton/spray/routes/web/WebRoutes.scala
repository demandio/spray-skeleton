package com.zipfworks.skeleton.spray.routes.web

import com.zipfworks.skeleton.spray.routes.api.users.ReadUsersModel
import spray.routing.{Route, Directives}
import scala.concurrent.ExecutionContext

class WebRoutes(implicit ec: ExecutionContext) extends Directives {

  private val list_users: Route =  (
    get
      & path("users")
      & ReadUsersModel.params
  ){(rum: ReadUsersModel) => {

    val response = rum.getUsers.map(users => users.map(user => {
      <tr><td>{user._id}</td><td>{user.email}</td></tr>
    })).map(userRows => {
      <html>
        <head>
          <title>List Users Page</title>
          <style type="text/css">
            {"""
              table {
                font-family: verdana,arial,sans-serif;
                font-size:11px;
                color:#333333;
                border-width: 1px;
                border-color: #666666;
                border-collapse: collapse;
              }
              table th {
                border-width: 1px;
                padding: 8px;
                border-style: solid;
                border-color: #666666;
                background-color: #dedede;
              }
              table td {
                border-width: 1px;
                padding: 8px;
                border-style: solid;
                border-color: #666666;
                background-color: #ffffff;
              }
            """}
          </style>
        </head>
        <body>
          <table>
            <th>ID</th><th>Email</th>
            {userRows}
          </table>
        </body>
      </html>
    })

    complete(response)
  }}

  val routes: Route = pathPrefix("web"){
    list_users
  }

}
