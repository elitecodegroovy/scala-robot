package com.ai.concurrent

import scala.actors.Actor

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
object BasicActor {
  //In the case of self.receive, this could mean waiting forever!
  //Instead, use receiveWithin with some timeout value:
  //  Actor.self.receive { case x => x }

  val echoActor = Actor.actor {
    while (true) {
      Actor.receive {
        case msg: String =>
          println("echoActor received string message: "+ msg)
      }
    }
  }

  val intActor = Actor.actor {
    while (true) {
      Actor.receive {
        case msg: Int =>
          println("intActor received int message code:  "+ msg)
      }
    }
  }

  def showActor(): Unit ={
    SillyActor.start()
    Actor.actor{
      print("Thinking in actor model ?")
      for (i <- 1 to 3){
        print(" ." * i )
        Thread.sleep(1000)
      }
      println("")
    }
    echoActor ! "+++++hi there"
    echoActor ! 12     //can't be received
    intActor ! "+++++hi there"  //can't be received
    intActor ! 12

    //Actor.loop function executes a block of code repeatedly, even if the code calls react.
    //    Actor.self ! "hello"
    NameResolver ! ("www.scalalang.org", Actor.self)
    Actor.self.receiveWithin(1000) { case x => println(x) }
  }

  def main(args: Array[String]): Unit ={
    showActor()
  }
}
