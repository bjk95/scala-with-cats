package example

import cats._
import cats.instances.all._
import cats.implicits._
import cats.syntax.all._

import cats.Eq


final case class AnotherCat(name: String, age: Int, color: String)

object AnotherCat {
    implicit val catShow: Show[AnotherCat] = 
    Show.show(kitty => s"${kitty.name} is ${kitty.color} cat that is ${kitty.age} years old")


    implicit val catEq: Eq[AnotherCat] = 
    Eq.instance[AnotherCat]{(cat1, cat2) => 
        cat1.name === cat2.name && cat1.age === cat2.age && cat1.color === cat2.color 
    }
    

}



