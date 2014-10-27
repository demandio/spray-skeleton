package com.zipfworks.skeleton.spray

import com.zipfworks.template.spray.services.api.APIWebServer

trait SystemServices {
  this: SystemConfig with SystemCore =>

  val apiService: APIWebServer = new APIWebServer(loadedConfig, system)
}
