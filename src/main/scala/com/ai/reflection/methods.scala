package com.ai.reflection

/**
 * @author john.grails@gmail.com
 * @since  2015/11/29..
 */
object methods extends  App{
  def printMethods[T](t: T) { // requires instance
  val meths = t.getClass.getMethods
    println(meths take 5 mkString "\n")
    println("---------------------------------")
  }
  def printMethods1(name: String) { // low-level
  val meths = Class.forName(name).getMethods
    println(meths take 5 mkString "\n")
    println("---------------------------------")
  }
  def printMethods2[T: Manifest] { // no instance
  val meths = manifest[T].runtimeClass.getMethods
    println(meths take 5 mkString "\n")
    println("---------------------------------")
  }
  printMethods(Some(""))
  printMethods1("scala.Some")
  printMethods2[Some[_]]
}
