package com.ai.common
/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
abstract class IntQueue {
  def get(): Int
  def put(x: Int)
}