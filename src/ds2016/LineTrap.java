/**
 * draw lines until you can't.
 * rowLocation++ moves up/down; colLocation moves left/right
 * consider having non-square grid; would require another field
 */

package src.ds2016;

import java.util.Scanner;

class LineTrap extends AlternatingGame{
	int numGridPoints;
	char[][] board;; //row 1st, col 2nd
	int rowLocation;
	int colLocation;
	int whoseTurn = 1;
	char VERTICALLINE = '\u2502';
	char HORIZONTALLINE = '\u2500';
	char DOT = '*';
	char MARKER = '@';
	char EMPTYSPACE = ' ';
	// int numMoves = 0; // if time, include for stats

	Scanner scanner;

	public LineTrap(){
		//sets grid size through user input
		scanner = new Scanner(System.in);
		System.out.print("Please enter game size (must be odd): ");
		do {
			numGridPoints = scanner.nextInt();
		} while (numGridPoints < 3 || numGridPoints % 2 != 1); //insert an upper limit as appropriate

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
	}

	public void drawBoard(){
		for(int row = 0; row < 2*numGridPoints-1; row++){
			for(int col = 0; col < 2*numGridPoints-1; col++){
				if (col != 2*numGridPoints-1) {
					System.out.print(board[row][col]);
				}
			}
			System.out.print("\n");
		}
	}

	@Override
	void setBoard(Object nb){
		char[][]newBoard = (char[][])nb;
		for(int row = 0; row < numGridPoints; row++){
			for(int col = 0; col < numGridPoints; col++){
				board[row][col] = newBoard[row][col];
			}
		}
	}

	@Override
	Object getBoard(){
		return board;
	}

	@Override
	// consider not printing "Please make a move" or equivalent out
	// need to protect against moves to outside the board
	void getHumanMove(){
		int newUpLocation = rowLocation + 2;
		int newDownLocation = rowLocation - 2;
		int newRightLocation = colLocation - 2;
		int newLeftLocation = colLocation + 2;
		String humanMove;

		do {
			System.out.println("Please make a move");
			humanMove = scanner.next();
			if (humanMove.indexOf("w") != -1 && rowLocation != 0 && rowLocation != numGridPoints) {
				board[rowLocation++][colLocation] = VERTICALLINE;
				board[newUpLocation][colLocation] = MARKER;
				rowLocation = newUpLocation;
			} else if (humanMove.indexOf("s") != -1 && rowLocation != 0 && rowLocation != numGridPoints) {
				board[--rowLocation][colLocation] = VERTICALLINE;
				board[newDownLocation][colLocation] = MARKER;
				rowLocation = newDownLocation;
			} else if (humanMove.indexOf("a") != -1 && colLocation != 0 && colLocation != numGridPoints) {
				board[rowLocation][--colLocation] = HORIZONTALLINE;
				board[rowLocation][newRightLocation] = MARKER;
				colLocation = newRightLocation;
			} else if (colLocation != 0 && colLocation != numGridPoints) {
				board[rowLocation][++colLocation] = HORIZONTALLINE;
				board[rowLocation][newLeftLocation] = MARKER;
				colLocation = newLeftLocation;
			}
		} while (humanMove.length() != 1 && humanMove.indexOf("w") == -1 && humanMove.indexOf("s") == -1 && humanMove.indexOf("a") == -1 && humanMove.indexOf("d") == -1);

		whoseTurn = 3-whoseTurn;
		// numMoves++;
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
		if (rowLocation++ == VERTICALLINE && rowLocation-- == VERTICALLINE && colLocation++ == HORIZONTALLINE && colLocation-- == HORIZONTALLINE) {
			return true;
		} else return false;
	}

	@Override
	int whoWon(){
		return whoseTurn;
	}

	@Override
	int whoWon(Object board){
		if (whoseTurn == 1) {
			return 2;
		} else return 1;
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
	Object[] getChildren(Object board){
		char[][] parentBoard = (char[][])board;
		char[][] child; //this probably shouldn't exist at such a large scope
		int childWhoseTurn = 3 - whoseTurn; //advantages vs. whoseTurn() method

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