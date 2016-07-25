package org.crazybob.talks.references;
import java.util.logging.Logger;
public class Connection {
  /// ...
  boolean closed;
  public synchronized void close() {
    reallyClose();
    closed = true;
  }
  private native void reallyClose();

  @Override protected synchronized void finalize() {
    if (!closed) {
      Logger.getLogger(Connection.class.getName())
          .warning("You forgot to close me!!!");
      close();
    }
  }
}
