package com.drguildo.stdlib;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class TrieTest {
  private static final String DICT_PATH = "src/test/resources/2of4brif.txt";

  @Test
  public void testContains() {
    Trie t = new Trie();

    assertFalse(t.contains(""));
    assertFalse(t.contains("a"));

    t.add("abcd");
    assertTrue(t.contains("abcd"));
    assertFalse(t.contains(""));
    assertFalse(t.contains("abc"));
    assertFalse(t.contains("abcde"));

    t.add("abcd");
    assertTrue(t.contains("abcd"));
    assertFalse(t.contains(""));
    assertFalse(t.contains("abc"));
    assertFalse(t.contains("abcde"));
  }

  @Test
  public void testAdd() throws IOException {
    Trie t = new Trie();

    List<String> lines = Files.readAllLines(Paths.get(DICT_PATH),
        Charset.forName("UTF-8"));

    for (String line : lines) {
      t.add(line);
    }

    assertTrue(t.contains("aah"));
    assertTrue(t.contains("aardvark"));

    assertFalse(t.contains("xaardvark"));
    assertFalse(t.contains("aardvarkx"));
    assertFalse(t.contains("aardxvark"));
  }
}
