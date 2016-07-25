package org.crazybob.deck;

/**
 *
 *
 */
class Bullet {

  final Text text;
  final Bullets children;

  Bullet(Text text, Bullets children) {
    this.text = text;
    this.children = children;
  }  
}
