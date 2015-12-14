package com.ai.actor.akka.reqresp

import akka.actor.{Actor, ActorLogging}
import com.ai.actor.akka.reqresp.protocol.TeacherProtocol.{QuoteResponse, QuoteRequest}

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */

class TeacherActor extends Actor with ActorLogging {

  val indexMap = scala.collection.immutable.Map(
    0 -> "Moderation is for cowards",
    1 -> "Anything worth doing is worth overdoing",
    2 -> "The trouble is you think you have time",
    3 -> "You never gonna know if you never even try")

  def receive = {
    case QuoteRequest(j) => {

      log.info(s"--request parameter: $j")
      var quoteResponse = QuoteResponse("")
      if(j < 0 || j > 3){
        quoteResponse = QuoteResponse(s"can't get the related value with index value $j")
      }else {
        quoteResponse = QuoteResponse(indexMap(j))
      }

      //Get a random Quote from the list and construct a response

      //respond back to the Student who is the original sender of QuoteRequest
      sender ! quoteResponse
    }
  }
}
