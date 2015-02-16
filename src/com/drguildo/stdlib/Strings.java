package com.drguildo.stdlib;

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
}
