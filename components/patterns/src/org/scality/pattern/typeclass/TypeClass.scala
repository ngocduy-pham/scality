package org.scality.pattern.typeclass

import scala.reflect.ClassTag

/** Should do things with objects of type `A` */
trait T[A] {
  val name: String
  def run: Unit =
    println(name)
}

class C

object TypeClass {

  def familiarUsecase[A: ClassTag](size: Int): Array[A] =
    new Array[A](size)

  def bar[A: T]: Unit = {
    val c = implicitly[T[A]]
  }

  def equivalence[A](implicit evidence: T[A]): Unit = {
    val c = implicitly[T[A]]
    println(c == evidence)
  }

  def main(args: Array[String]): Unit = {
    implicit val evidence = new T[C] { val name = "xxx" }
    bar[C]
    equivalence[C]
  }
}
