package ds2016;
import java.util.Scanner;
public class Welcome2 {
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		int a = 0;
		int b = 0;
		do{
			System.out.println("Enter number A that is not zero: ");
			a = sc.nextInt();
		}while(a <= 0);
		do{
			System.out.println("Enter number B that is not zero: ");
			b = sc.nextInt();
		}while(b <= 0 || b<a);
		int i = a;
		int sum = 0;		
		for(i = a; i <= b; i++){
			sum = sum+i;
		}
		System.out.println(sum);
	}
}
