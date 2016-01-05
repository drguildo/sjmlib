package io.sjm.stdlib;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;

public class FlashVars extends HashMap<String, String> {
  private static final long serialVersionUID = 1L;

  String[] patterns = {"<param name=\"flashvars\" value=\"(.*?)\"", "flashvars=\"(.*?)\""};

  public FlashVars(String html) {
    for (String pattern : patterns) {
      for (String match : Strings.matches(html, pattern))
        processVars(match);
    }
  }

  public FlashVars(URL url) throws IOException {
    this(HTTP.fetch(url));
  }

  void processVars(String vars) {
    String splitOn;
    if (vars.contains("&amp;"))
      splitOn = "&amp;";
    else
      splitOn = "&";

    try {
      String decodedVars = vars;
      for (String var : decodedVars.split(splitOn)) {
        String[] splitVar = var.split("=");
        if (splitVar.length == 2) {
          put(splitVar[0], URLDecoder.decode(splitVar[1], "UTF-8"));
        }
      }
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws MalformedURLException, IOException {
    String[] testUrls =
        {"http://xhamster.com/movies/2704686/sex_with_chubby_amateur_slut_in_hot_amateur_porn.html",
            "http://www.xvideos.com/video2552856/wife_fisting_hubbys_ass_and_gives_handjob"};

    for (String url : testUrls) {
      FlashVars fv = new FlashVars(new URL(url));
      for (Entry<String, String> e : fv.entrySet())
        System.out.println(e.getKey() + ": " + e.getValue());
      System.out.println();
    }
  }
}
