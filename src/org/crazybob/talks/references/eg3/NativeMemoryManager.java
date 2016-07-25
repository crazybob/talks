package org.crazybob.talks.references.eg3;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.lang.ref.Reference;
import com.google.common.base.FinalizableReferenceQueue;
import com.google.common.base.FinalizablePhantomReference;
public class NativeMemoryManager {
  private static final Set<Reference<?>> refs
      = Collections.synchronizedSet(new HashSet<Reference<?>>());
/// HIGHLIGHT
  private static final FinalizableReferenceQueue frq
      = new FinalizableReferenceQueue();
/// NORMAL
  public static NativeMemory allocate() {
    NativeMemory nm = new NativeMemory();
/// HIGHLIGHT
    final int address = nm.address;
    refs.add(new FinalizablePhantomReference<NativeMemory>(nm, frq) {
      public void finalizeReferent() {
        NativeMemory.free(address);
        refs.remove(this);
      }
    });
/// NORMAL
    return nm;
  }
}