package org.crazybob.deck;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 */
public abstract class Picture extends PositionedElement {

  public enum Layout {
    
  }

  int x = -1, y = -1, w = -1, h = -1;
  boolean center, fill;

  public Picture position(int x, int y) {
    this.x = x;
    this.y = y;
    return this;
  }

  public Picture width(int w) {
    this.w = w;
    this.h = -1;
    return this;
  }

  public Picture height(int h) {
    this.w = -1;
    this.h = h;
    return this;
  }

  /** Centers image relative to default content area. */
  public Picture center() {
    this.center = true;
    return this;
  }

  abstract Image asBackgroundImage();

  public static Picture parseFile(String path) {
    try {
      return new ImagePicture(Image.getInstance(path));
    } catch (BadElementException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static Picture parsePdf(String path) {
    return new Pdf(path);
  }

  public Picture fill() {
    fill = true;
    return this;
  }

  void writePdf(Deck deck, boolean hasTitle, Image image) throws DocumentException {
    int oldW = (int) image.getWidth();
    int oldH = (int) image.getHeight();

    Margins contentMargins = deck.template.contentMargins();

    if (!hasTitle) {
      // Expand to include title area.
      contentMargins = Margins.encompass(contentMargins, deck.template.titleMargins());
    }

    if (fill) {
      // try filling vertically
      int newH = contentMargins.height();
      int newW = oldW * newH / oldH;
      if (newW <= contentMargins.width()) {
        image.scaleAbsolute(newW, newH);
      } else {
        newW = contentMargins.width();
        image.scaleAbsolute(newW, oldH * newW / oldW);
      }
    } else {
      if (this.w > -1) {
        image.scaleAbsolute(w, oldH * w / oldW);
      } else if (this.h > -1) {
        image.scaleAbsolute(oldW * h / oldH, h);
      }
    }

    if (center) {
      image.setAbsolutePosition(
          contentMargins.centerX() - image.getScaledWidth() / 2,
          Deck.HEIGHT - contentMargins.centerY()
              - image.getScaledHeight() / 2
      );
    } else {
      image.setAbsolutePosition(x, Deck.HEIGHT - y - image.getScaledHeight());
    }

    deck.document.add(image);
  }

  static class ImagePicture extends Picture {

    final Image image;

    public ImagePicture(Image image) {
      this.image = image;
    }

    Image asBackgroundImage() {
      image.setAlignment(Image.UNDERLYING);
      image.scaleAbsolute(Deck.WIDTH, Deck.HEIGHT);
      image.setAbsolutePosition(0, 0);
      return image;
    }

    void writePdf(Deck deck, boolean hasTitle) throws DocumentException {
      writePdf(deck, hasTitle, image);
    }
  }

  public static Picture parseDot(String dot) {
    return new Dot(dot);
  }

  static class Dot extends Picture {

    final String dot;

    Dot(String dot) {
      this.dot = dot;
    }

    Image asBackgroundImage() {
      throw new UnsupportedOperationException();
    }

    void writePdf(Deck deck, boolean hasTitle) throws DocumentException {
      try {
        Process process = Runtime.getRuntime().exec(
            new String[] { "/usr/local/bin/dot", "-Tpdf" });
        OutputStream out = process.getOutputStream();
        out.write(dot.getBytes());
        out.close();
        PdfReader reader = new PdfReader(process.getInputStream());
        PdfImportedPage page = deck.writer.getImportedPage(reader, 1);
        Image image = Image.getInstance(page);

        writePdf(deck, hasTitle, image);
      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (BadElementException e) {
        throw new RuntimeException(e);
      }
    }
  }

  static class Pdf extends Picture {

    final String path;

    Pdf(String path) {
      this.path = path;
    }

    Image asBackgroundImage() {
      throw new UnsupportedOperationException();
    }

    void writePdf(Deck deck, boolean hasTitle) throws DocumentException {
      try {
        FileInputStream in = new FileInputStream(path);
        PdfReader reader = new PdfReader(in);
        PdfImportedPage page = deck.writer.getImportedPage(reader, 1);
        Image image = Image.getInstance(page);

        writePdf(deck, hasTitle, image);
      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (BadElementException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
