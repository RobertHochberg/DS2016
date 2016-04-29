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

		arrayTest();

//		Nim t = new Nim();
//		System.out.println("# game plays is " + t.buildTree(t.getBoard()).numLeaves());
=======
		Nim t = new Nim(5,5,5);
		System.out.println("# game plays is " + t.buildTree(t.getBoard()).numLeaves());
>>>>>>> master
		//TicTacToeGUI2 t = new TicTacToeGUI2();
		//TicTacToe t = new TicTacToe();
//		t.playGame();

		FileReader f = new FileReader("/usr/share/dict/words");
		BufferedReader reader = new BufferedReader(f);
		String line;
<<<<<<< HEAD


=======
		
>>>>>>> master
		DSArrayList <String> wordList = new DSArrayList<String>();
		while ((line = reader.readLine()) != null) {
			line = line.replaceAll("[\\?,~<01234567890()':;.!_-]", "");	
			
			wordList.add(line);
			
			
		}
		System.out.println("wordList has " + wordList.getSize() + " items");

	}
<<<<<<< HEAD

	
	public static void arrayTest(){
		DSArrayList<Integer> a = new DSArrayList<>();
		
		for(int i = 0; i < 10; i++)
			a.add(i);
		Integer temp = a.pop();
		System.out.println(temp);
		a.insert(temp, 6);
		a.remove(2);
		a.insert(a.pop(), 2);
		a.remove(3);
		
		System.out.println("0 3 4 2 5 7 8 1 <=== Should be contents of line below ");
		for(int i = 0; i < a.getSize(); i++)
			System.out.print(a.get(i) + " ");
		System.out.println("");
		
	}
=======
>>>>>>> master
}
