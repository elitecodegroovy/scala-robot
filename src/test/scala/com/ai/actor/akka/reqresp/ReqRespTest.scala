package com.ai.actor.akka.reqresp

import akka.actor.{Props, ActorSystem}
import akka.testkit.{EventFilter, ImplicitSender, TestKit}
import com.ai.actor.akka.reqresp.protocol.StudentProtocol.InitSignal
import com.ai.actor.akka.reqresp.protocol.TeacherProtocol.{QuoteResponse, QuoteRequest}
import com.typesafe.config.ConfigFactory
import org.scalatest._

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
/**
 * This Test case exactly does what the StudentActor was doing in the
 * requestresponse package
 *
 */
class ReqRespTest extends TestKit(ActorSystem("TestUniversityMessageSystem", ConfigFactory.parseString("""
                                            akka{
                                              loggers = ["akka.testkit.TestEventListener"]
                                              test{
                                                  filter-leeway = 10s
                                              }
                                            } """)))
with WordSpecLike
with MustMatchers
with ImplicitSender {

  "A teacher" must {

    "respond with a QuoteResponse when a QuoteRequest message is sent" in {

      val teacherRef = system.actorOf(Props[TeacherActor], "teacherActorChild")
      teacherRef!QuoteRequest(2)

      //expectMsg(QuoteResponse(_)) //Asserting that we are expecting a message back
//      expectMsg(QuoteResponse("The trouble is you think you have time"))
      expectMsgPF() {
        case QuoteResponse(quoteResponse) => println(quoteResponse)
        case _ => fail("Quote response not received")

      }
    }
  }

  "A student" must {

    "log a QuoteResponse" in {

      val teacherRef = system.actorOf(Props[TeacherActor], "teacherActor")

      val studentRef = system.actorOf(Props(new StudentActor(teacherRef)), "studentActor")


      studentRef!InitSignal(1)
//      EventFilter.info (start="Printing from Student Actor", occurrences=1).intercept{
//        studentRef!InitSignal(1)
//      }
    }

  }

  "A delayed student" must {

    "fire the QuoteRequest" in {

      val teacherRef = system.actorOf(Props[TeacherActor], "teacherActorDelayed")
      val studentRef = system.actorOf(Props(new StudentDelayedActor(teacherRef)), "studentDelayedActor")

      studentRef!InitSignal(0)
//      EventFilter.info (start="Printing from Student Actor", occurrences=1).intercept{
//        studentRef!InitSignal(0)
//      }

    }

  }

  def afterAll {
    system.shutdown()
  }
}
