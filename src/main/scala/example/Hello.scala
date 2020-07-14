package example

import example._

object main extends App {
  import  PrintableInstances._
  val kitty = Cat("Mr Pickles", 12, "Ginger")

  Printable.print(kitty)
}
