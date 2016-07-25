package org.crazybob.talks.foj.varargs.before2;
/** Collects instances of T. */
abstract class Sink<T> {
  /** Adds instances to this sink. */
  abstract void add(T... a);

  /** Adds t unless it's null. */
  void addUnlessNull(T t) {
    if (t != null)
      /// BAD
      // Warning: "unchecked cast"
      add((T[]) new Object[] { t });
      /// NORMAL
  }
}
