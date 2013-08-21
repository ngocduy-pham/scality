package org.scality
package design
package cake

import scala.util.parsing.input.{ CharArrayReader, Reader }

trait Scanners extends Parsers {

  type Elem = Char
  type Token

  def token: Parser[Token]

  class Scanner(in: Reader[Char]) extends Reader[Token] {
    def this(in: String) =
      this(new CharArrayReader(in.toCharArray()))
    def first = token(in) match { case Success(tok) => tok }
    def rest = new Scanner(in.rest)
    def pos = in.pos
    def atEnd = in.atEnd
  }

}
