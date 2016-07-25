package org.crazybob.talks.foj.varargs.after;
class BrokenSink<T> extends Sink<T> {
  Object[] array;

  /// BAD
  // Warning: "enables unsafe generic array creation"
  /// NORMAL
  @Override void add(T... a) {
    array = a;
  }

  void violateTypeSystem() {
    /// BAD
    array[0] = 5;
    /// NORMAL
  }
}
