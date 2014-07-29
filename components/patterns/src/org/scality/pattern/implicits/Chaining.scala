package org.scality.pattern.implicits

import language.higherKinds

trait Chaining {
  class A[T]
  object A { implicit def a[T]: A[T] = ??? }

  class B[T]
  object B { implicit def b[T: A]: B[T] = ??? }

  class C[T]
  object C { implicit def c[T: B]: C[T] = ??? }
}

object Chaining extends Chaining {
  def foo[T: C]: Unit = ???
}

object ChainingApp {
  class U
  Chaining.foo[U]
}
