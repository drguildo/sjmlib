package com.drguildo.stdlib.datastructures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Trie {
  private HashSet<String> anagrams;

  private class Node {
    private char val;
    private ArrayList<Node> children;
    private boolean terminated;

    /**
     * Creates a new node.
     *
     * @param c the value of the node
     */
    public Node(char c) {
      val = c;
      children = new ArrayList<Node>();
      terminated = false;
    }

    /**
     * Adds a new child node.
     *
     * @param c the value of the child node
     * @return the new child node
     */
    public Node addChild(char c) {
      if (hasChild(c))
        return getChild(c);

      Node n = new Node(c);
      children.add(n);
      return n;
    }

    /**
     * Checks whether this node has a child with the specified value.
     *
     * @param c the value of the child node
     * @return whether this node has a child with the specified value
     */
    public boolean hasChild(char c) {
      for (Node n : children)
        if (n.getChar() == c)
          return true;

      return false;
    }

    /**
     * Returns the child node with the specified value.
     *
     * @param c the value of the child node to return
     * @return the child value with the specified value
     * @throws IllegalArgumentException if a child with the specified value is not found
     */
    public Node getChild(char c) throws IllegalArgumentException {
      for (Node n : children)
        if (n.getChar() == c)
          return n;
      throw new IllegalArgumentException("node not found");
    }

    /**
     * Returns the value of this node.
     *
     * @return the value of this node
     */
    public char getChar() {
      return val;
    }

    /**
     * Set this node to be a terminator. A terminator can be seen as the end of a branch or the end
     * of a sequence. However, just because a node is a terminator does not mean it cannot have
     * children. For example in the sequence "penises", the first "s" would be a terminator but
     * would have "e" and the second "s" as children.
     */
    public void setTerminated() {
      terminated = true;
    }

    /**
     * Returns whether this node is a terminator.
     *
     * @return whether this node is a terminator
     */
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

  /**
   * Add a new string to this trie.
   *
   * @param s the string to add
   */
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

  /**
   * Checks whether a string is contained in the trie.
   *
   * @param s the string to search for
   * @return whether the trie contains the string
   */
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

  /**
   * Returns a collection of all anagrams of the specified string that are contained in the trie.
   *
   * @param s the string to search for anagrams of
   * @return a collection of all anagrams found
   */
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
