package com.drguildo.stdlib;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

public class HTTPTest {
  @Test
  public void testFilename() {
    try {
      URL url;

      url = new URL("http://www.example.com/test.txt");
      assertEquals(HTTP.filename(url), "test.txt");

      url = new URL("http://www.example.com/test.txt?a=123"
          + "&b=xyz");
      assertEquals(HTTP.filename(url), "test.txt");
    } catch (MalformedURLException e) {
      fail("malformed url");
    }
  }
}
