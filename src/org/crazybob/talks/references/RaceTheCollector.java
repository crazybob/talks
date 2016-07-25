package org.crazybob.talks.references;
public class RaceTheCollector {
  public <T> T dereference(WeakReference<T> referent) {
    T t = referent.get();
    if (t == null) {
      throw new NullPointerException("Reference is cleared.");
    }
    /// ...The garbage collector runs.
    /// BAD
    return referent.get(); // Can return null!!!
    /// NORMAL
  }
}
