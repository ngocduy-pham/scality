package org.scality.tools.time

import scala.collection.concurrent.TrieMap
import scala.compat.Platform

trait IntervalTracker {

  /** @tparam V        the type of values to keep track of
    * @tparam M        the type of tracking metric
    *
    * @param size      the number of timestamps to keep
    * @param interval  the duration between two time points, in seconds
    * @param default   the default value for the tracking metric
    * @param change    the way to update the tracking of one given value
    * @param sum       the way to compute the summary of the whole duration
    */
  class Tracker[V, M](size: Int,
                      interval: Long,
                      default: M,
                      change: M => M,
                      sum: (M, M) => M) {

    val tracks = TrieMap[Long, TrieMap[V, M]]()

    def update(value: V): Unit = {
      val now = Platform.currentTime / interval * interval

      tracks.putIfAbsent(now, TrieMap()) map { valueTrack =>
        val metric = valueTrack.getOrElse(value, default)
        valueTrack.update(value, change(metric))
      } getOrElse {
        tracks(now).update(value, change(default))
      }

      // Cannot use `filter` because of concurrency
      for ((minute, _) <- tracks) {
        if (minute <= now - size * interval) tracks.remove(minute)
      }
    }

    def summarize(value: V): M =
      (default /: tracks) {
        case (res, (_, track)) => sum(res, track.getOrElse(value, default))
      }
  }

}
