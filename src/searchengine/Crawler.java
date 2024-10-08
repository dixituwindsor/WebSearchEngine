package searchengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Crawler {

	  private static ArrayList<String> linkList = new ArrayList<>();

	  public String[] getListOfURL() {
	    String[] listOfURL = linkList.toArray(new String[linkList.size()]);
	    return listOfURL;
	  }

	  public static void getLinks(String url) throws Exception {
	    Document document;
	    try {
	      document = Jsoup.connect(url).get();
	      Elements links = document.select("a[href]");
	      for (Element link : links) {
	        String s = link.attr("abs:href");
	        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	        Pattern p1 = Pattern.compile(regex);
	        Matcher m1 = p1.matcher(s);
	        while (m1.find()) {
	          linkList.add(m1.group(0));
	        }
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    }

	    HTMLToText.dynamicHTMLtoTextConverter(linkList);
	  }
}
