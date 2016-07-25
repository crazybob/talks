// Copyright 2011 Square, Inc.
package org.crazybob.talks;

import org.crazybob.deck.Deck;
import org.crazybob.deck.Picture;
import org.crazybob.deck.Slide;
import org.crazybob.deck.Spacer;
import org.crazybob.deck.Text;

/** @author Bob Lee (bob@squareup.com) */
public class ThankYou {

  public static Slide slide() {
    return new Slide("Thank You!")
        .add(new Text("If you enjoyed this talk,\nfollow |@crazybob| on Twitter."))
        .add(Spacer.vertical(60))
        .add(new Text("*We're hiring!* Email résumés\nto |java-jobs@squareup.com|."))
        .add(fillRight(Picture.parseFile("images/square/swipe.png"), 2000, 2369));
  }

  private static Picture fillRight(Picture p, int w, int h) {
    p.height(Deck.HEIGHT);
    int newWidth = Deck.HEIGHT * w / h;
    p.position(Deck.WIDTH - newWidth, 0);
    return p;
  }
}
