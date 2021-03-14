package coratcoret;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Anagram {

	public static void main(String[] args) {
		// TODO Auto-generated method stub			
		String[] anagram = {"kita", "atik", "tika", "aku", "kia", "makan", "kua"};	       
        printAnagrams(anagram);        
	}	
	 
	 
	 private static void printAnagrams(String arr[])
	    {
	        HashMap<String, List<String> > map = new HashMap<String, List<String>>();
	 	      
	        for (int i = 0; i < arr.length; i++) {
	 	           
	            String word = arr[i];
	            char[] letters = word.toCharArray();
	            Arrays.sort(letters);
	            String newWord = new String(letters);
	 	           
	            if (map.containsKey(newWord)) {	 
	                map.get(newWord).add(word);
	            }else {	 
	                List<String> words = new ArrayList<String>();
	                words.add(word);
	                map.put(newWord, words);
	            }
	        }
	        	      
	        for (String s : map.keySet()) {
	            List<String> values = map.get(s);	            
	                System.out.print(values);	           
	        }
	    }

}
