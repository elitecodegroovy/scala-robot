package com.ai.common.impli

/**
 * @author john.grails@gmail.com
 * @since  2015/12/5..
 */
object Idea {

  def foo(f: String) = Console.println("input value:"+ f)

  implicit def intToString(x : Int) = x.toString

  def main(args: Array[String]): Unit ={
    foo(50)
  }
}

