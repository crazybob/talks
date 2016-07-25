package org.crazybob.deck;

import com.lowagie.text.Paragraph;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;

import java.awt.Color;
import java.io.IOException;

import static org.crazybob.deck.Objects.nonNull;

/**
 *
 */
public class Font {

  public enum Face {
    HELVETICA(com.lowagie.text.Font.HELVETICA),
    COURIER(com.lowagie.text.Font.COURIER);

    final int id;
    Face(int id) {
      this.id = id;
    }
  }

  public enum Style {
    NORMAL(com.lowagie.text.Font.NORMAL),
    BOLD(com.lowagie.text.Font.BOLD),
    ITALIC(com.lowagie.text.Font.ITALIC),
    BOLD_ITALIC(com.lowagie.text.Font.BOLDITALIC);

    final int id;
    Style(int id) {
      this.id = id;
    }
  }

  final Face face;
  final com.lowagie.text.Font pdfFont;
  final int leading;
  final int size;
  final Color color;
  final Style style;

  /**
   *
   * @param size in pts
   */
  public Font(Face face, int size, Style style, Color color) {
    this(face, size, style, color, size * 3 / 2);
  }

  /**
   *
   * @param size in pts
   */
  public Font(Face face, int size, Style style, Color color, int leading) {
    this.face = nonNull(face);
    this.leading = leading;
    int styleId = style.id;
    if (face == Face.COURIER) {
      if (style == Style.NORMAL) styleId = Style.BOLD.id;
      else if (style == Style.ITALIC) styleId = Style.BOLD_ITALIC.id;
    }
    this.pdfFont = new com.lowagie.text.Font(face.id, Deck.ptsToPixels(size),
        styleId, color);
    this.color = nonNull(color);
    this.size = size;
    this.style = nonNull(style);
  }

  public Font withStyle(Style style) {
    return new Font(face, size, style, color, leading);
  }

  public Font scale(int percent) {
    return new Font(face, size * percent / 100, style, color,
        leading * percent / 100);
  }

  Font toCode() {
    return new Font(Face.COURIER, size, style, color, leading);
  }

  Font fromCode() {
    return new Font(Face.HELVETICA, size, style, color, leading);
  }

  Paragraph newParagraph() {
    return new Paragraph(Deck.ptsToPixels(leading));
  }

  Paragraph newParagraph(String text) {
    return new Paragraph(Deck.ptsToPixels(leading), text, pdfFont);
  }
}
