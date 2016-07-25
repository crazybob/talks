package org.crazybob.talks.androidsquared.queuefile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
public class QueueFile {
  public QueueFile(File file) throws IOException { ... }

  public void add(byte[] data) throws IOException { ... }
  public void add(byte[] data, int offset, int count)
      throws IOException { ... }

  public byte[] peek() throws IOException { ... }
  public void remove() throws IOException { ... }

  public int size() { ... }
  public void clear() throws IOException { ... }
  public void close() throws IOException { ... }
}
