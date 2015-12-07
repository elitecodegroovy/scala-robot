package com.ai.actor.akka

import akka.actor.{Props, ActorSystem}

/**
 * @author john.grails@gmail.com
 * @since  2015/12/6..
 */
object BadShakespeareanMain {
  val system = ActorSystem("BadShakespearean")
  val actor = system.actorOf(Props[BadShakespeareanActor])
  // We'll use this utility method to talk with our Actor
  def send(msg: String) {
    println("Me: " + msg)
    actor ! msg
    Thread.sleep(100)
  }
  // And our driver
  def main(args: Array[String]) {
    send("Good Morning")
    send("You're terrible")
    system.shutdown()
  }
}
