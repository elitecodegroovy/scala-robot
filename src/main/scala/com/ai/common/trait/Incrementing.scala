package com.ai.common

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
trait Incrementing extends IntQueue {
  abstract override def put(x: Int) { super.put(x + 1) }
}