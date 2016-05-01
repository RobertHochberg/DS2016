/**
 * Tic Tac Toe game. Inteded to be two-player
 */

package ds2016;

import java.util.Arrays;
import java.util.Scanner;

class TicTacToe extends AlternatingGame {
	// Fields / Attributes
	char[] board = new char[10]; // indexed 0-9
	char playerOneCharacter = 'X'; // single quotes for chars
	char playerTwoCharacter = 'O';

	Scanner scanner;		// Reading keyboard input

	final static int[][] WINNERS = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9},
			{1, 4, 7}, {2, 5, 8}, {3, 6, 9},
			{1, 5, 9}, {3, 5, 7}};

	/**
	 * Default constructor
	 *
	 * A constructor is a (typically short) description of what to
	 * do when someone uses the "new" keyword to build an object.
	 *
	 * A constructor is recognizable because it:
	 *   - is public
	 *   - has no return value
	 *   - has the same exact name as the class itself
	 */
	public TicTacToe(){
		scanner = new Scanner(System.in);

		whoseTurn = 1;		// Player 1 gets to make first move
		for(int i = 0; i <= 9; i++) 
			board[i] = (char)((int)'0' + i); // chars are always in single-quotes
		char[] b = new char[10];
		for(int i = 0; i <= 9; i++) 
			b[i] = (char)((int)'0' + i); // chars are always in single-quotes

		System.out.println("Building tree...");
		buildTree(b);
		System.out.println("Done.");


	}

	public void drawBoard(){
		System.out.println(" " + board[1] + " \u2502 " + board[2] + " \u2502 " + board[3]);
		System.out.println("\u2500\u2500\u2500\u253C\u2500\u2500\u2500\u253C\u2500\u2500\u2500");
		System.out.println(" " + board[4] + " \u2502 " + board[5] + " \u2502 " + board[6]);
		System.out.println("\u2500\u2500\u2500\u253C\u2500\u2500\u2500\u253C\u2500\u2500\u2500");
		System.out.println(" " + board[7] + " \u2502 " + board[8] + " \u2502 " + board[9]);
		System.out.print("\n");
	}


	/**
	 * Gets the human player's move
	 *
	 * @override
	 */
	void getHumanMove(){
		System.out.print("Player " + whoseTurn + ", Please enter your move: ");
		int theMove = scanner.nextInt(); // Reads an int from the keyboard
		if(whoseTurn == 1)
			board[theMove] = playerOneCharacter;
		else
			board[theMove] = playerTwoCharacter;

		// update whose turn it is
		whoseTurn = 3 - whoseTurn;
		// whoseTurn = (whoseTurn % 2) + 1; // Also clever...
	}


	/**
	 * Gets the computer player's move
	 *
	 * Picks a random, open square to move in, by
	 * throwing darts until it hits an open square.
	 *
	 * @override
	 */
	void getRandomComputerMove(){
		boolean foundMove = false;

		do{
			// get a random int from 1 to 9
			int move = 1 + (int)Math.floor(9*Math.random());
			if("0123456789".indexOf( board[move] ) != -1){
				// make the move on the board
				if(whoseTurn == 1)
					board[move] = playerOneCharacter;
				else
					board[move] = playerTwoCharacter;

				foundMove = true;
			}

		} while(foundMove == false);

		whoseTurn = 3 - whoseTurn;
	}


	/**
	 * @override
	 */
	void getComputerMove(){
		getSmartComputerMove();
	}


	boolean isGameOver(){
		// Check to see if the board is full	
		boolean full = true;
		for(int i = 1; i <= 9; i++){
			if(board[i] != playerOneCharacter && 
					board[i] != playerTwoCharacter) {
				full = false;
				break; // exit the innermost loop
			}
		}
		if(full) 
			return true;

		// Check to see if somebody won
		for(int i = 0; i < WINNERS.length; i++){
			if(board[WINNERS[i][0]] == board[WINNERS[i][1]] && 
					board[WINNERS[i][1]] == board[WINNERS[i][2]])
				return true;
		}

		return false;
	}


	boolean isGameOver(char[] localBoard){
		// Check to see if the board is full	
		boolean full = true;
		for(int i = 1; i <= 9; i++){
			if(localBoard[i] != playerOneCharacter && 
					localBoard[i] != playerTwoCharacter) {
				full = false;
				break; // exit the innermost loop
			}
		}
		if(full) 
			return true;

		// Check to see if somebody won
		for(int i = 0; i < WINNERS.length; i++){
			if(localBoard[WINNERS[i][0]] == localBoard[WINNERS[i][1]] && 
					localBoard[WINNERS[i][1]] == localBoard[WINNERS[i][2]])
				return true;
		}

		return false;
	}

	/**
	 * Simply declares who won, if anybody.
	 */
	void doEndgameStuff(){
		int winner = whoWon();
		if(winner == 0)
			System.out.println("It was a tie!");
		else
			System.out.printf("Player %d won the game\n", winner);
	}


	/**
	 * Returns the winner of the game
	 *
	 * 1 or 2 if that player won, 0 if it's a tie.
	 */
	int whoWon(){
		// See who won a row/col/diag, if anybody
		for(int i = 0; i < WINNERS.length; i++){
			if(board[WINNERS[i][0]] == board[WINNERS[i][1]] && 
					board[WINNERS[i][1]] == board[WINNERS[i][2]]){
				if( board[WINNERS[i][0]] == playerOneCharacter)
					return 1;
				if( board[WINNERS[i][0]] == playerTwoCharacter)
					return 2;
			}
		}

		// If we make it to here, it's a tie
		return 0;
	}


	/**
	 * Returns the winner of the game
	 *
	 * 1 or 2 if that player won, 0 if it's a tie.
	 */
	int whoWon(char[] localBoard){
		// See who won a row/col/diag, if anybody
		for(int i = 0; i < WINNERS.length; i++){
			if(localBoard[WINNERS[i][0]] == localBoard[WINNERS[i][1]] && 
					localBoard[WINNERS[i][1]] == localBoard[WINNERS[i][2]]){
				if( localBoard[WINNERS[i][0]] == playerOneCharacter)
					return 1;
				if( localBoard[WINNERS[i][0]] == playerTwoCharacter)
					return 2;
			}
		}

		// If we make it to here, it's a tie
		return 0;
	}


	/**
	 * Returns the winner of the game
	 *
	 * 1 or 2 if that player won, 0 if it's a tie.
	 */
	int whoWon(Object localBoard){
		return whoWon((char[])localBoard);
	}


	/**
	 * Build an array of all children of a given board
	 */
	public Object[] getChildren(Object aB){
		char[] aBoard = (char[])aB;
		//printBoard(aBoard);

		// Build the children
		int numChildren = getNumChildren(aB);
		Object[] rv = new Object[numChildren];
		if(numChildren == 0)
			return rv;

		// Which player will be making the moves
		char playerChar = 'x';
		if(numChildren %2 == 0)
			playerChar = playerTwoCharacter;
		else
			playerChar = playerOneCharacter;

		// Find the blank spaces and make children for them
		int childIndex = 0;
		for(int i = 1; i<= 9; i++){
			if(aBoard[i] != playerOneCharacter &&
					aBoard[i] != playerTwoCharacter){

				char[] childBoard = new char[10]; // build the child array

				// copy the parent into the child board
				for(int j = 1; j <= 9; j++)
					childBoard[j] = aBoard[j];

				// put the new player's char on the board
				childBoard[i] = playerChar;

				rv[childIndex] = childBoard;
				childIndex++;
			}
		}
		return rv;
	}

	
	
	/**
	 * Counts the number of children of a board
	 */
	public int getNumChildren(Object aB){
		char[] aBoard = (char[])aB;
		
		// If the game is over, we have no children
		if(isGameOver(aBoard))
			return 0;
		
		int nc = 0;		// number of children
		for(int i = 1; i<= 9; i++){
			if(aBoard[i] != playerOneCharacter &&
					aBoard[i] != playerTwoCharacter){
				nc++;
			}
		}

		return nc;
	}

	/** 
	 * Draw a given board
	 */
	public void printBoard(char[] myBaord){
		System.out.println(" " + myBaord[1] + " \u2502 " + myBaord[2] + " \u2502 " + myBaord[3]);
		System.out.println("\u2500\u2500\u2500\u253C\u2500\u2500\u2500\u253C\u2500\u2500\u2500");
		System.out.println(" " + myBaord[4] + " \u2502 " + myBaord[5] + " \u2502 " + myBaord[6]);
		System.out.println("\u2500\u2500\u2500\u253C\u2500\u2500\u2500\u253C\u2500\u2500\u2500");
		System.out.println(" " + myBaord[7] + " \u2502 " + myBaord[8] + " \u2502 " + myBaord[9]);
		System.out.print("\n");
	}

	/** 
	 * Returns whose turn it is to make the next move
	 */
	public int whoseTurn(Object b){
		// char[] localBoard = (char[])b;
		int numBlanks = getNumChildren(b);
		return numBlanks % 2 ==0 ? 2 : 1; // Ternary operator! Read all about it!
	}


	public void setBoard(Object nb){
		char[] newBoard = (char[])nb;
		for(int i = 0; i < 10; i++){
			board[i] = newBoard[i];		
		}
	}
	
	public Object getBoard(){
		return board;
	}

	@Override
	String toString(Object board) {
		return Arrays.toString((char[])board);
	}

	@Override
	int heuristicEvaluation(Object board) {
		return whoseTurn(board);
	}


}

