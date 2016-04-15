/**
 * Tester class for our games
 */

package ds2016;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class TesterNew {

	public static void main(String args[]) throws IOException {
		// Nim t = new Nim();
		// TicTacToeGUI2 t = new TicTacToeGUI2();
//		TicTacToe t = new TicTacToe();
//		t.playGame();

		FileReader f = new FileReader("/usr/share/dict/words");
		BufferedReader reader = new BufferedReader(f);
		String line;

		DSArrayListTest<String> wordList = new DSArrayListTest<String>();
		while ((line = reader.readLine()) != null) {
			line = line.replaceAll("[\\?,~<01234567890()':;.!_-]", "");

			wordList.add(line);

		}
		System.out.println("wordList has " + wordList.getSize() + " items");

	}
}