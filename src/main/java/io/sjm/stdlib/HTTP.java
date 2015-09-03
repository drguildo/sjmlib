package io.sjm.stdlib;

import java.io.*;
import java.net.*;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// FIXME: Deal with certain classes of error (e.g. 504, invalid response codes).
public class HTTP {
  private static final int BLOCK_SIZE = 8192;
  private static HashMap<String, String> headers = new HashMap<>();

  /**
   * Downloads a list of URLs. Existing files will not be overwritten.
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
   * Downloads a list of URLs to the specified directory. Existing files will not be overwritten.
   */
  public static void download(Collection<URL> urls, String dirName) throws IOException {
    for (URL url : urls)
      download(url, dirName);
  }

  public static void download(URL url, File file) throws IOException {
    File outputFile = file;
    URLConnection connection = url.openConnection();

    for (String key : headers.keySet())
      connection.setRequestProperty(key, headers.get(key));

    connection.connect();

    if (connection instanceof HttpURLConnection) {
      HttpURLConnection httpConnection = (HttpURLConnection) connection;

      printResponse(httpConnection);

      BufferedInputStream bin = null;
      FileOutputStream out = null;

      try {
        // if there's a redirect, the path/filename will be different.
        URL trueUrl = httpConnection.getURL();
        if (!url.equals(trueUrl)) {
          System.out.println("redirected " + url + " -> " + trueUrl);

          if (outputFile.getParentFile() == null)
            outputFile = new File(filename(trueUrl));
          else
            outputFile = new File(outputFile.getParentFile() + File.separator + filename(trueUrl));
        }

        if (outputFile.exists()) {
          System.out.println(outputFile + ": file exists; skipping");
          return;
        }

        long fileSize = httpConnection.getContentLengthLong();
        String sizeStr = sizeString(fileSize);
        long read = 0; // how many bytes we've read so far

        bin = new BufferedInputStream(httpConnection.getInputStream());
        out = new FileOutputStream(outputFile);

        byte data[] = new byte[BLOCK_SIZE];
        int count;
        while ((count = bin.read(data, 0, BLOCK_SIZE)) != -1) {
          out.write(data, 0, count);
          if (fileSize == -1) {
            System.out.print(".");
          } else {
            read = read + count;
            System.out.print("\r" + outputFile + ": ");
            System.out.print(percent(read, fileSize) + " ");
            System.out.print("(" + sizeStr + ")");
          }
        }
        System.out.println();
      } catch (IOException e) {
        if (outputFile.exists())
          outputFile.delete();

        throw new IOException(e);
      } finally {
        if (bin != null)
          bin.close();
        if (out != null)
          out.close();
      }
    }
  }

  /**
   * Downloads a URL to the current directory, preserving the filename. Existing files will not be
   * overwritten.
   */
  public static void download(URL url) throws IOException {
    download(url, new File(filename(url)));
  }

  /**
   * Downloads a URL to the specified directory, creating it if it doesn't exist. Existing files
   * will not be overwritten.
   */
  public static void download(URL url, String dirName) throws IOException {
    download(url, dirName, filename(url));
  }

  public static void download(URL url, String dirName, String fileName) throws IOException {
    File dir = new File(dirName);

    if (dir.exists() && !dir.isDirectory())
      throw new IOException(dirName + " exists but is not a directory");

    if (!dir.exists())
      dir.mkdirs();

    download(url, new File(dirName + File.separator + fileName));
  }

  /**
   * Fetches a URL and returns it as a string.
   */
  public static String fetch(URL url) throws IOException {
    BufferedReader br = null;

    String line = null;
    StringBuilder sb = new StringBuilder();

    URLConnection connection = url.openConnection();

    for (String key : headers.keySet())
      connection.setRequestProperty(key, headers.get(key));

    connection.connect();

    if (connection instanceof HttpURLConnection) {
      HttpURLConnection httpConnection = (HttpURLConnection) connection;

      try {
        br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));

        while ((line = br.readLine()) != null) {
          sb.append(line);
        }
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } finally {
        if (br != null)
          br.close();
      }
    } else {
      throw new IllegalArgumentException("not a http request");
    }

    return sb.toString();
  }

  /**
   * Returns the content-type associated with a URL.
   */
  public static String getContentType(String url) throws IOException {
    String contentType;

    System.out.println(url);
    HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
    contentType = con.getContentType();
    con.disconnect();

    return contentType;
  }

  /**
   * Returns the status code... returned from the server.
   */
  public static int getStatusCode(String url) throws IOException {
    int statusCode;

    HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
    statusCode = con.getResponseCode();
    con.disconnect();

    return statusCode;
  }

  /**
   * Attempts to return the filename portion of a URL.
   */
  public static String filename(URL url) {
    String path = null;
    try {
      path = URLDecoder.decode(url.getPath(), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      System.exit(-1);
    }

    try {
      return path.substring(path.lastIndexOf('/') + 1);
    } catch (IndexOutOfBoundsException e) {
      return null;
    }
  }

  /**
   * Searches a URL for all strings matching a regular expression.
   */
  public static Collection<String> matches(URL url, String pattern) throws IOException {
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

  public static void addHeader(String key, String val) {
    if (!headers.containsKey(key))
      headers.put(key, val);
  }

  private static void printResponse(HttpURLConnection con) {
    try {
      System.out.println(con.getURL() + ": " + con.getResponseCode() + " " + con.getResponseMessage());
    } catch (IOException e) {
      System.out.println(e + ": " + e.getMessage());
    }
  }

  /**
   * Return a string representing x as a percentage of y
   */
  private static String percent(long x, long y) {
    return new DecimalFormat("##.#%").format((double) x / y);
  }

  private static String sizeString(long size) {
    if (size <= 0)
      return "0";

    String[] units = new String[]{"B", "KB", "MB", "GB", "TB", "PB", "EB"};
    int digitGroups = (int) (Math.log10(size) / Math.log10(1000));

    return new DecimalFormat("#,##0.#").format(size / Math.pow(1000, digitGroups))
            + units[digitGroups];
  }

  public static URL buildURL(String string) {
    // Sometimes I really fucking hate Java.
    try {
      String decodedURL = URLDecoder.decode(string, "UTF-8");
      URL url = new URL(decodedURL);
      URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
      return uri.toURL();
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }
}
