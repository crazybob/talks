package org.crazybob.deck.templates;

import org.crazybob.deck.Box;
import org.crazybob.deck.Deck;
import org.crazybob.deck.Font;
import org.crazybob.deck.Margins;
import org.crazybob.deck.Picture;
import org.crazybob.deck.Slide;
import org.crazybob.deck.Spacer;
import org.crazybob.deck.Template;
import org.crazybob.deck.Text;

import java.awt.Color;

import static java.awt.Color.BLACK;

/**
 * Square, Inc. template.
 */
public class Square implements Template {

  public static final Color GRAY = new Color(0x99, 0x99, 0x99);
  public static final Color DARK_GRAY = new Color(0x50, 0x50, 0x50);

  public Picture defaultBackground() {
    return Picture.parseFile("images/square/background.png");
  }

  public Slide titleSlide(Deck deck) {
    Slide titleSlide = new Slide();

    Box titleBox = new Box(600, 350, 50, 50);

    Font titleFont = new Font(Font.Face.HELVETICA, 34, Font.Style.NORMAL,
        DARK_GRAY, 38);
    titleBox.add(new Text(deck.title()).font(titleFont));
    titleBox.add(new Text(deck.subtitle()).font(titleFont));

    titleBox.add(Spacer.vertical(20));

    titleBox.add(new Text(deck.author()).font(
        new Font(Font.Face.HELVETICA, 28, Font.Style.NORMAL, GRAY)));
    titleBox.add(new Text(deck.company()).font(
        new Font(Font.Face.HELVETICA, 22, Font.Style.NORMAL, GRAY, 24)));

    titleSlide.add(titleBox);

    Picture squareLogo = Picture.parsePdf(
        "images/square/gem-logo.pdf");
    squareLogo.position(125, 350);
    titleSlide.add(squareLogo);

    return titleSlide;
  }

  public Font titleFont() {
    return new Font(Font.Face.HELVETICA, 28, Font.Style.NORMAL, BLACK);
  }

  public Margins titleMargins() {
    return new Margins(75, 30, 100, 300);
  }

  public Margins contentMargins() {
    return new Margins(75, 175, 100, 120);
  }

  public Font defaultFont() {
    return new Font(Font.Face.HELVETICA, 22, Font.Style.NORMAL, BLACK, 28);
  }

  public Font bulletFont(int depth) {
    return new Font(Font.Face.HELVETICA, bulletFontSize(depth), Font.Style.NORMAL, BLACK);
  }

  public Text bullet(int depth) {
    return new Text("¥ ").font(
        new Font(Font.Face.HELVETICA, bulletFontSize(depth), Font.Style.NORMAL, DARK_GRAY));
  }

  private int bulletFontSize(int depth) {
    switch (depth) {
      case 0: return 22;
      case 1: return 20;
      case 2: return 18;
      default: return 16;
    }
  }

  static final int CODE_SIZE = 12;
  static final int CODE_LEADING = 16;

  public Font codeFont() {
    return new Font(Font.Face.COURIER, CODE_SIZE, Font.Style.BOLD, BLACK, CODE_LEADING);
  }

  public Font highlightedCodeFont() {
    return new Font(Font.Face.COURIER, CODE_SIZE, Font.Style.BOLD, Color.BLUE, CODE_LEADING);
  }

  public Font badCodeFont() {
    return new Font(Font.Face.COURIER, CODE_SIZE, Font.Style.BOLD, Color.RED, CODE_LEADING);
  }
}
