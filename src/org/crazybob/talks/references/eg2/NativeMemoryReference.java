package org.crazybob.talks.references.eg2;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
class NativeMemoryReference
    extends PhantomReference<NativeMemory> {
/// HIGHLIGHT
  final int address;
/// NORMAL
  NativeMemoryReference(NativeMemory referent,
      ReferenceQueue<NativeMemory> rq) {
    super(referent, rq);
    address = referent.address;
  }
}
