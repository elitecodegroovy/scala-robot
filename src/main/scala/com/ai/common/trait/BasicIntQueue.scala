package com.ai.common

import scala.collection.mutable.ArrayBuffer

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
class BasicIntQueue  extends IntQueue {
  private val buf = new ArrayBuffer[Int]
  def get() = buf.remove(0)
  def put(x: Int) { buf += x }
}
