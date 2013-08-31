package org.scality.pattern.typeclass

import scala.reflect.ClassTag

trait T[A] {
  val name: String
  def run: Unit =
    println(name)
}

class C

object TypeClass {

  def foo[A: ClassTag](size: Int): Array[A] =
    new Array[A](size)

  def bar[A: T](evd: T[A]): Unit = {
    val c = implicitly[T[A]]
    println(c == evd)
  }

  def main(args: Array[String]): Unit = {
    implicit val evidence = new T[C] { val name = "xxx" }
    val evd = new T[C] { val name = "xxx" }
    bar[C](evidence)
    bar[C](evd)
  }
}
