package example

final case class Box[A](value: A)

object Box{
    implicit def boxPrintable[A](implicit p: Printable[A]): Printable[Box[A]] = {
        p.contramap(_.value)    
    } 

    implicit def boxCodex[A](implicit c : Codec[A]): Codec[Box[A]] = 
     c.imap(Box(_), _.value)
}