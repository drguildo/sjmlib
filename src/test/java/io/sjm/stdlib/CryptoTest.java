package io.sjm.stdlib;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CryptoTest {
  private Rand rand;

  @Before
  public void setUp() throws Exception {
    rand = new Rand();
  }

  @Test
  public void testEncryptString() throws GeneralSecurityException {
    String str = rand.randString(1024);
    String key = rand.randString(12);

    assertNotEquals(str, Crypto.encryptString(str, key));
  }

  @Test
  public void testDecryptString() throws GeneralSecurityException, UnsupportedEncodingException {
    String str = rand.randString(1024);
    String key = rand.randString(12);
    String encryptedStr = Crypto.encryptString(str, key);

    assertEquals(str, Crypto.decryptString(encryptedStr, key));
  }

  @Test
  public static void testHash() throws NoSuchAlgorithmException {
    assertEquals(Crypto.hash("test", "MD5"), "098f6bcd4621d373cade4e832627b4f6");
    assertEquals(Crypto.hash("test", "SHA-1"), "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3");
    assertEquals(Crypto.hash("test", "SHA-256"),
        "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08");
  }
}
