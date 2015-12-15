package com.ai.actor.akka.reqresp

/**
 * @author john.grails@gmail.com
 * @since  2015/12/15..
 */

import akka.actor.{PoisonPill, Actor, ActorLogging}
import com.ai.actor.akka.reqresp.protocol.QuoteRepositoryProtocol.{QuoteRepositoryResponse, QuoteRepositoryRequest}

import scala.util.Random

class QuoteRepositoryActor() extends Actor with ActorLogging {

  val quotes = List(
    "Moderation is for cowards",
    "Anything worth doing is worth overdoing",
    "The trouble is you think you have time",
    "You never gonna know if you never even try")

  var repoRequestCount:Int=1

  def receive = {

    case QuoteRepositoryRequest => {

      if (repoRequestCount > 3){
        self!PoisonPill
      }
      else {
        //Get a random Quote from the list and construct a response
        val random =  Random.nextInt(quotes.size);
        val quoteResponse = QuoteRepositoryResponse(quotes(random))

        log.info(s"-------------------------------------------------------\n" +
          s"Sending response to Teacher Actor $random: $quoteResponse")
        repoRequestCount = repoRequestCount + 1
        sender ! quoteResponse
      }
    }

  }

}