package example

import cats._
import cats.implicits._

object CatsMonoid {
    def add(items: List[Int]): Int = {
        items.foldLeft(0)((x,y) => x + y)
    }


    def add(items: List[Option[Int]]): Option[Int] = {
        items.foldLeft(Monoid[Option[Int]].empty)((x,y) => x |+| y)
    }

    implicit val orderMonoid = new Monoid[Order]{
        def combine(x: Order, y: Order): Order = {
            Order(x.totalCost + y.totalCost, x.quantity + y.quantity)
        }

        def empty: Order = Order(0,0)
    }

    def superAdder[A](items: List[A])(implicit m: Monoid[A]): A = {
        items.foldLeft(m.empty)((x,y) => m.combine(x,y))
    }
}

case class Order(totalCost: Double, quantity: Double)
