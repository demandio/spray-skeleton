package com.zipfworks.skeleton.spray.util

import com.zipfworks.skeleton.spray.Controller
import com.zipfworks.skeleton.spray.datastore.models.users.User
import spray.http.CacheDirectives.{`max-age`, `must-revalidate`, `no-cache`}
import spray.http.HttpHeaders.`Cache-Control`
import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions
import spray.http.ContentTypes
import spray.httpx.SprayJsonSupport
import spray.httpx.marshalling.Marshaller
import spray.json._
import spray.routing._

import scala.util.{Failure, Success}

trait ExtendedDirectives extends Directives with SprayJsonSupport with DefaultJsonProtocol {

  //Path matcher for getting a slug or ID
  val IdOrSlug: PathMatcher1[String] = PathMatcher("[a-z0-9A-Z_\\-]+".r)

  //authentication directives
  def requiredAuth(implicit ec: ExecutionContext): Directive1[User] = authenticate(new UserAuthenticator())

  def completeMap(f: Future[Map[String, JsValue]])(implicit ec: ExecutionContext): Route = onComplete(f){
    case Success(m) => complete(m)
    case Failure(e) => ERR(e).complete
  }

  def completeRoute(r: Future[Route])(implicit ec: ExecutionContext): Route = onComplete(r){
    case Success(s) => s
    case Failure(f) => ERR(f).complete
  }

  /**********************************************************************************
    * Print Out Compact/Pretty depending on Production/Development
    *********************************************************************************/
  val printer = if(Controller.IS_PRODUCTION) CompactPrinter else PrettyPrinter

  override implicit def sprayJsonMarshallerConverter[T](writer: RootJsonWriter[T])
                                                       (implicit printer: JsonPrinter = printer): Marshaller[T] = {
    sprayJsonMarshaller[T](writer, printer)
  }

  override implicit def sprayJsonMarshaller[T](implicit writer: RootJsonWriter[T], printer: JsonPrinter = printer): Marshaller[T] =
    Marshaller.delegate[T, String](ContentTypes.`application/json`) { value ⇒
      val json = writer.write(value)
      printer(json)
    }
}
