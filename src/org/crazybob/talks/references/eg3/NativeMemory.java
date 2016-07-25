package org.crazybob.talks.references.eg3;
public class NativeMemory {
  final int address = allocate();
  /** Allocates native memory. */
  static native int allocate();
  NativeMemory() {}

  /** Writes to native memory. */
  public void write(byte[] data) {
    write(address, data);
  }
  static native void write(int address, byte[] data);

  /** Frees native memory. */
  static native void free(int address);
}