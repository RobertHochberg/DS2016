	package ds2016;
	
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
	}
