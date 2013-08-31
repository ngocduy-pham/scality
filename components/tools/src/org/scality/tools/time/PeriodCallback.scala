package org.scality.tools.time

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait PeriodCallback {

  //import play.api.libs.concurrent.Execution.Implicits.defaultContext
  implicit lazy val executor: ExecutionContext = global

  class RunAfter[T](time: Long) extends ((=> T) => Future[T]) {
    def run(f: => T): Future[T] =
      apply(f)

    def repeat(f: => T): Future[T] =
      repeatAfter(time)(f)

    def apply(f: => T): Future[T] =
      runAfter(time)(f)
  }

  def after[T](time: Long): RunAfter[T] =
    new RunAfter[T](time)

  def runAfter[T](time: Long)(f: => T)(implicit rec: Boolean = false): Future[T] =
    Future {
      Thread sleep time
      if (rec) runAfter[T](time)(f)
      f
    }

  def repeatAfter[T](time: Long)(f: => T): Future[T] =
    runAfter(time)(f)(true)

}

object PeriodCallback extends PeriodCallback {
  def apply[T](time: Long): RunAfter[T] =
    after[T](time)
}
