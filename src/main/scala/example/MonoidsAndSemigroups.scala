package example

import cats._
import cats.implicits._

trait Semigroup[A]{
    def combine(x:A, y: A): A
}

trait Monoid[A] extends Semigroup[A]{
    def empty: A
}

object Monoid {
    def apply[A](implicit monoid: Monoid[A])=
     monoid

     implicit val boolMonoid: Monoid[Boolean] = {
         new Monoid[Boolean] {
             def combine(x: Boolean, y: Boolean): Boolean = {
                 x && y
             }

             def empty: Boolean = true
         }
     }


     implicit def setMonoid[A]: Monoid[Set[A]] = {
         new Monoid[Set[A]] {
             def combine(x: Set[A], y: Set[A]): Set[A] = {
                x.union(y)
             }


             def empty: Set[A] = Set.empty[A]
     }
}}