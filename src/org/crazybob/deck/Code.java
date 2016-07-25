package org.crazybob.deck;

import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.DocumentException;

import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;

public class Code extends Element {

  final List<String> lines;
  int scale = 100;

  Code(List<String> lines) {
    this.lines = lines;
  }

  public Code scale(int percent) {
    this.scale = percent;
    return this;
  }

  public static Code parseFile(String path) {
    try {
      List<String> lines = new ArrayList<String>();
      BufferedReader in = new BufferedReader(new FileReader(path));
      String line;
      while ((line = in.readLine()) != null) {
        lines.add(line);
      }
      in.close();
      return new Code(lines);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  void writePdf(Deck deck, ColumnText column) throws DocumentException {
    Template template = deck.template;
    Font font = template.codeFont().scale(scale);
    for (String line : lines) {
      String trimmed = line.trim();
      if (trimmed.startsWith("///")) {
        String command = trimmed.substring(4);
        if (command.equals("HIGHLIGHT")) {
          font = template.highlightedCodeFont();
        } else if (command.equals("BAD")) {
          font = template.badCodeFont();
        } else if (command.equals("NORMAL")) {
          font = template.codeFont();
        } else if (command.startsWith("...")) {
          String indent = line.substring(0, line.indexOf("///"));
          String comment = command.substring(3).trim();
          if (comment.trim().equals("")) {
            column.addElement(font.newParagraph(indent + "..."));
          } else {
            column.addElement(font.newParagraph(indent + "... // " + comment));
          }
        }
      } else if (trimmed.startsWith("package ")) {
        continue;
      } else if (trimmed.startsWith("import ")) {
        continue;
      } else if (trimmed.equals("return null;")) {
        continue;
      } else if (trimmed.equals("return -1;")) {
        continue;
      } else if (trimmed.trim().equals("")) {
        column.addElement(font.newParagraph(" "));
      } else {
        column.addElement(font.newParagraph(line));
      }
    }
  }
}
