/**
 * draw lines until you can't.
 */

package ds2016;

import java.util.Scanner;

class LineTrap extends AlternatingGame{
	int numGridPoints = 5;
	char[][] board = new char[2*numGridPoints-1][2*numGridPoints-1]; //row 1st, col 2nd
	int rowLocation;
	int colLocation;

	Scanner scanner;

	public LineTrap(){
		scanner = new Scanner(System.in);
		rowLocation = numGridPoints/2;
		colLocation = numGridPoints/2;

		//for future reference; obviously board shouldn't be full at beginning
		for(int row = 0; row < numGridPoints; row++){
			for(int col = 0; col < numGridPoints; col++){
				if (row%2 == 0 && col%2 == 0) {
					board[row][col] = '*';
				} else if (row%2 == 0 && col%2 != 0) {
					board[row][col] = '\u2500';
				} else if (row%2 != 0) {
					board[row][col] = '\u2502';
				} else board[row][col] = ' ';
			}
		}

		char[][] b = new char[2*numGridPoints-1][2*numGridPoints-1];
		for(int row = 0; row < numGridPoints; row++){
			for(int col = 0; col < numGridPoints; col++){
				if (row%2 == 0 && col%2 == 0) {
					b[row][col] = '*';
				} else if (row%2 == 0 && col%2 != 0) {
					b[row][col] = '\u2500';
				} else if (row%2 != 0) {
					b[row][col] = '\u2502';
				} else b[row][col] = ' ';
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
	int getHumanMove() {
		do {
			System.out.println("Please make a move");
			String humanMove = scanner.next();
			if (humanMove.indexOf("w") != -1 && rowLocation != 0 && rowLocation != numGridPoints) {
				++rowLocation;
			} else if (humanMove.indexOf("s") != -1 && rowLocation != 0 && rowLocation != numGridPoints) {
				--rowLocation;
			} else if (humanMove.indexOf("a") != -1 && colLocation != 0 && colLocation != numGridPoints) {
				--colLocation;
			} else if (colLocation != 0 && colLocation != numGridPoints) {
				++colLocation;
			}
		} while (humanMove.length() != 1 && humanMove.indexOf("w", "s", "a", "d") == -1);
		return rowLocation;
		return colLocation;
	}

	@Override
	void getComputerMove() {
		//might need to change depending on size of tree
		return getSmartComputerMove;
	}

	@Override
	int whoWon() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	int whoseTurn(Object localBoard) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	int whoWon(Object board) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	Object[] getChildren(Object board) {
		// TODO Auto-generated method stub
		return null;
	}	
}