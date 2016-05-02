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
 *       
 *  Written by: Jack Baumann and John Paul Hanson
 */
class BookReader{
    // Maps words to their frequency
    HashMap<String, Integer> wordCount;

    public static void main(String[] args){
        BookReader br = new BookReader();
        br.readABook("/home/share/hochberg/Books/mobydick.txt");
        br.printFrequencies();
        System.out.println("\n There are " + br.wordCount.size() + " words in Moby Dick.");
        br.mostFrequentWord();
        br.most5LetterWord();
        br.mostFrequentFirstLetter();
        br.mostCommonLength();
    }

    /*
     * Stuffs words into wordCount
     */
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

    /*
     * Prints out all of the wordCount keys and values
     */
    public void printFrequencies(){
        for(String s : wordCount.keySet()){
            System.out.printf("%s occurs %d times\n", s, wordCount.get(s));
        }
    }

    /*
     * Scans through each String in the wordCount key set and add their lengths
     * to a lengthCount HashMap, it then finds the most common length and prints it.
     */
    private void mostCommonLength() {
        int mostCommonLength = 1;
        HashMap<Integer, Integer> lengthCount = new HashMap<Integer, Integer>();
        for(String s: wordCount.keySet()){
            int wordLength = s.length();
            //After each line is split(), some blank words ("") are left over
            if(wordLength > 0){
                if(lengthCount.containsKey(wordLength))
                    lengthCount.put(wordLength, lengthCount.get(wordLength)+1);
                else
                    lengthCount.put(wordLength, wordCount.get(s));
            }
        }
        for(int i: lengthCount.keySet()){
            if(lengthCount.get(i) > lengthCount.get(mostCommonLength))
                mostCommonLength = i;
        }
        System.out.println("The most common word length is: " + mostCommonLength);
    }

    /*
     * Scans through each String in the wordCount key set and add their first character
     * to a letterCount HashMap, it then finds the most common letter and prints it.
     */
    private void mostFrequentFirstLetter() {
        char mostCommonLetter = 'a';
        HashMap<Character, Integer> letterCount = new HashMap<Character, Integer>();
        for(String s: wordCount.keySet()){
            if(s.length() > 0) {
                char firstChar = s.charAt(0);
                if(letterCount.containsKey(firstChar))
                    letterCount.put(firstChar, letterCount.get(firstChar)+1);
                else
                    letterCount.put(firstChar, wordCount.get(s));
            }
        }
        for(char c: letterCount.keySet()){
            if(letterCount.get(c) > letterCount.get(mostCommonLetter))
                mostCommonLetter = c;
        }
        System.out.println("The most common first letter is: " + mostCommonLetter);
    }

    /*
     * Scans through each String in the wordCount key set and prints the most common
     * word containing a length of 5.
     */
    private void most5LetterWord() {
        String most5Word = "light";
        for(String s : wordCount.keySet()){
            if(s.length() == 5 && wordCount.get(s) > wordCount.get(most5Word))
                most5Word = s;
        }
        System.out.println("The most frequent 5-letter word is: " + most5Word);
    }

    /*
     * Scans through each String in the wordCount key set and prints the most common non-empty ("") word.
     */
    private void mostFrequentWord() {
        String mostCommonWord = "a";
        for(String s : wordCount.keySet()){
            if(s.length() > 0 && wordCount.get(s) > wordCount.get(mostCommonWord))
                mostCommonWord = s;
        }
        System.out.println("The most common word is: " + mostCommonWord);
    }
}