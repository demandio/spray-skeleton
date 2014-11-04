package com.zipfworks.skeleton.spray

import com.zipfworks.skeleton.spray.routes.SampleRoute
import com.zipfworks.skeleton.spray.services.http.APIWebServer

/**
 * System Services
 * Contains internal services - things not handled at request time. For example,
 * the HttpServiceActor is declared here.
 *
 * Note that things declared here are instantiated AFTER SystemConfig and SystemCore
 *
 * In keeping with the GLOBAL_VARIABLE convention, things that are accessible
 * globally will be ALL_CAPS
 */
trait SystemServices {
  this: SystemConfig with SystemCore =>

  import SYSTEM.dispatcher

  val HTTP_SERVICE: APIWebServer =
    new APIWebServer(port = SERVER_PORT, timeout = SERVER_BIND_TIMEOUT, routes = new SampleRoute().routes)

}
