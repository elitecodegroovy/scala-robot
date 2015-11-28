package com.ai.common.sort

/**
 *  fast sort algorithm
 * @author liujignag@biostime.com
 * @since 1.6
 */
object simpleSort {
  def sort(a: Array[Int]) {

    def swap(i: Int, j: Int) {
      val t = a(i);
      a(i) = a(j);
      a(j) = t
    }

    def sort1(l: Int, r: Int) {
      val pivot = a((l + r) / 2)
      var i = l
      var j = r
      while (i <= j) {
        /**
         * Changed the operator , then you can get the reverse result (descent or ascend order)
         * < , >   ascend order
         * >, <    descend order
         */
        while (a(i) > pivot) {
          i += 1
        }
        while (a(j) < pivot) {
          j -= 1
        }
        if (i <= j) {
          swap(i, j)
          i += 1
          j -= 1
        }
      }
      if (l < j) sort1(l, j)
      if (j < r) sort1(i, r)
    }

    if (a.length > 0)
      sort1(0, a.length - 1)
  }

  def doSimpleSort(a: List[Int]): List[Int] = {
    if (a.length < 2)
      a
    else {
      val pivot = a(a.length / 2)
      doSimpleSort(a.filter(x => x > pivot)) :::
        a.filter(x => x == pivot) :::
        doSimpleSort(a.filter(x => x < pivot))
    }
  }

  def doSimpleSort2(a: List[Int]): List[Int] = {
    if (a.length < 2)
      a
    else {
      val pivot = a(a.length / 2)
      def lePivot(x: Int) = x < pivot
      def gtPivot(x: Int) = x > pivot
      def eqPivot(x: Int) = x == pivot
      doSimpleSort2(a filter lePivot) :::
        (a filter eqPivot) :::
        doSimpleSort2(a filter gtPivot)
    }
  }

  /**
   * print all array elements with the String Type . .
   * @param ar Array object .
   */
  def doPrintln(ar: Array[Int]) {
    def print1 = {
      def iter(i: Int): String =
        ar(i) + (if (i < ar.length - 1) "," + iter(i + 1) else "")
      if (ar.length == 0) "" else iter(0)
    }
    Console.println("[" + print1 + "]")
  }

  def fastSort(): Unit = {
    val ar = Array(6, 66, 2, 5, 8, 9, 10, 1)
    doPrintln(ar)
    sort(ar)
    doPrintln(ar)
  }

  def doListSimpleSort(): Unit = {
    val list = List(6, 66, 2, 5, 8, 9, 10, 1)
    println(list)
    val sortedList = doSimpleSort(list)
    val converseList = doSimpleSort2(list)
    println(sortedList)
    println(converseList)
  }


  def main(args: Array[String]): Unit = {
    fastSort()
    doListSimpleSort()
  }
}