/**
 * Game of Nim
 */

package ds2016;

import java.util.Scanner;

class Nim extends AlternatingGame {
	// board holds pile sizes in positions 1, 2, 3, ...
	// board[0] will hold whose turn it is.
	int[] board = {1, 3, 4, 5};
	char STONE = '@';
	int NUMPILES = 3;
	Scanner scanner = new Scanner(System.in);


	void drawBoard(){
		for(int i = 1; i <= NUMPILES; i++){
			System.out.print(board[i]);
			System.out.print(" ");
			for(int n = 0; n < board[i]; n++){
				System.out.print(STONE);
			}
			System.out.println("");
		}
		System.out.println("");
	}


	void getHumanMove(){
		System.out.print("What # of stones? ");
		int numStones = scanner.nextInt();
		System.out.print("Which pile? ");
		int pile = scanner.nextInt();

		board[pile] = board[pile] - numStones;

		// update whose turn it is
		whoseTurn = 3 - whoseTurn;
		board[0] = whoseTurn;
	}



	void getStupidComputerMove(){
		// pick a random pile, until it finds a 
		// pile that is not empty.
		int randPile = 0;
		do{
			// Select a number from 1, 2, 3, ..., numPiles
			randPile = 1 + (int)Math.floor(Math.random() * NUMPILES);
		} while(board[randPile] == 0);

		// select a random number of stones from that pile
		int randStones = 1 + (int)Math.floor(Math.random() * board[randPile]);

		board[randPile] = board[randPile] - randStones;

		// update whose turn it is
		whoseTurn = 3 - whoseTurn;

	}

	boolean isGameOver(){
		int tracker = 0;
		for(int i = 1; i <= NUMPILES; i++){
			tracker += board[i];
		}
		if(tracker == 0)
			return true;
		else
			return false;

	}

	/**
	 * Returns the winner of the game
	 *
	 * 1 or 2 if that player won, 0 if it's a tie.
	 */
	int whoWon(){
		return 3 - whoseTurn;	// The other player is the winner
	}


	@Override
	void setBoard(Object nb) {
		board = (int[])nb;
		
	}


	@Override
	Object getBoard() {
		return board;
	}


	@Override
	void getComputerMove() {
		getSmartComputerMove();		
	}


	@Override
	/**
	 * @return Whose turn it is, currently stored in board[0]
	 */
	int whoseTurn(Object localBoard) {
		return ((int[])(localBoard))[0];
	}


	@Override
	int whoWon(Object localBoard) {
		return 3 - whoseTurn(localBoard);
	}


	@Override
	Object[] getChildren(Object b) {
		int[] parentBoard = (int[])b;
		int childTurn = 3 - parentBoard[0];
		
		DSArrayList<int[]> children = new DSArrayList<int[]>();
		for(int pile = 1; pile <= NUMPILES; pile++){
			for(int newNum = 0; newNum < parentBoard[pile]; newNum++){
				int[] child = new int[NUMPILES + 1];
				// copy the board completely
				for(int i = 0; i <= NUMPILES; i++)
					child[i] = parentBoard[i];
				child[0] = childTurn; // Change whose turn it is
				// change the # of stones in the "move" pile
				child[pile] = newNum;
				children.add(child);
			}
		}
		return children.toArray();
	}

}
