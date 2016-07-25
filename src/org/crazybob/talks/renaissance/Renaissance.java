// Copyright 2010 Square, Inc.
package org.crazybob.talks.renaissance;

import org.crazybob.deck.Bullets;
import org.crazybob.deck.Deck;
import org.crazybob.deck.Picture;
import org.crazybob.deck.Slide;
import org.crazybob.deck.Template;
import org.crazybob.deck.Text;
import org.crazybob.deck.templates.Oscon;
import org.crazybob.talks.ThankYou;

/**
 * On the Cusp of a Java Renaissance.
 *
 * @author Bob Lee (bob@squareup.com)
 */
public class Renaissance {
  static final String PATH = "src/org/crazybob/talks/renaissance/";

  static Template template = new Oscon();

  public static void main(String[] args) {

    Deck deck = new Deck()
        .title("On the cusp of a Java renaissance")
        .subtitle("Where we've been and where we're going")
        .author("Bob Lee")
        .company("Square Inc.");

    // I'll talk a lot about Ruby because we're a Ruby shop.

    centerText(deck, "*Ren¥ais¥sance* (n) A revival of or renewed interest in something");

    // For interest to be renewed, presumably it waned at some point.
    // What happened?

    Picture tardis = picture("tardis.png").position(1200, 150);

    revealBullets(deck, "2004", tardis,
        "Struts was #1",
        "*March:* JSF 1.0 released",
        "*July:* Rails open sourced",
        "*September:* Java 5 released",
        "Frameworks took years to catch up.",
        "Early adopter attention moved elsewhere."

        // People who complain about the language today remind me of people who complained
        // that Java was slow long after it clearly wasn't.
        // Perception is important.
    );

    sectionSlide(deck, "Why now?"); // After 7 years.

    sectionSlide(deck, "Scalability");

    sectionSlide(deck, "You can only throw so much hardware at a problem.");
    // Concurrent workers -- Netty
    // Operational cost = ongoing tax
    // Increases throughput but doesn't help latency.

    revealBullets(deck, "Is JRuby the answer?",
        "Concurrency is rocket science.",
        "Ruby talent is tough to come by."
        // We hire Java engineers and train them to program Ruby.
    );

    // I'd be remiss if I didn't mention
    sectionSlide(deck, "Android");

    revealBullets(deck, "What's next?",
        "|invokedynamic|",
        "Closures & extension methods",
        "In the mean time, empower new users."
        // My experience coming from Google and being legitimately new to Ruby
    );

    sectionSlide(deck, "One Open Question");

    revealBullets(deck, "Is Java open?",
        "Openness is relative.",
        "Differentiate between Java SE vs. OpenJDK",
        // Marketing, not an open standard.

        // GPL v2 vs. 3
        // Sun/Oracle has effectively weaponized the GPL

        // It boils down to:
        "Sun/Oracle failed to deliver on its promises."
    );
    
    sectionSlide(deck, "Does it matter?");

    sectionSlide(deck, "Maybe.");
    // If Oracle is able to regain the trust of the community that built the Java ecosystem.
    // If not, I suspect a more open alternative will prevail.
    
    deck.add(ThankYou.slide());

    deck.writePdf(template, "out/renaissance.pdf", true);
  }

  static void centerText(Deck deck, String text) {
    deck.add(new Slide().add(new Text(text).toPicture(Deck.WIDTH, 80).center()));
  }

  static void sectionSlide(Deck deck, String text) {
    deck.add(new Slide().add(new Text(text).font(template.titleFont())
        .toPicture(Deck.WIDTH, 120).center()));
  }

  private static Picture picture(String name) {
    return Picture.parseFile("images/renaissance/" + name);
  }

  private static Picture fillBottom(Picture p, int w, int h) {
    p.width(Deck.WIDTH);
    int newHeight = Deck.WIDTH * h / w;
    System.out.println(newHeight);
    p.position(0, Deck.HEIGHT - newHeight);
    return p;
  }

  private static Picture fillRight(Picture p, int w, int h) {
    p.height(Deck.HEIGHT);
    int newWidth = Deck.HEIGHT * w / h;
    p.position(Deck.WIDTH - newWidth, 0);
    return p;
  }

  /** Starts bullets. */
  private static $Bullets bullets() {
    return new $Bullets();
  }

  /**
   * Creates a series of steps, with numbered titles like "Step 1: xxx" and an
   * image for each step.
   */
  private static void steps(Deck deck, String... titlesAndImages) {
    int step = 1;
    for (int i = 0; i < titlesAndImages.length; i += 2) {
      String title = titlesAndImages[i];
      String image = titlesAndImages[i + 1];

      deck.add(new Slide(String.format("Step %d: %s", step++, title))
          .add(picture(image).center()));
    }
  }

  private static void bullets(Deck deck, String title, String... bullets) {
    $Bullets b = new $Bullets();
    for (String bullet : bullets) {
      b.add(bullet);
    }

    deck.add(new Slide(title).add(b));
  }

  private static void revealBullets(Deck deck, String title, String... bullets) {
    revealBullets(deck, title, null, bullets);
  }

  private static void revealBullets(Deck deck, String title, Picture picture, String... bullets) {
    for (int max = 0; max <= bullets.length; max++) {
      Bullets b = new Bullets();
      for (int i = 0; i < max; i++) {
        b.add(bullets[i]);
      }
      Slide slide = new Slide(title).add(b);
      if (picture != null) slide.add(picture);
      deck.add(slide);
    }
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
