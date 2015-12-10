package com.ai.actor.akka

import akka.actor.{Props, ActorSystem}
import akka.testkit.{TestKit, TestActorRef, ImplicitSender}
import org.scalatest.{WordSpecLike, MustMatchers}

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
object TestFlightAttendant {
  def apply() = new FlightAttendant with AttendantResponsiveness {
    val maxResponseTimeMS = 5
  }
}

class FlightAttendantSpec extends TestKit(ActorSystem("FlightAttendantSpec"))
                                          with ImplicitSender
                                          with WordSpecLike
                                          with MustMatchers{
  import FlightAttendant._
  "FlightAttendant" should{
    "get a drink when asked" in {
      val a = TestActorRef(Props(TestFlightAttendant()))
      a ! GetDrink("Soda")
      expectMsg(Drink("Soda"))
    }
  }
}
