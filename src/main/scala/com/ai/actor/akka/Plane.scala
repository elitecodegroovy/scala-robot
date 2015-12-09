package com.ai.actor.akka

import akka.actor.{Props, Actor, ActorLogging}

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
// We want the Plane to own the Altimeter and we're going to do that
// by passing in a specific factory we can use to build the Altimeter
class Plane extends Actor with ActorLogging {
  import Plane._
  val altimeter = context.actorOf(Props[Altimeter])
  val controls = context.actorOf(Props(new ControlSurfaces(altimeter)))
  def receive = {
    case GiveMeControl =>
      log.info("Plane giving control.")
      sender ! controls
  }
}

object Plane{
  // Returns the control surface to the Actor that asks for them
  case object GiveMeControl
}
