package com.ai.common

import com.wisdom.common.ScalaParameterName

/**
 * @author john.grails@gmail.com
 * @since  2015/11/29..
 */
class Person(
              @ScalaParameterName("name") name: String,
              @ScalaParameterName("age") age: Int = -1,
              @ScalaParameterName("city") city: String = "unknown"
              ) {

  override def toString =  name + ", age = "+ age + ", city = " + city

  def setEmail(address: String = name + "@gmail.com"): Unit ={
    println("--------set email address:"+ address)
  }


}
