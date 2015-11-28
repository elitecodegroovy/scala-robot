package com.ai.common

import java.io.File
import java.util.Collection

import scala.tools.nsc.interpreter.JCollection

/**
 * @author john.grails@gmail.com
 * @since  2015/11/28..
 */
object Basic {

  def main(args: Array[String]): Unit ={
    breakFunc()
//    printCurrentScalaFileName()
    importPackage
    doBasicOperation()
  }

  def breakFunc(){
    val env = System.getenv("SCALA_HOME")
    val sysProp = System.getProperty("SCALA_HOME")
    val s = sys.env.get("JAVA_HOME")
    if(env == null && sysProp == null && s == None){        //equal Unit
      println("Set the Scala home enviroment")
    }
    else{
      println("found scala home lets"+env+" do the real work" + ", prop:"+ sysProp+ ", s:"+ s)
    }
  }

  def printCurrentScalaFileName(){
    val files = new java.io.File("./src/main/").listFiles
    for(file <- files) {
      printScalaFile(file)
    }
  }

  def printScalaFile(file: File): Unit ={
    if(file.isDirectory()){
      val files = file.listFiles()
//      files.foreach()
//      printScalaFile(file.listFiles())
    }else{
      print("FileName : " + file.getName() + " and related path :");
      val fileName = file.getName()
      if(fileName.endsWith(".scala")) println( "file name: " + file)
    }
  }

  def importPackage(){
    //import class in closure bracket
    val randomValue = { import scala.util.Random
      new Random().nextLong()
    }
    println("random long type value :" + randomValue)
    //Implicit conversion with implicit classes
    //    println(9.201)
    println(2.4)    //It doesn't work.
    //implicit class
    println(new RangeMaker(1).-->(10))

    //call the type parameter
    val xs = List("one", "two", "three")
    println("two in list index value: "+  position(xs, "two"))

    println("None : Int " +position(List(), "something"))
    //position(List("one", "two", "three"), "three").getOrElse(-1)

    val yx = List(3, 6, 9, 12)
    println("two in list index value: "+  position(yx, 9))

    //fold operations
//    println(" add all :" + List(1, 2, 3, 4).foldLeft(0) { _ + _ })
    println("add all :"+ List(1, 2, 3, 4).sum)
    //count the size of the list
    println(List(1, 2, 3, 4, 5, 6).foldLeft(0) { (a, b) => a + 1})
  }

  def position[A](xs: List[A], value: A): Int = {
    xs.indexOf(value)
  }

  class  RangeMaker(val left: Int) extends AnyVal {
    def -->(right: Int): Range = left to right
  }

  class JavaToTraversable[E](javaCollection: JCollection[E]) extends Traversable[E] {
    def foreach[U](f: E => U): Unit = {
      val iterator = javaCollection.iterator
      while (iterator.hasNext) {
        f(iterator.next)
      }
    }
  }

  def doBasicOperation(): Unit ={
    doList


  }

  def doList(): Unit ={
    val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9 ,10)
    //function style iteration, java8 forEach
    numbers.reverse.foreach{ n: Int =>
      print("\t "+ n)
    }
    //take
    print(" take :"+ numbers.take(3)+ " , drop : "+ numbers.drop(7))

    //:: expression
    val simpleList = 6 :: Nil
    val twoList = List(6).:: (5)
    val thirdList = (Nil).:: (9)
    val concatenatedList = twoList::: simpleList:::thirdList
    print(" \t"+ concatenatedList + " mix tpye: " + List(1, 1.00, true))

  }
}
