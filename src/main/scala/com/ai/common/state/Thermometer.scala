package com.ai.common.state

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
class Thermometer {

  var celsius: Float = _
  def fahrenheit = celsius * 9 / 5 + 32
  def fahrenheit_= (f: Float) {
    celsius = (f * 32) * 5 / 9
  }
  override def toString = fahrenheit + "F/"+ celsius +"C"
}

object  Thermometer{
  case class Book(title: String, authors: String*)

  def main(args: Array[String]): Unit ={
    val temp = new Thermometer
    println("--"+ temp.celsius)
    temp.celsius = 100
    println("---"+ temp)
    val person = new Person("John", "Nash")
    val person2 = new Person("Johnson", "Lau")
    println("compare two persons:"+ (person > person2))

    for(d <- Direction.values){
      println("position:"+ d+ ", Direction.East.toString:"+ Direction.East.toString)
    }
    val books: List[Book] =
      List(
        Book(
          "Structure and Interpretation of Computer Programs",
          "Abelson, Harold", "Sussman, Gerald J."
        ),
        Book(
          "Principles of Compiler Design",
          "Aho, Alfred", "Ullman, Jeffrey"
        ),
        Book(
          "Programming in Modula2",
          "Wirth, Niklaus"
        ),
        Book(
          "Elements of ML Programming",
          "Ullman, Jeffrey"
        ),
        Book(
          "The Java Language Specification", "Gosling, James",
          "Joy, Bill", "Steele, Guy", "Bracha, Gilad"
        )
      )
    val titles = for(b <- books; a <- b.authors
                     if(a.startsWith("Gosling")))
      yield b.title
    println("Gosling books:"+ titles)
  }
}

