package com.ai.reflection

/**
 * @author john.grails@gmail.com
 * @since  2015/12/3..
 */

object ScalaReflection {
  /**
   * it is a handy class allowing for very limited type reification
   */
  def inspect[T](l: List[T])
                (implicit manifest: scala.reflect.Manifest[T]) = println(manifest.toString())

  def isA[A](o: Object)(implicit m: scala.reflect.Manifest[A]) ={
     val `class` = Class.forName(m.toString)
     `class`.isAssignableFrom(o.getClass)
  }



  def main(args: Array[String]): Unit = {
    print("int type :" + inspect(List(1, 2, 3, 4)))
    println("-----2:" + inspect(List(List(1, 2), List(3, 4))))
    println("-----3:" + inspect(List(List(List(1), List(2)), List(List(3), List(4)))))
    val l:List[Iterable[Int]] = List(List(1,2))
    println("-----4"+ inspect(l))

    println("Integer judgement:"+ isA[java.lang.Integer](java.lang.Integer.valueOf(19)))
  }
}