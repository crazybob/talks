package org.crazybob.talks.foj.varargs.after;
import java.util.ArrayList;
import java.util.List;
class StringSink extends Sink<String> {
  final List<String> list = new ArrayList<String>();
  /// BAD
  // Warning #2: "override generates a more specific varargs
  //              type erasure"
  /// NORMAL
  /// HIGHLIGHT
  // Note: We don't get the first warning because String[]
  //       is reifiable.
  /// NORMAL
  @Override void add(String... a) {
    list.addAll(Arrays.asList(a));
  }
  @Override public String toString() {
    return list.toString();
  }
  public static void main(String[] args) {
    Sink<String> ss = new StringSink();
    ss.addUnlessNull("seppuku");
    System.out.println(ss);
  }
}
