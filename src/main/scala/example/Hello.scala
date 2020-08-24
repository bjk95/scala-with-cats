package example

import example._

import cats._
// import cats.instances.all._
import cats.implicits._

object Hello extends App {
  import  PrintableInstances._
  import PrintableSyntax._
  val kitty1 = AnotherCat("Mr Pickles", 12, "ginger")
  val kitty2 = AnotherCat("Miss Pickles", 1, "green")

  val option1 = kitty1 === kitty2
  println(option1)


  val option = Some(1)
  // println(option.show)

  option.print

  val list1 = List(Some(4), Some(9), None)

  val list2: List[String] = list1.collect{
    case Some(value) => value.toString()
    case None => "No existe"
  }
  list2.foreach(println)

}
