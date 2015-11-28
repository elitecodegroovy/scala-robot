package com.ai.common


/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
/**
 *  *  Visibility and companion objects:
 *  Scala’s access rules privilege companion objects and classes when it
 *  comes to private or protected accesses. A class shares all its access rights
 *  with its companion object and vice versa. In particular, an object can access
 *  all private members of its companion class, just as a class can access all
 *  private members of its companion object.
 * @author liujignag@biostime.com
 * @since 1.6
 */
class Rocket {
  import Rocket.fuel

  def say(): Unit ={
    println("call Rocket class method say:" )
  }
  private def canGoHomeAgain = fuel > 20
}

object Rocket {
  private def fuel = 10
  var size = 0
  var userName = "John Lau"
  def greet = (s: String) => s + userName
  def chooseStrategy(rocket: Rocket) {
    if (rocket.canGoHomeAgain)
      goHome()
    else
      stayHere()
  }
  def goHome(): Unit = {
    println("You should go home!")
  }
  def stayHere(): Unit = {
    println("Please stay here!")
  }

  def say(): Unit ={
    println("call Rocket object method say, which is hidden static." )
  }
  def main(args: Array[String]): Unit ={
    size = 1
    chooseStrategy(new Rocket)
    size_=(2)
    val hi = greet
    println("----"+ hi(" Hi, "))
  }
}
