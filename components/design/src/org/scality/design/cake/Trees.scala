package org.scality
package design
package cake

trait Trees {
  trait Tree
}

trait LowTrees extends Trees {
  object OnlyTree extends Tree
}

trait HighTrees extends Trees {
  object OnlyTree extends Tree
}

trait Tokens {
  trait Token
  case object OnlyToken extends Token
}
