package org.crazybob.talks.foj.arm.witharm;
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
    /// HIGHLIGHT
    try (Reader fin = new FileReader(file);
        BufferedReader in = new BufferedReader(fin)) {
    /// NORMAL
      return parse(in.readLine());
    /// HIGHLIGHT
    }
    /// NORMAL
  }

  private static Header parse(String first) throws ParseException {
    /// ...
    return null;
  }
}
