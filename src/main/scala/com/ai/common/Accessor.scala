package com.ai.common

/**
 * @author john.grails@gmail.com
 * @since  2015/11/29..g
 */
object Accessor {
  var size = 0
  var name = "Scala-lang"
  var greetWords = ", nice to see you!"

  def greet =( s: String ) =>  s + greetWords
  def main(args: Array[String]) {
    size = 1
    println("size="+size)
    size_=(2)
    println("size="+size)

    name = "Scala-lang & Java"
    println(name)

    println(greet("Kay"))
    val greeting = greet
    println("new variable :"+ greeting("John "))
  }
}
