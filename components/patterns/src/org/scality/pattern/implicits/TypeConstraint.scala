package org.scality.pattern.implicits

import scala.annotation.implicitNotFound

trait TypeConstraint {
  @implicitNotFound("This request is not safe")
  class Constraint[T]
}

object StringConstraint extends TypeConstraint {
  implicit val stringConstraint = new Constraint[String]
}

trait TypeConstraintLib[TC <: TypeConstraint] {
  def foo[T: TC#Constraint](t: T): Unit = ???
}

object TypeConstraintApp extends TypeConstraintLib[StringConstraint.type] {
  foo("")
}
