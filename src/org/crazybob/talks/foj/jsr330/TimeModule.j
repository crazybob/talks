public class TimeModule extends AbstractModule {
  public void configure() {
    bind(Stopwatch.class);
    bind(StopwatchWidget.class);
    bind(TimeSource.class).to(AtomicClock.class);
  }
}
