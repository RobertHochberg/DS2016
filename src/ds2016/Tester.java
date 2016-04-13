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
		//Nim t = new Nim();
		//TicTacToeGUI2 t = new TicTacToeGUI2();
		//TicTacToe t = new TicTacToe();
		DotsAndBoxes t = new DotsAndBoxes();
		t.playGame();

		//System.out.println("# game plays is " + t.buildTree(t.getBoard()).numLeaves());
		//System.out.println("# game moves is " + t.buildTree(t.getBoard()).numNodes());

		/*FileReader f = new FileReader("/usr/share/dict/words");
		BufferedReader reader = new BufferedReader(f);
		String line;
		
		DSArrayList <String> wordList = new DSArrayList<String>();
		while ((line = reader.readLine()) != null) {
			line = line.replaceAll("[\\?,~<01234567890()':;.!_-]", "");	
			
			wordList.add(line);
			
			
		}
		System.out.println("wordList has " + wordList.getSize() + " items");*/
		
		/*DSArrayList<Integer> l = new DSArrayList<Integer>();
		l.add(0);
		l.add(1);
		l.add(2);
		l.add(3);
		l.add(4);
		l.add(5);
		l.add(6);
		l.add(7);
		l.add(8);
		l.add(9);
		System.out.println(l);
		System.out.println("Removing " + l.remove(7)+ ":");
		System.out.println(l);
		System.out.println("Popping off " + l.pop() + ":");
		System.out.println(l);
		System.out.println("Setting 2 -> 10:");
		l.set(2, 10);
		System.out.println(l);
		System.out.println("Inserting 100 @ 5:");
		l.insert(100, 5);
		System.out.println(l);*/
	}
}
