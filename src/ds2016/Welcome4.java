package ds2016;
import java.util.Scanner;
import java.lang.Math;

public class Welcome4 {
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		boolean isPrime = true;
		System.out.println("Please enter a positive integer: ");
		int a = sc.nextInt();
		if(a == 1)
			isPrime = false;
		else if(a == 2)
			isPrime = true;
		else if(a%2 == 0)
			isPrime = false;
		else 
			for(int i = 3; i <= (int)Math.sqrt(a); i=i+2){
				if(a%i == 0)
					isPrime = false;
			}
		if(isPrime == false)
			System.out.println(a + " is not prime");
		if(isPrime == true)
			System.out.println(a + " is prime");
	}
}

