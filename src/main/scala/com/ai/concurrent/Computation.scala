package com.ai.concurrent

import scala.concurrent._
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
object Computation {
  //TODO ...
  def someLengthyComputation = 1
  def anotherLengthyComputation = 2

  //TODO ...
  def f(x: Int) = x + x
  def g(x: Int) = x * x

  //f(sLC) + g(aLC) = 6
  def main(args: Array[String]) {
    val d = Duration("1 sec")
    val fgSum =
      for {
        a <- future(someLengthyComputation) map f
        b <- future(anotherLengthyComputation) map g
      } yield (a + b)
    println("--sum :"  + Await.result(fgSum, d))
    assert(6 == Await.result(fgSum, d))

    //the second approach to get the same result.
    val alt = List(someLengthyComputation _ -> f _, anotherLengthyComputation _ -> g _)
    val all = Future.traverse(alt)(p => future(p._1()) map p._2)
    println("--foldLeft :"  +  Await.result(all, d))
    assert(6 == Await.result(all, d).foldLeft(0)(_ + _))


    //the third approach to get the same result.
    val altf = List(
      future(someLengthyComputation) map f,
      future(anotherLengthyComputation) map g
    )
    val res = Future.fold(altf)(0)(_ + _)
    assert( 6 ==  Await.result(res, d))
  }
}
