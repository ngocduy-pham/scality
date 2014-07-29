package org.scality.pattern.implicits

trait OuterTypeCompanion[T]
object OuterTypeCompanion { implicit def o[T]: OuterTypeCompanion[T] = ??? }

trait Outer[T]
trait InnerTypeCompanion
object InnerTypeCompanion { implicit def i[T]: Outer[T] = ??? }

object TypeCompanionApp {
  def outer[T: OuterTypeCompanion]: Unit = ???
  outer[String]

  def inner[T: Outer]: Unit = ???
  inner[InnerTypeCompanion]
}
