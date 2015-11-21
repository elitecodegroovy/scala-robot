package com.ai.concurrent

import scala.actors.Actor

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
object SillyActor extends Actor{
  override def act(): Unit = {
    for(i <- 1 to 3){
      println("Actor is running!!")
      Thread.sleep(1000)
      println("To be or not to be ,that is a question!!")
    }

  }
}
