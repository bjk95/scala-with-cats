package example

trait Printable[-A]{
    def format(a: A): String
}

object PrintableInstances {
    implicit val stringFormatter: Printable[String] = 
    new Printable[String]{
        def format(value: String): String = value
    }

    implicit val intFormatter: Printable[Int] = 
    new Printable[Int] {
        def format(value: Int): String = value.toString
    }

    implicit def optionFormatter[A](implicit printable: Printable[A]): Printable[Option[A]] = 
    new Printable[Option[A]] {
        def format(value: Option[A]): String = value.fold("")(a => printable.format(a))
    }

    implicit val catFormatter: Printable[Cat]  = 
    new Printable[Cat]{
        def format(value: Cat): String = {
            val name = Printable.format(value.name)
            val age = Printable.format(value.age)
            val color = Printable.format(value.color)

            s"$name is a $age year-old $color cat"
        }
    }
}


object Printable {
    def format[A](value: A)(implicit p: Printable[A]): String =
    p.format(value)
    def print[A](value: A)(implicit p: Printable[A]): Unit =
    println(format(value))
}

object PrintableSyntax{
    implicit class PrintableOps[A](a: A){
        def format(implicit printable: Printable[A]): String = {
            printable.format(a)
        }
        def print(implicit printable: Printable[A]): Unit = {
            println(format)
        }
    }
}


    final case class Cat(name: String, age: Int, color: String)



