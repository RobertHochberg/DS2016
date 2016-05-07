/**
 * Guido and Peter
 */

package src.ds2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * This class is designed just to demonstrate
 * * how to read from a file in Java
 * * how to remove non-letter characters
 * * how to use a hashmap to count the number of occurrences
 *       of each word.
 */
class BookReader{
	// Maps words to their frequency
	HashMap<String, Integer> wordCount;

	public static void main(String[] args){
		BookReader br = new BookReader();
		br.readABook("C:\\Users\\peterpc\\Desktop\\discrete structures\\pg2701.txt");
		br.printFrequencies();
		br.mostCommonFive();
		br.mostCommonInitial();
		br.mostCommonLength();
	}

	public void readABook(String book){
		System.out.println("** Reading Book File");
		wordCount = new HashMap<String, Integer>();

		try { 
			FileReader f = new FileReader(book);
			BufferedReader reader = new BufferedReader(f);
			String line = reader.readLine();
			while (line != null) {
				line = line.replaceAll("[\\?,~<01234567890()':;.!_-]", "");	
				String[] parts = line.split(" ");
				for(int i = 0; i < parts.length; i++){
					String word = parts[i].toLowerCase();
					if(wordCount.containsKey(word))
						wordCount.put(word, wordCount.get(word) + 1);
					else
						wordCount.put(word, 1);
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
		}
		System.out.println("Done Reading Book File");
	}

	public void printFrequencies(){
		for(String s : wordCount.keySet()){
			System.out.printf("%s occurs %d times\n", s, wordCount.get(s));
		}
	}

	public void mostCommonFive(){
		//hash five letter words to # of occurrences
		HashMap<String, Integer> fiveLetterWords = new HashMap<String, Integer>();

		for(String s : wordCount.keySet()){
			if (s.length() == 5){
				fiveLetterWords.put(s, wordCount.get(s));
			}
		}

		int occurrences = 0;
		String rv = "";
		for(String i : fiveLetterWords.keySet()){
			if (fiveLetterWords.get(i) > occurrences) {
				occurrences = fiveLetterWords.get(i);
				rv = i;
			}
		}
		System.out.printf("The most common five letter word is " + rv + "\n");
	}

	public void mostCommonInitial(){
		//hash # of occurrences to initials
		HashMap<Integer, String> initials = new HashMap<Integer, String>();

		for(String s : wordCount.keySet()){
			if (s.length() >= 1) {
				initials.put(wordCount.get(s), s.substring(0, 1));
			}
		}

		String rv = "";
		int occurrences = 0;
		for(int i : initials.keySet()){
			if (i > occurrences) {
				occurrences = i;
				rv = initials.get(i);
			}
		}
		System.out.printf("The most common first letter is " + rv + "\n");
	}

	public void mostCommonLength(){
		//hash # of occurrences to word length
		HashMap<Integer, Integer> mostCommon = new HashMap<Integer, Integer>();

		for(String s : wordCount.keySet()){
			mostCommon.put(wordCount.get(s), s.length());
		}

		int rv = 0;
		for(int i : mostCommon.keySet()){
			if (i > rv) {
				rv = mostCommon.get(i);
			}
		}
		System.out.printf("The most common word length is " + rv + "\n");
	}
}