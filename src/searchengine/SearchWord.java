package searchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;


public class SearchWord {

	  private final int radix; // the radix
	  private static int[] right; // bad-match table to skip characters

	  //Search given pattern
	  public SearchWord(String pattern) {
	    this.radix = 10000;

	    // position of rightmost occurrence of character in the pattern
	    right = new int[radix];
	    for (int c = 0; c < radix; c++) right[c] = -1;
	    	for (int j = 0; j < pattern.length(); j++) 
	    		right[pattern.charAt(j)] =j;
	  }

	  public Hashtable<String, Integer> searchWordWithFrequency(String word) throws IOException {
	    Hashtable<String, Integer> hashtable = new Hashtable<String, Integer>();
	    File textFiles = new File("./text_pages");
	    File[] arrOfTextFiles = textFiles.listFiles();
	    int totalNumberFiles = 0;

	    for (int i = 0; i < arrOfTextFiles.length; i++) {
	      String text = readFile(arrOfTextFiles[i].getPath());
	      int frequency = wordSearch(text, word, arrOfTextFiles[i].getName());
	      if (frequency != 0) {
	    	  hashtable.put(arrOfTextFiles[i].getName(), frequency);
	    	  totalNumberFiles++;
	      }
	    }
	    if (totalNumberFiles > 0) {
	    	System.out.println("\nWord \"" + word + "\" is found in total " + totalNumberFiles + " files\n");
	    } else {
	    	System.out.println("\nWord \"" + word + "\" could not be found");
	    }
	    return hashtable;
	  }

	  //read file from given path
	  public static String readFile(String path) throws IOException {
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
	    try {
	      StringBuilder stringBuilder = new StringBuilder();
	      String nextLine = bufferedReader.readLine();

	      while (nextLine != null) {
	    	stringBuilder.append(nextLine);
	    	stringBuilder.append("\n");
	    	nextLine = bufferedReader.readLine();
	      }
	      
	      return stringBuilder.toString();
	    } finally {
	    	bufferedReader.close();
	    }
	  }

	//return offset of first match or N if no match found
	  public static int search(String pattern, String text) {
	    int patLength = pattern.length();
	    int txtLength = text.length();
	    int skip;
	    for (int i = 0; i <= txtLength - patLength; i += skip) {
	      skip = 0;
	      for (int j = patLength - 1; j >= 0; j--) {
	        if (pattern.charAt(j) != text.charAt(i + j)) {
	          skip = Math.max(1, j - right[text.charAt(i + j)]);
	          break;
	        }
	      }
	      if (skip == 0) return i; // found
	    }
	    return txtLength; // not found
	  }

	  
	  public static int wordSearch(String data, String word, String fileName) {
	    int count = 0;
	    int offset = 0;
	    for (
	      int location = 0;
	      location <= data.length();
	      location += offset + word.length()
	    ) {
	      offset = SearchWord.search(word, data.substring(location));
	      if ((offset + location) < data.length()) {
	    	  count++;
	      }
	    }
	    return count;
	  }
	  

	}
