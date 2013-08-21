package org.scality
package design
package loan

class Resource {
  def print(x: Int): String =
    x.toString
}

trait Provider {

  def withResource[T](f: Resource => T): T =
    try f(loadResource())
    catch { case _: Throwable => ??? }

  def loadResource(): Resource =
    new Resource
}

object Consummer extends Provider {

  val x = withResource { implicit rs =>
    foo(100)
  }

  def foo(x: Int)(implicit resource: Resource): String =
    resource print x

  def main(args: Array[String]): Unit =
    withResource(println)
}
