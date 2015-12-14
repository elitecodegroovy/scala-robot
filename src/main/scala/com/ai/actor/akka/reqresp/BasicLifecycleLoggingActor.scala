package com.ai.actor.akka.reqresp

import akka.actor.{ActorLogging, Actor}
import akka.event.LoggingReceive

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
class BasicLifecycleLoggingActor extends Actor with ActorLogging{

  def receive = LoggingReceive{
    case "hello" => log.info ("______________________hello")
    case "stop" => context.stop(self)
  }
}
