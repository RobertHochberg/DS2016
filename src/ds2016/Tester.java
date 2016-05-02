/**
 * Tester class for our games
 */

package ds2016;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class Tester{

	public static void main(String args[]) throws IOException{
		Nim t = new Nim(5,5,5);
		System.out.println("# game plays is " + t.buildTree(t.getBoard()).numLeaves());
		//TicTacToeGUI2 t = new TicTacToeGUI2();
		//TicTacToe t = new TicTacToe();
		t.playGame();

		FileReader f = new FileReader("C:/Users/User/Downloads/words/words.txt");
		BufferedReader reader = new BufferedReader(f);
		String line;
		
		DSArrayList <String> wordList = new DSArrayList<String>();
		while ((line = reader.readLine()) != null) {
			line = line.replaceAll("[\\?,~<01234567890()':;.!_-]", "");	
			
			wordList.add(line);
			
			
		}
		System.out.println("wordList has " + wordList.getSize() + " items");
      //wordList.pop();
      //wordList.remove(2);
      //wordList.insert("hello", 2);
      System.out.println("wordList has " + wordList.getSize() + " items");
      

	}
}
