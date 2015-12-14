package com.ai.actor.akka.reqresp

import akka.actor.{Actor, DeadLetter}

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
class MyCustomDeadLetterListener extends Actor {
  def receive = {
    case deadLetter: DeadLetter => println(s"\n ***************************************\n" +
      s"FROM CUSTOM LISTENER $deadLetter")
  }
}