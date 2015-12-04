package com.ai.actor

import scala.actors.Actor
/**
 * @author john.grails@gmail.com
 * @since  2015/12/3..
 */
object actorMain {

  def download(): Unit ={
    val urls: List[java.lang.String] = List(
                       "https://www.baidu.com/",
                      "http://music.baidu.com/",
                    "http://image.baidu.com/",
                      "http://www.infoq.com/cn",
    "https://www.yahoo.com/")
    new Downloader(urls).start

  }

  def main(args: Array[String]): Unit ={
    download()
  }
}
