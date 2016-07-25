package org.crazybob.talks.references.eg2;
public class NativeMemory {
  final int address = allocate();
  /** Allocates native memory. */
  static native int allocate();
/// HIGHLIGHT
  NativeMemory() {}
/// NORMAL

  /** Writes to native memory. */
  public void write(byte[] data) {
    write(address, data);
  }
  static native void write(int address, byte[] data);

  /** Frees native memory. */
/// BAD
  @Override protected void finalize() {
    free(address);
  }
/// NORMAL
  static native void free(int address);
}