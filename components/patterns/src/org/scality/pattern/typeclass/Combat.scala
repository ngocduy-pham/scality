package org.scality.pattern.typeclass

object Combat {

  def dragTo[F: Movement, T: Movement](target: T, caster: F): Unit = {

    def dummyMethodJustForShow(f: F, t: T) {
      val mover = implicitly[Movement[T]]
      val stayer = implicitly[Movement[F]]
      mover.move(target, stayer.position(caster))
    }

    /* If `target` and `caster` had the same type of `Hero`, they could be
     * passed in with the wrong order. */
    dummyMethodJustForShow(caster, target)
  }
}
