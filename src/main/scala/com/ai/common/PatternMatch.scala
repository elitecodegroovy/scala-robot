package com.ai.common

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
 * Sealed classes
 * The alternative is to make the superclass of your case classes sealed.
 * A sealed class cannot have any new subclasses added except the ones in the
 * same file. This is very useful for pattern matching, because it means you only
 * need to worry about the subclasses you already know about.
 *
 * Option Type
 * Scala has a standard type named Option for optional values. Such a value
 * can be of two forms. It can be of the form Some(x) where x is the actual
 * value. Or it can be the None object, which represents a missing value.
 * @author liujignag@biostime.com
 * @since 1.6
 * @author liujignag@biostime.com
 * @since 1.6
 */
object PatternMatch {

  def show(x: Option[String]) = x match {
    case Some(s) => s
    case None => "?"
  }

  /**
   * splitAt, span avoids traversing the list xs twice
   */
  def doList(): Unit ={
    val allList = List(List(11, 12), List(13), List(), List(4, 15)).flatten
    println(allList)
    print("\t take(2) " + allList.take(2))
    print("\t drop(2) " + allList.drop(2))
    print(" \t partion:"+ allList.partition(_  % 2 == 0))
    print(" \t allList.find( _ == 4)) "+ allList.find( _ == 4))

    val fruit = "apples" :: ("oranges" :: ("pears" :: Nil))
    val conbinatorList = List(1, 2) ::: List(13, 14, 15)
    //    println(conbinatorList.flatten)
    println(conbinatorList.head)
    println(conbinatorList.tail.reverse)
    println(conbinatorList.take(2))
    println(conbinatorList.drop(2))
    print("\t ---zip: "+ (conbinatorList.indices zip allList))
    print("\t -zipWithIndex:"+ conbinatorList.zipWithIndex)
    print("\t -mkString;"+ conbinatorList.mkString("\t,"))
    print("\t -toArray:"+ conbinatorList.toArray.foreach(print(_)))

    val attr2 = new Array[Int](10)
    conbinatorList copyToArray(attr2, 3)
    println(" copyToArray:"+ attr2.foreach(print(_)))

    val words = List("the", "quick", "brown", "fox")
    println("List map:"+ words.map("'" + _ + "'"))
    println("List map toList:"+ words.map(_.toList))
    println("List flatMap toList:"+ words.flatMap(_.toList))
    println("for arrange yield collection:"+ ( for (i <- List.range(1, 5); j <- List. range(1, i)) yield (i, j)))
    println("filter:"+ List(1, 3, 10, 2).filter(_ > 3))

    println(" takeWhile"+List(-1, -2, 3, 4, 5) takeWhile (_ > 3))
    //    println(" takeWhile"+ words dropWhile(_startsWith "t"))

    println("fold element: "+ sum(List(1, 3, 6)))
    println("sort List element:"+ List(1, 3, 4, 2, 6) sortWith (_ < _))

    //concatenate multiple List to one
    val concatList = List.concat(List("a", "b"), List("3"))
    println("concatenate lists: "+ concatList)

    //zip and map
    val myZipped = (List(10, 20) , List(3, 5, 10)).zipped
    println("zip two List and tuple size:"+ myZipped.size)
    println("map zipped variable:"+ myZipped.map(_ * _))

    //ListBuffer operation
    val buf = new ListBuffer[Int]
    buf += 1
    buf += 2
    buf += 3
    println(" ListBuffer operation result:"+ buf.toList)

    val arrayBuf = new ArrayBuffer[Int]
    arrayBuf += 5
    arrayBuf += 6
    arrayBuf += 7
    println("ArrayBuffer operation, ArrayBuffer size:"+ arrayBuf.size+ ", [2] lement:"+ arrayBuf(2))

    println("mutable.Map operation:"+ countWords("See Spot run! Run"))
    var nameSet = Predef.Set("John", "Johnson", "Joe")
    nameSet += "Lucy"
    println("immutable Set operation:+=:" + nameSet)
    nameSet -= "John"
    println("immutable Set operation:-=:" + nameSet)
    nameSet ++= Predef.Set("Kate", "Jane")
    println("immutable Set operation ++= "+ nameSet)

    //use a collection to be parameters for another collection
    val colors = List("Red", "Yellow", "Green", "Blue")
    val treeSet = scala.collection.immutable.TreeSet[String]() ++ colors
    println("immutable treeSet operation: "+ treeSet)
    println(" convert treeSet to List collection:"+ treeSet.toList)

    //Converting between mutable and immutable sets and maps
    val mutableTreeSet = scala.collection.mutable.Set.empty ++=treeSet
    println("convert from treeSet to mutable Set:"+ mutableTreeSet)
    val immutableSet = scala.collection.immutable.Set.empty ++ mutableTreeSet
    println("convert from mutable Set to immutable Set " + immutableSet)

    println("find the longest word in 'convert from mutable Set to immutable Set'" +
      ""+ getLongestWord("convert from mutable Set to immutable Set".split("[ !,.]+")))
    val longest = getLongestWord("convert from mutable Set to immutable Set".split("[ !,.]+"));
    println("--tuple value 1:"+ longest._1)
    println("--tuple value 2:"+ longest._2)
    val (word, index) = longest
    println("building new tuple:"+ (word, index))
    val _word, _index = longest
    println("building two tuple :"+ _word +" , "+ _index)
  }

  def getLongestWord(words: Array[String]) = {
    var word = words(0)
    var index = 0
    for(i <- 1 to (words.length-1)){
      if(words(i).length > word.length){
        word = words(i)
        index = i
      }
    }
    (word, index)
  }
  /**
   * One other sequence to be aware of is StringOps, which implements many
   * sequence methods.
   * Because Predef has an implicit conversion from String
   * to StringOps, you can treat any string like a sequence
   * The easy access is provided
   * via the Predef object, which is implicitly imported into every Scala
   * source file.
   */
  def doString(): Unit ={
    def hasUpperCase(s: String) = s.exists(_.isUpper)
    println("string upperCase:"+ hasUpperCase("Welcome"))
  }

  def countWords(text: String) ={
    val counts = scala.collection.mutable.Map.empty[String, Int]
    for(rawWord <- text.split("[ ,!.]+")){
      val word = rawWord.toLowerCase()
      val count = if(counts.contains(word)) counts(word)
      else 0
      counts += (word -> (count + 1))
    }
    counts
  }
  //fold operation
  def sum(e: List[Int]): Int = (0 /: e)(_ + _)

  def doMap(): Unit ={
    val capitals = Map("France" -> "Paris", "Japan" -> "Tokyo")
    println("" + show(capitals get "France"))
    println("" + show(capitals get "Beijing"))

    for ((country, city) <- capitals)
      println("The capital of "+ country +" is "+ city)
  }
  def main(args: Array[String]): Unit ={

    doMap
    doList
    doString

    System.out.println("-------------------------------------------")
  }

}
