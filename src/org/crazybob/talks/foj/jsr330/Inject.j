class Stopwatch {
  final TimeSource timeSource;
/// HIGHLIGHT
  @Inject Stopwatch(TimeSource TimeSource) {
    timeSource = TimeSource;
  }
/// NORMAL
  void start() { ... }
  long stop() { ... }
}
