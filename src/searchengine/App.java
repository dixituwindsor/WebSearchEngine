package searchengine;

import java.io.File;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;

public class App {

	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) throws Exception {
		deleteFiles("./text_pages");
	    deleteFiles("./url_pages_crawled");
		
		System.out.println("Welcome to Web Search Engine\n");
	    System.out.println("How do you want to search? By Word or By URL? \nEnter 'w' or 'u' accordingly");
	    
	    String userChoice = sc.next();
	    
	    if (userChoice.equals("U") || userChoice.equals("u")) {
	    	System.out.println("\nEnter URL to Search:");
	        String url = sc.next();
	        String formedURL = "https://" + url + "/";

	        // remove unnecessary white spaces
	        url = url.trim();
	        
	        //Crawler and HTML to Text Conversion
	        Crawler.getLinks(formedURL);
	    }
	    else if(userChoice.equals("W") || userChoice.equals("w")){
	    	//Static HTML to Text Conversion
	        HTMLToText.staticHTMLtoTextConverter();
	    }
	    else {
	    	System.out.println("Invalid Choice");
	    	return;
	    }
	    
	    do{
	      System.out.println("\nEnter a word to search: ");
	      String word = sc.next();

	      //BoyerMoore
	      SearchWord sw = new SearchWord(word);
	      Hashtable<String, Integer> fileList = sw.searchWordWithFrequency(word);
	        
	      //Edit Distance
	       if (fileList.isEmpty()) {
	         System.out.println("No files found, here are some suggestions: ");
	         String[] list = SpellCheck.getOtherWords(word);
	         if(list.length > 0) {
	             if(list[0] != null) {
	                 for(String item: list){
	                      System.out.println(item);
	                  }
	              }else {
	                  System.out.println("No suggestions found!");
	              }
	          } else {
	              System.out.println("No suggestions found!");
	          }
	        }
	        
	        //Sorting
	        else {
	          System.out.println("How do you want the result? Sort By Rank(r) or Alphabetical Order(a): ");
	          String userSortInput = sc.next();
	          if (userSortInput.equals("r")) {
	          	SortPages.rankPages(fileList, fileList.size());
	          } else {
	            Set<String> keys = fileList.keySet();
	            String[] pages = new String[fileList.size()];
	            int i = 0;
	            for (String key : keys) {
	              pages[i] = key;
	              i++;
	            }
	            SortPages.quicksort(pages);
	          }
	        }
	 
	       System.out.println();
	       System.out.println("Enter 0 to exit or any other number to continue another word search");
	      }while(sc.nextInt() != 0);
	    
	    //delete all files crawled and all converted files
	    deleteFiles("./text_pages");
        deleteFiles("./url_pages_crawled");

        System.out.println("\nThank You");

	}
	
	//delete files in given filePath
	 private static void deleteFiles(String filePath) {
	   File files = new File(filePath);
	   File[] arrayFiles = files.listFiles();

	   for (int i = 0; i < arrayFiles.length; i++) {
	    arrayFiles[i].delete();
	   }
	 }

}
