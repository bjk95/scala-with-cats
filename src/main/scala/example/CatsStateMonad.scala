package example

import cats.data.State
import scala.collection.immutable.Nil
import cats.implicits._

object CatsStateMonad extends App {
    val myState = State[Int, String]{ state =>
     (state, s"State is $state")
    } 

    println(myState.run(5).value)

    type CalcState[A] = State[List[Int], A]

    def evalOne(sym: String): CalcState[Int] = {
            sym match {
                case "+" => operator(_ + _)
                case "-" => operator(_ - _)
                case "*" => operator(_ * _)
                case "/" => operator(_ / _)
                case number => State[List[Int],Int] { old =>
                    (number.toInt :: old, number.toInt)
                }
        }

    }
    
    def operator(f: (Int, Int) => Int): CalcState[Int] = {
        State[List[Int], Int]{ old => 
            old match {
                case head :: second :: next => 
                val answer = f(head, second)
                (answer :: next, answer)
                case _ => throw new Exception("Ohhh no, illegal state")
            }
        
        }
    }

    val state = evalOne("+").run(List(1,2))

    println(state.value)
    // val badState = evalOne("+").run(List(1))

    // println(badState.value)

    def evalAll(inputs: List[String]): CalcState[Int] = 
        inputs.foldLeft(0.pure[CalcState]){ (a, b) =>
            a.flatMap(_ => evalOne(b))     
        }

    val allState = evalAll(List("1", "2", "+", "3", "4","+", "*")).run(Nil)

    println(allState.value)


    def evalInput(s: String): Int = {
        val inputs: List[String] = s.split(" ").toList
        val stateMonad = evalAll(inputs)
        stateMonad.runA(Nil).value
    }


    println("evalInput")
    println(evalInput("1 2 + 3 4 + *"))
        
}