package org.crazybob.talks.foj.varargs.before;
class BrokenSink<T> extends Sink<T> {
  Object[] array;

  @Override void add(T... a) {
    array = a;
  }

  void violateTypeSystem() {
    /// BAD
    array[0] = 5;
    /// NORMAL
  }
}
