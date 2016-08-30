//Wyatt and Peter

package fall2016;
import java.util.Scanner;
import java.lang.Math;

public class Welcome5 {

    public static void main(String[] args) {
        boolean flag = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enger a positive integer: ");
        int input = sc.nextInt();
        //        int a = 0;
        //        int b = input - a;
        for(int a = 0; a < input/2; a++){
            int b = input - a;
            int x = (int)Math.sqrt((double)a);
            int y = (int)Math.sqrt((double)b);
            if(x*x == a && y*y == b)
                if(x*x + y*y == input){
                    flag = true;
                    System.out.println(x + "^2 + " + y + "^2");
                }

        }
        if(flag == false){
            System.out.println("can't be done!");

        }

    }

}