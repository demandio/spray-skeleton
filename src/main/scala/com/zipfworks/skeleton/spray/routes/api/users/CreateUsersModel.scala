package com.zipfworks.skeleton.spray.routes.api.users

import com.github.t3hnar.bcrypt._
import com.zipfworks.skeleton.spray.Controller
import com.zipfworks.skeleton.spray.datastore.models.users.{Auth, User}
import com.zipfworks.skeleton.spray.util.{ERR, Errors}
import com.zipfworks.sprongo.ExtendedJsonProtocol
import spray.json.JsValue
import scala.concurrent.{Future, ExecutionContext}
import com.zipfworks.sprongo.macros.SprongoDSL._

case class CreateUsersModel(
  email: String,
  password: String
)

object CreateUsersModel extends ExtendedJsonProtocol {
  implicit val createUserJS = jsonFormat2(CreateUsersModel.apply)

  implicit class CUMPimps(cum: CreateUsersModel)(implicit ec: ExecutionContext){

    def create: Future[User] = validate().flatMap({
      case Nil    => createUser()
      case errors => throw ERR.badRequest(errors: _*)
    })

    private def createUser(): Future[User] = {
      val auth = Seq(Auth(token = cum.password.bcrypt, userID = "email", appDes = "email"))
      val newUser = User(email = cum.email.toLowerCase, auth = auth)
      Controller.MONGODB.Users.execute(insert(newUser)).map(_ => newUser)
    }

    private def validate(): Future[List[(String, JsValue)]] = {
      val validEmail = if(User.isValidEmail(cum.email)) None else Some(Errors.EMAIL_INVALID_FORMAT)
      val validPassword = if(User.isValidPassword(cum.password)) None else Some(Errors.PASSWORD_TOO_WEAK)
      val fEmailExists = User.emailExists(cum.email).map({
        case true  => Some(Errors.EMAIL_ALREADY_EXISTS)
        case false => None
      })

      for {
        emailExists <- fEmailExists
      } yield List(emailExists, validEmail, validPassword).flatten
    }

  }
}
