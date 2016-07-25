package org.crazybob.deck;

import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;

/**
 *
 */
public class Spacer {

  private Spacer() {}

  public static Element vertical(final int height) {
    return new Element() {
      void writePdf(Deck deck, ColumnText column) throws DocumentException {
        Paragraph spacer = new Paragraph(" ");
        spacer.setLeading(height);
        column.addElement(spacer);
      }
    };
  }
}
