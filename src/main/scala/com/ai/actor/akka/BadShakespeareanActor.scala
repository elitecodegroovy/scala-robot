package com.ai.actor.akka

import akka.actor.Actor

/**
 * @author john.grails@gmail.com
 * @since  2015/12/6..
 */
class BadShakespeareanActor  extends Actor{
  override def receive = {
    case "Good Morning" =>
      println("Him: Forsooth 'tis the 'morn, but mourneth for thou doest I do!")
    case "You're terrible" =>
      println("Him: Yup")
  }
}
