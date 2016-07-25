package org.crazybob.deck;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.ColumnText;

import java.util.List;
import java.util.ArrayList;

/**
 * Absolutely-positioned container.
 */
public class Box extends PositionedElement {

  final List<Element> elements = new ArrayList<Element>();
  final Margins margins;

  public Box(Margins margins) {
    this.margins = margins;
  }

  public Box(int left, int top, int right, int bottom) {
    this.margins = new Margins(left, top, right, bottom);
  }

  public Box add(Element element) {
    elements.add(element);
    return this;
  }
  
  void writePdf(Deck deck, boolean hasTitle) throws DocumentException {
    ColumnText column = new ColumnText(deck.writer.getDirectContent());
    margins.applyTo(column);
    for (Element element : elements) {
      element.writePdf(deck, column);
    }
    if (column.go() == ColumnText.NO_MORE_COLUMN) {
      System.err.println("Warning: Overrun in slide #" + deck.slideIndex);
    }
  }
}
