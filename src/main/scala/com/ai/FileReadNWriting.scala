package com.ai

import java.io.{IOException, FileNotFoundException, File, PrintWriter}

import scala.io.Source

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
object FileReadNWriting extends App {
  for (season <- List("fall", "winter", "spring")){
    println("season: " + season )
  }
  new Rational(14, 5)
  //  new Rational(14, 0)   //exceptioin java.lang.IllegalArgumentException

  val pw = new PrintWriter(new File("sql4shop.sql" ))

  /**
   * FileWriter
val file = new File(canonicalFilename)
val bw = new BufferedWriter(new FileWriter(file))
bw.write(text)
bw.close()
   */
  try {
//    val file = Source.fromFile(filename)
    val filename = "/shop_code.txt"
    val file = Source.fromURL(getClass.getResource(filename))
    for (line <- file.getLines()) {
      pw.write("INSERT INTO PAS_SHOP(SHOP_CODE, CREATED_DATE, CREATED_BY, HAS_ACTIVITY, PAS_DEF_ID, PAS_DEF_PARENT_ID)\nVALUES( '" + line + "', now(), '12516', false, 372, -1 );\n")
    }
  }catch {
    case ex: FileNotFoundException => println("Couldn't find that file.")
    case ex: IOException => println("Had an IOException trying to read that file")
  }
  pw.close
}
