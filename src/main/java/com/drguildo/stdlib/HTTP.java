package com.drguildo.stdlib;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// FIXME: Deal with certain classes of error (e.g. 504).
public class HTTP {
  private static final int BLOCK_SIZE = 8192;

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
      file.getParentFile().mkdirs();
      download(url, file);
    }
  }

  /**
   * Downloads a list of URLs to the specified directory. Existing files will
   * not be overwritten.
   * 
   * @param urls
   *          a list of URLs
   * @param dirName
   *          the name of the directory to which the files will be downloaded
   * @throws IOException
   */
  public static void download(Collection<URL> urls, String dirName)
      throws IOException {
    for (URL url : urls)
      download(url, dirName);
  }

  private static void download(HttpURLConnection con, File file)
      throws IOException {
    System.out.print(file + ": ");

    if (file.exists()) {
      System.out.println("file exists; skipping");
      return;
    }

    BufferedInputStream bin = null;
    FileOutputStream out = null;

    try {
      long fileSize = con.getContentLengthLong();
      String sizeStr = sizeString(fileSize);
      long read = 0; // how many bytes we've read so far

      bin = new BufferedInputStream(con.getInputStream());
      out = new FileOutputStream(file);

      byte data[] = new byte[BLOCK_SIZE];
      int count;
      while ((count = bin.read(data, 0, BLOCK_SIZE)) != -1) {
        out.write(data, 0, count);
        if (fileSize == -1) {
          System.out.print(".");
        } else {
          read = read + count;
          System.out.print("\r" + file + ": ");
          System.out.print(percent(read, fileSize) + " ");
          System.out.print("(" + sizeStr + ")");
        }
      }
      System.out.println();
    } catch (FileNotFoundException e) {
      System.out.println("file not found");

      if (file.exists())
        file.delete();
    } finally {
      if (bin != null)
        bin.close();
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
    URLConnection con = url.openConnection();

    // This needs to be called so that the call to con.getURL() gives us the
    // correct URL in cases where we get redirected.
    try {
      con.getInputStream();
    } catch (FileNotFoundException e) {
      System.err.println(filename(con.getURL()) + ": file not found");
      return;
    }
    System.out.println("redirected url: " + con.getURL());

    download((HttpURLConnection) con, new File(filename(con.getURL())));
  }

  /**
   * Downloads a URL to the specified filename. Existing files will not be
   * overwritten.
   * 
   * @param url
   *          the URL to download
   * @param file
   *          the local file that the URL will be written to
   * @throws IOException
   */
  public static void download(URL url, File file) throws IOException {
    URLConnection con = url.openConnection();

    download((HttpURLConnection) con, file);
  }

  /**
   * Downloads a URL to the specified directory, creating it if it doesn't
   * exist. Existing files will not be overwritten.
   * 
   * @param url
   *          the URL to download
   * @param dirName
   *          the directory to download the files to
   * @throws IOException
   */
  public static void download(URL url, String dirName) throws IOException {
    URLConnection con = url.openConnection();

    File dir = new File(dirName);

    if (dir.exists() && !dir.isDirectory())
      throw new IOException(dirName + " exists but is not a directory");

    if (!dir.exists())
      dir.mkdirs();

    download((HttpURLConnection) con, new File(dirName + File.separator
        + filename(url)));
  }

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
   * @param x
   * @param y
   * @return a string representing x as a percentage of y
   */
  private static String percent(long x, long y) {
    return new DecimalFormat("##.#%").format((double) x / y);
  }

  private static String sizeString(long size) {
    if (size <= 0)
      return "0";

    String[] units = new String[] { "B", "KB", "MB", "GB", "TB", "PB", "EB" };
    int digitGroups = (int) (Math.log10(size) / Math.log10(1000));

    return new DecimalFormat("#,##0.#").format(size
        / Math.pow(1000, digitGroups))
        + units[digitGroups];
  }
}
