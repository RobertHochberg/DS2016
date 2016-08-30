package ds2016;

public class DSArrayListTester {

	public static void main(String[] args) {
		DSArrayList<Integer> list = new DSArrayList<Integer>();
		// must be an class (Integer) not a field type (int)
		
		list.add(3);
		list.add(2);
		list.add(17);
//		
//		for(int i = 0; i < list.getSize(); i++)
//			System.out.println(list.get(i));
		
		for(Integer x: list)
			System.out.println(x);
		
	}

}
