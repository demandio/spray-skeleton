package com.zipfworks.template.spray

import com.zipfworks.template.spray.services.api.APIWebServer

trait SystemServices {
  this: SystemConfig with SystemCore =>

  val apiService: APIWebServer = new APIWebServer(loadedConfig, system)
}
