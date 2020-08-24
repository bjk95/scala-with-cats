import cats._
import cats.implicits._
import scala.language.higherKinds._

object Monads extends App {
    trait Monad[F[_]]{
        def pure[A](a:A): F[A] 

        def flatMap[A,B](value: F[A])(func: A => F[B]): F[B]

        def map[A,B](value: F[A])(func: A => B): F[B] = {
            // pure[B](func(value))
            flatMap(value)(a => pure[B](func(a)))
        }
    }

    type Id[A] = A

    object Id {
        implicit val idMonad: Monad[Id] = {
            new Monad[Id] {
                def flatMap[A, B](value: Id[A])(func: A => Id[B]): Id[B] = {
                    func(value)
                }

                def pure[A](a: A): Id[A] = a

                override def map[A, B](value: Id[A])(func: A => B): Id[B] = {
                    func(value)
                }
            }
        }
    }

}