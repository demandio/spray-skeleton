package com.zipfworks.skeleton.spray.util

import spray.http.CacheDirectives.{`max-age`, `must-revalidate`, `no-cache`, `public`}
import spray.http.HttpHeaders.`Cache-Control`
import spray.routing.Directives

trait CacheDirectives {
  this: Directives =>

  //default to NOT CACHE the routes
  lazy val nocache = respondWithSingletonHeader(`Cache-Control`(`no-cache`, `must-revalidate`, `max-age`(0)))

  //Cache the route for {seconds}
  def cache(seconds: Long) = respondWithHeader(`Cache-Control`(`public`, `max-age`(seconds)))

  //Cache the route one day (24 hours)
  lazy val cacheOneDay = cache(86400)

  //Cache the route one week (7 days)
  lazy val cacheOneWeek = cache(86400 * 7)

  //Cache the route one month (30 days)
  lazy val cacheOneMonth = cache(86400 * 30)
}
