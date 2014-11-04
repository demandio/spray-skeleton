package com.zipfworks.skeleton.spray.datastore.models.users

import com.zipfworks.skeleton.spray.Controller
import com.zipfworks.sprongo.ExtendedJsonProtocol
import com.zipfworks.sprongo.macros.ExtendedMacroHandlers
import java.util.UUID
import reactivemongo.bson.Macros
import com.zipfworks.sprongo.macros.SprongoDSL._

import scala.concurrent.{ExecutionContext, Future}


case class User(
  email: String,
  auth: Seq[Auth],
  sessions: Option[List[Session]] = None,
  _id: UUID = UUID.randomUUID()
)

object User extends ExtendedMacroHandlers with ExtendedJsonProtocol {
  implicit val userHandler = Macros.handler[User] //converts the case class to a BSONDocument for Mongo
  implicit val userJS = jsonFormat4(User.apply)   //converts the case class to a JsonValue for Route Responses

  /**
   * Checks to make sure the password passes our security requirements
   * 1. at least 8 characters long
   * @param password String, password to check
   * @return true if it passes; false otherwise
   */
  def isValidPassword(password: String): Boolean = {
    password.length > 7
  }

  /**
   * Uses a regex to see if the email passed in 'looks' like an email
   * @param email String, the email to check
   * @return true if it looks like an email; false otherwise
   */
  def isValidEmail(email: String): Boolean = {
    email.toLowerCase.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@" + //before @
      "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+(?:[A-Za-z]{2}" + //domain name
      "|com|org|net|edu|gov|mil|biz|info|mobi|name|aero|asia|jobs|museum)\\b") //high level domain
  }

  /**
   * Checks the DB to see if the email already exists
   * @param email String, the function will lowercase the email just in case
   * @param ec ExecutionContext, because its needs to do stuff in the future
   * @return true if the email exists; false otherwise
   */
  def emailExists(email: String)(implicit ec: ExecutionContext): Future[Boolean] = {
    Controller.MONGODB.Users.execute(count("email" -> email.toLowerCase)).map(_ > 0)
  }


  implicit class UserPimps(user: User)(implicit ec: ExecutionContext){

    /**
     * Generates a New Session, adds it to the user, and returns it
     * @return Session
     */
    def getSession: Future[Session] = {
      //generate a new session
      val newSession = Session.generate

      //add the session onto previous sessions, drop if there are too many
      val newSessions = user.sessions.getOrElse(Nil).:+(newSession) match {
        case sessx if sessx.length > 10 => sessx.drop(sessx.length - 10)
        case sessx => sessx
      }

      Controller.MONGODB.Users
        .execute(update("_id" -> user._id).ops($set("sessions", newSessions)))
        .map(_ => newSession)
    }

  }
}

