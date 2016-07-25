package org.crazybob.talks.references.eg1;
public class Foo extends Bar {
  /// HIGHLIGHT
  @Override protected void finalize() throws Throwable {
    try {
      /// ...Clean up Foo.
    } finally {
      super.finalize(); // Clean up Bar.
    }
  }
  /// NORMAL
}