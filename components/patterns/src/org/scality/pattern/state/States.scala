package org.scality
package pattern
package state

trait State {
  def announce: String
  def getKilled(): Unit
  def kill(): Unit
}

case class Feeding(hero: Hero) extends State {

  def announce: String =
    s"${hero.name} is feeding!"

  def getKilled(): Unit =
    announce

  def kill(): Unit = {
    hero.state = new KillingSpree(hero)
    hero.state.announce
  }
}

case class KillingSpree(hero: Hero) extends State {

  def announce: String =
    s"${hero.name} is in killing spree!"

  def getKilled(): Unit = {
    hero.state = new Feeding(hero)
    hero.state.announce
  }

  def kill(): Unit = {
    hero.state = new GodLike(hero)
    hero.state.announce
  }
}

case class GodLike(hero: Hero) extends State {

  def announce: String =
    s"${hero.name} is God like!"

  def getKilled(): Unit = {
    hero.state = new Feeding(hero)
    hero.state.announce
  }

  def kill(): Unit =
    announce
}
