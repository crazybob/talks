package org.crazybob.talks.foj.arm.wrongexception;
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
    try {
      /// HIGHLIGHT
      return parse(in.readLine());
      /// NORMAL
    } finally {
      /// BAD
      in.close();
      /// NORMAL
    }
  }

  private static Header parse(String first) throws ParseException {
    /// ...
    return null;
  }
}
