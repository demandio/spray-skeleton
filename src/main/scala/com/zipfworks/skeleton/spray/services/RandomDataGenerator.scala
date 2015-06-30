package com.zipfworks.skeleton.spray.services

import akka.actor._
import com.zipfworks.skeleton.spray.AppDatabases
import com.zipfworks.skeleton.spray.models.DummyData
import com.zipfworks.sprongo.SprongoDSL

import scala.concurrent.Future

class RandomDataGenerator(Databases: AppDatabases) extends Actor with ActorLogging {

  private def generateRandomData(c: Int) = {
    import context.dispatcher
    val insertDummyData = for (i <- 0 to c) yield {
      Databases.DummyDB.Data.exec(SprongoDSL.create.model(DummyData()))
        .map(_ => log.debug("generated random data..."))
    }
    Future.sequence(insertDummyData).andThen({
      case _ => self ! PoisonPill
    })
  }

  def receive = {
    case c: Int => generateRandomData(c)
    case msg => log.debug(s"unknown message received: $msg")
  }
}

object RandomDataGenerator {
  def generate(count: Int, Databases: AppDatabases)(implicit actorRefFactory: ActorRefFactory) =
    actorRefFactory.actorOf(Props(classOf[RandomDataGenerator], Databases)) ! count
}
