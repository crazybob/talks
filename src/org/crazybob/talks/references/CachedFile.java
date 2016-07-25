package org.crazybob.talks.references;
import java.io.File;
import java.lang.ref.SoftReference;
public class CachedFile {
  final File file;
  public CachedFile(File file) {
    this.file = file;
  }
  volatile SoftReference<byte[]> dataReference
      = new SoftReference<byte[]>(null);
  /** Gets file contents, reading them if necessary. */
  public byte[] getData() {
    byte[] data = dataReference.get();
    if (data != null) return data;
    data = readData();
    dataReference = new SoftReference<byte[]>(data);
    return data;
  }
  /** Reads file contents. */
  byte[] readData() {
    /// ...
    return null;
  }
}
