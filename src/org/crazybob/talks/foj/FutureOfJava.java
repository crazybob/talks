package org.crazybob.talks.foj;

import org.crazybob.deck.*;
import org.crazybob.deck.templates.Plain;

/**
 * The Future of Java
 */
public class FutureOfJava {

  static final String PATH = "src/org/crazybob/talks/foj/";

  public static void main(String[] args) {
    Deck deck = new Deck()
        .title("The Future of Java")
        .subtitle("")
        .author("Bob Lee")
        .company("Google Inc.");

    /*
     * Some speakers like to picture the audience naked... I take a different
     * approach.
     */
    deck.add(new Slide("Who is Bob Lee?").add(bullets()
        .$("Google engineer")
        .$("Android core library lead")
        .$("Guice creator")
        .$("JSR-330 lead")
        .$("Google's alternate EC rep")
        .$("St. Louisan")
        .$("Speedo model")
    ).add(speedo()));

    /*
     * How I picked these:
     *   1) Projects I worked directly on.
     *   2) Topics of possible general interest, i.e. to non-Java programmers.
     */
    deck.add(new Slide("Let's talk about...").add(bullets()
        .$("Project Coin")
        .$("JSR-330: Dependency Injection for Java")
    ));

    deck.add(new Slide("Project Coin").add(
        fillRight(Picture.parseFile("images/misc/coins.jpg"), 1400, 2048)
    ).add(new Text("Small language *change*s")));

    deck.add(new Slide("Currently accepted proposals").add(bullets()
        .$("Strings in switch")
        .$("Automatic Resource Management (ARM)")
        .$("Improved generic type inference for constructors")
        .$("Simplified varags method invocation")
        .$("Collection literals and access syntax")
        .$("Better integral literals")
        .$("JSR-292 (Invokedynamic) support")
    ));

    deck.add(new Slide("Currently accepted proposals").add(bullets()
        .$("Strings in switch")
        .$("*Automatic Resource Management (ARM)*")
        .$("Improved generic type inference for constructors")
        .$("*Simplified varags method invocation*")
        .$("Collection literals and access syntax")
        .$("Better integral literals")
        .$("JSR-292 (Invokedynamic) support")
    ));

    deck.add(new Slide("1. ARM").add(bullets()
        .$("Automatic Resource Management")
        .$("Helps dispose of resources")
        .$("Proposed by Josh Bloch")
    ));

    deck.add(new Slide("*Example:* Parsing a file header")
        .add(Code.parseFile(PATH + "arm/plain/HeaderParser.java")
    ));

    deck.add(new Slide("*Example:* Parsing a file header")
        .add(Code.parseFile(PATH + "arm/plain/HeaderParser.java"))
        .add(Spacer.vertical(30),
          new Text("*See the problem?*")
    ));

    deck.add(new Slide("If we don't reach |close()|, we leak. ")
        .add(Code.parseFile(PATH + "arm/leaky/HeaderParser.java")));

    deck.add(new Slide("|finally| ensures |close()| is always called.")
        .add(Code.parseFile(PATH + "arm/notleaky/HeaderParser.java")));

    deck.add(new Slide("But what happens when |close()| throws?")
        .add(Code.parseFile(PATH + "arm/wrongexception/HeaderParser.java")));

    deck.add(new Slide("We _could_ ignore the exception from |close()|.")
        .add(Code.parseFile(PATH + "arm/ignoreclose/HeaderParser.java")));

    deck.add(new Slide("But it's better to throw the right exception.")
        .add(Code.parseFile(PATH + "arm/correct/HeaderParser.java")));

    String armExample = "Equivalent code, using an ARM block.";
    deck.add(new Slide(armExample )
        .add(Code.parseFile(PATH + "arm/witharm/HeaderParser.j")));

    deck.add(new Slide(armExample)
        .add(Code.parseFile(PATH + "arm/witharm/HeaderParser2.j"))
        .add(Spacer.vertical(30),
          new Text("*Note:* Techincally, we could still leak.")
    ));

    deck.add(new Slide(armExample)
        .add(Code.parseFile(PATH + "arm/witharm2/HeaderParser.j")));

    revealBullets(deck, "Why ARM is important",
        "The JDK opens & closes resources in 110 places.",
        "74 of those can leak. _2/3rds!_",
        "None suppress exceptions correctly.",
        "ARM reduces error-prone boilerplate.",
        "Ideally, _all_ |finally| blocks would work this way."
    );

    // Varargs

    deck.add(new Slide("2. Simplified varargs method invocation").add(bullets()
        .$("Moves varargs warnings from the caller to the callee")
        .$("Only relevant for non-reifiable varargs types")
        .$("Vastly reduces # of warnings", bullets()
          .$("A warning per caller (the status quo) vs.")
          .$("One warning on |Arrays.asList()| itself")
        )
        .$("Helps catch errors sooner")
        .$("Language changes don't get much simpler.")
        .$("Proposed by Bob Lee")
    ));

    deck.add(new Slide("Today, the compiler warns the caller.").add(
        Code.parseFile(PATH + "varargs/before/Sink.java")
    ));
    deck.add(new Slide("What's really going on here...").add(
        Code.parseFile(PATH + "varargs/before2/Sink.java")
    ));
    deck.add(new Slide("After the language change...").add(
        Code.parseFile(PATH + "varargs/after/Sink.java")
    ));

    deck.add(new Slide("Before the language change").add(
        Code.parseFile(PATH + "varargs/before/BrokenSink.java")
    ));
    deck.add(new Slide("After the language change").add(
        Code.parseFile(PATH + "varargs/after/BrokenSink.java")
    ));

    deck.add(new Slide("What does this program print?").add(
        Code.parseFile(PATH + "varargs/before/StringSink.java")
    ));
    deck.add(new Slide("What does this program print?").add(
        Code.parseFile(PATH + "varargs/before/StringSink.java"),
        Spacer.vertical(40),
        new Text("a) StringSink@32c41a"),
        new Text("b) [\"seppuku\"]"),
        new Text("c) Nothing. It throws an exception.")
    ));
    deck.add(new Slide("If you answered C, you're correct!").add(
        Code.parseFile(PATH + "varargs/before2/StringSink.java"),
        Spacer.vertical(40),
        new Text("a) StringSink@32c41a"),
        new Text("b) [\"seppuku\"]"),
        new Text("*c) Nothing. It throws |ClassCastException|.*")
    ));
    deck.add(new Slide("Let's look at |Sink| again...").add(
        Code.parseFile(PATH + "varargs/before2/Sink.java")
    ));
    deck.add(new Slide("After the language change").add(
        Code.parseFile(PATH + "varargs/after/StringSink.java")
    ));

    deck.add(new Slide("Not so fast. One more loophole...").add(
        Code.parseFile(PATH + "varargs/before/PlainSink.java")
    ));
    deck.add(new Slide("After the language change").add(
        Code.parseFile(PATH + "varargs/after/PlainSink.java")
    ));

    deck.add(new Slide("All that work just to get...").add(
        Code.parseFile(PATH + "varargs/before/Arrays.java")
    ));
    deck.add(new Slide("So we can suppress it once and for all!").add(
        Code.parseFile(PATH + "varargs/after/Arrays.java")
    ));

    deck.add(new Slide("The moral of this story...").add(bullets()
        .$("Arrays and generics don't mix.")
        .$("Varargs should have used |List|.")
    ));

    deck.add(new Slide()
        .add(new Box(400, 775, 0, 0).add(
            new Text("*JSR-330:* Dependency Injection for Java")))
        .background(Picture.parseFile("images/misc/syringe.jpg")));

    deck.add(new Slide("package |javax.inject|").add(
        Picture.parseFile("images/misc/jsr330-api.png").center()));

    deck.add(new Slide("For example").add(
        Code.parseFile(PATH + "jsr330/constructor/Stopwatch.java")
    ));
    deck.add(new Slide("We could construct the time source directly.").add(
        Code.parseFile(PATH + "jsr330/constructor2/Stopwatch.java")
    ));
    deck.add(new Slide("Or use a factory.").add(
        Code.parseFile(PATH + "jsr330/factory/Stopwatch.java")
    ));
    deck.add(new Slide("|@Inject| provides the best of both worlds.").add(
        Code.parseFile(PATH + "jsr330/inject/Stopwatch.java")
    ));

    deck.add(new Slide("Testing against a factory").add(
        Code.parseFile(PATH + "jsr330/factory/StopwatchTest.java")
    ));
    deck.add(new Slide("Testing against a factory").add(
        Code.parseFile(PATH + "jsr330/factory/StopwatchTest.java"))
        .add(Spacer.vertical(30),
          new Text("*See the problem?*")
    ));

    deck.add(new Slide("Testing against a factory _the right way_").add(
        Code.parseFile(PATH + "jsr330/factory2/StopwatchTest.java")
    ));
    deck.add(new Slide("Testing using |@Inject|").add(
        Code.parseFile(PATH + "jsr330/inject/StopwatchTest.java")
    ));

    deck.add(new Slide("Scoping a dependency").add(
        Code.parseFile(PATH + "jsr330/AtomicClock.java")
    ));

    revealBullets(deck, "|@Inject| vs. the factory pattern",
        "Unit testing is easier.",
        "You don't need to write the factory.",
        "Scopes are declarative--no more hand-written DCL.",
        "Easier modularization.",
        "We can reuse |Stopwatch| with different time sources.",
        "Even concurrently.",
        "Unlike the service loader pattern...",
        "We can verify dependencies at build time."
        );

    revealBullets(deck, "JSR-330 itself",
        "Most open JSR ever",
        "Hosted on Google Code Hosting",
        "EG mailing list is publicly readable.",
        "Public observer list and issue tracker",
        "Spec, RI and TCK are all Apache-licensed.",
        "Fasted JSR ever: proposed to final in 4.5 months!"
        );

    deck.add(new Slide("Next step: Configuration").add(
        Code.parseFile(PATH + "jsr330/TimeModule.j")
    ));

    deck.add(new Slide("Other cool stuff").add(
        fillRight(Picture.parseFile("images/misc/icecream.jpg"), 555, 740)
    ).add(bullets()
        .$("Modules")
        .$("New sorting routines")
        .$("The G1 collector")
        .$("MapMaker")
    ));

    deck.add(new Slide("Thank you!").add(bullets()
        .$("Follow @crazybob or http://crazybob.org/")
        .$("Don't miss Alex's modules talk.")
        .$("Or my references talk.")
        .$("One more thing...")
    ).add(Picture.parseFile("images/misc/FatLadySings.jpg").width(400)
        .position(750, 300)));

    deck.writePdf(new Plain(), "out/foj.pdf", true);
  }

  private static Picture speedo() {
    return fillRight(Picture.parseFile("images/misc/speedo.jpg"), 683, 1024);
  }


  private static Picture fillRight(Picture p, int w, int h) {
    p.height(Deck.HEIGHT);
    int newWidth = Deck.HEIGHT * w / h;
    p.position(Deck.WIDTH - newWidth, 0);
    return p;
  }

  private static void revealBullets(Deck deck, String title,
      String... bullets) {
    for (int max = 0; max <= bullets.length; max++) {
      Bullets b = new Bullets();
      for (int i = 0; i < max; i++) {
        b.add(bullets[i]);
      }
      deck.add(new Slide(title).add(b));
    }
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
