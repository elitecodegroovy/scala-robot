package com.ai.common

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
object MainTrait {

  def main(args:Array[String]): Unit ={
    def myPhilosophy = new MyPhilosophy()
    myPhilosophy.philosophize()

    val oneRef: Philosophy = myPhilosophy
    oneRef.philosophize()

    val myQueue = new BasicIntQueue
    myQueue.put(1)
    myQueue.put(2)
    println("---"+ myQueue.get())

    val _queue = new MyQueue
    _queue.put(12)
    println("myQueue: "+ _queue.get())
  }

}
