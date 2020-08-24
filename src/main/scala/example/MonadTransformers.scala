package example

import cats._
import cats.implicits._
import scala.concurrent.Future
import cats.data.EitherT

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.Await

object MonadTransformers extends App {

    type Response[A] = EitherT[Future, String, A]

    def getPowerLevel(autobot: String): Response[Int] = {
        powerLevels.get(autobot) match {
            case Some(value) => EitherT.right(Future(value))
            case None => EitherT.left(Future("Sadness"))
        }
    }
        

    val powerLevels = Map(
        "Jazz"-> 6,
        "Bumblebee"-> 8,
        "HotRod"-> 10
        )


    println(Await.result(getPowerLevel("Jazz").value, 10.seconds))
    println(Await.result(getPowerLevel("NotJazz").value, 10.seconds))


    def canSpecialMove(ally1: String, ally2: String): Response[Boolean] = 
        for {
            p1 <- getPowerLevel(ally1)
            p2 <- getPowerLevel(ally2)
        } yield p1 + p2 > 15
    

    println(Await.result(canSpecialMove("Jazz", "Bumblebee").value, 1.second))
    println(Await.result(canSpecialMove("HotRod", "Bumblebee").value, 1.second))

    def tacticallyReport(ally1: String, ally2: String): String = 
        Await.result(canSpecialMove(ally1, ally2).map(p => if (p) "Transformer go boom" else "No Boom").value, 1.second) match {
            case Left(value) => value
            case Right(value) => value
        }

    
    
}