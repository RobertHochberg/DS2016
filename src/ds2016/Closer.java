package ds2016;

import java.util.*;

public class Closer
{
	static char[] board;
	static char playerOneCharacter = 'X';
	static char playerTwoCharacter = 'O';
	static int upperBound;
   static int p1guess;
   static int num;
   static int computerGuess;
	static ArrayList<Integer> usedNumbers = new ArrayList<Integer>();
	static int round = 1;
   static int index = 0;
	
	public static void main(String args[])
	{
		boolean done = false;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("What is the highest number that you want to play with?");
		upperBound = keyboard.nextInt();
		num = (int)Math.ceil(Math.random() * upperBound);
      board = new char[upperBound];
		
      for(int i = 0; i < upperBound; i++)
      {
			board[i] = '-';
		}
         while(!done)
			{
				System.out.println("\nThis is round " + round);
				System.out.println("\nPlayer One, take your guess: ");
				p1guess = keyboard.nextInt();
					while(usedNumbers.contains(p1guess) || p1guess > upperBound || p1guess < 0)
					{
						System.out.println("\nThat move is not available, select another number: ");
						p1guess = keyboard.nextInt();
                  if(!usedNumbers.contains(p1guess))
                     break;
					}
					
				usedNumbers.add(p1guess);
            board[p1guess - 1] = playerOneCharacter; 
				
				computerGuess = (int)Math.ceil(Math.random() * upperBound);
					while(usedNumbers.contains(computerGuess) || computerGuess > upperBound || computerGuess < 0)
					{
						computerGuess = (int)Math.ceil(Math.random() * upperBound);
                  if(!usedNumbers.contains(computerGuess))
                     break;
					}
				System.out.println("\nComputer selects " + computerGuess);
            usedNumbers.add(computerGuess);
            board[computerGuess - 1] = playerTwoCharacter;
				
				 if(p1guess == num){
		                System.out.println("\nPlayer 1 wins");
		                break;
		            }
		            if(computerGuess == num){
		                System.out.println("\nComputer wins");
		                break;
		            }
                  
                  
                  for(int i = 0; i < board.length; i++)
                  {
                     System.out.print(board[i]);
                  }
                  
                  System.out.println("\nNumbers already used: " + usedNumbers);
                  
                  board[usedNumbers.get(index) - 1] = '-';
                  index++;
                  board[usedNumbers.get(index) - 1] = '-';
                  index++;

		            if(Math.abs(num - p1guess) < Math.abs(num - computerGuess))
		                System.out.println("\nPlayer 1 is closer");
		            if(Math.abs(num - computerGuess) < Math.abs(num - p1guess))
		                System.out.println("\nComputer is closer");
                  if(Math.abs(num - computerGuess) == Math.abs(num - p1guess))
		                System.out.println("\nBoth players are equidistant from the answer");		            
		            
                  round++;
			}
		}
}