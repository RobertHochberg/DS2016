/**
 * Tester class for our games
 */

package ds2016;

class Tester{

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
		}
	}
}
