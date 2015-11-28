package com.ai.common

import java.io.{PrintWriter, File}
import java.text.SimpleDateFormat

/**
 * @author liujignag@biostime.com
 * @since 1.6
 *        Any
 *      /     \
 *   AnyRef     AnyVal  (built-in value class)
 *
 *  1. Unit has a single instance value, which is written ().
 *  2. Once a trait is defined, it can be mixed in to a class using either the
 *     extends or with keywords. Scala programmers “mix in” traits rather than
 *     inherit from them, because mixing in a trait has important differences from
 *     the multiple inheritance found in many other languages.
 *     At this point you might philosophize that traits are like Java interfaces
 *     with concrete methods, but they can actually do much more. Traits can, for
 *     example, declare fields and maintain state.The other difference between
 *     classes and traits is that whereas in classes,super calls are statically
 *     bound, in traits, they are dynamically bound.
 * 3. To trait, or not to trait
 *    If the behavior will not be reused, then make it a concrete class. It is not
      reusable behavior after all.
      If it might be reused in multiple, unrelated classes, make it a trait. Only
      traits can be mixed into different parts of the class hierarchy.
      If you want to inherit from it in Java code, use an abstract class. Since
      traits with code do not have a close Java analog, it tends to be awkward to
      inherit from a trait in a Java class. Inheriting from a Scala class, meanwhile,
      is exactly like inheriting from a Java class.
 */
object funcationProg {
  var increase = (x: Int) => {
    println("first-class functions")
    x + 1
  }

  def position[A](xs: List[A], value: A): Int = {
    //    val index = xs.indexOf(value)
    //    if(index != -1) Just(index) else Nil
    xs.indexOf(value)
  }

  def fileLines(file: java.io.File) =
    scala.io.Source.fromFile(file).getLines().toList

  /**
   * grep content.
   * @param pattern
   */
  def grep(pattern: String): Unit = {
    val filesHere = (new java.io.File(".")).listFiles
    for (file <- filesHere
         if  file.getName.endsWith(".sql") || file.getName.endsWith(".txt");
         line <- fileLines(file)
         if line.trim.matches(pattern)
    ) {
      println(file.getCanonicalPath +" file contained: "+ line.trim)
    }
  }

  def makeRowSeq(row: Int) =
    for (col <- 1 to 10) yield {
      val prod = (row * col).toString
      val padding = " " * (4 - prod.length)
      padding + prod
    }
  // Returns a row as a string
  def makeRow(row: Int) = makeRowSeq(row).mkString
  // Returns table as a string with one row per line
  def multiTable() = {
    // a sequence of row strings
    val tableSeq =
      for (row <- 1 to 10)
        yield makeRow(row)
    print(tableSeq.mkString("\n"))

    println("--increase(10):" + increase(10))
  }

  def filterList(): Unit ={
    val someNumbers = List(11, 10, 5, 0, 5, 10)
    println("bigger than zero: " + someNumbers.filter((x: Int) => x > 0))
    println("bigger than zero: " + someNumbers.filter(x => x > 0))
    println("bigger than zero: " + someNumbers.filter(_ > 0))

    val f = (_: Int) + (_: Int)
    println("placeholder syntax:"+ f(1, 2))
    someNumbers.foreach(print _)
  }

  def funApply(): Unit ={
    def sum(a: Int, b: Int, c: Int) = a + b + c
    val a = sum _
    println(" a called the reference directly:"+ a(1, 3, 6))
    println("a called the apply method: "+ a.apply(1, 3, 6))

    val b = sum(1, _:Int, 6)
    println(" b called the summer:"+ b(3))
  }

  def callSpecialFormFunc(): Unit ={
    def echo(args: String*) =
      for(arg <- args) print( arg+ ",")
    echo("Hi!")
    echo("Hello", "John Nash")
    val attr = Array("What's", "up", "!")
    echo(attr: _*)

    def speed(distatnce: Float, time: Float): Float = distatnce/time
    println("speed: "+ speed(100, 10))
    println("--"+ speed(time= 10, distatnce = 100))

    //default parameter
    def printTime(out: java.io.PrintStream = Console.out, divisor: Int =1000) =
      out.print("time:"+ System.currentTimeMillis()/divisor+ "s")

    printTime(Console.err)

    val nums = List(2, 50, -2, 0)
    println("nums contained the negative value:" +containNeg(nums))
    println("nums has odd value:"+ containsOdd(nums))
  }

  def containsOdd(nums: List[Int]) = nums.exists(_ % 2 == 1)

  def containNeg(nums: List[Int]) = nums.exists(_ < 0)

  //functional programming technique called currying
  def doCurryFunc(): Unit ={
    def currySum(x: Int)(y: Int) = x + y
    println(" curry func:"+ currySum(2)(1))

    def first(x: Int) = (y: Int) => x + y
    val second = first(1)
    println("-----" + second(2))
    val onePlus = currySum(1)_
    println("+++++"+ onePlus(2))

    def withPrintWriter(file: File)(op: PrintWriter => Unit): Unit ={
      val writer = new PrintWriter(file)
      try{
        op(writer)
      }finally{
        writer.close()
      }
    }

    val file = new File("date.txt")
    withPrintWriter(file) {
      writer => writer.println(new SimpleDateFormat("yyyy-MM-dd hh:MM:ss").format(new java.util.Date))
    }
  }

  //two-dimensional layout library
  def doLayoutLib(): Unit ={
    //    val column1 = elem("hello") above elem("***")
    //    val column2 = elem("***") above elem("world")
    //    column1 beside column2
  }

  def main(args: Array[String]): Unit = {
    val xs = List("one", "two", "three")
    println("list xs indexOf : " + position(xs, "two"))

    val ys = List(20, 30, 40)
    println("list yz indexOf : " + position(ys, 20))
    println("list yz indexOf : " + position(ys, 31))

    val filesHere = new java.io.File(".").listFiles
    for (file <- filesHere if file.getName.endsWith(".sql")|| file.getName.endsWith(".txt")){
      println(file.getCanonicalPath)
    }

    //to expresion
    for (i <- 1 to 4){
      for (j <- 1 until 4){
        print("\t  j:"+ j+ ", i:" + i)
      }
    }

    //grep functionality
    println()
    //    grep(".*\\d{3,}.*")
    //    grep(".*VALUE.*")
    multiTable()

    //Scala offers an additional approach: you can define functions inside other functions.
    filterList()
    //call special function form
    callSpecialFormFunc()
    //curry style function
    doCurryFunc()
  }
}
