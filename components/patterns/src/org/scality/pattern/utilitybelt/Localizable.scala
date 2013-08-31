package org.scality
package pattern
package utilitybelt

trait Localizable {
  def act(action: Int)(implicit config: Counting with Greetings): Unit =
    action match {
      case 1 => println(s"${config.meet} ${config.one}")
      case _ => println(s"${config.meet} ${config.others}")
    }
}
