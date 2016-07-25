package org.crazybob.talks.foj.varargs.after;
import java.util.List;
public class Arrays {
  /// HIGHLIGHT
  @SuppressWarnings("generic-varargs")
  // Ensures only values of type T can be stored in elements.
  /// NORMAL
  public static <T> List<T> asList(T... a) {
    /// ...
    return null;
  }
  /// ...
}
