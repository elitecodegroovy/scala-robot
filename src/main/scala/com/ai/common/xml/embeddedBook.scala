package com.ai.common.xml

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
object embeddedBook {
  val company  = <a href="http://acme.org">ACME</a>
  val first    = "Burak"
  val last     = "Emir"
  val location = "work"

  val embBook =
    <phonebook>
      <descr>
        This is the <b>phonebook</b> of the
        {company} corporation.
      </descr>
      <entry>
        <name>{ first+" "+last }</name>
        <phone where={ location }>+41 21 693 68 {val x = 60 + 7; x}</phone>
      </entry>
    </phonebook>


  val labPhoneBook =
    <phonebook>
      <descr>
        This is the <b>phonebook</b> of the
        <a href="http://acme.org">ACME</a> corporation.
      </descr>
      <entry>
        <name>Burak</name>
        <phone where="work">  +41 21 693 68 67</phone>
        <phone where="mobile">+41 79 602 23 23</phone>
      </entry>
    </phonebook>

  println( labPhoneBook )

  // XML is immutable - adding an element

  import scala.xml.{ Node, Text }

  def add( phonebook:Node, newEntry:Node ):Node = phonebook match {
    case <phonebook>{ ch @ _* }</phonebook> =>
      <phonebook>{ ch }{ newEntry }
      </phonebook>
  }

  val pb2 =
    add( labPhoneBook,
      <entry>
        <name>Kim</name>
        <phone where="work">  +41 21 111 11 11</phone>
      </entry> )

  def doVerboseXml(): Unit ={
    import scala.xml.{ UnprefixedAttribute, Elem, Node, Null, Text, TopScope }

    val pbookVerbose =
      Elem(null, "phonebook", Null, TopScope, false,
        Elem(null, "descr", Null, TopScope, false,
          Text("This is a "),
          Elem(null, "b", Null, TopScope, false, Text("sample")),
          Text("description")
        ),
        Elem(null, "entry", Null, TopScope, false,
          Elem(null, "name", Null, TopScope, false, Text("Burak Emir")),
          Elem(null, "phone", new UnprefixedAttribute("where","work", Null), TopScope, false,
            Text("+41 21 693 68 67"))
        )
      )
    println( pbookVerbose)
  }
  def main(args: Array[String]): Unit ={
    println( embBook )
    println( pb2 )
    doVerboseXml()
  }
}

