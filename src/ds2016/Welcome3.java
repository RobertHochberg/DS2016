package ds2016;
import java.util.Scanner;

public class Welcome3 {
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		DSArrayList list = new DSArrayList();
		int a;
		
		do{
			System.out.println("Please enter as many numbers as you want, press 0 to print out largest in list...");
			a = sc.nextInt();
			list.add(a);
//			System.out.println("Size: " + list.getSize());
		}while(a != 0);
		
		int max = (int)list.get(0);
		
		for(int i = 0; i < list.getSize(); i++){
//			System.out.println("i = " + i);
			if((int)list.get(i) > max)
				max = (int)list.get(i);
		}
		
		System.out.println("The max is: " + max);
	}
}
