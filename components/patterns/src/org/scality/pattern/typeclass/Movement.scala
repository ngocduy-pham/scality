package org.scality.pattern.typeclass

trait Hero {
  def name: String
  def position: (Int, Int)
}

trait Movement[H] {
  def position(hero: H): (Int, Int)
  def move(hero: H, position: (Int, Int)): Unit
}

object Movement {
  implicit val heroMovement = new Movement[Hero] {

    def position(hero: Hero): (Int, Int) =
      hero.position

    def move(hero: Hero, position: (Int, Int)): Unit =
      println(s"${hero.name} moves to $position")
  }
}