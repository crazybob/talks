package org.crazybob.talks.references;
import java.lang.ref.*;
import java.lang.ref.ReferenceQueue;
public class WeakPhantomReference<T> extends PhantomReference<T> {
  final WeakReference<T> weakReference;

  public WeakPhantomReference(T referent,
      ReferenceQueue<? super T> q) {
    super(referent, q);
    weakReference = new WeakReference<T>(referent);
  }

  /** Returns referent so long as it's weakly-reachable. */
  @Override public T get() {
    return weakReference.get();
  }
}
