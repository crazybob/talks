package org.crazybob.talks.foj.jsr330.constructor2;
import org.crazybob.talks.foj.jsr330.AtomicClock;
import org.crazybob.talks.foj.jsr330.TimeSource;
class Stopwatch {
  final TimeSource timeSource;
  Stopwatch() {
    /// HIGHLIGHT
    timeSource = new AtomicClock();
    /// NORMAL
  }
  void start() {
    /// ...
  }
  long stop() {
    /// ...
    return -1;
  }
}
