package org.crazybob.talks.foj.varargs.after;
abstract class PlainSink<T> extends Sink<T> {
  /// BAD
  // Warning #3: "Overrides non-reifiable varargs type with array"
  /// NORMAL
  @Override abstract void add(T[] a);
}
