package com.ai.actor.akka

import java.util.concurrent.TimeUnit

import akka.actor.{ActorLogging, Actor}

import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
object Altimeter {
  // Sent to the Altimeter to inform it about a rate of climb changes
  case class RateChange(amount: Float)
}

class Altimeter extends Actor with ActorLogging {
  import Altimeter._
  // The maximum ceiling of our plane in 'feet'
  val ceiling = 43000
  // The maximum rate of climb for our plane in 'feet per minute'
  val maxRateOfClimb = 5000
  // The varying rate of climb depending on the movement of the stick
  var rateOfClimb: Float =0
  // Our current altitude
  var altitude: Double =0
  // As time passes, we need to change the altitude based on the time passed.
  // The lastTick allows us to figure out how much time has passed
  var lastTick = System.currentTimeMillis
  // We need to periodically update our altitude. This scheduled message send
  // will tell us when to do that
  val ticker = context.system.scheduler.schedule(Duration(500, TimeUnit.MILLISECONDS),
                                       Duration(500, TimeUnit.MILLISECONDS), self, Tick)
  // An internal message we send to ourselves to tell us to update our altitude
  case object Tick

  def receive = {
    // Our rate of climb has changed
    case RateChange(amount) =>
      // Keep the value of rateOfClimb within [1, 1]
      rateOfClimb = amount * maxRateOfClimb
      log.info(s"altimeter high:  $amount, Altimeter changed rate of climb to $rateOfClimb.")
    // Calculate a new altitude
    case Tick =>
      val tick = System.currentTimeMillis
      altitude = altitude + ((tick - lastTick) / 60000.0) * rateOfClimb
      lastTick = tick
  }

  override def postStop(): Unit = ticker.cancel()
}
