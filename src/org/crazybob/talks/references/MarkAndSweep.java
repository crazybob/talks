package org.crazybob.talks.references;

import org.crazybob.deck.dot.DiGraph;
import org.crazybob.deck.dot.Node;
import org.crazybob.deck.dot.Link;
import org.crazybob.deck.Slide;
import org.crazybob.deck.Picture;
import org.crazybob.deck.Deck;
import org.crazybob.deck.Text;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.LinkedHashSet;

public class MarkAndSweep {

  final Random random;
  final Deck deck;
  final DiGraph heap = new DiGraph();
  final Root root = new Root();
  final FinalizerQueue finalizerQueue = new FinalizerQueue();
  final List<Normal> normalList = new ArrayList<Normal>();
  final Set<Normal> normals = new LinkedHashSet<Normal>();
  final Set<Soft> softs = new LinkedHashSet<Soft>();
  final Set<Weak> weaks = new LinkedHashSet<Weak>();
  final Set<Phantom> phantoms = new LinkedHashSet<Phantom>();
  final List<Obj> allObjects = new ArrayList<Obj>();
  String title = null;

  private static final String GRAY = "#999999";
  private static final String BLACK = "black";
  private static final String WHITE = "#FFFFFF";
  private static final String TRANSPARENT = "#00000000";

  private static final String[] STEPS = {
      "*1.* Start at a root.",
      "*2.* Trace and mark strongly-referenced objects.",
      "*3.* Optionally clear soft references.",
      "*4.* Trace and mark softly-referenced objects.",
      "*5.* Clear weak references.",
      "*6.* Enqueue finalizable objects.",
      "*7.* Repeat steps 1 through 5 for the queue.",
      "*8.* Possibly enqueue phantom references.",
      "*9.* The remaining objects are dead.",
      "*10.* Repeat."
  };

  static class Pointer {
    final Obj target;
    final Link link;
    Pointer(Obj target, Link link) {
      this.target = target;
      this.link = link;
    }
    void light() {
      link.style("dashed");
      link.color(GRAY);
    }
    void dark() {
      link.style(null);
      link.color(BLACK);
    }
    void hide() {
      link.hide();
    }
  }

  abstract class Obj {
    final Node node;
    final List<Pointer> pointers = new ArrayList<Pointer>();
    boolean marked = false;

    Obj(String label) {
      this.node = heap.newNode(label);
      dark();
    }

    abstract void light();
    abstract void dark();

    void show() {
      node.show();
      dark();
    }

    void hide() {
      node.hide();
    }

    Pointer reference(Obj target) {
      Pointer p = new Pointer(target, node.pointTo(target.node));
      pointers.add(p);
      return p;
    }

    void mark() {
      marked = true;
      dark();
    }

    boolean shouldTrace() {
      return false;
    }

    boolean pointsTo(Obj other) {
      for (Pointer pointer : pointers) {
        if (pointer.target == other) return true;
      }
      return false;
    }
  }

  class FinalizerQueue extends Obj {
    FinalizerQueue() {
      super("Finalizer Queue");
      node.hide();
    }
    void light() {
      throw new UnsupportedOperationException();
    }
    void show() {
      node.show();
      dark();
    }
    void dark() {
      node.fillColor(BLACK).fontColor(WHITE).lineColor(BLACK);
    }
    @Override boolean shouldTrace() {
      return true;
    }
  }

  class Root extends Obj {
    Root() {
      super("root");
    }
    void light() {
      node.fillColor(GRAY).lineColor(GRAY).fontColor(WHITE);
    }
    void dark() {
      node.fillColor(BLACK).fontColor(WHITE).lineColor(BLACK);
    }
    @Override boolean shouldTrace() {
      return true;
    }
  }

  class Normal extends Obj {
    Normal() {
      super("object");
    }
    void light() {
      node.fillColor(TRANSPARENT).fontColor(GRAY).lineColor(GRAY);
    }
    void dark() {
      node.fillColor("#999999").fontColor(WHITE).lineColor(BLACK);
    }
    @Override boolean shouldTrace() {
      return true;
    }
  }

  boolean traceSofts = false;

  class Soft extends Obj {
    Soft() {
      super("soft");
    }
    void light() {
      node.fillColor("#eeffee").fontColor(GRAY).lineColor(GRAY);
    }
    void dark() {
      node.fillColor("#009900").fontColor(WHITE).lineColor(BLACK);
    }
    @Override boolean shouldTrace() {
      return traceSofts;
    }
  }

  class Weak extends Obj {
    Weak() {
      super("weak");
    }
    void light() {
      node.fillColor("#eeeeff").fontColor(GRAY).lineColor(GRAY);
    }
    void dark() {
      node.fillColor("#000099").fontColor(WHITE).lineColor(BLACK);
    }
  }

  class Phantom extends Obj {
    Phantom() {
      super("phantom");
    }
    void light() {
      node.fillColor("#ffeeee").fontColor(GRAY).lineColor(GRAY);
    }
    void dark() {
      node.fillColor("#990000").fontColor(WHITE).lineColor(BLACK);
    }
  }

  public MarkAndSweep(Deck deck, int seed) {
    this.deck = deck;
    this.random = new Random(seed);

    for (int i = 0; i < 35; i++) {
      Normal n = new Normal();
      normalList.add(n);
      normals.add(n);
      allObjects.add(n);
    }

    for (int i = 0; i < 3; i++) {
      softs.add(new Soft());
      weaks.add(new Weak());
      phantoms.add(new Phantom());
    }
    allObjects.addAll(softs);
    allObjects.addAll(weaks);
    allObjects.addAll(phantoms);

    for (int i = 0; i < 5; i++)
      randomlyLink(root);
    for (Normal normal : normalList)
      randomlyLink(normal);
    for (int i = 0; i < 15; i++)
      randomlyLink(normalList.get(random.nextInt(normalList.size())));

    List<Obj> refs = new ArrayList<Obj>();
    refs.addAll(softs);
    refs.addAll(weaks);
    refs.addAll(phantoms);
    for (Obj o : refs)
      o.reference(normalList.get(random.nextInt(normalList.size())));

    // Add root last so we don't reference it.
    allObjects.add(root);

    // Set up finalizable object queue.
    Set<Normal> unreachables = new LinkedHashSet<Normal>();
    unreachables.addAll(normals);
    removeReachables(root, unreachables);
    for (Obj o : allObjects) o.marked = false;
    int count = 0;
    for (Normal unreachable : unreachables) {
      if (count++ > 2) break;
      finalizerQueue.reference(unreachable).light();
    }
  }

  private void removeReachables(Obj current, Set<Normal> unreachables) {
    if (!(current instanceof Weak || current instanceof Phantom)) {
      for (Pointer pointer : current.pointers) {
        MarkAndSweep.Obj target = pointer.target;
        if (target.marked) continue;
        target.marked = true;
        unreachables.remove(pointer.target);
        removeReachables(pointer.target, unreachables);
      }
    }
  }

  public void addSeededSlide(int seed) {
    title = "Seed: " + seed;
    addSlide();
  }

  public void addSlides() {
    title = "Let's mark and sweep a heap!";
    addSlide();

    for (Obj o : allObjects) {
      o.light();
      for (Pointer pointer : o.pointers) pointer.light();
    }

    title = "No objects are marked at first.";
    addSlide();

    root.mark();

    title = STEPS[0];
    addSlide();

    title = STEPS[1];
    addSlide();
    trace(root);
    flushSlides();

    title = STEPS[2];
    addSlide();

    // Note: It's important to trace all strong references before soft
    // references so we don't clear a soft reference that points to
    // a strongly-references object.

    title = STEPS[3];
    // Note: we'd normally also recursively clear/trace softly-referenced
    // soft references.
    addSlide();
    traceSofts = true;
    for (Soft soft : softs) {
      if (soft.marked) {
        trace(soft);
      }
    }
    flushSlides();

    title = STEPS[4];
    addSlide();
    traceMarkedWeakRefs();
    List<Weak> weaksToClear = weaksToClear();
    for (Weak weak : weaksToClear) {
      weak.pointers.iterator().next().link.color("#ff0000");
    }
    addSlide();
    for (Weak weak : weaksToClear) {
      weak.pointers.iterator().next().hide();
    }    
    addSlide();

    title = STEPS[5];
    addSlide();
    finalizerQueue.show();
    addSlide();

    title = STEPS[6];
    addSlide();
    trace(finalizerQueue);
    traceMarkedWeakRefs();
    addSlide();

    title = STEPS[7];
    addSlide();
    for (Phantom phantom : phantoms) {
      Pointer pointer = phantom.pointers.iterator().next();
      if (phantom.marked && pointer.target.marked) {
        pointer.dark();
      }
    }
    addSlide();

    title = STEPS[8];
    addSlide();
    for (Obj o : allObjects) {
      if (!o.marked) o.hide();
    }
    addSlide();

    title = STEPS[9];
    addSlide();

    Slide recap = new Slide("Recap");
    for (String step : STEPS) {
      recap.add(new Text(step));
    }
    deck.add(recap);



//    title = "Here's the before snapshot for comparison.";
//
//    finalizerQueue.hide();
//    for (Obj o : allObjects) {
//      o.show();
//      for (Pointer pointer : o.pointers) {
//        pointer.dark();
//      }
//    }
//
//    addSlide();
  }

  /**
   * If a weak reference points to a marked object, darken the pointer.
   */
  private void traceMarkedWeakRefs() {
    for (Weak weak : weaks) {
      Pointer pointer = weak.pointers.iterator().next();
      if (weak.marked && pointer.target.marked) {
        pointer.dark();
      }
    }
  }

  private List<Weak> weaksToClear() {
    List<Weak> l = new ArrayList<Weak>();
    for (Weak weak : weaks) {
      if (weak.marked) {
        Pointer p = weak.pointers.iterator().next();
        if (!p.target.marked) {
          l.add(weak);
        }
      }
    }
    return l;
  }

  private void randomlyLink(Obj source) {
    // Pick an object that isn't the same as the source and that isn't already
    // pointed to.
    Obj other;
    do {
      int destIndex = random.nextInt(allObjects.size());
      other = allObjects.get(destIndex);
    } while (other == source || source.pointsTo(other));
    source.reference(other);
  }

  private void addSlide() {
    this.deck.add(new Slide(title).add(
        Picture.parseDot(heap.toString()).fill().center()));
  }

  int counter = 0;

  void maybeAddSlide() {
    counter++;
    if (counter == 5) {
      counter = 0;
      addSlide();
    }
  }

  private void flushSlides() {
    if (counter > 0) {
      counter = 0;
      addSlide();
    }
  }
  
  private void trace(Obj from) {
    if (from.shouldTrace()) {
      for (Pointer p : from.pointers) {
        p.dark();
        if (p.target.marked) {
          maybeAddSlide();
        } else {
          p.target.mark();
          maybeAddSlide();
          trace(p.target);
        }
      }
    }
  }
}
