package io.sjm.stdlib;

import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;

public class HTTPTest {
  @Test
  public void testGetStatusCode() throws Exception {
    assertEquals(HTTP.getStatusCode("http://httpstat.us/200"), 200);
    assertEquals(HTTP.getStatusCode("http://httpstat.us/201"), 201);
    assertEquals(HTTP.getStatusCode("http://httpstat.us/202"), 202);
    assertEquals(HTTP.getStatusCode("http://httpstat.us/203"), 203);
    assertEquals(HTTP.getStatusCode("http://httpstat.us/204"), 204);
    assertEquals(HTTP.getStatusCode("http://httpstat.us/205"), 205);
    assertEquals(HTTP.getStatusCode("http://httpstat.us/206"), 206);
    assertEquals(HTTP.getStatusCode("http://httpstat.us/300"), 300);
    assertEquals(HTTP.getStatusCode("http://httpstat.us/400"), 400);

    assertNotEquals(HTTP.getStatusCode("http://httpstat.us/200"), 400);
  }

  @Test
  public void testFilename() throws Exception {
    assertEquals(HTTP.filename(new URL("http://www.example.net/")), "");
    assertEquals(HTTP.filename(new URL("http://www.example.net/file.jpg")), "file.jpg");
    assertEquals(HTTP.filename(new URL("http://www.example.net/path/file.jpg")), "file.jpg");
    assertEquals(HTTP.filename(new URL("http://www.example.net/path/to/file.jpg")), "file.jpg");
    assertEquals(HTTP.filename(new URL("http://www.example.net/path/to/file.jpg?a=b&c=d")), "file.jpg");
  }

  @Test
  public void testMatches() throws Exception {
  }
}
