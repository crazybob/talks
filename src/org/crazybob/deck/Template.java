package org.crazybob.deck;

/**
 *
 */
public interface Template {

  Slide titleSlide(Deck deck);
  Picture defaultBackground();
  Font defaultFont();
  Margins contentMargins();
  Margins titleMargins();
  Font titleFont();
  Font bulletFont(int depth);
  Text bullet(int depth);
  Font codeFont();
  Font highlightedCodeFont();
  Font badCodeFont();
}
