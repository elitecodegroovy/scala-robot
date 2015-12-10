package com.ai.actor.akka

import akka.actor.Props

/**
 * @author john.grails@gmail.com
 * @since  2015/12/10..
 */
object FlightAttendantPathChecker {
  def main(args: Array[String]) {
    val system = akka.actor.ActorSystem("PlaneSimulation")
    val lead = system.actorOf(Props(
      new LeadFlightAttendant with AttendantCreationPolicy),
      "LeadFlightAttendant")
    Thread.sleep(2000)
    system.shutdown()
  }
}