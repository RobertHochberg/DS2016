package ds2016;

import java.util.Scanner;

class ConnectFour extends AlternatingGame {

	final static int WIDTH = 7;
	final static int HEIGHT = 6;

	char[][] board = new char[WIDTH+1][HEIGHT+1];
	static char playerOneCharacter = 'X'; // single quotes for chars
	static char playerTwoCharacter = 'O';

	Scanner scanner;        // Reading keyboard input

	// for WIDTH = 4 and HEIGHT = 4
/*	final static int[][][] WINNERS = {
			{{1, 1}, {2, 1}, {3, 1}, {4, 1}},// {{2, 1}, {3, 1}, {4, 1}, {5, 1}}, 
			//{{3, 1}, {4, 1}, {5, 1}, {6, 1}}, {{4, 1}, {5, 1}, {6, 1}, {7, 1}},
			{{1, 2}, {2, 2}, {3, 2}, {4, 2}},// {{2, 2}, {3, 2}, {4, 2}, {5, 2}}, 
			//{{3, 2}, {4, 2}, {5, 2}, {6, 2}}, {{4, 2}, {5, 2}, {6, 2}, {7, 2}},
			{{1, 3}, {2, 3}, {3, 3}, {4, 3}},// {{2, 3}, {3, 3}, {4, 3}, {5, 3}}, 
			//{{3, 3}, {4, 3}, {5, 3}, {6, 3}}, {{4, 3}, {5, 3}, {6, 3}, {7, 3}},
			{{1, 4}, {2, 4}, {3, 4}, {4, 4}}, {{1, 1}, {2, 2}, {3, 3}, {4, 4}},
			{{1, 4}, {2, 3}, {3, 2}, {4, 1}}, {{1, 1}, {1, 2}, {1, 3}, {1, 4}}, 
			{{2, 1}, {2, 2}, {2, 3}, {2, 4}}, {{3, 1}, {3, 2}, {3, 3}, {3, 4}},
			{{4, 1}, {4, 2}, {4, 3}, {4, 4}},
			//additionals for HEIGHT = 5
			{{1, 2}, {1, 3}, {1, 4}, {1, 5}}, {{2, 2}, {2, 3}, {2, 4}, {2, 5}},
			{{3, 2}, {3, 3}, {3, 4}, {3, 5}}, {{4, 2}, {4, 3}, {4, 4}, {4, 5}},
			{{1, 2}, {2, 3}, {3, 4}, {4, 5}}, {{1, 5}, {2, 4}, {3, 3}, {4, 2}},
			{{1, 5}, {2, 5}, {3, 5}, {4, 5}}, 
	}; */

	// for WIDTH = 7 and HEIGHT = 6

	final static int[][][] WINNERS = {
			{{1, 1}, {1, 2}, {1, 3}, {1, 4}}, {{1, 2}, {1, 3}, {1, 4}, {1, 5}},
			{{1, 3}, {1, 4}, {1, 5}, {1, 6}}, {{2, 1}, {2, 2}, {2, 3}, {2, 4}},
			{{2, 2}, {2, 3}, {2, 4}, {2, 5}}, {{2, 3}, {2, 4}, {2, 5}, {2, 6}},
			{{3, 1}, {3, 2}, {3, 3}, {3, 4}}, {{3, 2}, {3, 3}, {3, 4}, {3, 5}},
			{{3, 3}, {3, 4}, {3, 5}, {3, 6}}, {{4, 1}, {4, 2}, {4, 3}, {4, 4}},
			{{4, 2}, {4, 3}, {4, 4}, {4, 5}}, {{4, 3}, {4, 4}, {4, 5}, {4, 6}},
			{{5, 1}, {5, 2}, {5, 3}, {5, 4}}, {{5, 2}, {5, 3}, {5, 4}, {5, 5}},
			{{5, 3}, {5, 4}, {5, 5}, {5, 6}}, {{6, 1}, {6, 2}, {6, 3}, {6, 4}},
			{{6, 2}, {6, 3}, {6, 4}, {6, 5}}, {{6, 3}, {6, 4}, {6, 5}, {6, 6}},
			{{7, 1}, {7, 2}, {7, 3}, {7, 4}}, {{7, 2}, {7, 3}, {7, 4}, {7, 5}},
			{{7, 3}, {7, 4}, {7, 5}, {7, 6}}, {{1, 1}, {2, 1}, {3, 1}, {4, 1}},
			{{2, 1}, {3, 1}, {4, 1}, {5, 1}}, {{3, 1}, {4, 1}, {5, 1}, {6, 1}},
			{{4, 1}, {5, 1}, {6, 1}, {7, 1}}, {{1, 2}, {2, 2}, {3, 2}, {4, 2}},
			{{2, 2}, {3, 2}, {4, 2}, {5, 2}}, {{3, 2}, {4, 2}, {5, 2}, {6, 2}},
			{{4, 2}, {5, 2}, {6, 2}, {7, 2}}, {{1, 3}, {2, 3}, {3, 3}, {4, 3}},
			{{2, 3}, {3, 3}, {4, 3}, {5, 3}}, {{3, 3}, {4, 3}, {5, 3}, {6, 3}},
			{{4, 3}, {5, 3}, {6, 3}, {7, 3}}, {{1, 4}, {2, 4}, {3, 4}, {4, 4}},
			{{2, 4}, {3, 4}, {4, 4}, {5, 4}}, {{3, 4}, {4, 4}, {5, 4}, {6, 4}},
			{{4, 4}, {5, 4}, {6, 4}, {7, 4}}, {{1, 5}, {2, 5}, {3, 5}, {4, 5}},
			{{2, 5}, {3, 5}, {4, 5}, {5, 5}}, {{3, 5}, {4, 5}, {5, 5}, {6, 5}},
			{{4, 5}, {5, 5}, {6, 5}, {7, 5}}, {{1, 6}, {2, 6}, {3, 6}, {4, 6}},
			{{2, 6}, {3, 6}, {4, 6}, {5, 6}}, {{3, 6}, {4, 6}, {5, 6}, {6, 6}},
			{{4, 6}, {5, 6}, {6, 6}, {7, 6}}, {{1, 1}, {2, 2}, {3, 3}, {4, 4}},
			{{2, 1}, {3, 2}, {4, 3}, {5, 4}}, {{3, 1}, {4, 2}, {5, 3}, {6, 4}},
			{{4, 1}, {5, 2}, {6, 3}, {7, 4}}, {{1, 2}, {2, 3}, {3, 4}, {4, 5}},
			{{2, 2}, {3, 3}, {4, 4}, {5, 5}}, {{3, 2}, {4, 3}, {5, 4}, {6, 5}},
			{{4, 2}, {5, 3}, {6, 4}, {7, 5}}, {{1, 3}, {2, 4}, {3, 5}, {4, 6}},
			{{2, 3}, {3, 4}, {4, 5}, {5, 6}}, {{3, 3}, {4, 4}, {5, 5}, {6, 6}},
			{{4, 3}, {5, 4}, {6, 5}, {7, 6}}, {{1, 4}, {2, 3}, {3, 2}, {4, 1}},
			{{2, 4}, {3, 3}, {4, 2}, {5, 1}}, {{3, 4}, {4, 3}, {5, 2}, {6, 1}},
			{{4, 4}, {5, 3}, {6, 2}, {7, 1}}, {{1, 5}, {2, 4}, {3, 3}, {4, 2}},
			{{2, 5}, {3, 4}, {4, 3}, {5, 2}}, {{3, 5}, {4, 4}, {5, 3}, {6, 2}},
			{{4, 5}, {5, 4}, {6, 3}, {7, 2}}, {{1, 6}, {2, 5}, {3, 4}, {4, 3}},
			{{2, 6}, {3, 5}, {4, 4}, {5, 3}}, {{3, 6}, {4, 5}, {5, 4}, {6, 3}},
			{{4, 6}, {5, 5}, {6, 4}, {7, 3}} 
	}; 

	
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
	public ConnectFour(){
		scanner = new Scanner(System.in);

		for(int i = 1; i <= WIDTH; i++){
			for(int j = 1; j <= HEIGHT; j++){
				board[i][j] = (char)((int)'0' + i);
			}
		}
		//board[0][0] = (char)((int)'0' + 1);
		//whoseTurn = (int)board[0][0];
		whoseTurn = 1;

		char[][] b = new char[WIDTH+1][HEIGHT+1];
		for(int x = 1; x <= WIDTH; x++){
			for(int y = 1; y <= HEIGHT; y++){
				b[x][y] = (char)((int)'0' + x); // chars are always in single-quotes
			}
		}
		//b[0][0] = (char)((int)'0' + 1);

		System.out.println("Building tree...");
		buildTree(b, 8);
		System.out.println("Done.");
	}

	public char getChar(char[][] localBoard, int[] slot){
		return localBoard[slot[0]][slot[1]];
	}

	// for width = 7;
	public void drawBoard(){
		for(int i = 1; i <= HEIGHT; i++){
			for(int j = 1; j <= WIDTH; j++){
				System.out.print(" " + board[j][i]);
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

	public void printBoard(Object aBoard){
		char[][] localBoard = (char[][])aBoard;
		for(int i = 1; i <= HEIGHT; i++){
			for(int j = 1; j <= WIDTH; j++){
				System.out.print(" " + localBoard[j][i]);
			}
			System.out.print("\n");
		}
	}

	@Override
	void setBoard(Object nb) {
		//	char[][] newBoard = (char[][])nb;
		//	for(int i = 1; i <= WIDTH; i++){
		//		for(int j = 1; j <= HEIGHT; i++){
		//			board[i][j] = newBoard[i][j];        
		//		}
		//	}
		board = (char[][])nb;
	}

	@Override
	public Object getBoard(){
		return board;
	}

	@Override
	void getHumanMove() {
		System.out.print("Player " + whoseTurn + ", Please enter your move: ");
		int moveColumn = scanner.nextInt(); // Reads an int from the keyboard
		if(moveColumn == 0){
			System.out.println("Invalid input. ");
			getHumanMove();
			return;
		}
		if(moveColumn > WIDTH){
			System.out.println("Invalid input. ");
			getHumanMove();
			return;
		}
		int moveRow = 0; 
		// check for empty spots below the current one
		for(int i = 1; i <= HEIGHT; i++){ 
			if(moveColumn == (int)board[moveColumn][i] - 48) 
				moveRow = i;
		}
		if(moveRow == 0){
			System.out.println("Invalid input. " + moveRow);
			getHumanMove();
			return;
		}
		if(whoseTurn == 1)			
			board[moveColumn][moveRow] = playerOneCharacter;
		else
			board[moveColumn][moveRow] = playerTwoCharacter;

		// update whose turn it is
		//	board[0][0] = (char) (3 - whoseTurn());
		whoseTurn = 3 - whoseTurn;
	}

	@Override
	void getComputerMove() {
		getSmartComputerMove();
	}

	@Override
	int whoWon() {
		// See who won a row/col/diag, if anybody
		for(int i = 0; i < WINNERS.length; i++){
			if(getChar(board, WINNERS[i][0]) == getChar(board, WINNERS[i][1]) &&
					getChar(board, WINNERS[i][0]) == getChar(board, WINNERS[i][2])
					&& getChar(board, WINNERS[i][0]) == getChar(board, WINNERS[i][3]))
			{
				if(getChar(board, WINNERS[i][0]) == playerOneCharacter)
					return 1;
				if(getChar(board, WINNERS[i][0]) == playerTwoCharacter)
					return 2;
			}
		}

		// If we make it to here, it's a tie
		return 0;
	}

	@Override
	boolean isGameOver() {
		// Check to see if the board is full    
		if(getNumOpenSlots(board) == 0)
			return true;

		// Check to see if somebody won
		if(whoWon() != 0)
			return true;
		else
			return false;
	}

	boolean isGameOver(Object aB){
		char[][] aBoard = (char[][])aB;
		boolean bool = false;
		// Check to see if somebody won
		if(whoWon(aBoard) != 0)
			bool = true;
		// Check to see if the board is full    
		if(getNumOpenSlots(aBoard) == 0)
			bool = true;

		return bool;
	}

	//	int whoseTurn() {
	//		return (int)board[0][0];
	//	}

	@Override
	int whoseTurn(Object b) {
		int counter = getNumOpenSlots(b);
		if((WIDTH*HEIGHT - counter) % 2 == 0)
			return 1;
		else
			return 2;
	}

	public int getNumChildren(Object aB) {
		char[][] aBoard = (char[][])aB;

		// If the game is over, we have no children
		if(isGameOver(aBoard))
			return 0;

		int nc = 0;        // number of children
		boolean slotOpen = false;
		for(int i = 1; i <= WIDTH; i++){
			slotOpen = false;
			for(int j = 1; j <= HEIGHT; j++){
				if(aBoard[i][j] != playerOneCharacter &&
						aBoard[i][j] != playerTwoCharacter){
					slotOpen = true;
				}
			}
			if(slotOpen)
				nc++;
		}
		// for debugging
		//System.out.println(nc);

		return nc;
	}

	int getNumOpenSlots(Object aB){
		char[][] aBoard = (char[][])aB;
		int nos = 0;        // number of open slots
		for(int i = 1; i <= WIDTH; i++){
			for(int j = 1; j <= HEIGHT; j++){
				if(aBoard[i][j] != playerOneCharacter &&
						aBoard[i][j] != playerTwoCharacter){
					nos++;
				}
			}
		}
		return nos;
	}

	@Override
	int whoWon(Object localBoard) {
		return whoWon((char[][])localBoard);
	}

	/**
	 * Returns the winner of the game
	 *
	 * 1 or 2 if that player won, 0 if it's a tie.
	 */
	int whoWon(char[][] localBoard){
		// See who won a row/col/diag, if anybody
		for(int i = 0; i < WINNERS.length; i++){
			if(getChar(localBoard, WINNERS[i][0]) == getChar(localBoard, WINNERS[i][1]) &&
					getChar(localBoard, WINNERS[i][0]) == getChar(localBoard, WINNERS[i][2])
					&& getChar(localBoard, WINNERS[i][0]) == getChar(localBoard, WINNERS[i][3]))
			{
				if(getChar(localBoard, WINNERS[i][0]) == playerOneCharacter)
					return 1;
				if(getChar(localBoard, WINNERS[i][0]) == playerTwoCharacter)
					return 2;
			}
		}

		// If we make it to here, it's a tie
		return 0;
	}

	/**
	 * Build an array of all children of a given board
	 */
	public Object[] getChildren(Object aB){
		char[][] aBoard = (char[][])aB;

		// Build the children
		int numChildren = getNumChildren(aB);
		Object[] rv = new Object[numChildren];
		if(numChildren == 0)
			return rv;

		// Which player will be making the moves
		char playerChar = 'x';
		if(whoseTurn(aBoard) == 1)
			playerChar = playerOneCharacter;
		else
			playerChar = playerTwoCharacter;

		// Find the blank spaces and make children for them
		int childIndex = 0;
		for(int i = 1; i <= WIDTH; i++){
			int dropHeight = 0;
			for(int j = 1; j <= HEIGHT; j++){
				if(aBoard[i][j] != playerOneCharacter &&
						aBoard[i][j] != playerTwoCharacter){
					dropHeight++;
				}
			}
			if(dropHeight != 0){
				char[][] childBoard = new char[WIDTH+1][HEIGHT+1]; // build the child array

				// copy the parent into the child board
				for(int x = 1; x <= WIDTH; x++){
					for(int y = 1; y <= HEIGHT; y++)
						childBoard[x][y] = aBoard[x][y];
				}

				// put the new player's char on the board
				childBoard[i][dropHeight] = playerChar;

				rv[childIndex] = childBoard;
				childIndex++;
			}
		}
		return rv;
	}

	@Override
	String toString(Object board) {
		char[][] aB = (char[][]) board;
		String str = "";
		for(int i = 1; i <= WIDTH; i++){
			for(int j = 1; j <= HEIGHT; j++){
				str += aB[i][j];
			}
		}
		return str;
	}

}
