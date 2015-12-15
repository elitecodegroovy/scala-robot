package com.ai.actor.akka.reqresp.protocol

/**
 * @author john.grails@gmail.com
 * @since  2015/12/15..
 */
object QuoteRepositoryProtocol{

  case class QuoteRepositoryRequest()
  case class QuoteRepositoryResponse(quoteString:String)

}