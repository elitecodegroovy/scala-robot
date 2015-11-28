package com.ai.common

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
class Rational(n: Int, d: Int) {

  //validate the parameter when constructing the object
  require(d != 0)
  val numer: Int = n
  val denom: Int = d
  def add(that: Rational): Rational =
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )
  println("Created Rational with parameters: "+ n +"/"+ d + ". expression:"+ this.toString)
  /**
   * Override the toString method.
   * @return
   */
  override def toString = n +"/"+ d



}