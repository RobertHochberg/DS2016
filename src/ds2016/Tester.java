/**
 * Tester class for our games
 */

package src.ds2016;

/**
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
*/

class Tester{

	public static void main(String args[]){
		/**
		Nim t = new Nim(5,5,5);
		System.out.println("# game plays is " + t.buildTree(t.getBoard()).numLeaves());
		//TicTacToeGUI2 t = new TicTacToeGUI2();
		//TicTacToe t = new TicTacToe();
		 */
		LineTrap t = new LineTrap();
		t.playGame();

		/**
		FileReader f = new FileReader("/usr/share/dict/words");
		BufferedReader reader = new BufferedReader(f);
		String line;
		
		DSArrayList <String> wordList = new DSArrayList<String>();
		while ((line = reader.readLine()) != null) {
			line = line.replaceAll("[\\?,~<01234567890()':;.!_-]", "");	
			
			wordList.add(line);
			
			
		}
		System.out.println("wordList has " + wordList.getSize() + " items");
	*/
	}
}