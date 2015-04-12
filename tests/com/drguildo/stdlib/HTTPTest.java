package com.drguildo.stdlib;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.Test;

import com.drguildo.stdlib.HTTP;

public class HTTPTest {
  @Test
  public void test() {
    try {
      HTTP.download(new URL("http://ipv4.download.thinkbroadband.com/5MB.zip"));
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
}
