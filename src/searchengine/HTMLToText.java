package searchengine;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class HTMLToText {

  public static void staticHTMLtoTextConverter() throws Exception {
    File file = new File("url_pages_fixed");
    String[] listOfFile = file.list();
    for (int i = 0; i < listOfFile.length; i++) {
    	//HTML pages file from url_pages_fixed and convert them to text
    	convertHtmlToText(listOfFile[i], "fixed"); 
    }
  }
  

  public static void dynamicHTMLtoTextConverter(ArrayList<String> stringList) throws Exception {
    System.out.println("Crawled Links: \n");
    int inc = 0;
    for (String s : stringList) {
      inc++;
      //creates new connection and then fetches and parses html content of url
      Document docNewLink = Jsoup.connect(s).get(); 

      String html = docNewLink.html();
      String htmlOutputPath = "url_pages_crawled";
      File htmlOutputFolder = new File(htmlOutputPath);
      String regex = "[a-zA-Z0-9]+";
      Pattern p2 = Pattern.compile(regex);
      Matcher m2 = p2.matcher(s);
      StringBuffer stringBuffer = new StringBuffer();
      while (m2.find()) {
    	  stringBuffer.append(m2.group(0));
      }

      String linkAdress = stringBuffer.substring(0);
      System.out.println("Link: " + linkAdress);

      PrintWriter out = new PrintWriter(htmlOutputPath + "\\" + linkAdress + ".html");
      out.println(html);
      out.close();
      if (inc == 20) {
        break;
      }
      
    }
    File file = new File("url_pages_crawled");
    String[] fileList = file.list();
    for (int i = 0; i < fileList.length; i++) {
    	 //take html files and convert them to text
    	convertHtmlToText(fileList[i], "crawled");
    }
  }

  public static void convertHtmlToText(String file, String type) throws Exception {
    String folderToFetchFile;
    if (type.equals("fixed")) {
    	folderToFetchFile = "url_pages_fixed";
    } else {
    	folderToFetchFile = "url_pages_crawled";
    }
    
    //fetching the files from folder
    File f1 = new File(folderToFetchFile + "\\" + file); 
    
    //Parse the file using JSoup
    //Jsoup parses the file to document
    Document doc = Jsoup.parse(f1, "UTF-8");   
    
    //Convert the file to text
    //Converting document to text
    String str = doc.text(); 
    
    //writing converted files to another folder
    PrintWriter pw = new PrintWriter("text_pages\\" + file.replaceAll(".html", ".txt"));
    pw.println(str);
    pw.close();
  }
}
