package org.crazybob.talks.guice;

import org.crazybob.deck.Deck;
import org.crazybob.deck.Slide;
import org.crazybob.deck.Code;
import org.crazybob.deck.Text;
import org.crazybob.deck.Bullets;
import org.crazybob.deck.templates.Plain;

public class Main {

  static final String PATH = "src/org/crazybob/talks/guice/";

  public static void main(String[] args) {
    Deck deck = new Deck()
        .title("JSR-330 Dependency Injection for Java")
        .subtitle("as implemented by Google Guice")
        .author("Bob Lee")
        .company("Google Inc.");

    deck.add(new Slide("Goals").add(bullets()
        .$("Improve upon constructors and factories")
        .$("Write less boilerplate code")
        .$("Improve modularity")
        .$("Abstract scope")
        .$("Easier unit testing")
    ));

    deck.writePdf(new Plain(), "out/guice.pdf", true);
  }

  private static void highlightBullets(Deck deck, String title,
      String... bullets) {
    highlightBullet(deck, title, null, bullets);
    for (String current : bullets) {
      highlightBullet(deck, title, current, bullets);
    }
  }

  @SuppressWarnings("StringEquality")
  private static void highlightBullet(Deck deck, String title, String current,
      String... bullets) {
    Bullets b = new Bullets();
    for (String s : bullets) {
      b.add(current == s ? "*" + s + "*" : s);
    }
    deck.add(new Slide(title).add(b));
  }

  private static Code parseCode(String path) {
    return Code.parseFile(PATH + path);
  }

  /** Starts bullets. */
  private static $Bullets bullets() {
    return new $Bullets();
  }

  static class $Bullets extends Bullets {
    public $Bullets $(Text text) {
      super.add(text);
      return this;
    }

    public $Bullets $(Text text, Bullets children) {
      super.add(text, children);
      return this;
    }

    public $Bullets $(String text) {
      super.add(text);
      return this;
    }

    public $Bullets $(String text, Bullets children) {
      super.add(text, children);
      return this;
    }
  }
}
