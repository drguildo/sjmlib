package com.drguildo.stdlib;

import static org.junit.Assert.*;

import org.junit.Test;

public class TrieTest {
  @Test
  public void testContains() {
    Trie t = new Trie();

    assertFalse(t.contains(""));
    assertFalse(t.contains("a"));

    t.add("abcd");
    assertTrue(t.contains("abcd"));
    assertFalse(t.contains("abc"));
    assertFalse(t.contains("abcde"));
  }
}
