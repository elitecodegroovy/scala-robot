package com.ai.actor.akka.reqresp

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.ai.actor.akka.reqresp.protocol.StudentProtocol.InitSignal
import com.ai.actor.akka.reqresp.protocol.TeacherProtocol.{QuoteRequest, QuoteResponse}
import scala.concurrent.duration._
/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
/*
 * The Student Actor that uses Scheduler.
 *
 */

class StudentDelayedActor (teacherActorRef:ActorRef) extends Actor with ActorLogging {

  def receive = {

    /*
     * This InitSignal is received from the DriverApp.
     * On receipt and after 5 seconds, the Student sends a message to the Teacher actor.
     * The teacher actor on receipt of the QuoteRequest responds with a QuoteResponse
     */
    case InitSignal(j) => {
      import context.dispatcher
      //context.system.scheduler.scheduleOnce(5 seconds, teacherActorRef, QuoteRequest)
      context.system.scheduler.schedule(0 seconds, 5 seconds, teacherActorRef, QuoteRequest(j))
      //teacherActorRef!QuoteRequest
    }

    case QuoteResponse(quoteString) => {
      log.info (s"Received QuoteResponse from Teacher. Printing from Student Actor[ $quoteString]")
    }

  }

}
