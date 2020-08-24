package example


import cats.Monad
import example.TreeMonad.Branch
import example.TreeMonad.Leaf

object TreeMonad extends App {

    sealed trait Tree[+A]

    final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
    final case class Leaf[A](value: A) extends Tree[A]

    def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = Branch(left, right)
    def leaf[A](value: A): Tree[A] = Leaf(value)

    object Tree {
        implicit val treeMonad = new Monad[Tree]{

          override def flatMap[A, B](fa: Tree[A])(f: A => Tree[B]): Tree[B] = fa match {
              case Branch(left, right) => Branch(flatMap(left)(f), flatMap(right)(f))
              case Leaf(value) => f(value)
          }

          override def tailRecM[A, B](a: A)(f: A => Tree[Either[A,B]]): Tree[B] = ???

          override def pure[A](x: A): Tree[A] = Leaf(x)

            
        }
    }


    val leafExample = leaf(5)

    // leafExample.flatMap(_ * 2)


}