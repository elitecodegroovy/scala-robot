package com.ai.common

/**
 * @author john.grails@gmail.com
 * @since  2015/11/28..
 */
class ThinkingInScala {
  def think() { println("thinking in scala (class)") } // [1]
}
object ThinkingInScala{
  def study() { println("studying (object)") } // [2]
  def think() { println("thinking in scala (object)") } // [3]
}
