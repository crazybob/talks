package org.crazybob.talks.foj.varargs.before;
abstract class PlainSink<T> extends Sink<T> {
  @Override abstract void add(T[] a);
}
