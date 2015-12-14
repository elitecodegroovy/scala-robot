package com.ai.actor.akka.reqresp

import akka.actor.{Actor, ActorLogging}
import com.ai.actor.akka.reqresp.protocol.TeacherProtocol.{QuoteResponse, QuoteRequest}

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */

class TeacherActor extends Actor with ActorLogging {


  val quotes = List(
    "Moderation is for cowards",
    "Anything worth doing is worth overdoing",
    "The trouble is you think you have time",
    "You never gonna know if you never even try")

  def receive = {

    case QuoteRequest(j) => {

      log.info(s"--request parameter: $j")
      var quoteResponse = QuoteResponse("")
      if(j < 0 || j > 3){
        quoteResponse = QuoteResponse(s" $j beyond the array index.")
      }else {
        quoteResponse = QuoteResponse(quotes(j))
      }

      //Get a random Quote from the list and construct a response


      //respond back to the Student who is the original sender of QuoteRequest
      sender ! quoteResponse

    }
  }
}
