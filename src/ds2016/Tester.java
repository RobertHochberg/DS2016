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

<<<<<<< HEAD
//		Nim t = new Nim();
//		System.out.println("# game plays is " + t.buildTree(t.getBoard()).numLeaves());
//		//TicTacToeGUI2 t = new TicTacToeGUI2();
//		//TicTacToe t = new TicTacToe();
//
//		t.playGame();
=======
		arrayTest();

		Nim t = new Nim();
		System.out.println("# game plays is " + t.buildTree(t.getBoard()).numLeaves());
		//TicTacToeGUI2 t = new TicTacToeGUI2();
		//TicTacToe t = new TicTacToe();
		t.playGame();
>>>>>>> master

		FileReader f = new FileReader("/usr/share/dict/words");
		BufferedReader reader = new BufferedReader(f);
		String line;
<<<<<<< HEAD
		
		DSArrayListTest <String> wordList = new DSArrayListTest<String>();
=======

		DSArrayList <String> wordList = new DSArrayList<String>();
>>>>>>> master
		while ((line = reader.readLine()) != null) {
			line = line.replaceAll("[\\?,~<01234567890()':;.!_-]", "");	

			wordList.add(line);


		}
		System.out.println("wordList has " + wordList.getSize() + " items");

	}

	
	public static void arrayTest(){
		DSArrayList<Integer> a = new DSArrayList<>();
		
		for(int i = 0; i < 10; i++)
			a.add((3*i) % 10);
			
		a.insert(a.pop(), 6);
		a.remove(2);
		a.insert(a.pop(), 2);
		a.remove(3);
		
		System.out.println("0 3 4 2 5 7 8 1 <=== Should be contents of line below ");
		for(int i = 0; i < a.getSize(); i++)
			System.out.print(a.get(i) + " ");
		System.out.println("");
		
	}
}
