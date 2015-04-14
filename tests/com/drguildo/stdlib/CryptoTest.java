package com.drguildo.stdlib;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import org.junit.Before;
import org.junit.Test;

public class CryptoTest {
  Rand rand;

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
}
