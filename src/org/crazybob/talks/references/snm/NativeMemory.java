package org.crazybob.talks.references.snm;
public class NativeMemory {
  final int address = allocate();
  /** Allocates native memory. */
  static native int allocate();

  /** Writes to native memory. */
/// HIGHLIGHT
  boolean finalized;
  public synchronized void write(byte[] data) {
    if (!finalized) write(address, data);
    else /* do nothing? */;
  }
/// NORMAL
  static native void write(int address, byte[] data);

  /** Frees native memory. */
/// HIGHLIGHT
  @Override protected synchronized void finalize() {
    finalized = true;
/// NORMAL
    free(address);
  }
  static native void free(int address);
}