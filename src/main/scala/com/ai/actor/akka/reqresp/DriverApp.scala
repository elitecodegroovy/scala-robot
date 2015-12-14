package com.ai.actor.akka.reqresp

import akka.actor.{Props, ActorSystem}
import com.ai.actor.akka.reqresp.protocol.StudentProtocol.InitSignal

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
object DriverApp {

  def main(args: Array[String]): Unit ={
    //Initialize the ActorSystem
    val system = ActorSystem("UniversityMessageSystem")

    //create the teacher actor
    val teacherRef = system.actorOf(Props[TeacherActor], "teacherActor")

    //create the Student Actor - pass the teacher actorref as a constructor parameter to StudentActor
    val studentRef = system.actorOf(Props(new StudentActor(teacherRef)), "studentActor")

    //send a message to the Student Actor
    studentRef ! InitSignal(1)

    studentRef ! InitSignal(-1)

    //Let's wait for a couple of seconds before we shut down the system
    Thread.sleep(2000)

    //Shut down the ActorSystem.
    system.shutdown()
  }
}
