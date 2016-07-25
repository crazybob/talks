package org.crazybob.deck;

/**
 *
 */
class Objects {
  static <T> T nonNull(T t, String message) {
    if (t == null) throw new NullPointerException(message);
    return t;
  }

  static <T> T nonNull(T t) {
    if (t == null) throw new NullPointerException();
    return t;
  }
}
