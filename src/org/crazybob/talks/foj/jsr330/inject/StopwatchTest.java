package org.crazybob.talks.foj.jsr330.inject;
import org.crazybob.talks.foj.jsr330.MockTimeSource;
import org.crazybob.talks.foj.jsr330.TestCase;
public class StopwatchTest extends TestCase {
  public void testStopwatch() {
    MockTimeSource mts = new MockTimeSource();
    Stopwatch stopwatch = new Stopwatch(mts);
    stopwatch.start();
    long actual = stopwatch.stop();
    mts.verify(actual);
  }
}
