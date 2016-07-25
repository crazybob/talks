package org.crazybob.deck;

import com.lowagie.text.DocumentException;

/**
 *
 */
public abstract class PositionedElement {

  abstract void writePdf(Deck deck, boolean hasTitle) throws DocumentException;
  protected void layOut(Deck deck, Slide slide) {}
}