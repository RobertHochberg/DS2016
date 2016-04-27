package ds2016;

import java.util.Arrays;
import java.util.HashMap;

public class HashTest {

	public static void main(String[] args) {
		
		int[] x = {1, 2, 4,3};
		System.out.println(Arrays.toString(x));
		
		HashMap<String, Character> h = new HashMap<>();
		
		h.put("Daniel", 'A');
		h.put("Bob", 'D');
		h.put("Tom",  'W');
		h.put("Daniel", 'B');
		
		System.out.println(h);
		System.out.println(Arrays.toString(h.keySet().toArray()));

		System.out.println("Daniel's grade is " + h.get("Daniel"));
		
		
	}

}
