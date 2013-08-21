package org.scality
package design
package utilitybelt

object English extends Counting with Greetings {
  def one: String =
    "one"

  def others: String =
    "lots"

  def meet: String =
    "hello"

  def leave: String =
    "bye"
}

trait EnglishConfigured {
  implicit val config = English
}

object InEngland extends Localizable with EnglishConfigured {
  def main(args: Array[String]): Unit =
    act(0)
}
