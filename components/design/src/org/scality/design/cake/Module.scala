package org.scality
package design
package cake

object XScanner extends Scanners with Tokens {
  def token: Parser[Token] =
    'X' ^^ { case c => OnlyToken }
}

object LowParser extends Parsers with LowTrees {

  type Elem = XScanner.Token

  def program: Parser[Tree] =
    elem(XScanner.OnlyToken) ^^ { case _ => OnlyTree }
}

object HighParser extends Parsers with HighTrees {

  type Elem = LowParser.Tree

  def program: Parser[Tree] =
    elem(LowParser.OnlyTree) ^^ { case _ => OnlyTree }

  def transform(tree: Elem): Tree =
    tree match { case _ => OnlyTree }
}

object HowToUse {
  def main(args: Array[String]): Unit =
    LowParser.program(new XScanner.Scanner("X")) match {
      case LowParser.Success(tree) => HighParser.transform(tree)
    }
}

object PathDependentTypes {
  val x: HighParser.Elem = LowParser.OnlyTree
  val y: HighTrees#Tree = (new HighTrees {}).OnlyTree
  //new HighTrees { val t: Tree = HighParser.NoneTree }
}
