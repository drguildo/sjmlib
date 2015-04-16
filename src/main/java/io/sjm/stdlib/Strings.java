package io.sjm.stdlib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strings {
  /**
   * Searches a CharSequence for instances of /pattern/ and returns them.
   */
  public static Collection<String> matches(final CharSequence sequence, final String pattern) {
    ArrayList<String> matches = new ArrayList<String>();

    Matcher m = Pattern.compile(pattern).matcher(sequence);

    if (m.groupCount() < 1)
      throw new IllegalArgumentException("no capturing groups specified");

    while (m.find())
      matches.add(m.group(1));

    return matches;
  }

  /**
   * Converts a byte array to a hexadecimal string.
   * 
   * @param bs the byte array to convert
   * @return a hexadecimal string representation of the byte array
   */
  public static String byteArrayToHex(byte[] bs) {
    String s = "";
    for (byte b : bs)
      s += String.format("%02x", b & 0xff);
    return s;
  }
}
