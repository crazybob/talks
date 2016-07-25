package org.crazybob.deck;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.ColumnText;

/**
 *
 */
public abstract class Element {

  abstract void writePdf(Deck deck, ColumnText column)
      throws DocumentException;
}
