package example

import cats._
import cats.implicits._
import cats.data.Writer
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.duration._

object CatsEval extends App {
    def foldRight[A, B](as: List[A], acc: Eval[B])(fn: (A, Eval[B]) => Eval[B]): Eval[B] =
    as match {
        case head :: tail => 
            Eval.defer(fn(head, foldRight(tail, acc)(fn)) )
        case Nil => acc
    }

    val bigList = List.fill(123456)(666)

    val res = foldRight(bigList, Eval.now(0))((x,y) => Eval.now(0))
    println(res.value)

    type Logged[A]  = Writer[Vector[String], A]

    def slowly[A](body: => A) =
        try body finally Thread.sleep(100)
    
    def factorial(n: Int): Writer[Vector[String], Int] = for{

        ans <-  
            if(n == 0) 1.pure[Logged]
            else slowly( factorial(n - 1).map(_ * n)
            )

        
        _ <- Vector(s"fact$n$ans").tell
        
    } yield ans


    val result = factorial(10).written.apply(5)
    println(result)


    val resss = Await.result(Future.sequence(Vector(
        Future(factorial(3)),
        Future(factorial(5))
    )), 5.seconds)

    resss.foreach(x => println(x.run))
}