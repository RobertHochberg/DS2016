/**
 * draw lines until you can't.
 * rowLocation++ moves up/down; colLocation moves left/right
 * consider having non-square grid; would require another field
 */

package src.ds2016;

import java.util.Scanner;

class LineTrap extends AlternatingGame{
	int numGridPoints;
	char[][] board; //row 1st, col 2nd
	int rowLocation;
	int colLocation;
	char VERTICALLINE = '\u2502';
	char HORIZONTALLINE = '\u2500';
	char DOT = '*';
	char USEDDOT = 'O';
	char MARKER = 'X';
	String humanMove = "";
	// these exist so that board[1][1] can hold whose turn
	char pOne = 1;
	char pTwo = 2;
	// int numMoves = 0; // if time, include for stats

	Scanner scanner;

	public LineTrap(){
		//sets grid size through user input
		scanner = new Scanner(System.in);
		do {
			System.out.print("Please enter game size (must be odd): ");
			while (!scanner.hasNextInt()) {
				System.out.print("That is not a valid input. Please try again: ");
				scanner.next();
			}
			numGridPoints = scanner.nextInt();
		} while (numGridPoints < 3 || numGridPoints % 2 != 1);

		board = new char[2*numGridPoints-1][2*numGridPoints-1];

		// these use Math.floor() because computers count from 0, not 1
		rowLocation = (int)Math.floor((double)(2*numGridPoints-1)/2);
		colLocation = (int)Math.floor((double)(2*numGridPoints-1)/2);

		//draws the initial blank board
		for(int row = 0; row < 2*numGridPoints-1; row++){
			for(int col = 0; col < 2*numGridPoints-1; col++){
				if (row%2 == 0 && col%2 == 0) {
					board[row][col] = DOT;
				}
			}
		}

		//places the marker in the center of the board
		board[rowLocation][colLocation] = MARKER;
		
		//whoseTurn is tracked through board[1][1]
		board[1][1] = pOne;
		
		whoseTurn = 1;
	}

	public void drawBoard(){
		for(int row = 0; row < 2*numGridPoints-1; row++){
			for(int col = 0; col < 2*numGridPoints-1; col++){
				if (col != 2*numGridPoints-1) {
					if (row == 1 && col == 1) {
						System.out.print(" ");
					}else System.out.print(board[row][col]);
				}
			}
			System.out.print("\n");
		}
	}

	@Override
	void setBoard(Object nb){
		board = (char[][])nb;
	}

	@Override
	Object getBoard(){
		return board;
	}

	@Override
	void getHumanMove(){
		do {
			System.out.print("Please make a move: ");
			humanMove = scanner.next();
		} while (humanMove.indexOf("w") == -1 && humanMove.indexOf("W") == -1 && humanMove.indexOf("s") == -1 && humanMove.indexOf("S") == -1 && humanMove.indexOf("a") == -1 && humanMove.indexOf("A") == -1 && humanMove.indexOf("d") == -1 && humanMove.indexOf("D") == -1 || humanMove.length() != 1);

		if (humanMove.indexOf("w") != -1) {
			getUpMove();
		} else if (humanMove.indexOf("W") != -1) {
			getUpMove();
		} else if (humanMove.indexOf("s") != -1) {
			getDownMove();
		} else if (humanMove.indexOf("S") != -1) {
			getDownMove();
		} else if (humanMove.indexOf("a") != -1) {
			getRightMove();
		} else if (humanMove.indexOf("A") != -1) {
			getRightMove();
		} else if (humanMove.indexOf("d") != -1) {
			getLeftMove();
		} else if (humanMove.indexOf("D") != -1) {
			getLeftMove();
		}

		whoseTurn = 3 - whoseTurn;
		
		if (whoseTurn == 1) {
			board[1][1] = pOne;
		} else board[1][1] = pTwo;
		// numMoves++;
	}

	void getUpMove(){
		while (rowLocation == 0 || board[rowLocation - 1][colLocation] == VERTICALLINE) {
			System.out.println("I'm sorry, Dave. I'm afraid I can't do that.");
			humanMove = "";
			getHumanMove();
			return;
		}
		board[rowLocation - 1][colLocation] = VERTICALLINE;
		board[rowLocation][colLocation] = USEDDOT;
		rowLocation = rowLocation - 2;
		board[rowLocation][colLocation] = MARKER;
	}

	void getDownMove(){
		while (rowLocation == 2*numGridPoints-2 || board[rowLocation + 1][colLocation] == VERTICALLINE) {
			System.out.println("I'm sorry, Dave. I'm afraid I can't do that.");
			humanMove = "";
			getHumanMove();
			return;
		}
		board[rowLocation + 1][colLocation] = VERTICALLINE;
		board[rowLocation][colLocation] = USEDDOT;
		rowLocation = rowLocation + 2;
		board[rowLocation][colLocation] = MARKER;
	}

	void getRightMove(){
		while (colLocation == 0 || board[rowLocation][colLocation - 1] == HORIZONTALLINE) {
			System.out.println("I'm sorry, Dave. I'm afraid I can't do that.");
			humanMove = "";
			getHumanMove();
			return;
		}
		board[rowLocation][colLocation - 1] = HORIZONTALLINE;
		board[rowLocation][colLocation] = USEDDOT;
		colLocation = colLocation - 2;
		board[rowLocation][colLocation] = MARKER;
	}

	void getLeftMove(){
		while (colLocation == 2*numGridPoints-2 || board[rowLocation][colLocation + 1] == HORIZONTALLINE) {
			System.out.println("I'm sorry, Dave. I'm afraid I can't do that.");
			humanMove = "";
			getHumanMove();
			return;
		}
		board[rowLocation][colLocation + 1] = HORIZONTALLINE;
		board[rowLocation][colLocation] = USEDDOT;
		colLocation = colLocation + 2;
		board[rowLocation][colLocation] = MARKER;
	}

	@Override
	void getComputerMove(){
		getSmartComputerMove();
		// numMoves++;
	}

	@Override
	int whoseTurn(Object localBoard){
		return whoseTurn(localBoard);
	}

	@Override
	boolean isGameOver(){
		boolean isGameOver = false;
		if (rowLocation == 0 && colLocation == 0) {
			//upper left corner
			if (board[rowLocation + 1][colLocation] == VERTICALLINE && board[rowLocation][colLocation + 1] == HORIZONTALLINE) {
				isGameOver = true;
			}
		} else if (rowLocation == 0 && colLocation == 2*numGridPoints-2) {
			//upper right corner
			if (board[rowLocation + 1][colLocation] == VERTICALLINE && board[rowLocation][colLocation - 1] == HORIZONTALLINE) {
				isGameOver = true;
			}
		} else if (rowLocation == 2*numGridPoints-2 && colLocation == 0) {
			//lower left corner
			if (board[rowLocation - 1][colLocation] == VERTICALLINE && board[rowLocation][colLocation + 1] == HORIZONTALLINE) {
				isGameOver = true;
			}
		} else if (rowLocation == 2*numGridPoints-2 && colLocation == 2*numGridPoints-2) {
			//lower right corner
			if (board[rowLocation - 1][colLocation] == VERTICALLINE && board[rowLocation][colLocation - 1] == HORIZONTALLINE) {
				isGameOver = true;
			}
		} else if (rowLocation == 0) {
			//upper edge
			if (board[rowLocation + 1][colLocation] == VERTICALLINE && board[rowLocation][colLocation - 1] == HORIZONTALLINE && board[rowLocation][colLocation + 1] == HORIZONTALLINE) {
				isGameOver = true;
			}
		} else if (rowLocation == 2*numGridPoints-2) {
			//lower edge
			if (board[rowLocation - 1][colLocation] == VERTICALLINE && board[rowLocation][colLocation - 1] == HORIZONTALLINE && board[rowLocation][colLocation + 1] == HORIZONTALLINE) {
				isGameOver = true;
			}
		} else if (colLocation == 0) {
			//left edge
			if (board[rowLocation - 1][colLocation] == VERTICALLINE && board[rowLocation + 1][colLocation] == VERTICALLINE && board[rowLocation][colLocation + 1] == HORIZONTALLINE) {
				isGameOver = true;
			}
		} else if (colLocation == 2*numGridPoints-2) {
			//right edge
			if (board[rowLocation - 1][colLocation] == VERTICALLINE && board[rowLocation + 1][colLocation] == VERTICALLINE && board[rowLocation][colLocation - 1] == VERTICALLINE) {
				isGameOver = true;
			}
		} else if (board[rowLocation - 1][colLocation] == VERTICALLINE && board[rowLocation + 1][colLocation] == VERTICALLINE && board[rowLocation][colLocation - 1] == HORIZONTALLINE && board[rowLocation][colLocation + 1] == HORIZONTALLINE) {
			//middle of the board
			isGameOver = true;
		} else isGameOver = false;
		return isGameOver;
	}

	@Override
	int whoWon(){
		return 3 - whoseTurn;
	}

	@Override
	int whoWon(Object board){
		return 3 - whoseTurn;
	}

	/**
	 * logic for smart game play implemented here
	 * move 1 up/down/left/right and check if space is occupied.  if so, children can't be built there.
	 * if not occupied, continue the process until can go no further.
	 * when dead end is reached, evaluate who wins that leaf by analyzing whose turn
	 * pick optimal move by propagating winners up the tree and then choosing
	 * 
	 * place lines and then switch whose turn
	 *
	 */
	@Override
	Object[] getChildren(Object b){
		char[][] parentBoard = (char[][])b;
		char[][] child;
		int childWhoseTurn = (int)(board[1][1]);

		DSArrayList<char[][]> children = new DSArrayList<char[][]>();

		for(int i = rowLocation; i < numGridPoints; i++){
			for(int j = colLocation; j < numGridPoints; j++){
				if (!isGameOver()) {
					child = parentBoard;
				} else if (child[i][j] == VERTICALLINE || child[i][j] == HORIZONTALLINE) {
					continue;
				} else 
			}
		}
		for(int k = rowLocation; k > 0; k--){
			for(int l = colLocation; l > 0; l--){
				if (!isGameOver()) {
					child = parentBoard;
				} else 
			}
		}
	}
}