package com.drguildo.stdlib;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class RandomTest {
  Random rand;

  @Before
  public void setUp() {
    rand = new Random();
  }

  @Test
  public void testRandString() throws Exception {
    String s = rand.randString(1024);
    for (char c : s.toCharArray()) {
      assertFalse(Character.isWhitespace(c));
    }
  }
}
