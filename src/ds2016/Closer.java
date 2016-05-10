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
      boolean isCloser = false;
      boolean equidistant = false;
		Scanner keyboard = new Scanner(System.in);
		System.out.println
      ("What is the highest number that you want to play with?");
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
        
         
         if(round % 2 != 0)
         {                 
                  System.out.println("\nNumbers already used: ");
                  if(usedNumbers.size() > 10)
                  {
                     for(int i = 0; i<usedNumbers.size(); i++)
                     {
                        if(i % 10 == 0)
                        {
                           System.out.println();
                           System.out.print(usedNumbers.get(i) + " ");
                        }
                        else
                           System.out.print(usedNumbers.get(i) + " ");
                     }
                  }
                  else
                     for(int i = 0; i < usedNumbers.size(); i++)
                     {
                        System.out.print(usedNumbers.get(i) + " ");
                     }
               
               System.out.println();
   				System.out.println("\nPlayer 1, take your guess: ");
   				p1guess = keyboard.nextInt();
   					while(usedNumbers.contains(p1guess) || 
                  p1guess > upperBound || p1guess < 0)
   					{
   						System.out.println
                     ("\nThat move is not available select another number: ");
   						p1guess = keyboard.nextInt();
                     if(!usedNumbers.contains(p1guess) || computerGuess == num)
                        break;
   					}
   					
   				usedNumbers.add(index, p1guess);
               board[p1guess - 1] = playerOneCharacter;
               
               if(p1guess == num)
                  {
		                System.out.println("\nPlayer 1 wins");
		                break;
		            }
               
               while(usedNumbers.contains(computerGuess) || 
                  computerGuess > upperBound || computerGuess <= 0)
   					{
                     if(equidistant && p1guess > computerGuess)
                     {
                        computerGuess += ((p1guess - computerGuess) / 2); 
                     }
                     else if(equidistant && p1guess < computerGuess)
                     {
                        computerGuess -= ((computerGuess - p1guess) / 2); 
                     }
   						else
                        computerGuess = (int)Math.ceil(Math.random() * upperBound);
                     if(!usedNumbers.contains(computerGuess)|| p1guess == num)
                        break;
   					}
                                     
   				System.out.println("\nComputer selects " + computerGuess);
               usedNumbers.add(index, computerGuess);
               board[computerGuess - 1] = playerTwoCharacter;
            }
            
            else
            {
                  while(usedNumbers.contains(computerGuess) || 
                  computerGuess > upperBound || computerGuess <= 0)
   					{
                     if(equidistant && p1guess > computerGuess)
                     {
                        computerGuess += ((p1guess - computerGuess) / 2); 
                     }
                     else if(equidistant && p1guess < computerGuess)
                     {
                        computerGuess -= ((computerGuess - p1guess) / 2); 
                     }
   						else
                        computerGuess = (int)Math.ceil(Math.random() * upperBound);
                     if(!usedNumbers.contains(computerGuess)|| p1guess == num)
                        break;
   					}
    

   				System.out.println("\nComputer selects " + computerGuess);
               usedNumbers.add(index, computerGuess);
               board[computerGuess - 1] = playerTwoCharacter;
               
               if(computerGuess == num)
                  {
		                System.out.println("\nComputer wins");
		                break;
		            }
                 
                  System.out.println("\nNumbers already used: ");
                  if(usedNumbers.size() > 10)
                  {
                     for(int i = 0; i<usedNumbers.size(); i++)
                     {
                        if(i % 10 == 0)
                        {
                           System.out.println();
                           System.out.print(usedNumbers.get(i) + " ");
                        }
                        else
                           System.out.print(usedNumbers.get(i) + " ");
                     }
                  }
                  else
                     for(int i = 0; i < usedNumbers.size(); i++)
                     {
                        System.out.print(usedNumbers.get(i) + " ");
                     }
               System.out.println();
   				System.out.println("\nPlayer 1, take your guess: ");
   				p1guess = keyboard.nextInt();
   					while(usedNumbers.contains(p1guess) || 
                  p1guess > upperBound || p1guess < 0)
   					{
   						System.out.println
                     ("\nThat move is not available, select another number: ");
   						p1guess = keyboard.nextInt();
                     if(!usedNumbers.contains(p1guess) || computerGuess == num)
                        break;
   					}
   					
   				usedNumbers.add(index, p1guess);
               board[p1guess - 1] = playerOneCharacter; 
   		}
				      if(p1guess == num)
                  {
		                System.out.print("\n1 ");
                        if(board.length > 100)
                        {
                           for(int i = 0; i < board.length; i++)
                           {
                              if(i % 25 == 0 || board[i] == playerOneCharacter || board[i] == playerTwoCharacter)
                                 System.out.print(board[i]);
                           }
                        }
                        else
                        {
                          for(int i = 0; i < board.length; i++)
                              System.out.print(board[i]);
                        }
                      System.out.print(" " + upperBound);
                      System.out.println();
		                System.out.println("\nPlayer 1 wins");
		                break;
		            }
                   
		            if(computerGuess == num)
                  {
                      System.out.print("\n1 ");
                        if(board.length > 100)
                        {
                           for(int i = 0; i < board.length; i++)
                           {
                              if(i % 25 == 0 || board[i] == playerOneCharacter || board[i] == playerTwoCharacter)
                                 System.out.print(board[i]);
                           }
                        }
                        else
                        {
                          for(int i = 0; i < board.length; i++)
                              System.out.print(board[i]);
                        }
                      System.out.print(" " + upperBound);
                      System.out.println();
		                System.out.println("\nComputer wins");
		                break;
		            }
                  
                  
                  System.out.print("\n1 ");
                  if(board.length > 100)
                  {
                     for(int i = 0; i < board.length; i++)
                     {
                        if(i % 25 == 0 || board[i] == playerOneCharacter || board[i] == playerTwoCharacter)
                           System.out.print(board[i]);
                     }
                  }
                  else
                  {
                     for(int i = 0; i < board.length; i++)
                        System.out.print(board[i]);
                  }
                  System.out.print(" " + upperBound);
                                    
                  board[usedNumbers.get(index) - 1] = '-';
                  index++;
                  board[usedNumbers.get(index) - 1] = '-';
                  index++;

		            if(Math.abs(num - p1guess) < Math.abs(num - computerGuess))
                  {
		                System.out.println("\nPlayer 1 is closer");
                      isCloser = false;
                      equidistant = false;
                  }
		            if(Math.abs(num - computerGuess) < Math.abs(num - p1guess))
                  {
		                System.out.println("\nComputer is closer");
                      isCloser = true;
                      equidistant = false;
                  }
                  if(Math.abs(num - computerGuess) == Math.abs(num - p1guess))
                  {
		                System.out.println
                      ("\nBoth players are equidistant from the answer");
                      isCloser = false;
                      equidistant = true;
                  }
                  
                  if(isCloser && computerGuess > p1guess)
                  {
                     for(int i = 1; i < (computerGuess - ((computerGuess - p1guess) / 2)); i++)
                     {
                        if(i != num && !usedNumbers.contains(i))
                           usedNumbers.add(i);
                     }
                  }
      				else if(isCloser && p1guess > computerGuess)
                  {
                     for(int i = (p1guess - ((p1guess - computerGuess) / 2)) ; i <= upperBound; i++)
                     {
                        if(i != num && !usedNumbers.contains(i))
                           usedNumbers.add(i);
                     }
                  }
                  else if (!isCloser && computerGuess > p1guess)
                  {
                     for(int i = (computerGuess - ((computerGuess - p1guess) / 2)); i <= upperBound; i++)
                        {
                           if(i != num && !usedNumbers.contains(i))
                              usedNumbers.add(i);
                     }
                  }
                  else if (!isCloser && p1guess > computerGuess)
                  {
                     for(int i = 1; i < (p1guess - ((p1guess - computerGuess) / 2)); i++)
                     {
                        if(i != num && !usedNumbers.contains(i))
                           usedNumbers.add(i);
                     }
                  }	            
		            
                  round++;
			}
		}
}