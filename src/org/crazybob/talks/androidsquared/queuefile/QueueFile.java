package org.crazybob.talks.androidsquared.queuefile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
public class QueueFile {
  public QueueFile(File file) throws IOException {
    /// ...
  }
  public void add(byte[] data) throws IOException {
    /// ...
  }
  public synchronized void add(byte[] data, int offset, int count)
      throws IOException {
    /// ...
  }  
  public int size() {
    /// ...
    return -1;
  }
  public byte[] peek() throws IOException {
    /// ...
    return null;
  }
  public synchronized void remove() throws IOException {
    /// ...
  }
  public synchronized void clear() throws IOException {
    /// ...
  }
  public synchronized void close() throws IOException {
    /// ....
  }
}
