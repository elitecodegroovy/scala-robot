package com.ai

import java.util

import org.scalatest.{Matchers, FlatSpec}

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
class ExampleSpec extends FlatSpec with Matchers {

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new util.Stack[Int]
    stack.push(1)
    stack.push(2)
    stack.pop() should be (2)
    stack.pop() should be (1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new util.Stack[Int]
    a [java.util.EmptyStackException] should be thrownBy {
      emptyStack.pop()
    }
  }

}
