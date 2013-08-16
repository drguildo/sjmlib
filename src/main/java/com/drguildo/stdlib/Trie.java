package com.drguildo.stdlib;

import java.util.ArrayList;

public class Trie {
  private class Node {
    private char c;
    private ArrayList<Node> children;
    private boolean terminated;

    public Node(char c) {
      this.c = c;
      children = new ArrayList<Node>();
      terminated = false;
    }

    public Node addChild(char c) {
      if (hasChild(c))
        return getChild(c);

      Node n = new Node(c);
      children.add(n);
      return n;
    }

    public boolean hasChild(char c) {
      for (Node n : children)
        if (n.getChar() == c)
          return true;
      return false;
    }

    public Node getChild(char c) throws IllegalArgumentException {
      for (Node n : children)
        if (n.getChar() == c)
          return n;
      throw new IllegalArgumentException("node not found");
    }

    public char getChar() {
      return c;
    }

    public void setTerminated() {
      terminated = true;
    }

    public boolean isTerminated() {
      return terminated;
    }
  }

  private ArrayList<Node> children;

  public Trie() {
    children = new ArrayList<Node>();
  }

  public void add(String s) {
    if (s.length() < 1)
      throw new IllegalArgumentException("string length is less than 1");

    // XXX: maybe allow spaces?
    if (s.matches("\\s"))
      throw new IllegalArgumentException("string contains spaces");
    ;

    Node n = new Node(s.charAt(0));
    children.add(n);

    for (int i = 1; i < s.length(); i++) {
      n = n.addChild(s.charAt(i));
    }

    n.setTerminated();
  }

  public boolean contains(String s) {
    if (s.length() < 1)
      return false;

    Node n = null;
    for (Node child : children)
      if (child.getChar() == s.charAt(0)) {
        n = child;
        break;
      }

    if (n == null)
      return false;

    for (int i = 1; i < s.length(); i++)
      if (n.hasChild(s.charAt(i))) {
        n = n.getChild(s.charAt(i));
      } else {
        return false;
      }

    return n.isTerminated();
  }
}
