package io.sjm.sjmlib;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * A simple helper class for encryption and decryption.
 */
public class Crypto {
  private final static String CIPHER = "Blowfish";
  private final static String CHAR_ENCODING = "UTF-8";

  /**
   * Encrypt the given string using Blowfish and returns the Base64 encoded result.
   *
   * @param plainText the text to encode
   * @param encryptionKey the encryption key
   * @return the Bases64 encoded result of encrypting the plaintext
   * @throws GeneralSecurityException
   */
  public static String encryptString(String plainText, String encryptionKey)
      throws GeneralSecurityException {
    byte[] encrypted;

    SecretKeySpec secretKeySpec = new SecretKeySpec(encryptionKey.getBytes(), CIPHER);
    Cipher cipherInstance = Cipher.getInstance(CIPHER);

    cipherInstance.init(Cipher.ENCRYPT_MODE, secretKeySpec);
    encrypted = cipherInstance.doFinal(plainText.getBytes());

    return Base64.getEncoder().encodeToString(encrypted);
  }

  /**
   * Decrypts the given Base64 encoded Blowfish ciphertext.
   *
   * @param cipherText the text to decode
   * @param encryptionKey the decryption key
   * @return the Base64 decoded result of decrypting the ciphertext
   * @throws GeneralSecurityException
   * @throws UnsupportedEncodingException
   */
  public static String decryptString(String cipherText, String encryptionKey)
      throws GeneralSecurityException, UnsupportedEncodingException {
    Cipher cipherInstance = Cipher.getInstance(CIPHER);
    SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(CHAR_ENCODING), CIPHER);

    cipherInstance.init(Cipher.DECRYPT_MODE, key);

    return new String(cipherInstance.doFinal(Base64.getDecoder().decode(cipherText)),
        CHAR_ENCODING);
  }

  public static String hash(String data, String func) throws NoSuchAlgorithmException {
    return hash(data.getBytes(), func);
  }

  public static String hash(byte[] data, String func) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance(func);
    return Strings.byteArrayToHex(md.digest(data));
  }
}
