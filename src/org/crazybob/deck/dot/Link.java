package org.crazybob.deck.dot;

/**
 *
 */
public class Link {

  String color = "black";
  String style = null;
  final Node target;

  Link(Node target) {
    this.target = target;
  }

  public Link style(String style) {
    this.style = style;
    return this;
  }

  public Link hide() {
    return color("transparent");
  }

  public Node target() {
    return this.target;
  }

  public Link color(String color) {
    this.color = Node.filterColor(color);
    return this;
  }  
}
