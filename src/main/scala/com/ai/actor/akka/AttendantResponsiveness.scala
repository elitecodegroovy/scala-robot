package com.ai.actor.akka

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
trait AttendantResponsiveness {
  val maxResponseTimeMS: Int
  def responseDuration = scala.util.Random.nextInt(maxResponseTimeMS).max(60000)
}
