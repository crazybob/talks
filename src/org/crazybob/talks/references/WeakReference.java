package org.crazybob.talks.references;
public class WeakReference<T> {
  public WeakReference(T referent) {
    /// ...
  }
  public WeakReference(T referent,
      ReferenceQueue<? super T> q) {
    /// ...
  }
  public T get() {
    /// ...
    return null;
  }
  /// ...
}
