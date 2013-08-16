package com.drguildo.stdlib;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTTP {
  private static final int BLOCK_SIZE = 8192;

  /**
   * Fetches a URL and returns it as a string.
   *
   * @param url
   *          the URL to fetch
   * @return the URL's string representation
   * @throws IOException
   */
  public static String fetch(URL url) throws IOException {
    BufferedReader br = null;

    String line = null;
    StringBuilder sb = new StringBuilder();

    try {
      br = new BufferedReader(new InputStreamReader(url.openStream()));

      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } finally {
      if (br != null)
        br.close();
    }

    return sb.toString();
  }

  /**
   * Searches a URL for all strings matching a regular expression.
   *
   * @param url
   *          the URL to search
   * @param pattern
   *          the regular expression to match
   * @return the matches found
   * @throws IOException
   */
  public static Collection<String> matches(URL url, String pattern)
      throws IOException {
    HashSet<String> matches = new HashSet<String>();

    String text = fetch(url);

    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(text);
    while (m.find()) {
      matches.add(m.group(0));
      for (int i = 1; i <= m.groupCount(); i++)
        matches.add(m.group(i));
    }

    return matches;
  }

  /**
   * Downloads a URL to the specified filename. Existing files will not be
   * overwritten.
   *
   * @param file
   *          the local file that the URL will be written to
   * @param url
   *          the URL to download
   * @throws IOException
   */
  public static void download(File file, URL url) throws IOException {
    BufferedInputStream in = null;
    FileOutputStream out = null;

    System.out.print(file + ": ");

    if (file.exists()) {
      System.out.println("skipping");
      return;
    }

    try {
      in = new BufferedInputStream(url.openStream());
      out = new FileOutputStream(file);

      byte data[] = new byte[BLOCK_SIZE];
      int count;
      while ((count = in.read(data, 0, BLOCK_SIZE)) != -1) {
        out.write(data, 0, count);
        System.out.print(".");
      }
      System.out.println();
    } finally {
      if (in != null)
        in.close();
      if (out != null)
        out.close();
    }
  }

  /**
   * Downloads a URL to the current directory, preserving the filename. Existing
   * files will not be overwritten.
   *
   * @param url
   *          the URL to download
   * @throws IOException
   */
  public static void download(URL url) throws IOException {
    HTTP.download(new File(filename(url)), url);
  }

  /**
   * Downloads a list of URLs. Existing files will not be overwritten.
   *
   * @param urls
   *          a list of URLs
   * @throws IOException
   */
  public static void download(Collection<URL> urls) throws IOException {
    File file = null;

    for (URL url : urls) {
      // Remove the preceding '/'.
      file = new File(url.getFile().substring(1));
      if (!file.exists()) {
        file.getParentFile().mkdirs();
        download(file, url);
      }
    }
  }

  /**
   * Attempts to return the filename portion of a URL.
   *
   * @param url
   * @return the filename or null otherwise
   */
  public static String filename(URL url) {
    String path = url.getPath();
    try {
      return path.substring(path.lastIndexOf('/') + 1);
    } catch (IndexOutOfBoundsException e) {
      return null;
    }
  }

  public static void main(String[] args) throws MalformedURLException,
      IOException {
    System.out
        .println(matches(
            new URL(
                "http://www.yuvutu.com/modules.php?name=Video&op=view&video_id=945450"),
            "file=(http://\\S+?\\.mp4\\S+?)&amp;width"));
  }
}
