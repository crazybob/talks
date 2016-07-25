class VarargsClient {
  static List<Callable<String>> stringFactories() {
    Callable<String> a, b, c;
    ...
    return asList(a, b, c);
  }
}


public class Arrays {
  /// HIGHLIGHT
  @SuppressWarnings("generic-varargs")
  // Ensures only values of type T can be stored in elements.
  /// NORMAL
  public static <T> List<T> asList(T... elements) { ... }
  ...
}
