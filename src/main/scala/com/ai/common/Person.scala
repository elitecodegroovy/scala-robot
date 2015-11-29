package com.ai.common

/**
 * @author john.grails@gmail.com
 * @since  2015/11/29..
 */
class Person(name: String, age : Int = -1, city: String = "unknown") {

  override def toString =  name + ", age = "+ age + ", city = " + city

  def setEmail(address: String = name + "@gmail.com"): Unit ={
    println("--------set email address:"+ address)
  }


}
