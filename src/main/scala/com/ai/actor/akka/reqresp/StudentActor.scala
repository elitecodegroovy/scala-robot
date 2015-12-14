package com.ai.actor.akka.reqresp

import akka.actor.{ActorRef, Actor, ActorLogging}
import com.ai.actor.akka.reqresp.protocol.StudentProtocol.InitSignal
import com.ai.actor.akka.reqresp.protocol.TeacherProtocol.{QuoteRequest, QuoteResponse}

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
class StudentActor (teacherActorRef:ActorRef) extends Actor with ActorLogging {

  def receive = {
    case InitSignal(i) => {
      teacherActorRef!QuoteRequest(i)
    }

    case QuoteResponse(quoteString) => {
      log.info("*********************************")
      log.info(s"Received QuoteResponse from Teacher.Printing from Student Actor:... $quoteString")
    }
  }
}