package com.ai.actor

import scala.actors.Actor
import scala.io.Source

/**
 * @author john.grails@gmail.com
 * @since  2015/12/3..
 */
class Downloader(urls: List[String])  extends Actor{
  val collector = Actor.actor {
    var count = 3
    var data = ""
    Actor.loop {
      Actor.react {
        case payload: String => {
          Actor.reply ("thank you")
          println ("content size: " + data.length)
          data += payload + "\n\n"
          count -= 1
          if (count == 0) {
            println ("Tasks has been done!")
            sys.exit()
          }
        }
      }
    }
  }

  override def act(): Unit = {
    for(url <- urls){
      val source = Source.fromURL(new java.net.URL(url))
      val data = source.getLines().mkString("\n")
      //    println(data)
      collector! data
      receive{
        case s =>{
          println("done with url: "+ url + ", s:"+ s)
        }
      }
    }
  }
}
