package org.crazybob.talks.foj.arm.plain;
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
    Header header = parse(in.readLine());
    in.close();
    return header;
  }

  private static Header parse(String first) throws ParseException {
    /// ...
    return null;
  }
}
