package com.ai.common

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
object base {
  /**
   * general :
   * scalac ChecksumAccumulator.scala Summer.scala
   * fast way:
   * fsc ChecksumAccumulator.scala Summer.scala
   * @param args
   */
  def main(args: Array[String]): Unit = {
    expArray()
    expList()
    expTuple()
    expSetMap()
    readFromFile()  //TODO
    println(say("Johnson"))
    println(myFirstMethod())
    //max
    println(max(5, 7))

    //pattern match
    ordinal(2)
    ordinal(12)
    printType(List(1, 2, 3))
    printType(new java.util.Date())
    myArrangeMatch(101)
    myArrangeMatch(100001)
  }

  // Array is a mutable sequence of objects that all share the same type.
  def expArray(): Unit ={
    val greetStrings: Array[String] = new Array[String](3)
    greetStrings(0) = "Hello"
    greetStrings(1) = " World"
    greetStrings(2) = " !"
    for (i <- 0 to 2){
      print(greetStrings(i))
    }
    greetStrings.update(2, ".")
    println()
    //    for (i <- 0 to 2){
    //      print(greetStrings(i))
    //    }
    greetStrings.foreach(print)

    val names = Vector("Bob", "Fred", "Joe", "Julia", "Kim")
    for( name <- names){
      print(" "+ name)
    }
    println()
  }

  //Scala Lists are always immutable
  def expList(): Unit ={
    val oneTwo = List(1, 2)
    val threeFour = List(3, 4)
    val oneTwoThreeFour = oneTwo ::: threeFour
    println(oneTwo +" and "+ threeFour +" were not mutated.")
    println("Thus, "+ oneTwoThreeFour +" is a new list.")

    //
    val builtList = 1:: 2 :: 3:: Nil
    println("build list instance: " + builtList)
  }

  /**
   * Like lists, tuples are immutable,
   * but unlike lists, tuples can contain different types of elements.
   * Once you have a tuple instantiated, you can access its elements
   * individually with a dot, underscore, and the one-based index of the element
   */
  def expTuple(): Unit ={
    println("\n----------tuple-------------------")
    val pair = (99, "glorious")
    print(pair._1)
    print("\t")
    print(pair._2)
  }

  /**
   * Scala also provides mutable and immutable
   * alternatives for sets and maps, but uses the same simple names for both versions.
   * For sets and maps, Scala models mutability in the class hierarchy.
   * Scala then provides two subtraits, one for mutable sets and another for immutable
   * sets.
   * the Scala API contains a base trait for sets, where a trait is
   * similar to a Java interface.
   */
  def expSetMap(): Unit ={
    println(" ---------------------Set Map collection ----------------- " )
    val movieSet = scala.collection.mutable.Set("Forst Gamp", "The beautiful mind")
    movieSet += "Shrek" //movieSet.+=("Shrek")
    println(" mutable set collection: " + movieSet)
    val hashSet = scala.collection.immutable.HashSet("Tomatoes", "Chilies")
    println(hashSet + "Coriander")

    val treasureMap = scala.collection.mutable.Map[Int, String]()
    treasureMap += (1 -> " You are great!")
    treasureMap += (2 -> " You are nice!")
    treasureMap += (3 -> " It is kind of you!")
    println("treasureMap index 2 value:" + treasureMap(2))

    val helloMap = scala.collection.immutable.Map(1 -> "Hi!", 2 -> "Hello!", 3 -> "Nice to meet you!")
    println(helloMap )

    val res = formatArgs(Array("zero", "one", "two"))
    assert(res == "zero,one,two")
  }

  def readFromFile(): Unit ={
    val fileName = "someFile.txt"
    for (line <- scala.io.Source.fromFile(fileName).getLines()){
      println(line)
    }
  }

  def formatArgs(args: Array[String]) = args.mkString(",")
  def say(name: String): String = { "Excited time ahead!" + name}

  //When calling the function you can also leave out the parentheses:
  def myFirstMethod() = "exciting times ahead" //def myFirstMethod = "exciting times ahead"

  def max(a: Int, b: Int) = if(a > b) a else b

  //generic type
  def toList[A](value:A) = List(value)

  //pattern marching
  def ordinal(number: Int) = number match{
    case 1 => println("1st");
    case 2 => println("2nd");
    case 3 => println("3rd");
    case 4 => println("4th");
    case _ => println("Cannot do beyond 4")
  }

  def printType(obj: AnyRef)= obj match{
    case s:String => println("String input ");
    case l: List[_] => println("This is List")
    case a: Array[_] => println("This is an array")
    case d: java.util.Date => println("This is a date")
    case _ => println("Can't support the type you input.")
  }

  //arrange match
  def myArrangeMatch(num: Int) = num match{
    //case tenTo20 if 0 to 20 contains tenTo20 => number + "th"
    case with10 if with10 <= 10 => println(" Your number is in 10")
    case with100 if with100 <= 100 => println(" Your number is in 10 to 100")
    case with1000 if with1000 <= 1000 => println(" Your number is in 100 to 1000")
    case _ => println("Can't support the number you input.")
    //    case _ => throw new IllegalArgumentException(
    //      "Only values between 0 and 1000 are allowed")
  }
}
