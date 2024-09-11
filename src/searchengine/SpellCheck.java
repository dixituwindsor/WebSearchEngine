package searchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;


public class SpellCheck {

    private static ArrayList<String> vocabList = new ArrayList<>();

    public static String[] getOtherWords(String query) throws IOException {
        getVocab();
        HashMap<String, Integer> hashMap = new HashMap<>();
        String[] otherWords = new String[10];
        for (String word : vocabList) {
            int editDistance = wordEditDistance(query, word);
            hashMap.put(word, editDistance);
        }
        Map<String, Integer> map = sortByValue(hashMap);

        int rank = 0;
        for (Map.Entry<String, Integer> en : map.entrySet()) {
            if (en.getValue() != 0) {
                otherWords[rank] = en.getKey();
                rank++;
                if (rank == 10){ break; }
            }
        }
        return otherWords;
    }
    
    private static void getVocab() throws IOException {
        String currentDir = System.getProperty("user.dir");
        File textFiles = new File(currentDir+ "/text_pages");

        File[] arrOfTextFiles = textFiles.listFiles();

        StringBuilder stringBuilder = new StringBuilder();
        assert arrOfTextFiles != null;
        for (File text : arrOfTextFiles) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(text));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
            	stringBuilder.append(str);
            }
            bufferedReader.close();
        }
        String fullText = stringBuilder.toString();
        StringTokenizer stringTokenizer = new StringTokenizer(fullText, "0123456789 ,`*$|~(){}_@><=+[]\\?;/&#-.!:\"'\n\t\r");
        while (stringTokenizer.hasMoreTokens()) {
            String tk = stringTokenizer.nextToken().toLowerCase(Locale.ROOT);
            if (!vocabList.contains(tk)) {
            	vocabList.add(tk);
            }
        }
    }
    
    private static int wordEditDistance(String word1, String word2) {
        int w1Length = word1.length();
        int w2Length = word2.length();
        int[][] arr = new int[w1Length + 1][w2Length + 1];

        for (int i = 0; i <= w1Length; i++) {
        	arr[i][0] = i;
        }
        for (int j = 0; j <= w2Length; j++) {
        	arr[0][j] = j;
        }

        for (int i = 0; i < w1Length; i++) {
            	char char1 = word1.charAt(i);
            for (int j = 0; j < w2Length; j++) {
                char char2 = word2.charAt(j);
                
                if (char1 == char2) {
                	arr[i + 1][j + 1] = arr[i][j];
                } else {
                    int replaceValue = arr[i][j] + 1;
                    int insertValue = arr[i][j + 1] + 1;
                    int deleteValue = arr[i + 1][j] + 1;

                    int min = Math.min(replaceValue, insertValue);
                    min = Math.min(deleteValue, min);
                    arr[i + 1][j + 1] = min;
                }
            }
        }
        return arr[w1Length][w2Length];
    }
    
    private static HashMap<String, Integer> sortByValue(HashMap<String, Integer> map) {
        List<Map.Entry<String, Integer> > list = new LinkedList<>(map.entrySet());

        list.sort(Map.Entry.comparingByValue());

        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
