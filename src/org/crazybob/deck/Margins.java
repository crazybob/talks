package org.crazybob.deck;

import com.lowagie.text.pdf.ColumnText;

/**
 *
 */
public class Margins {

  final int left, top, right, bottom;

  public Margins(int left, int top, int right, int bottom) {
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
  }

  public int centerX() {
    return (Deck.WIDTH - right - left) / 2 + left;
  }

  public int centerY() {
    return (Deck.HEIGHT - top - bottom) / 2 + top;
  }

  public int width() {
    return Deck.WIDTH - left - right;
  }

  public int height() {
    return Deck.HEIGHT - top - bottom;  
  }

  void applyTo(ColumnText column) {
    column.setSimpleColumn(left, bottom, Deck.WIDTH - right, Deck.HEIGHT - top);
  }

  static Margins encompass(Margins... marginArray) {
    int left = Integer.MAX_VALUE;
    int top = Integer.MAX_VALUE;
    int right = Integer.MAX_VALUE;
    int bottom = Integer.MAX_VALUE;

    for (Margins margins : marginArray) {
      left = Math.min(left, margins.left);
      top = Math.min(top, margins.top);
      right = Math.min(right, margins.right);
      bottom = Math.min(bottom, margins.bottom);
    }
    return new Margins(left, top, right, bottom);
  }
}
