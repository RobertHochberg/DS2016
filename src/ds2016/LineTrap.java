/**
 * rowLocation moves up/down; colLocation moves left/right
 */

package src.ds2016;

import java.util.Scanner;

class LineTrap extends AlternatingGame{
	int numGridPoints;
	int boardSize;
	char[][] board; //row 1st, col 2nd
	int rowLocation;
	int colLocation;
	char UPLINE = '\u2191';
	char DOWNLINE = '\u2193';
	char LEFTLINE = '\u2190';
	char RIGHTLINE = '\u2192';
	char EMPTYSPACE = ' ';
	char DOT = '\u25CB';
	char USEDDOT = '\u25CF';
	char MARKER = 'X';
	String humanMove = "";
	boolean error = false;

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

		boardSize = 2*numGridPoints-1;
		board = new char[boardSize][boardSize];

		// these use Math.floor() because computers count from 0, not 1
		rowLocation = (int)Math.floor((double)(boardSize)/2);
		colLocation = (int)Math.floor((double)(boardSize)/2);

		//draws the initial blank board and sets board[1][1] to turn
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				if (row%2 == 0 && col%2 == 0) {
					board[row][col] = DOT;
				} else board[row][col] = EMPTYSPACE;
			}
		}
		// works if I shove 1 and 2 right into 1,1 but not if I set them to a variable and cast
		// if I do the second, computer does not play to win
		board[1][1] = 1;

		//places the marker in the center of the board
		board[rowLocation][colLocation] = MARKER;

		whoseTurn = 1;
	}

	public void drawBoard(){
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				if (col != boardSize) {
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
		char[][] localBoard = (char[][])nb;
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				board[row][col] = localBoard[row][col];
			}
		}

		//finds MARKER location on board[][] to update rowLocation and colLocation
		markerfinder:
			for(int row = 0; row < boardSize; row++){
				for(int col = 0; col < boardSize; col++){
					if (board[row][col] == MARKER) {
						rowLocation = row;
						colLocation = col;
						break markerfinder;
					} else if (board[row][col] != MARKER) {
						continue;
					}
				}
			}
	}

	@Override
	Object getBoard(){
		return board;
	}

	@Override
	int getNumGridPoints(){
		return numGridPoints;
	}

	@Override
	void getHumanMove(){
		do {
			System.out.print("Please make a move: ");
			humanMove = scanner.next();
		} while (humanMove.indexOf("w") == -1 && humanMove.indexOf("W") == -1 && humanMove.indexOf("s") == -1 && humanMove.indexOf("S") == -1 && humanMove.indexOf("a") == -1 && humanMove.indexOf("A") == -1 && humanMove.indexOf("d") == -1 && humanMove.indexOf("D") == -1 || humanMove.length() != 1);

		if (humanMove.indexOf("w") != -1 || humanMove.indexOf("W") != -1) {
			getUpMove();
		} else if (humanMove.indexOf("s") != -1 || humanMove.indexOf("S") != -1) {
			getDownMove();
		} else if (humanMove.indexOf("a") != -1 || humanMove.indexOf("A") != -1) {
			getLeftMove();
		} else if (humanMove.indexOf("d") != -1 || humanMove.indexOf("D") != -1) {
			getRightMove();
		}

		if (error == false) {
			whoseTurn = 3 - whoseTurn;
			if (whoseTurn == 1) {
				board[1][1] = 1;
			} else board[1][1] = 2;
		} else error = false;
	}

	void getUpMove(){
		while (rowLocation == 0 || board[rowLocation - 1][colLocation] == DOWNLINE || board[rowLocation - 1][colLocation] == UPLINE) {
			System.out.println("I'm sorry, Dave. I'm afraid I can't do that.");
			humanMove = "";
			error = true;
			getHumanMove();
			return;
		}
		board[rowLocation - 1][colLocation] = UPLINE;
		board[rowLocation][colLocation] = USEDDOT;
		rowLocation = rowLocation - 2;
		board[rowLocation][colLocation] = MARKER;
	}

	void getDownMove(){
		while (rowLocation == 2*numGridPoints-2 || board[rowLocation + 1][colLocation] == UPLINE || board[rowLocation + 1][colLocation] == DOWNLINE) {
			System.out.println("I'm sorry, Dave. I'm afraid I can't do that.");
			humanMove = "";
			getHumanMove();
			error = true;
			return;
		}
		board[rowLocation + 1][colLocation] = DOWNLINE;
		board[rowLocation][colLocation] = USEDDOT;
		rowLocation = rowLocation + 2;
		board[rowLocation][colLocation] = MARKER;
	}

	void getLeftMove(){
		while (colLocation == 0 || board[rowLocation][colLocation - 1] == RIGHTLINE || board[rowLocation][colLocation - 1] == LEFTLINE) {
			System.out.println("I'm sorry, Dave. I'm afraid I can't do that.");
			humanMove = "";
			getHumanMove();
			error = true;
			return;
		}
		board[rowLocation][colLocation - 1] = LEFTLINE;
		board[rowLocation][colLocation] = USEDDOT;
		colLocation = colLocation - 2;
		board[rowLocation][colLocation] = MARKER;
	}

	void getRightMove(){
		while (colLocation == 2*numGridPoints-2 || board[rowLocation][colLocation + 1] == LEFTLINE || board[rowLocation][colLocation + 1] == RIGHTLINE) {
			System.out.println("I'm sorry, Dave. I'm afraid I can't do that.");
			humanMove = "";
			getHumanMove();
			error = true;
			return;
		}
		board[rowLocation][colLocation + 1] = RIGHTLINE;
		board[rowLocation][colLocation] = USEDDOT;
		colLocation = colLocation + 2;
		board[rowLocation][colLocation] = MARKER;
	}

	@Override
	void getComputerMove(){
		getSmartComputerMove();
		if (whoseTurn == 1) {
			board[1][1] = 1;
		} else board[1][1] = 2;
	}

	@Override
	int whoseTurn(Object localBoard){
		return (int)((char[][])localBoard)[1][1];
	}

	@Override
	//need to check for both types of lines
	boolean isGameOver(){
		boolean isGameOver = false;
		if (rowLocation == 0 && colLocation == 0) {
			//upper left corner
			if (board[rowLocation + 1][colLocation] == UPLINE || board[rowLocation + 1][colLocation] == DOWNLINE) {
				if (board[rowLocation][colLocation + 1] == LEFTLINE || board[rowLocation][colLocation + 1] == RIGHTLINE) {
					isGameOver = true;
				}
			}
		} else if (rowLocation == 0 && colLocation == boardSize - 1) {
			//upper right corner
			if (board[rowLocation + 1][colLocation] == UPLINE || board[rowLocation + 1][colLocation] == DOWNLINE) {
				if (board[rowLocation][colLocation - 1] == LEFTLINE || board[rowLocation][colLocation - 1] == RIGHTLINE) {
					isGameOver = true;
				}
			}
		} else if (rowLocation == boardSize - 1 && colLocation == 0) {
			//lower left corner
			if (board[rowLocation - 1][colLocation] == UPLINE || board[rowLocation - 1][colLocation] == DOWNLINE) {
				if (board[rowLocation][colLocation + 1] == LEFTLINE || board[rowLocation][colLocation + 1] == RIGHTLINE) {
					isGameOver = true;
				}
			}
		} else if (rowLocation == boardSize - 1 && colLocation == boardSize - 1) {
			//lower right corner
			if (board[rowLocation - 1][colLocation] == UPLINE || board[rowLocation - 1][colLocation] == DOWNLINE) {
				if (board[rowLocation][colLocation - 1] == LEFTLINE || board[rowLocation][colLocation - 1] == RIGHTLINE) {
					isGameOver = true;
				}
			}
		} else if (rowLocation == 0) {
			//top edge
			if (board[rowLocation + 1][colLocation] == UPLINE || board[rowLocation + 1][colLocation] == DOWNLINE) {
				if (board[rowLocation][colLocation - 1] == LEFTLINE || board[rowLocation][colLocation - 1] == RIGHTLINE) {
					if (board[rowLocation][colLocation + 1] == LEFTLINE || board[rowLocation][colLocation + 1] == RIGHTLINE) {
						isGameOver = true;
					}
				}
			}
		} else if (rowLocation == boardSize - 1) {
			//bottom edge
			if (board[rowLocation - 1][colLocation] == UPLINE || board[rowLocation - 1][colLocation] == DOWNLINE) {
				if (board[rowLocation][colLocation - 1] == LEFTLINE || board[rowLocation][colLocation - 1] == RIGHTLINE) {
					if (board[rowLocation][colLocation + 1] == LEFTLINE || board[rowLocation][colLocation + 1] == RIGHTLINE) {
						isGameOver = true;
					}
				}
			}
		} else if (colLocation == 0) {
			//left edge
			if (board[rowLocation - 1][colLocation] == UPLINE || board[rowLocation - 1][colLocation] == DOWNLINE) {
				if (board[rowLocation + 1][colLocation] == UPLINE || board[rowLocation + 1][colLocation] == DOWNLINE) {
					if (board[rowLocation][colLocation + 1] == LEFTLINE || board[rowLocation][colLocation + 1] == RIGHTLINE) {
						isGameOver = true;
					}
				}
			}
		} else if (colLocation == boardSize - 1) {
			//right edge
			if (board[rowLocation - 1][colLocation] == UPLINE || board[rowLocation - 1][colLocation] == DOWNLINE) {
				if (board[rowLocation + 1][colLocation] == UPLINE || board[rowLocation + 1][colLocation] == DOWNLINE) {
					if (board[rowLocation][colLocation - 1] == LEFTLINE || board[rowLocation][colLocation - 1] == RIGHTLINE) {
						isGameOver = true;
					}
				}
			}
		} else if (board[rowLocation - 1][colLocation] == UPLINE || board[rowLocation - 1][colLocation] == DOWNLINE) {
			//middle of the board
			if (board[rowLocation + 1][colLocation] == UPLINE || board[rowLocation + 1][colLocation] == DOWNLINE) {
				if (board[rowLocation][colLocation - 1] == LEFTLINE || board[rowLocation][colLocation - 1] == RIGHTLINE) {
					if (board[rowLocation][colLocation + 1] == LEFTLINE || board[rowLocation][colLocation + 1] == RIGHTLINE) {
						isGameOver = true;

					}
				}
			}
		} else isGameOver = false;
		return isGameOver;
	}

	@Override
	int whoWon(){
		return 3 - whoseTurn;
	}

	@Override
	int whoWon(Object localBoard){
		return 3 - whoseTurn(localBoard);
	}

	@Override
	Object[] getChildren(Object b){
		char[][] parent = (char[][])b;
		int childWhoseTurn = 3 - (int)parent[1][1];
		DSArrayList<Object[]> childrenHolder = new DSArrayList<Object[]>();
		int localRowLocation = 0;
		int localColLocation = 0;

		//finds MARKER location on parent[][]
		markerfinder:
			for(int row = 0; row < boardSize; row++){
				for(int col = 0; col < boardSize; col++){
					if (parent[row][col] == MARKER) {
						localRowLocation = row;
						localColLocation = col;
						break markerfinder;
					} else if (parent[row][col] != MARKER) {
						continue;
					}
				}
			}

		//up move
		if (localRowLocation != 0 && parent[localRowLocation - 1][localColLocation] != UPLINE && parent[localRowLocation - 1][localColLocation] != DOWNLINE) {
			char[][] child = new char[boardSize][boardSize];
			int lRL = localRowLocation;
			int lCL = localColLocation;
			for(int row = 0; row < boardSize; row++){
				for(int col = 0; col < boardSize; col++){
					child[row][col] = parent[row][col];
				}
			}
			if (childWhoseTurn == 1) {
				child[1][1] = 1;
			} else child[1][1] = 2;

			child[lRL - 1][lCL] = UPLINE;
			child[lRL][lCL] = USEDDOT;
			lRL = lRL - 2;
			child[lRL][lCL] = MARKER;
			childrenHolder.add(child);
		}

		//down move
		if (localRowLocation != boardSize - 1 && parent[localRowLocation + 1][localColLocation] != UPLINE && parent[localRowLocation + 1][localColLocation] != DOWNLINE) {
			char[][] child = new char[boardSize][boardSize];
			int lRL = localRowLocation;
			int lCL = localColLocation;
			for(int row = 0; row < boardSize; row++){
				for(int col = 0; col < boardSize; col++){
					child[row][col] = parent[row][col];
				}
			}
			if (childWhoseTurn == 1) {
				child[1][1] = 1;
			} else child[1][1] = 2;

			child[lRL + 1][lCL] = DOWNLINE;
			child[lRL][lCL] = USEDDOT;
			lRL = lRL + 2;
			child[lRL][lCL] = MARKER;
			childrenHolder.add(child);
		}

		//left move
		if (localColLocation != 0 && parent[localRowLocation][localColLocation - 1] != LEFTLINE && parent[localRowLocation][localColLocation - 1] != RIGHTLINE) {
			char[][] child = new char[boardSize][boardSize];
			int lRL = localRowLocation;
			int lCL = localColLocation;
			for(int row = 0; row < boardSize; row++){
				for(int col = 0; col < boardSize; col++){
					child[row][col] = parent[row][col];
				}
			}
			if (childWhoseTurn == 1) {
				child[1][1] = 1;
			} else child[1][1] = 2;

			child[lRL][lCL - 1] = LEFTLINE;
			child[lRL][lCL] = USEDDOT;
			lCL = lCL - 2;
			child[lRL][lCL] = MARKER;
			childrenHolder.add(child);
		}

		//right move
		if (localColLocation != boardSize - 1 && parent[localRowLocation][localColLocation + 1] != LEFTLINE && parent[localRowLocation][localColLocation + 1] != RIGHTLINE) {
			char[][] child = new char[boardSize][boardSize];
			int lRL = localRowLocation;
			int lCL = localColLocation;
			for(int row = 0; row < boardSize; row++){
				for(int col = 0; col < boardSize; col++){
					child[row][col] = parent[row][col];
				}
			}
			if (childWhoseTurn == 1) {
				child[1][1] = 1;
			} else child[1][1] = 2;

			child[lRL][lCL + 1] = RIGHTLINE;
			child[lRL][lCL] = USEDDOT;
			lCL = lCL + 2;
			child[lRL][lCL] = MARKER;
			childrenHolder.add(child);
		}

		return childrenHolder.toArray();
	}

	@Override
	//necessary to get more mileage out of hash map
	String toString(Object board) {
		String boardAsString = "";
		char[][] b = (char[][]) board;
		String[][] stringBoard = new String[boardSize][boardSize];

		//copies char[][] b into equivalent String[][] stringBoard
		//done so that it concats into String
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				if (b[i][j] == UPLINE || b[i][j] == DOWNLINE) {
					stringBoard[i][j] = "v";
				} else if (b[i][j] == LEFTLINE || b[i][j] == RIGHTLINE) {
					stringBoard[i][j] = "h";
				} else if (b[i][j] == MARKER) {
					stringBoard[i][j] = "m";
				} else if (b[i][j] == 1) {
					stringBoard[1][1] = "1";
				} else if (b[i][j] == 2) {
					stringBoard[1][1] = "2";
				} else stringBoard[i][j] = " ";
			}
		}

		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				boardAsString = boardAsString.concat(stringBoard[i][j]);
			}
		}

		return boardAsString;
	}

	@Override
	int evaluateHeuristic(Object board) {
		char[][] b = (char[][])(((DSNode)board).getBoard());
		int lRL = 0;
		int lCL = 0;

		markerfinder:
			for(int row = 0; row < boardSize; row++){
				for(int col = 0; col < boardSize; col++){
					if (b[row][col] == MARKER) {
						lRL = row;
						lCL = col;
						break markerfinder;
					} else if (b[row][col] != MARKER) {
						continue;
					}
				}
			}

		int boardScore = 0;

		int spacesFilled = 0;
		if (lRL != 0 && lCL != 0) {
			if (lRL != boardSize - 1 && lCL != boardSize - 1) {
				if (b[lRL - 1][lCL] != EMPTYSPACE) {
					spacesFilled = spacesFilled + 1;
				} else if (b[lRL + 1][lCL] != EMPTYSPACE) {
					spacesFilled = spacesFilled + 1;
				} else if (b[lRL][lCL - 1] != EMPTYSPACE) {
					spacesFilled = spacesFilled + 1;
				} else if (b[lRL][lCL + 1] != EMPTYSPACE) {
					spacesFilled = spacesFilled + 1;
				}
			}
		}

		if (b[1][1] == 1) {
			if (spacesFilled == 4) {
				boardScore = 1000;
			} else if (spacesFilled == 3) {
				boardScore = 75;
			} else if (spacesFilled == 2) {
				boardScore = 50;
			} else boardScore = 25;
		} else {
			if (spacesFilled == 4) {
				boardScore = -1000;
			} else if (spacesFilled == 3) {
				boardScore = -75;
			} else if (spacesFilled == 2) {
				boardScore = -50;
			} else boardScore = -25;
		}

		/**
		if (b[1][1] == 1)
			boardScore = (int)Math.floor(100*Math.random());
		else boardScore = 0 - (int)Math.floor(100*Math.random());
		 */

		return boardScore;
	}
}