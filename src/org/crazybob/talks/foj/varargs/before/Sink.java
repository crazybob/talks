package org.crazybob.talks.foj.varargs.before;
/** Collects instances of T. */
abstract class Sink<T> {
  /** Adds instances to this sink. */
  abstract void add(T... a);

  /** Adds t unless it's null. */
  void addUnlessNull(T t) {
    if (t != null)
      /// BAD
      // Warning: "uses unchecked or unsafe operations"
      add(t);
      /// NORMAL
  }
}
