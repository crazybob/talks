package org.crazybob.talks.references.eg2;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.Reference;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
public class NativeMemoryManager {
  private static final Set<Reference<?>> refs
      = Collections.synchronizedSet(new HashSet<Reference<?>>());
  private static final ReferenceQueue<NativeMemory> rq
      = new ReferenceQueue<NativeMemory>();
  public static NativeMemory allocate() {
    NativeMemory nm = new NativeMemory();
    refs.add(new NativeMemoryReference(nm, rq));
    cleanUp();
    return nm;
  }
  private static void cleanUp() {
    NativeMemoryReference ref;
    while ((ref = (NativeMemoryReference) rq.poll()) != null) {
      NativeMemory.free(ref.address);
      refs.remove(ref);
    }
  }
}
