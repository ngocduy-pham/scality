package org.scality
package pattern
package cake

trait Trees {
  trait Tree
  case object OnlyTree extends Tree
}

trait LowTrees extends Trees

trait HighTrees extends Trees

trait Tokens {
  trait Token
  case object OnlyToken extends Token
}
