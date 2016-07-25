package org.crazybob.talks.references.nm;
public class SegfaultFactory {
  private final NativeMemory nm;

  public SegfaultFactory(NativeMemory nm) {
    this.nm = nm;
  }

  @Override protected void finalize() {
    /// BAD
    // 50/50 chance of failure
    nm.write("I'm taking the VM with me!".getBytes());
    /// NORMAL
  }
}
