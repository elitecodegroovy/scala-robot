package com.ai.concurrent

import java.net.{UnknownHostException, InetAddress}


import scala.actors.Actor

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
object NameResolver  extends Actor{
  def getIp(name: String): Option[InetAddress] = {
    try {
      println("name:"+ name+ ", ip:"+ InetAddress.getByName(name));
      Some(InetAddress.getByName(name))
    } catch {
      case _:UnknownHostException => None
    }
  }

  override def act(): Unit = {
    react {
      case (name: String, actor: Actor) =>
        actor ! getIp(name)
        act()
      case "EXIT" =>
        println("Name resolver exiting.")
      // quit
      case msg =>
        println("Unhandled message: "+ msg)
        act()
    }
  }
}
