package com.ai.actor.akka

import java.util.concurrent.TimeUnit

import akka.actor.Actor

import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
/**
 * @author liujignag@biostime.com
 * @since 1.6
 */

object FlightAttendant {
  case class GetDrink(drinkname: String)
  case class Drink(drinkname: String)
  // By default we will make attendants that respond within 5 minutes
  def apply() = new FlightAttendant with AttendantResponsiveness {
    val maxResponseTimeMS = 300000
  }
}

class FlightAttendant extends Actor { this: AttendantResponsiveness =>
  import FlightAttendant._
  def receive = {
    case GetDrink(drinkname) =>
      // We don't respond right away, but use the scheduler to ensure
      // we do eventually
      context.system.scheduler.scheduleOnce( Duration(responseDuration, TimeUnit.MILLISECONDS), sender,
        Drink(drinkname))
  }
}
