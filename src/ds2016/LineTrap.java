/**
 * draw lines until you can't.
 * rowLocation++ moves up/down; colLocation moves left/right
 */

package ds2016;

import java.util.Scanner;

class LineTrap extends AlternatingGame{
	int numGridPoints = 5;
	char[][] board = new char[2*numGridPoints-1][2*numGridPoints-1]; //row 1st, col 2nd
	int rowLocation;
	int colLocation;
	int whoseTurn = 1;
	char VERTICALLINE = '\u2502';
	char HORIZONTALLINE = '\u2500';
	char MARKER = '@';
	// int numMoves = 0; // if time, include for stats

	Scanner scanner;

	public LineTrap(){
		scanner = new Scanner(System.in);
		rowLocation = numGridPoints/2;
		colLocation = numGridPoints/2;

		for(int row = 0; row < numGridPoints; row++){
			for(int col = 0; col < numGridPoints; col++){
				if (row%2 == 0 && col%2 == 0) {
					board[row][col] = '*';
				}
			}
		}

		char[][] b = new char[2*numGridPoints-1][2*numGridPoints-1];
		for(int row = 0; row < numGridPoints; row++){
			for(int col = 0; col < numGridPoints; col++){
				if (row%2 == 0 && col%2 == 0) {
					board[row][col] = '*';
				}
			}
		}

		public void drawBoard(){
			for(int row = 0; row < numGridPoints; row++){
				for(int col = 0; col < numGridPoints; col++){
					System.out.println(board[row][col]);
				}
			}
		}

		@Override
		void setBoard(Object nb) {
			char[][]newBoard = (char[][])nb;
			for(int row = 0; row < numGridPoints; row++){
				for(int col = 0; col < numGridPoints; col++){
					board[row][col] = newBoard[row][col];
				}
			}
		}

		@Override
		Object getBoard() {
			return board;
		}

		@Override
		// consider not printing anything out
		// need to protect against moves to outside the board
		void getHumanMove() {
			int newUpLocation = rowLocation + 2;
			int newDownLocation = rowLocation -2;
			int newRightLocation = colLocation - 2;
			int newLeftLocation = colLocation + 2;
			do {
				System.out.println("Please make a move");
				String humanMove = scanner.next();
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
			} while (humanMove.length() != 1 && humanMove.indexOf("w", "s", "a", "d") == -1);

			whoseTurn = 3-whoseTurn;
			// numMoves++;
		}

		@Override
		// might need to change depending on tree size, which is related to numGridPoints
		void getComputerMove() {
			return getSmartComputerMove;
			// numMoves++;
		}

		@Override
		int whoseTurn(Object localBoard) {
			return whoseTurn(localBoard);
		}

		@Override
		boolean isGameOver() {
			if (rowLocation++ == VERTICALLINE && rowLocation-- == VERTICALLINE && colLocation++ == HORIZONTALLINE && colLocation-- == HORIZONTALLINE) {
				return true;
			} else return false;
		}

		@Override
		int whoWon() {
			return whoseTurn;
		}

		@Override
		int whoWon(Object board) {
			if (whoseTurn == 1) {
				return 2;
			} else return 1;
		}

		@Override
		Object[] getChildren(Object board) {
			// TODO Auto-generated method stub
			return null;
		}	
	}