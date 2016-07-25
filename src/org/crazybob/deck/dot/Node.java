package org.crazybob.deck.dot;

import java.util.List;
import java.util.ArrayList;

/**
 *
 */
public class Node {

  final List<Link> links = new ArrayList<Link>();

  final String id, label;
  String shape = "ellipse";

  Node(String id, String label) {
    this.id = id;
    this.label = label;
  }

  public List<Link> links() {
    return links;
  }

  public Link pointTo(Node other) {
    Link link = new Link(other);
    links.add(link);
    return link;
  }

  boolean hidden;

  public Node hide() {
    hidden = true;
    return this;
  }

  public Node show() {
    hidden = false;
    return this;
  }

  public Node shape(String name) {
    this.shape = name;
    return this;
  }

  String lineColor = "black";
  String fillColor = "lightgrey";
  String fontColor = "black";

  public Node lineColor(String c) {
    this.lineColor = filterColor(c);
    return this;
  }

  public Node fillColor(String c) {
    this.fillColor = filterColor(c);
    return this;
  }

  public Node fontColor(String c) {
    this.fontColor = filterColor(c);
    return this;
  }

  void appendDecl(StringBuilder builder) {
    String lineColor = this.lineColor;
    String fillColor = this.fillColor;
    String textColor = this.fontColor;

    if (hidden) {
      lineColor = fillColor = textColor = "transparent";
    }

    builder.append(id).append("[label=\"").append(label)
        .append("\",color=").append(lineColor)
        .append(",fillcolor=").append(fillColor)
        .append(",shape=").append(shape)
        .append(",fontcolor=").append(textColor).append("];\n");
  }

  void appendLinks(StringBuilder builder) {
    for (Link link : links) {
      String color = hidden || link.target.hidden ? "transparent" : link.color;
      builder.append(id).append(" -> ").append(link.target.id).append("[color=")
          .append(color);
      if (link.style != null) {
        builder.append(",style=").append(link.style);
      }
      builder.append("];\n");
    }
  }

  static String filterColor(String color) {
    return color.startsWith("#") ? "\"" + color + "\"" : color;
  }
}
