package com.ai.concurrent

import java.util.concurrent.TimeUnit

import org.scalatest.{MustMatchers, WordSpecLike}
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global


/**
 * @author john.grails@gmail.com
 * @since  2015/12/12..
 */
class MultiExec extends WordSpecLike with MustMatchers{
  import scala.math.BigInt
  lazy val fibs: Stream[BigInt] = BigInt(0) #:: BigInt(1) #::
    fibs.zip(fibs.tail).map { n => n._1 + n._2}

  "Future" should {
    "calculate fibonacci numbers" in {
      import scala.concurrent.{ExecutionContext, Future, Await}
      import java.util.concurrent.Executors
      // We need to create the ExecutionContext, which we can build from an
      // existing ExecutorService from plain ol' java.util.concurrent
      val execService = Executors.newCachedThreadPool()
      val execContext = ExecutionContext.fromExecutorService(execService)
      // We pass the ExecutionContext to the Future on which it will execute
      val futureFib = Future {
        fibs.drop(99).head
      }(execContext)
      // We then use the Await object's result() funtion to block the current
      // thread until the result is available or 1 second has passed
      val fib = Await.result(futureFib, Duration(1, TimeUnit.SECONDS))
      // Just make sure it's cool
      println(s" ----fibonacci 1...99: ${fib}")
      fib must be(BigInt("218922995834555169026"))
      // Shut down the ExecutionContext or this thread will never die
      execContext.shutdown()
    }
  }

  "Promise" should {
    "future and promise demo " in {
      import scala.concurrent.{Await, Future}
      import scala.concurrent.Promise

      // Create a Promise
      val promise = Promise[String]()
      // Get the associated Future from that Promise
      val future = promise.future
      // Successfully fulfill the Promise
      promise.success("I always keep my promises!")
      // Extract the value from the Future
      println(future.value)

      val noOops = Future { 1 to 10 } map { seq =>
        seq filter { _ % 2 == 0 }
      }
      println(Await.result(noOops, Duration(1, TimeUnit.SECONDS)))

      //two ways to deal with the exception: fallbackTo , recover
      val oops = Future(5) filter { _ % 2 == 0 } fallbackTo Future(5)
      val result = Await.result(oops, Duration(1, TimeUnit.SECONDS))
      result must be (5)

      val oops2 = Future(5) filter { _ % 2 == 0 } recover {
        case e: NoSuchElementException =>5
      }
      val result2 = Await.result(oops2, Duration(1, TimeUnit.SECONDS))
      result2 must be (5)

      val oops3 = Future(5) filter { _ % 2 == 0 } recover{
        // This doesn't throw an ArithmeticException
        case e: ArithmeticException => 5
      }
      // Test with an assertion
      an [NoSuchElementException] must be thrownBy {
        val result = Await.result(oops3, Duration(1, TimeUnit.SECONDS))
      }
    }
  }

  "RecoverWith" should {
    "RecoverWith case" in {
      class CacheTimedOutException(msg: String) extends Exception(msg)
      val longCalculation = Future{
        // It takes a long time to calculate 5... really
        5
      }
      val value = Future{
        // Except it's not
        throw new CacheTimedOutException("That didn't work")
      } recoverWith {
        case _: CacheTimedOutException =>
          longCalculation
      }
      Await.result(value, Duration(1, TimeUnit.SECONDS)) must be (5)
    }
  }

  "future.fold" should {
    "Future fold case" in {
      // Some of my favourite words
      val words = Vector("Joker", "Batman", "Two Face", "Catwoman")
      // Transform the words into Futures
      val futures = words map { w =>
        Future {
          val sleepTime = scala.util.Random.nextInt(15)
          Thread.sleep(sleepTime)
          println(s"$w finished after $sleepTime milliseconds")
          w
        }
      }
      // Fold over them, adding up their ASCII values
      val sum = Future.fold(futures)(0) { (acc, word) =>
        print("\t"+acc + " , "+ word)
        word.foldLeft(acc) { (a, c) =>
          print(" \t"+ a + ", "+ c)
          a + c.toInt }
      }
      // Assert
      println("Waiting for result")
      Await.result(sum, Duration(1, TimeUnit.SECONDS)) must be (2641)
    }
  }

  "Future reduce" should {
    "Future reduce case " in {
      val letters = Vector("B", "a", "t", "m", "a", "n")
      // Transform the letters into Futures
      val futures = letters map { l =>
        Future {
          val sleepTime = scala.util.Random.nextInt(15)
          Thread.sleep(sleepTime)
          println(s"$l finished after $sleepTime milliseconds")
          Thread.sleep(sleepTime)
          l
        }
      }
      // Reduce the letters down to a single word
      val wordFuture = Future.reduce(futures) { (word, letter) =>
        word + letter
      }
      // Assert
      println("Waiting for result")
      Await.result(wordFuture, Duration(1, TimeUnit.SECONDS)) must be ("Batman")
    }
  }

  "Future.find" should {
    "find" in {
      val letters = Vector('B', 'a', 't', 'm', 'a', 'n')
      // Transform the letters into Futures
      val futures = letters map { l =>
        Future{
          Thread.sleep(scala.util.Random.nextInt(15))
          l
        }
      }
      // find anything less than or equal to 'm'
      val foundFuture = Future.find(futures) { l => l <= 'm'}
      val found = Await.result(foundFuture, Duration(1, TimeUnit.SECONDS))
      println("Found " + found)
    }
  }

  "onSuccess or onFailure" should {
    "future onSuccess" in {
      Future { 13 } filter{
        _ % 2 == 0
      } fallbackTo Future{
        "That didn't work."
      } onSuccess {
        case i: Int => println("Disco!")
        case m => println("Boogers! " + m)
      }

      Future { 13 }.filter{
        _ % 2 == 0
      }.onFailure {
        case _ => println("onFailure Boogers! That didn't work.")
      }

      // Prints: Boogers! That didn't work.
    }
  }
}
