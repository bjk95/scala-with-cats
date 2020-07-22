package example

import example._

import cats._
// import cats.instances.all._
import cats.implicits._

object main extends App {
  import  PrintableInstances._
  import PrintableSyntax._
  val kitty1 = AnotherCat("Mr Pickles", 12, "ginger")
  val kitty2 = AnotherCat("Miss Pickles", 1, "green")

  val option1 = kitty1 === kitty2
  println(option1)


  val option = Some(1)
  // println(option.show)

  option.print

}
