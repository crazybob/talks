package org.crazybob.talks.foj.varargs.before;
import java.util.List;
public class Arrays {
  /// BAD
  // Warning: "enables unsafe generic array creation"
  /// NORMAL
  public static <T> List<T> asList(T... a) {
    /// ...
    return null;
  }
  /// ...
}
