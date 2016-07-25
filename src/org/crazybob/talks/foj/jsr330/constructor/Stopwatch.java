package org.crazybob.talks.foj.jsr330.constructor;
import org.crazybob.talks.foj.jsr330.AtomicClock;
import org.crazybob.talks.foj.jsr330.TimeSource;
class Stopwatch {
  final TimeSource timeSource;
  Stopwatch() {
    timeSource = new AtomicClock();
  }
  void start() {
    /// ...
  }
  long stop() {
    /// ...
    return -1;
  }
}
