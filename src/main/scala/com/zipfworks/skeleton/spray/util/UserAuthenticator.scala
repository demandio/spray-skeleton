package com.zipfworks.skeleton.spray.util

import com.zipfworks.skeleton.spray.Controller
import com.zipfworks.skeleton.spray.datastore.models.users.{Session, User}
import reactivemongo.bson.BSONDocument
import spray.http.{DateTime, HttpCookie}
import spray.routing.AuthenticationFailedRejection.{CredentialsMissing, CredentialsRejected}
import spray.routing.{Directive0, Directives, AuthenticationFailedRejection, RequestContext}
import spray.routing.authentication.{Authentication, ContextAuthenticator}
import com.zipfworks.sprongo.macros.SprongoDSL._

import scala.concurrent.{Future, ExecutionContext}

class UserAuthenticator(implicit ec: ExecutionContext) extends ContextAuthenticator[User] {

  private val sess_cookie_name = UserAuthenticator.sess_cookie_name

  override def apply(v1: RequestContext): Future[Authentication[User]] = {

    v1.request.cookies.find(_.name == sess_cookie_name).map(_.content).map(sessID => {
      Controller.MONGODB.Users.execute(
        find("sessions.id" -> sessID).asList
      ).map(users => {
        users.find(user => user.sessions.getOrElse(Nil).exists(sess => sess.id.toString == sessID && sess.expiresOn.isAfterNow))
      }).map({
        case None       => Left(AuthenticationFailedRejection(CredentialsRejected, Nil))
        case Some(user) => Right(user)
      })
    }).getOrElse({
      Future.successful(Left(AuthenticationFailedRejection(CredentialsMissing, Nil)))
    })

  }

}

object UserAuthenticator extends Directives {
  final val sess_cookie_name = "spray_session"
  private final val cookie_path = Some("/")

  /**
   * Sets the Session Cookie for a User.
   * - we also set HttpOnly and Secure to be true in production. By setting HttpOnly, we disallow javascript from
   *   reading the cookie. By setting Secure, we enforce that the cookie must be sent over an encrypted channel.
   * @param session Session
   * @return Directive0
   */
  def setSessionCookie(session: Session): Directive0 = {
    //31 days
    val expires = DateTime.now.+(1000.toLong * 3600.toLong * 24.toLong * 31.toLong)

    val session_cookie = HttpCookie(
      name      = sess_cookie_name,
      path      = cookie_path,
      content   = session.id.toString,
      expires   = Some(expires),
      secure    = if(Controller.IS_PRODUCTION) true else false,
      httpOnly  = if(Controller.IS_PRODUCTION) true else false
    )

    setCookie(session_cookie)
  }

  def delSessionCookie: Directive0 = {
    setCookie(HttpCookie(
      name        = sess_cookie_name,
      path        = cookie_path,
      content     = "",
      expires     = Some(DateTime.MinValue),
      secure      = if(Controller.IS_PRODUCTION) true else false,
      httpOnly    = if(Controller.IS_PRODUCTION) true else false
    ))
  }

}
