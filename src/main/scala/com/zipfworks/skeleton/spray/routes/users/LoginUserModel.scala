package com.zipfworks.skeleton.spray.routes.users

import com.github.t3hnar.bcrypt._
import com.zipfworks.skeleton.spray.Controller
import com.zipfworks.skeleton.spray.datastore.models.users.User
import com.zipfworks.skeleton.spray.util.{Errors, ERR}
import com.zipfworks.sprongo.ExtendedJsonProtocol
import com.zipfworks.sprongo.macros.SprongoDSL

import scala.concurrent.{Future, ExecutionContext}

case class LoginUserModel(
  email: String,
  password: String
)

object LoginUserModel extends ExtendedJsonProtocol {
  implicit val loginUserJS = jsonFormat2(LoginUserModel.apply)

  implicit class LoginUserPimps(lum: LoginUserModel)(implicit ec: ExecutionContext){
    def login: Future[User] = {
      Controller.MONGODB.Users.execute(
        SprongoDSL.find("email" -> lum.email.toLowerCase).one
      ).map({
        case None => throw ERR.badRequest(Errors.EMAIL_NOT_FOUND)
        case Some(user) => user
      }).map(user => {
        val isValidPass = user.auth.find(_.appDes == "email").map(_.token).exists(encPass => {
          lum.password.isBcrypted(encPass)
        })

        if(isValidPass) user else throw ERR.badRequest(Errors.PASSWORD_INVALID)
      })
    }
  }
}
