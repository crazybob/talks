package org.crazybob.talks.foj.jsr330.factory2;

import org.crazybob.talks.foj.jsr330.DefaultTimeSource;
import org.crazybob.talks.foj.jsr330.TimeSource;

class Stopwatch {
  final TimeSource timeSource;
  Stopwatch() {
    /// HIGHLIGHT
    timeSource = DefaultTimeSource.getInstance();
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
