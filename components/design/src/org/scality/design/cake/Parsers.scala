package org.scality
package design
package cake

import scala.util.parsing.input.Reader

trait Parsers {

  type Elem
  type Input = Reader[Elem]

  trait Parser[+T] extends (Input => ParseResult[T]) {
    def apply(in: Input): ParseResult[T]
    def ^^[U](f: T => U): Parser[U] =
      Parser { in => this(in) map (f) }
  }

  sealed abstract class ParseResult[+T] {
    def map[U](f: T => U): ParseResult[U]
  }

  case class Success[+T](result: T) extends ParseResult[T] {
    def map[U](f: T => U) = Success(f(result))
  }

  case class Failure(err: String) extends ParseResult[Nothing] {
    def map[U](f: Nothing => U) = this
  }

  def Parser[T](f: Input => ParseResult[T]): Parser[T] =
    new Parser[T] { def apply(in: Input) = f(in) }

  implicit def elem(e: Elem): Parser[Elem] =
    Parser { in => Success(in.first) }
}
