package org.scality.tools.time

object Main extends IntervalTracker {

  def main(args: Array[String]): Unit = {
    val tracker = new Tracker[Int, Int](3, 1000, 0, _ + 1, _ + _)
    for (i <- 1 to 40) {
      tracker.update(i % 15)
      for ((st, track) <- tracker.tracks) {
        println(st)
        println(track)
      }
      Thread sleep 10
    }
  }

}