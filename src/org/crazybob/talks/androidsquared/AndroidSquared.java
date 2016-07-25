// Copyright 2010 Square, Inc.
package org.crazybob.talks.androidsquared;

import org.crazybob.deck.*;
import org.crazybob.deck.templates.Square;

/**
 * Android Squared.
 *
 * @author Eric Burke (eric@squareup.com)
 */
public class AndroidSquared {
  static final String PATH = "src/org/crazybob/talks/androidsquared/";

  public static void main(String[] args) {
    Deck deck = new Deck()
        .title("Android\u00B2")
        .subtitle("")
        .author("Bob Lee & Eric Burke")
        .company("Square, Inc.");

    Template template = new Square();

    // Break it down into main sections for ease of collaboration.
    introduction(template, deck);
    squarewave(template, deck);
    retrofit(template, deck);
    io(template, deck);
    shakeDetector(template, deck);
    restAdapter(template, deck);
    api(template, deck);
    thankYou(template, deck);

    deck.writePdf(template, "out/androidsquared.pdf", true);
  }

  private static void introduction(Template template, Deck deck) {
    deck.add(new Slide("Square")
        .add(picture("nexusone.png").width(1000).center()));

    //deck.add(new Slide().background(picture("buffet.jpg")));
    deck.add(new Slide().add(picture("woot-bag-of-crap.jpg").center()));

    deck.add(new Slide("Overview").add(bullets()
        .$("Squarewave")
        .$("Retrofit", bullets()
            .$("I/O")
            .$("Shake detection")
            .$("REST")
        )
        .$("Point-of-sale API")
    ));
  }

  private static void squarewave(Template template, Deck deck) {
    deck.add(new Slide("*Squarewave:* Magnetic Stripe Decoder").add(
        fillBottom(picture("swipe.jpg"), 640, 308)
    ));

    deck.add(new Slide("Square Reader")
        .add(fillRight(picture("reader.png"),
            362, 393))
        .add(new Text("Acts as a microphone")));

    deck.add(new Slide("Ideal Waveform")
        .add(picture("ideal-waveform.png").center()));

    deck.add(new Slide()
        .background(picture("dr-evil-epic-4g.png")));

    deck.add(new Slide()
        .background(picture("devices.jpg")));

    deck.add(new Slide("Actual Swipe Recording")
        .add(picture("swipe-1.png").center()));

    deck.add(new Slide("Swipe Start")
        .add(picture("swipe-2.png").center()));

    deck.add(new Slide("Swipe End")
        .add(picture("swipe-3.png").center()));

    bullets(deck, "Challenges",
        "Swipe speed",
        "Device sample rate",
        "Audio correction");

    steps(deck,
        "Swipe Detection", "swipe-detection.png",
        "Denoising", "denoising.png",
        "Peak Detection", "peak-detection.png",
        "Window Peak Removal", "window-peak-removal.png",
        "Consecutive Peak Removal", "consecutive-peak-removal.png",
        "Decoding", "decoding.png"
    );

    deck.add(new Slide("Workbench")
        .add(picture("workbench.png").center()));

    deck.add(new Slide("Testing")
        .add(picture("test-swipes.png").center()));

    bullets(deck, "Find the Best Options",
        "Decode every swipe",
        "What percentage passed?",
        "Repeat");

    deck.add(new Slide("Good Enough?")
        .add(picture("80-percent.png").center()));

    bullets(deck, "95% Success!",
        "For the remaining 20%",
        "Find the best options again",
        "And again...");
  }

  private static void retrofit(Template template, Deck deck) {
    deck.add(new Slide().background(picture("retrofit.jpg")));

    deck.add(new Slide("Retrofit").add(bullets()
        .$("Extends Android and Java")
        .$("Apache-licensed")
        .$("Modules", bullets()
            .$("|core|")
            .$("|io|")
            .$("|http|")
            .$("|android|")
          )
        .$("|http://github.com/square/retrofit|")
    ));
  }

  private static void io(Template template, Deck deck) {
    deck.add(new Slide("Square for Android Persistence").add(bullets()
        .$("Queues")
        .$("Key-value pairs")
        .$("No SQL")
    ));

    deck.add(new Slide("Persistent Queue").add(bullets()
        .$("Sending data to a server", bullets()
            .$("Emails (Receipts)")
            .$("Image uploads") // receipt images, signatures
            .$("Payments")      // captures, voids, cash
            .$("Analytics")     // swipe stats, funnels
            .$("Crash dumps")
        )
        .$("Histories")
    ));

    deck.add(new Slide("Traditional Approaches").add(bullets()
        .$("SQLite", bullets()
            .$("Operations are O(log(n))")
            .$("Rollback journal requires multiple operations")
            .$("Write ahead log has other tradeoffs")
            .$("|xDeviceCharacteristics|")
        )
        .$("File-per-element", bullets()
            .$("4k/entry minimum")
            .$("Several I/O operations")
        )
    ));

    deck.add(new Slide("|QueueFile|").add(bullets()
        .$("All operations are O(1)")
        .$("Writes sync")
        .$("Writes are atomic")
    ).add(new Text(" "))
        .add(Code.parseFile(PATH + "queuefile/QueueFile.j").scale(90)));

    Picture queueFilePicture = picture("QueueFile.png").width(Deck.WIDTH * 3 / 4)
        .position(150, Deck.HEIGHT - 375);

    deck.add(new Slide("The Implementation").add(bullets()
        .$("Depends somewhat on YAFFS", bullets()
            .$("Yet Another Flash File System")
            .$("Android's preeminent file system")
            .$("Supports atomic sector writes")
        )
        .$("Writing the header commits a change")
    ).add(queueFilePicture));

    deck.add(new Slide("|QueueFile.add()|").add(bullets()
        .$("Write element data")
        .$("Write header (16 bytes < 4k)")
        .$("Update in-memory state")
    ).add(queueFilePicture));

    deck.add(new Slide("|QueueFile.remove()|").add(bullets()
        .$("Write header")
        .$("Update in-memory state")
    ).add(queueFilePicture));

    deck.add(new Slide("Buffer expansion").add(bullets()
        .$("|file.setLength(oldLength << 1)|")
        .$("Make ring buffer contiguous")
        .$("Write header (including file length)")
        .$("Update in-memory state")
    ).add(queueFilePicture));

    deck.add(new Slide("Future Features").add(bullets()
        .$("Support file systems without atomic segment writes", bullets()
          .$("Rollback journal"))
        .$("Batch writes", bullets()
          .$("Optimistic batching")
          .$("> 3 orders of magnitude throughput"))
    ));

  }

  private static void shakeDetector(Template template, Deck deck) {
    deck.add(new Slide().background(picture("etch-a-sketch.jpg")));

    deck.add(new Slide("Shake to Clear Signature")
        .add(picture("signature.png").center()));

    deck.add(new Slide("Using the Accelerometer").add(
        Code.parseFile(PATH + "shakedetector/HelloAccelerometer.java")
    ));

    bullets(deck, "Accelerometer Values",
        "x, y, and z acceleration",
        "Units are m/s^2",
        "Acceleration applied to device _minus force of gravity_",
        "When flat on a table, Z accel = +9.81 (0 - -9.81)");

    deck.add(new Slide("Device at Rest")
        .add(picture("accelerometer-at-rest.png").center()));

    deck.add(new Slide("Magnitude (Pythagorean)").add(
        Code.parseFile(PATH + "shakedetector/Magnitude.java")));

    deck.add(new Slide("Magnitude Graph")
        .add(picture("accelerometer-data.png").center()));

    deck.add(new Slide("Threshold")
        .add(picture("acceleration-threshold.png").center()));

    deck.add(new Slide("Data Rates Vary by Device")
        .add(picture("accelerometer-rates.png").center()));

    deck.add(new Slide("Solution: Variable Size Window").add(bullets()
        .$("Did the magnitude exceed the threshold?")
        .$("Store true/false readings in a queue")
        .$("Queue holds readings from last 500ms")
        .$("When 75% of readings are true, shake")));

    deck.add(new Slide("ShakeDetector").add(
        Code.parseFile(PATH + "shakedetector/ShakeDetector.java")));

    deck.add(new Slide("Using ShakeDetector").add(
        Code.parseFile(PATH + "shakedetector/ShakeDemo.java")));

  }

  private static void restAdapter(Template template, Deck deck) {
    deck.add(new Slide().background(picture("rest.jpg")));

    deck.add(new Slide("|RestAdapter|").add(bullets()
        .$("Makes RESTful clients a breeze")
        .$("Currently supports JSON responses")
        .$("All user code runs in main thread")
    ));

    deck.add(new Slide("Example").add(
        Code.parseFile(PATH + "http/AccountService.java")));

    deck.add(new Slide("|ServerCall|").add(bullets()
        .$("UI handler for server calls")
        .$("Handles all dialogs", bullets()
          .$("Progress")
          .$("Operation failed")
          .$("Network unavailable")
          .$("Server unavailable")
          .$("Unexpected error")
        )
        .$("Supports retries")
    ));

    deck.add(new Slide("Examples").add(bullets()
        .$("Login")
        .$("Signup")
        .$("Authorization")
    ));

    deck.add(new Slide("Example").add(
        Code.parseFile(PATH + "http/LoginActivity.j").scale(80)));
  }

  private static void api(Template template, Deck deck) {
    deck.add(new Slide("Point-of-Sale API")
        .add(picture("taste.png").center().height(Deck.HEIGHT * 2 / 3)));

    deck.add(new Slide("Example").add(
        Code.parseFile(PATH + "TwoCents.j")));
  }

  private static void thankYou(Template template, Deck deck) {
    deck.add(new Slide("Questions?").add(picture("questions.jpg")
        .height(Deck.HEIGHT * 2 / 3).center()));

    deck.add(new Slide().background(picture("office.jpg")));

    deck.add(new Slide("Thank You!").add(bullets()
      .$("Retrofit: |http://github.com/square/retrofit|")
      .$("We're hiring!", bullets()
        .$("|http://squareup.com/jobs|"))
    ));
  }

  private static Picture picture(String name) {
    return Picture.parseFile("images/androidsquared/" + name);
  }

  /** Returns a section title slide. */
  private static Slide sectionTitleSlide(Template template, String title) {
    Slide slide = new Slide();
    Box titleBox = new Box(300, 300, 50, 50);
    titleBox.add(new Text(title).font(template.titleFont().scale(125)));

    // TODO a horizontal line would look good here

    slide.add(titleBox);

    return slide;
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
