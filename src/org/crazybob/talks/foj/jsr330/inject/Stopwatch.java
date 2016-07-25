package org.crazybob.talks.foj.jsr330.inject;
import org.crazybob.talks.foj.jsr330.Inject;
import org.crazybob.talks.foj.jsr330.TimeSource;
class Stopwatch {
  final TimeSource timeSource;
  /// HIGHLIGHT
  @Inject Stopwatch(TimeSource injected) {
    timeSource = injected;
  }
  /// NORMAL
  void start() {
    /// ...
  }
  long stop() {
    /// ...
    return -1;
  }
}
