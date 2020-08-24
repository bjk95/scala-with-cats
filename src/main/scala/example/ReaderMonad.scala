package example

import cats._
import cats.implicits._
import cats.data.Reader


object ReaderMonad extends App {
    case class DB(
        users: Map[Int, String],
        pw: Map[String, String]
    )

    type DbReader[A] = Reader[DB, A]

    def findUsername(userId: Int): DbReader[Option[String]] = 
        Reader(_.users.get(userId))

    def checkPassword(username: String, password: String): DbReader[Boolean] = 
         Reader( db => db.pw.get(username).contains(password))

    def checkLogin(userId: Int, password: String): DbReader[Boolean] = 
        for {
            username <- findUsername(userId)


            passwordCorrect <- username match {
                case Some(value) => checkPassword(value, password)
                case None => false.pure[DbReader]
            }
        } yield passwordCorrect
    
    
    val users = Map(1 ->"dade",2 ->"kate",3 ->"margo")
    val passwords = Map(
        "dade"->"zerocool", 
        "kate"->"acidburn"
        ,"margo"->"secret"
    )

    println(findUsername(2).run(DB(users, passwords)))
    println(checkLogin(2, "acidburn").run(DB(users, passwords)))
}