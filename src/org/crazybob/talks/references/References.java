package org.crazybob.talks.references;

import java.awt.Color;
import org.crazybob.deck.Box;
import org.crazybob.deck.Bullets;
import org.crazybob.deck.Code;
import org.crazybob.deck.Deck;
import org.crazybob.deck.Font;
import org.crazybob.deck.Picture;
import org.crazybob.deck.Slide;
import org.crazybob.deck.Spacer;
import org.crazybob.deck.Text;
import org.crazybob.deck.dot.DiGraph;
import org.crazybob.deck.dot.Link;
import org.crazybob.deck.dot.Node;
import org.crazybob.deck.templates.Square;
import org.crazybob.talks.ThankYou;

public class References {

  static final String PATH = "src/org/crazybob/talks/references/";

  public static void main(String[] args) {
    Deck deck = new Deck()
        .title("The Ghost in the Virtual Machine")
        .subtitle("A Reference to References")
        .author("Bob Lee")
        .company("Square Inc.");

    deck.add(new Slide("Goals").add(bullets().$("Take the mystery out of garbage collection.").$
        ("Put an end to finalizers.").$("Perform manual cleanup the Right Way.")));

    addHeapSlides(deck);

    deck.add(new Slide("Reachability").add(bullets()
        .$("An object is _reachable_ if a live thread can access it.")
        .$("Examples of heap roots:", bullets()
          .$("System classes (which have static fields)")
          .$("Thread stacks")
          .$("In-flight exceptions")
          .$("JNI global references")
          .$("The finalizer queue")
          .$("The interned String pool")
          .$("etc. (VM-dependent)")
        )
    ));

    deck.add(new Slide("Garbage collection isn't a magic bullet.").add(bullets()
        .$("Some things require manual cleanup.", bullets()
            .$("Listeners")
            .$("File descriptors")
            .$("Native memory")
            .$("External state (|IdentityHashMap|)")
        )
        .$("Tools at your disposal:", bullets()
            .$("try-finally")
            .$("Overriding |Object.finalize()|")
            .$("References (and reference queues)")
        )
    ));

    deck.add(new Slide("Try-finally first.").add(
        Code.parseFile(PATH + "copy/CopyFile.j")));

    deck.add(new Slide("Try-finally first.").add(bullets().$("Pros:", bullets().$("More "
        + "straightforward").$("Handles exceptions in main thread").$("Ensures cleanup keeps "
        + "pace")).$("Cons:", bullets().$("More work for programmers").$("More error prone").$
        ("Cleanup happens in main thread")).$("Try-with-resources will help.")));

    deck.add(new Slide("Try-with-resources").add(new Text("Before:").scale(75),
        Spacer.vertical(30), Code.parseFile(PATH + "copy/CopyFile.j").scale(90)));

    deck.add(new Slide("Try-with-resources").add(
        new Text("Before:").scale(75),
        Spacer.vertical(30),
        Code.parseFile(PATH + "copy/CopyFile.j").scale(90),
        Spacer.vertical(30),
        new Text("After:").scale(75),
        Spacer.vertical(30),
        Code.parseFile(PATH + "copy/CopyFileWithArm.j").scale(90)
    ));

    deck.add(new Slide("What is a finalizer?").add(
        new Text("A callback used by the garbage collector to notify an object"
            + " when it is about to be reclaimed:"),
        Spacer.vertical(60),
        Code.parseFile(PATH + "eg1/Foo.java")
    ));

    deck.add(new Slide("Finalizers are seductively simple, but...").add(bullets()
        .$("They're not guaranteed to run, especially not timely.")
        .$("Undefined threading model; they can run concurrently!")
        .$("You must remember to call |super.finalize()|.")
        .$("Exceptions are ignored (per spec).")
        .$("You can resurrect references.")
        .$("They keep objects alive longer than necessary.")
        .$("They can make allocation/reclamation 430X slower!"
            + "\n    (Bloch, _Effective Java_)")
        .$("Worst of all, they messed up the reference API.")
    ));

    deck.add(new Slide("Example").add(
        Code.parseFile(PATH + "nm/NativeMemory.java")));

    Slide s = new Slide("Let's play War!").add(
        new Text("|SegfaultFactory| can cause a segfault if its finalizer"
            + " executes after |NativeMemory|'s:"),
        Spacer.vertical(60),
        Code.parseFile(PATH + "nm/SegfaultFactory.java")
    );
    s.add(Picture.parseFile("images/references/war.jpg")
        .position(950, 350));
    deck.add(s);

    deck.add(new Slide("Always use protection.").add(
        Code.parseFile(PATH + "snm/NativeMemory.java")));

    s = new Slide("Basically, finalizers are good for one thing.").add(
        new Text("Logging warnings:"),
        Spacer.vertical(40),
        Code.parseFile(PATH + "Connection.java"));
    deck.add(s.copy());
    s.add(new Box(400, 800, 0, 0).add(
        new Text("Unless you want to disable the warnings.")
            .font(new Font(Font.Face.HELVETICA, 24, Font.Style.BOLD, Color.RED))));
    deck.add(s);

    deck.add(new Slide("The alternative: The Reference API").add(bullets()
        .$("|@since 1.2|")
        .$("Reference types", bullets()
          .$("*Soft:* for caching")
          .$("*Weak:* for fast cleanup (pre-finalizer)")
          .$("*Phantom:* for safe cleanup (post-finalizer)")
        )
        .$("*Reference queues:* for notifications")
    ));

    deck.add(new Slide("|package java.lang.ref|").add(
        Code.parseFile(PATH + "ref.api").scale(90)
    ));

    deck.add(new Slide("Soft references").add(bullets()
        .$("Cleared when the VM runs low on memory", bullets()
            .$("_Hopefully_ in LRU fashion")
        )
        .$("Tuned with |-XX:SoftRefLRUPolicyMSPerMB|", bullets()
          .$("How long to retain soft refs in _ms per free MB of heap_")
          .$("*Default:* 1000ms")
        )
    ));

    deck.add(new Slide("Use soft references judiciously.").add(bullets()
        .$("For quick-and-dirty caching only")
        .$("Soft refs have no notion of _weight_:", bullets()
          .$("Memory usage")
          .$("Computation time")
          .$("CPU usage")
        )
        .$("Soft refs can exacerbate low memory conditions.")
    ));

    deck.add(new Slide("Caching a file").add(
        Code.parseFile(PATH + "CachedFile.java")
    ));

    deck.add(new Slide("Caching a file").add(Code.parseFile(PATH + "CachedFile.java"))
        .add(new Box(150, 0, 50, 50).add(
            new Text("X").font(
                new Font(Font.Face.HELVETICA, 200, Font.Style.NORMAL, Color.RED)))));

    deck.add(new Slide("Weak references").add(bullets()
        .$("Cleared as soon as no strong or soft refs remain.")
        .$("Cleared ASAP, before the finalizer runs.")
        .$("*Not for caching!* Use soft references, as intended:"),
        Spacer.vertical(50),
        new Text("_ÒVirtual machine implementations are encouraged"
            + " to bias against clearing recently-created or recently-used"
            + " soft references.Ó_"),
        Spacer.vertical(30),
        new Text("- The |SoftReference| documentation").scale(75)
    ));

    deck.add(new Slide("_Can you hear me now?_").add(
        Code.parseFile(PATH + "Button.java")
    ));

    deck.add(new Slide("Phantom references").add(bullets()
        .$("Enqueued after no other references remain, _post-finalizer_.", bullets()
          .$("Can suffer similar problems to finalizers."))
        .$("Must be cleared manually, for no good reason.")
        .$("|get()| always returns |null|.", bullets()
          .$("So you must use a reference queue.")
        )
    ));

    deck.add(new Slide("Let's replace a finalizer!").add(
        Code.parseFile(PATH + "eg2/NativeMemory.java")));

    deck.add(new Slide("The reference").add(
        Code.parseFile(PATH + "eg2/NativeMemoryReference.java")));

    deck.add(new Slide("The manager").add(
        Code.parseFile(PATH + "eg2/NativeMemoryManager.java")));

    deck.add(new Slide("The manager _with the Guava Libraries_").add(
        Code.parseFile(PATH + "eg3/NativeMemoryManager.java")));

    deck.add(new Slide("*Tip:* accessing a phantom referent").add(
        Code.parseFile(PATH + "WeakPhantomReference.java")
    ));

    deck.add(new Slide("Don't forget...").add(
      new Text("The GC runs concurrently with your code:"),
      Spacer.vertical(60),
      Code.parseFile(PATH + "RaceTheCollector.java")
    ));

    deck.add(new Slide("|java.util.WeakHashMap|").add(bullets()
      .$("Useful for emulating additional fields")
      .$("Keeps weak refs to keys, strong refs to values")
      .$("Not concurrent")
      .$("Uses |equals()| when it should use |==|")
    ));

    deck.add(new Slide("Guava |MapMaker|").add(bullets()
      .$("Near drop-in replacement for |WeakHashMap|")
      .$("Strong, soft, or weak key and/or value references")
      .$("Uses |==| to compare weak and soft referents")
    ));

    deck.add(new Slide("Guava |CacheBuilder|").add(bullets()
      .$("Superset of |MapMaker|")
      .$("Supports on-demand computation of values")
      .$("Expiration")
      .$("Size limiting")
    ));

    deck.add(new Slide("Guava |CacheBuilder|").add(
        Code.parseFile(PATH + "GetterMethods.java"),
        Spacer.vertical(20),
        new Text("*Usage:  |List<Method> l = GetterMethods.on(Foo.class);|*").scale(80)
    ));

    highlightBullets(deck, "Recap: The Levels of Reachability",
        "Strong", "Soft", "Weak", "Finalizer", "Phantom, JNI weak",
        "Unreachable");

    MarkAndSweep tracer = new MarkAndSweep(deck, 12);
    tracer.addSlides();

    deck.add(new Slide("Brought to you by the letters J, A, V & A...")
            .add(Picture.parseFile("images/references/ide2.png").fill().center()));

    deck.add(ThankYou.slide());

    deck.writePdf(new Square(), "out/references.pdf", true);
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

  private static void addHeapSlides(Deck deck) {
    DiGraph heap = new DiGraph();

    Node root1 = heap.newNode("root").fillColor("black").fontColor("white");
    Node root2 = heap.newNode("root").fillColor("black").fontColor("white");

    Node a = heap.newNode("a").fillColor("white");
    Node b = heap.newNode("b").fillColor("white");
    Node c = heap.newNode("c").fillColor("white");
    Node d = heap.newNode("d").fillColor("white");
    Node e = heap.newNode("e").fillColor("white");

    Link r1a = root1.pointTo(a);
    Link r1d = root1.pointTo(d);

    Link r2b = root2.pointTo(b);

    Link ab = a.pointTo(b);
    Link ac = a.pointTo(c);
    Link bc = b.pointTo(c);

    Link de = d.pointTo(e);
    Link eb = e.pointTo(b);

    deck.add(new Slide("How does garbage collection work?").add(
        Picture.parseDot(heap.toString()).fill().center()));

    r1d.hide();

    deck.add(new Slide("If the reference to D goes away...").add(
        Picture.parseDot(heap.toString()).fill().center()));

    d.fillColor("lightgrey").lineColor("lightgrey");
    e.fillColor("lightgrey").lineColor("lightgrey");
    de.color("lightgrey");
    eb.color("lightgrey");

    deck.add(new Slide("We can no longer reach D or E.").add(
        Picture.parseDot(heap.toString()).fill().center()));

    d.hide();
    e.hide();

    deck.add(new Slide("So the collector reclaims them.").add(
        Picture.parseDot(heap.toString()).fill().center()));
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
