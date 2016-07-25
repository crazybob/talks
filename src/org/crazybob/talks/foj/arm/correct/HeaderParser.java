package org.crazybob.talks.foj.arm.correct;
import org.crazybob.talks.foj.arm.Header;
import org.crazybob.talks.foj.arm.ParseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class HeaderParser {
  /** Parses header from the first line of file. */
  public static Header parse(File file) throws IOException,
      ParseException {
    BufferedReader in = new BufferedReader(new FileReader(file));
    /// HIGHLIGHT
    boolean successful = false;
    /// NORMAL
    try {
      Header header = parse(in.readLine());
      /// HIGHLIGHT
      successful = true;
      /// NORMAL
      return header;
    } finally {
      /// HIGHLIGHT
      try { in.close(); } catch (IOException e) {
        if (successful) throw e;
        else e.printStackTrace(); // let original exception propagate
      }
      /// NORMAL
    }
  }

  private static Header parse(String first) throws ParseException {
    /// ...
    return null;
  }
}
