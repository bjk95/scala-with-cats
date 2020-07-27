package example

import cats._
import cats.implicits._
import example.Functors.Branch
import example.Functors.Leaf

object Functors extends App {
    sealed trait Tree[+A]
    final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
    final case class Leaf[A](value: A) extends Tree[A]


    object Tree {
        implicit val treeFunctor: Functor[Tree] = new Functor[Tree]{
            def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = 
                fa match {
                    case Leaf(value) => Leaf(f(value))
                    case Branch(left, right) => Branch(map(left)(f), map(right)(f))
                }
        }
    }



    val b1: Tree[String] = Branch(Leaf("I'm a leaf"), Leaf("Im another leaf"))
    println(b1.map(_.toUpperCase))
    println(b1.map(identity)  == b1)

    println(b1.map(_.capitalize).map(_.replace("leaf", "beef"))  == b1.map(_.capitalize. replace("leaf", "beef")))


}