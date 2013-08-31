package org.scality
package pattern
package state

trait Hero {

  private[state] var state: State
  val name: String

  def getKilled(): Unit =
    state.getKilled()

  def kill(): Unit =
    state.kill()
}

object LinaInverse extends Hero {
  private[state] var state: State = new Feeding(this)
  val name = "Lina Inverse"
}
