package com.ai.actor.akka.reqresp

import akka.actor.{Terminated, Props, Actor, ActorLogging}
import com.ai.actor.akka.reqresp.protocol.QuoteRepositoryProtocol.QuoteRepositoryRequest
import com.ai.actor.akka.reqresp.protocol.TeacherProtocol.QuoteRequest

/**
 * @author john.grails@gmail.com
 * @since  2015/12/15..
 */
class TeacherActorWatcher extends Actor with ActorLogging {

  val quoteRepositoryActor=context.actorOf(Props[QuoteRepositoryActor], "quoteRepositoryActor")
  context.watch(quoteRepositoryActor)


  def receive = {
    case QuoteRequest(j) => {
      quoteRepositoryActor ! QuoteRepositoryRequest(j)
    }
    case Terminated(terminatedActorRef)=>{
      log.error(s"++++++++++++++++++++++++\nChild Actor {$terminatedActorRef} Terminated")
    }
  }
}
