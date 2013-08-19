package com.drguildo.stdlib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Trie {
  private HashSet<String> anagrams;

  private class Node {
    private char val;
    private ArrayList<Node> children;
    private boolean terminated;

    public Node(char c) {
      val = c;
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
      return val;
    }

    public void setTerminated() {
      terminated = true;
    }

    public boolean isTerminated() {
      return terminated;
    }

    public String toString() {
      return "" + val + (isTerminated() ? "|" : "") + children;
    }
  }

  private ArrayList<Node> children;

  public Trie() {
    children = new ArrayList<Node>();
  }

  public void add(String s) {
    if (s.length() < 1)
      throw new IllegalArgumentException("string length is less than 1");

    if (s.matches("\\s"))
      throw new IllegalArgumentException("string contains spaces");

    Node n = null;
    for (Node child : children)
      if (child.getChar() == s.charAt(0))
        n = child;

    if (n == null) {
      n = new Node(s.charAt(0));
      children.add(n);
    }

    for (int i = 1; i < s.length(); i++)
      n = n.addChild(s.charAt(i));

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
      if (n.hasChild(s.charAt(i)))
        n = n.getChild(s.charAt(i));
      else
        return false;

    return n.isTerminated();
  }

  private void anagramSearch(ArrayList<Character> cs, Node n, String s) {
    s = s + n.getChar();

    if (n.isTerminated())
      anagrams.add(s);

    ArrayList<Character> copy;
    for (Character c : cs)
      if (n.hasChild(c)) {
        copy = new ArrayList<Character>(cs);
        copy.remove(copy.indexOf(c));
        anagramSearch(copy, n.getChild(c), s);
      }
  }

  public Collection<String> getAnagrams(String s) {
    anagrams = new HashSet<String>();

    ArrayList<Character> cs = new ArrayList<Character>();
    for (char c : s.toCharArray())
      cs.add(c);

    ArrayList<Character> copy;
    for (Character c : cs)
      for (Node n : children) {
        if (n.getChar() == c) {
          copy = new ArrayList<Character>(cs);
          copy.remove(copy.indexOf(c));
          anagramSearch(copy, n, "");
        }
      }

    return anagrams;
  }

  public String toString() {
    return children.toString();
  }
}
