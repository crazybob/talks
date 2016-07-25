package org.crazybob.deck;

import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
public class Slide {

  String title;
  final List<Element> inlineElements = new ArrayList<Element>();
  final List<PositionedElement> positionedElement
      = new ArrayList<PositionedElement>();
  Picture background;

  public Slide() {}

  public Slide(String title) {
    this.title = title;
  }

  public Slide copy() {
    Slide copy = new Slide(title);
    copy.inlineElements.addAll(inlineElements);
    copy.positionedElement.addAll(positionedElement);
    copy.background = background;
    return copy;
  }

  public Slide background(Picture background) {
    this.background = background;
    return this;
  }

  public Slide add(Element element) {
    inlineElements.add(element);
    return this;
  }

  public Slide add(Element... elements) {
    inlineElements.addAll(Arrays.asList(elements));
    return this;
  }

  public Slide add(PositionedElement element) {
    positionedElement.add(element);
    return this;
  }

  void writePdf(Deck deck) throws DocumentException {
    Template template = deck.template;

    if (background != null) {
      deck.document.add(background.asBackgroundImage());
    } else {
      Picture defaultBackground = template.defaultBackground();
      if (defaultBackground != null) {
        deck.document.add(defaultBackground.asBackgroundImage());
      }
    }

    if (title != null) {
      ColumnText column = new ColumnText(deck.writer.getDirectContent());
      template.titleMargins().applyTo(column);
      column.addElement(new Text(title).toParagraph(template.titleFont()));
      if (column.go() == ColumnText.NO_MORE_COLUMN) {
        System.err.println("Warning: Overrun in slide #" + deck.slideIndex);
      }
    }

    if (!inlineElements.isEmpty()) {
      ColumnText column = new ColumnText(deck.writer.getDirectContent());
      template.contentMargins().applyTo(column);
      for (Element element : inlineElements) {
        element.writePdf(deck, column);
      }
      if (column.go() == ColumnText.NO_MORE_COLUMN) {
        System.err.println("Warning: Overrun in slide #" + deck.slideIndex);
      }
    }

    for (PositionedElement element : positionedElement) {
      element.layOut(deck, this);
      element.writePdf(deck, title != null);
    }
  }
}
