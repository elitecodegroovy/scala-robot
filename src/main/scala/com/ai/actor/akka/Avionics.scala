package com.ai.actor.akka

import java.util.concurrent.TimeUnit

import akka.actor.{Props,  ActorRef, ActorSystem}
import akka.pattern.ask
import scala.concurrent.Await

import akka.util.Timeout
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
object Avionics {
  // needed for '?' below
  implicit val timeout = Timeout(Duration(5, TimeUnit.SECONDS))
  val system = ActorSystem("PlaneSimulation")
  val plane = system.actorOf(Props[Plane], "Plane")
  println(s"----plane path:${plane.path}, name :${plane.path.name}")
  println(s"----plane address:${plane.path.address.toString}")

  def main(args: Array[String]) {
    // Grab the controls
    val control = Await.result(
      (plane ? Plane.GiveMeControl).mapTo[ActorRef], Duration(5, TimeUnit.SECONDS))
    // Takeoff!
    system.scheduler.scheduleOnce(Duration(500, TimeUnit.MILLISECONDS)) {
      control ! ControlSurfaces.StickBack(1f)
    }
    // Level out
    system.scheduler.scheduleOnce(Duration(1, TimeUnit.SECONDS)) {
      control ! ControlSurfaces.StickBack(0.7f)
    }
    // Climb
    system.scheduler.scheduleOnce(Duration(2, TimeUnit.SECONDS)) {
      control ! ControlSurfaces.StickBack(0.5f)
    }
    // Climb
    system.scheduler.scheduleOnce(Duration(2, TimeUnit.SECONDS)) {
      control ! ControlSurfaces.StickBack(0.2f)
    }
    // Level out
    system.scheduler.scheduleOnce(Duration(4, TimeUnit.SECONDS)) {
      control ! ControlSurfaces.StickBack(0f)
    }
    // Shut down
    system.scheduler.scheduleOnce(Duration(5, TimeUnit.SECONDS)) {
      system.shutdown()
    }
  }
}
