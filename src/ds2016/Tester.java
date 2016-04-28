/**
 * Tester class for our games
 */

package ds2016;

class Tester{

<<<<<<< HEAD
	public static void main(String args[]){
		
		DSArrayList<String> someArray = new DSArrayList<String>();
		
		someArray.add("steel");
		someArray.add("cheese");
		someArray.add("useless");
		
		System.out.println(someArray.get(0));
		
		for(int i = 0; i < someArray.getSize(); i++)
		{
			System.out.println(someArray.get(i));
		}
		
		System.out.println(someArray.getSize());
		
		System.out.printf("Pop! We got: %s%n", someArray.pop());
		
		System.out.println(someArray.getSize());
		
		someArray.add("neptune");
		someArray.add("noire");
		
		someArray.insert(2,  "processor unit");
		
		for(int i = 0; i < someArray.getSize(); i++)
		{
			System.out.println(someArray.get(i));
=======
	public static void main(String args[]) throws IOException{
		Nim t = new Nim(5,5,5);
		System.out.println("# game plays is " + t.buildTree(t.getBoard()).numLeaves());
		//TicTacToeGUI2 t = new TicTacToeGUI2();
		//TicTacToe t = new TicTacToe();
		t.playGame();

		FileReader f = new FileReader("/usr/share/dict/words");
		BufferedReader reader = new BufferedReader(f);
		String line;
		
		DSArrayList <String> wordList = new DSArrayList<String>();
		while ((line = reader.readLine()) != null) {
			line = line.replaceAll("[\\?,~<01234567890()':;.!_-]", "");	
			
			wordList.add(line);
			
			
>>>>>>> refs/remotes/origin/master
		}
	}
}
