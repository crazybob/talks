package org.crazybob.talks.foj.jsr330.factory2;
import org.crazybob.talks.foj.jsr330.DefaultTimeSource;
import org.crazybob.talks.foj.jsr330.MockTimeSource;
import org.crazybob.talks.foj.jsr330.TestCase;
import org.crazybob.talks.foj.jsr330.TimeSource;
public class StopwatchTest extends TestCase {
  public void testStopwatch() {
    /// BAD
    TimeSource original = DefaultTimeSource.getInstance();
    try {
    /// NORMAL
      MockTimeSource mts = new MockTimeSource();
      DefaultTimeSource.setInstance(mts);
      Stopwatch stopwatch = new Stopwatch();
      stopwatch.start();
      long actual = stopwatch.stop();
      mts.verify(actual);
    /// BAD
    } finally {
      DefaultTimeSource.setInstance(original);
    }
    /// NORMAL
  }
}
