package org.crazybob.deck;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 */
public class Text extends Element {

  final String s;
  Font font;
  int scale = 100;

  public Text(String s) {
    this.s = s;
  }

  public Text font(Font font) {
    this.font = font;
    return this;
  }

  public Text scale(int percent) {
    this.scale = percent;
    return this;
  }

  Paragraph toParagraph(Font defaultFont) {
    Font f = Objects.nonNull(font == null ? defaultFont : font).scale(scale);
    Paragraph p = new Paragraph(Deck.ptsToPixels(f.leading));
    StringBuilder b = new StringBuilder(s.length());
    boolean inCode = false;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      switch (c) {
        case '|':
          flush(b, p, f);
          if (inCode) {
            f = f.fromCode();
            inCode = false;
          } else {
            f = f.toCode();
            inCode = true;
          }
          break;
        case '*':
          flush(b, p, f);
          switch (f.style) {
            case NORMAL: f = f.withStyle(Font.Style.BOLD); break;
            case BOLD: f = f.withStyle(Font.Style.NORMAL); break;
            case BOLD_ITALIC: f = f.withStyle(Font.Style.ITALIC); break;
            case ITALIC: f = f.withStyle(Font.Style.BOLD_ITALIC); break;
          }
          break;
        case '_':
          flush(b, p, f);
          switch (f.style) {
            case NORMAL: f = f.withStyle(Font.Style.ITALIC); break;
            case BOLD: f = f.withStyle(Font.Style.BOLD_ITALIC); break;
            case BOLD_ITALIC: f = f.withStyle(Font.Style.BOLD); break;
            case ITALIC: f = f.withStyle(Font.Style.NORMAL); break;
          }
          break;
        default:
          b.append(c);
      }
    }
    flush(b, p, f);
    return p;
  }

  private void flush(StringBuilder b, Paragraph p, Font f) {
    if (b.length() == 0) return;
    p.add(new Chunk(b.toString(), f.pdfFont));
    b.setLength(0);
  }

  Chunk toChunk(Deck deck) {
    return new Chunk(s, font == null ? deck.template.defaultFont().pdfFont
        : font.pdfFont);
  }

  void writePdf(Deck deck, ColumnText column) throws DocumentException {
    column.addElement(toParagraph(deck.template.defaultFont()));
  }

  /**
   * Enables us to scale and position text like a picture.
   */
  public Picture toPicture(final int width, final int height) {
    return new Picture() {
      @Override Image asBackgroundImage() {
        throw new UnsupportedOperationException();
      }

      @Override void writePdf(Deck deck, boolean hasTitle) throws DocumentException {
        Document document = new Document(new Rectangle(width, height));
        document.setMargins(0, 0, 0, 0);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();
        Paragraph paragraph = toParagraph(deck.template.defaultFont());
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        document.close();

        try {
          PdfReader reader = new PdfReader(out.toByteArray());
          PdfImportedPage page = deck.writer.getImportedPage(reader, 1);
          Image image = Image.getInstance(page);
          writePdf(deck, hasTitle, image);
        } catch (IOException e) {
          throw new AssertionError(e);
        }
      }
    };
  }
}
