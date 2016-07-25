class VarargsClient {
  static List<Callable<String>> stringFactories() {
    Callable<String> a, b, c;
    ...
    return asList(a, b, c);
  }
}


public class Arrays {
  /// BAD
  // Warning: "enables unsafe generic array creation"
  public static <T> List<T> asList(T... elements) { ... }
  /// NORMAL
  ...
}
