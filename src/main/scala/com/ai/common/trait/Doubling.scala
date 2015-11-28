package com.ai.common


/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
trait Doubling extends IntQueue {
  abstract override def put(x: Int) { super.put(2 * x) }
}

