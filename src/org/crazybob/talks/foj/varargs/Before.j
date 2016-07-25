class VarargsClient {
  static List<Callable<String>> stringFactories() {
    Callable<String> a, b, c;
    ...
    /// BAD
    // Warning: "uses unchecked or unsafe operations"
    return asList(a, b, c);
    /// NORMAL
  }
}


public class Arrays {
  public static <T> List<T> asList(T... elements) { ... }
  ...
}
