package org.crazybob.deck.dot;

import java.util.List;
import java.util.ArrayList;

/**
 *
 */
public class DiGraph {

  final List<Node> nodes = new ArrayList<Node>();

  int lastId = 0;

  public Node newNode(String label) {
    Node node = new Node("n" + lastId++, label);
    nodes.add(node);
    return node;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("digraph g { aspect=1.66; margin=0; rankdir=LR;"
        + " graph [truecolor bgcolor=\"#00000000\"]; node[style=filled];\n");
    for (Node node : nodes) {
      node.appendDecl(builder);
    }
    for (Node node : nodes) {
      node.appendLinks(builder);
    }
    builder.append("}");
    return builder.toString();
  }
}
