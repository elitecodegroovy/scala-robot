package com.ai.actor.akka.reqresp

import akka.actor.{DeadLetter, Props, ActorSystem}

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
object LifecycleApp extends App {

  val actorSystem = ActorSystem("LifecycleActorSystem")
  val lifecycleActor = actorSystem.actorOf(Props[BasicLifecycleLoggingActor], "lifecycleActor")

  val deadLetterListener = actorSystem.actorOf(Props[MyCustomDeadLetterListener])
  actorSystem.eventStream.subscribe(deadLetterListener, classOf[DeadLetter])

  lifecycleActor ! "hello"
  lifecycleActor ! "stop"
  lifecycleActor ! "hello"

  Thread.sleep(5000)
  actorSystem.shutdown()
}
