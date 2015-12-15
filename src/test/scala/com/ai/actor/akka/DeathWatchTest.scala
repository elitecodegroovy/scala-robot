package com.ai.actor.akka

import akka.actor.ActorSystem
import akka.testkit._
import com.ai.actor.akka.reqresp.protocol.QuoteRepositoryProtocol.{QuoteRepositoryRequest, QuoteRepositoryResponse}
import com.ai.actor.akka.reqresp.protocol.TeacherProtocol.QuoteRequest
import com.ai.actor.akka.reqresp.{QuoteRepositoryActor, TeacherActorWatcher}
import com.typesafe.config.ConfigFactory
import org.scalatest.{MustMatchers, WordSpecLike}

import scala.concurrent.duration._

/**
 * @author john.grails@gmail.com
 * @since  2015/12/15..
 */
class DeathWatchTest extends TestKit(ActorSystem("TestUniversityMessageSystem", ConfigFactory.parseString("""
                                            akka{
                                              loggers = ["akka.testkit.TestEventListener"]
                                              test{
                                                  filter-leeway = 7s
                                              }
                                            }
                                                                                                          """)))
with WordSpecLike
with MustMatchers
with ImplicitSender {

  "QuoteRepositoryActor" must {
    "send back" in {
      val quoteRepository = TestActorRef[QuoteRepositoryActor]

      within (1000 millis) {
        var receivedQuotes = List[String]()
        (1 to 3).foreach(i => quoteRepository ! QuoteRepositoryRequest(i))

        receiveWhile() {
          case QuoteRepositoryResponse(quoteString) => {
            receivedQuotes = receivedQuotes :+ quoteString
          }
        }
        println(s"|||||||||||||receiveCount ${receivedQuotes.size}")
        receivedQuotes.size must be (3)
      }
    }


    "send back a termination message to the watcher on 4th message" in {
      val quoteRepository=TestActorRef[QuoteRepositoryActor]

      val testProbe=TestProbe()
      testProbe.watch(quoteRepository) //Let's watch the Actor

      within (1000 millis) {
        var receivedQuotes = List[String]()
        (1 to 3).foreach(i => quoteRepository ! QuoteRepositoryRequest(i))
        receiveWhile() {
          case QuoteRepositoryResponse(quoteString) => {
            receivedQuotes = receivedQuotes :+ quoteString
          }
        }

        receivedQuotes.size must be (3)
        println(s"receiveCount ${receivedQuotes.size}")

        //4th message
        quoteRepository!QuoteRepositoryRequest
        testProbe.expectTerminated(quoteRepository)  //Expect a Terminated Message
      }
    }

    "not send back a termination message on 4th message if not watched" in {
      val quoteRepository=TestActorRef[QuoteRepositoryActor]

      val testProbe=TestProbe()
      testProbe.watch(quoteRepository) //watching

      within (1000 millis) {
        var receivedQuotes = List[String]()
        (1 to 3).foreach(i => quoteRepository ! QuoteRepositoryRequest(i))
        receiveWhile() {
          case QuoteRepositoryResponse(quoteString) => {
            receivedQuotes = receivedQuotes :+ quoteString
          }
        }

        testProbe.unwatch(quoteRepository) //not watching anymore
        receivedQuotes.size must be (3)
        println(s"receiveCount ${receivedQuotes.size}")

        //4th message
        quoteRepository!QuoteRepositoryRequest
        testProbe.expectNoMsg() //Not Watching. No Terminated Message
      }
    }

    "end back a termination message to the watcher on 4th message to the TeacherActor" in {

      //This just subscribes to the EventFilter for messages. We have asserted all that we need against the QuoteRepositoryActor in the previous testcase
      val teacherActor=TestActorRef[TeacherActorWatcher]

      within (1000 millis) {
        (1 to 3).foreach (i =>teacherActor!QuoteRequest(i)) //this sends a message to the QuoteRepositoryActor

        EventFilter.error (pattern="""Child Actor .* Terminated""", occurrences = 1).intercept{
          teacherActor!QuoteRequest //Send the dangerous 4th message
        }
      }
    }
  }


}
