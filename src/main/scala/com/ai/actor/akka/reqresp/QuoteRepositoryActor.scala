package com.ai.actor.akka.reqresp

/**
 * @author john.grails@gmail.com
 * @since  2015/12/15..
 */

import akka.actor.{PoisonPill, Actor, ActorLogging}
import com.ai.actor.akka.reqresp.protocol.QuoteRepositoryProtocol.{QuoteRepositoryResponse, QuoteRepositoryRequest}

import scala.util.Random

class QuoteRepositoryActor() extends Actor with ActorLogging {

  var quotesMap = scala.collection.Map(
    0 -> "Moderation is for cowards",
    1 -> "Anything worth doing is worth overdoing",
    2 -> "The trouble is you think you have time",
    3 -> "The trouble is you think you have time"
   )

  var repoRequestCount:Int=1

  def receive = {

    case QuoteRepositoryRequest(i) => {

      if (repoRequestCount > 3){
        self!PoisonPill
      }
      else {
        //Get a random Quote from the list and construct a response
        var number :Int = 0
        if(i < 0 || i > 3){
          number =  Random.nextInt(quotesMap.size)
        }else{
          number = i
        }

        val quoteResponse = QuoteRepositoryResponse(quotesMap(number))

        log.info(s"-------------------------------------------------------\n" +
          s"Sending response to Teacher Actor $number: $quoteResponse")
        repoRequestCount = repoRequestCount + 1
        sender ! quoteResponse
      }
    }

  }

}