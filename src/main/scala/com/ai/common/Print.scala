package com.ai.common

/**
 * @author john.grails@gmail.com
 * @since  2015/11/29..
 */
trait Print {
  def print(s: String)
  def println()
  def println(s: String): Unit ={
    print(s)
    println()
  }
}
