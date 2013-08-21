package org.scality
package design
package memoization

import scala.collection.mutable.WeakHashMap

trait Memoization {
  def memoize[A, R](f: A => R) = {
    // Releases cache members if memory tightens
    val cache = WeakHashMap[A, R]()
    arg: A => cache.getOrElseUpdate(arg, f(arg))
  }
}

object HugeComputing extends Memoization {
  // Huge computation
  private def _foo(i: Int): Int =
    i * 2 + 1

  // Needs to be a function value
  val foo = memoize(_foo)
}
