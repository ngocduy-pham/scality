package org.scality.pattern.typeclass

import Movement._

object Pudge extends Hero {

  val name = "Pudge the Butcher"
  val position = (0, 0)

  def hook(hero: Hero): Unit = {
    Combat.dragTo(hero, this: Hero)
  }
}

object Sniper extends Hero {
  val name = "Kardel Sharpeyes the Sniper"
  val position = (1, 1)
}

object Hooking {
  def main(args: Array[String]): Unit = {
    Pudge hook Sniper
  }
}
