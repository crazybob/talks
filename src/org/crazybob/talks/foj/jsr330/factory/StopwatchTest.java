package org.crazybob.talks.foj.jsr330.factory;
import org.crazybob.talks.foj.jsr330.DefaultTimeSource;
import org.crazybob.talks.foj.jsr330.MockTimeSource;
import org.crazybob.talks.foj.jsr330.TestCase;
public class StopwatchTest extends TestCase {
  public void testStopwatch() {
    MockTimeSource mts = new MockTimeSource();
    DefaultTimeSource.setInstance(mts);
    Stopwatch stopwatch = new Stopwatch();
    stopwatch.start();
    long actual = stopwatch.stop();
    mts.verify(actual);
  }
}
