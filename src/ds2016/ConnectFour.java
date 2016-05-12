package ds2016;
//Author: Michael Bolot DS2016
import java.util.Scanner;
/** Connect four game
 * Board is made up of 7 columns, each of 6 space length
 * For 2 players
 * @author Michael
 *
 */

public class ConnectFour extends AlternatingGame {
	//Fields
	char[][] board = new char[7][6];
	char playerOneCharacter = 'X'; // used from tictactoe for simplicities' sake
	char playerTwoCharacter = 'O';
	
	Scanner scanner;
	
/**
 * Constructor
 * Taken (partially) from tic tac toe
 * Creates a board, and sets empty spaces equal to the int of that column
 */
	public ConnectFour() {
		scanner = new Scanner(System.in);
		for(int i = 0; i <=6; i++){
			for(int j = 0; j <= 5; j++){
				board[i][j] = (char) ('0' + i);
			}
		}
	}
	/**
	 * Basic Set board
	 * Taken from DSnode and adopted for this particular board
	 */
	@Override
	void setBoard(Object nb) {
		board = (char[][])nb;
	}

	@Override
	Object getBoard() {
		return board;
	}

	/**
	 * Drawn board for connect 4
	 * The first int after board indicates the column, the second indicates the row
	 */
	@Override
	void drawBoard() {
		// TODO Auto-generated method stub
		System.out.println("---------------");
		System.out.println("|" + board[0][0] + "|" + board[1][0] + "|" + board[2][0] + "|" + board[3][0] + "|" + board[4][0] + "|" + board[5][0] + "|" + board[6][0] + "|");
		System.out.println("---------------");
		System.out.println("|" + board[0][1] + "|" + board[1][1] + "|" + board[2][1] + "|" + board[3][1] + "|" + board[4][1] + "|" + board[5][1] + "|" + board[6][1] + "|");
		System.out.println("---------------");
		System.out.println("|" + board[0][2] + "|" + board[1][2] + "|" + board[2][2] + "|" + board[3][2] + "|" + board[4][2] + "|" + board[5][2] + "|" + board[6][2] + "|");
		System.out.println("---------------");
		System.out.println("|" + board[0][3] + "|" + board[1][3] + "|" + board[2][3] + "|" + board[3][3] + "|" + board[4][3] + "|" + board[5][3] + "|" + board[6][3] + "|");
		System.out.println("---------------");
		System.out.println("|" + board[0][4] + "|" + board[1][4] + "|" + board[2][4] + "|" + board[3][4] + "|" + board[4][4] + "|" + board[5][4] + "|" + board[6][4] + "|");
		System.out.println("---------------");
		System.out.println("|" + board[0][5] + "|" + board[1][5] + "|" + board[2][5] + "|" + board[3][5] + "|" + board[4][5] + "|" + board[5][5] + "|" + board[6][5] + "|");
		System.out.println("---------------");
	}
	
	
	/**
	 * Get human move takes an int
	 * If the int is between 0 and 6, it places the appropriate player character in the chosen row
	 * If it is outside of 0 and 6, it asks the to enter a valid int
	 * If the column is full, it tells the player so and asks for a valid column
	 */
	@Override
	void getHumanMove() {
		System.out.println("Pick a column:");
		if(whoWon(board) != 0){
			System.out.println("Player " + whoWon(board) +" has won!");
		}
		int theMove = scanner.nextInt();
		char[] theArray = new char[6];
		if (theMove < 7){
			theArray = board[theMove];
		}
		else{
			System.out.println("That is not a valid move, please enter an int between 1 and 7");
			getHumanMove();
		}
		
		if(isArrayFull(theArray) == false && whoWon(board) == 0){
		for(int i = 1; i <= 5; i++){
			if(theArray[i] == playerOneCharacter){
				if (whoseTurn(board) == 1){
					theArray[i - 1] = playerOneCharacter;
					break;
				}
				else{
					theArray[i - 1] = playerTwoCharacter;
					break;
				}
			}
			else if(theArray[i] == playerTwoCharacter){
				if (whoseTurn(board) == 1){
					theArray[i - 1] = playerOneCharacter;
					break;
				}
				else{
					theArray[i - 1] = playerTwoCharacter;
					break;
				}
			}
			else if (i == 5 && theArray[5] != playerOneCharacter && theArray[5] != playerTwoCharacter){
				if (whoseTurn(board) == 1){
					theArray[5] = playerOneCharacter;
					break;
				}
				else{
					theArray[5] = playerTwoCharacter;
					break;
				}
			}
		}
		whoseTurn = 3 - whoseTurn;
		}
		else{
			if(isArrayFull(theArray) == true){
			System.out.println("That collumn is full, pick another");
			getHumanMove();
			}
			else{
				System.out.println("Player " + whoWon(board) +" has won!");
			}
		}
	}
	/**
	 * Build tree works like the build tree of DSnode, but has a depth
	 * It builds a tree to the depth, then stops
	 * @param board
	 * @param depth
	 * @return
	 */
	
	DSNode buildTree(Object board, int depth){
		char[][] localBoard = (char[][]) board;
		// root is the DSNode we we return
		DSNode root = new DSNode< Object >();
		//root.board = board; // bad, violates encapsulation
		root.setBoard(localBoard); // good
		// We get all boards obtainable from this board in 1 move
		Object[] ch = getChildren(board);
		// System.out.println(ch.length);
		if(depth == 0){
			return root;
		}
		
		for(int i = 0; i < ch.length; i++){
			DSNode childNode = buildTree(ch[i] , depth - 1);
			root.addChild(childNode);
		}
		return root;
	}

	/**
	 * This is the smart computer move for the ConnectFour class
	 */
	@Override
	void getComputerMove() {
		Object board = getBoard();
		Object[] children = getChildren(board);
		
		if(whoWon(board) == 0){
		Object newBoard = children[0];
			//Assume for 2 players
		int winner = 3 - whoseTurn;
		int x = 0; //x is the array position of the current "winning" tree
		int y = 5; //y is the depth for buildTree
		if(whoWon(board) == 0){
		for(int i = 0; i < children.length; i++){
			DSNode childTree = buildTree(children[i], y);
			DSNode challengeTree = buildTree(newBoard, y);
			int challengeVal = evaluateTree(challengeTree);
			int childVal = evaluateTree(childTree);  // Recursive call
			if(whoseTurn == 1){
				if(childVal < challengeVal)
				x = i;
				newBoard = children[x];
;			}
			if(whoseTurn == 2){
				if(childVal > challengeVal)
				x = i;
				newBoard = children[x];
			}
		}
		}
		if(winner != 0 && winner > 0)
			System.out.println("Player " + 2 + " has the lead!");
		else if(winner != 0 && winner < 0)
			System.out.println("Player " + 1 + " has the lead!");
		
		setBoard(newBoard);
		whoseTurn = 3 - whoseTurn;
		drawBoard();
		}
		else
			System.out.println("Player " + whoWon(board) + " has won!"); 
	}
	
	/**
	 * Defunct Function, only here b/c of superclass
	 */
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

	/**
	 * WhoseTurn based on the board
	 * Adds up all player characters on the board
	 * Then checks if this total is even or odd
	 * Depending on that answer, it returns a value (one for player one) (two for player two)
	 */
	@Override
	int whoseTurn(Object localBoard) {
		board = (char[][])localBoard;
		int totalMoves = 0;
		for (int i = 0; i <= 5; i++){
			if (board[0][i] == playerOneCharacter){
				totalMoves += 1;
			}
			if (board[0][i] == playerTwoCharacter){
				totalMoves += 1;
			}	
		}
		
		for (int i = 0; i <= 5; i++){
			if (board[1][i] == playerOneCharacter){
				totalMoves += 1;
			}
			if (board[1][i] == playerTwoCharacter){
				totalMoves += 1;
			}	
		}
		
		for (int i = 0; i <= 5; i++){
			if (board[2][i] == playerOneCharacter){
				totalMoves += 1;
			}
			if (board[2][i] == playerTwoCharacter){
				totalMoves += 1;
			}	
		}
		
		for (int i = 0; i <= 5; i++){
			if (board[3][i] == playerOneCharacter){
				totalMoves += 1;
			}
			if (board[3][i] == playerTwoCharacter){
				totalMoves += 1;
			}	
		}
		
		for (int i = 0; i <= 5; i++){
			if (board[4][i] == playerOneCharacter){
				totalMoves += 1;
			}
			if (board[4][i] == playerTwoCharacter){
				totalMoves += 1;
			}	
		}
		
		for (int i = 0; i <= 5; i++){
			if (board[5][i] == playerOneCharacter){
				totalMoves += 1;
			}
			if (board[5][i] == playerTwoCharacter){
				totalMoves += 1;
			}	
		}
		for (int i = 0; i <= 5; i++){
			if (board[6][i] == playerOneCharacter){
				totalMoves += 1;
			}
			if (board[6][i] == playerTwoCharacter){
				totalMoves += 1;
			}	
		}
		if (totalMoves == 0){
			return 1;
		}
		
		if (totalMoves % 2 == 0){
			return 1;
		}
		
		else{
			return 2;
		}
	}

	/**
	 * whoWon just returns the whoWon function for the board
	 */
	@Override
	int whoWon(Object newBoard) {
		return whoWon((char[][])newBoard);
	}
	
	/**
	 * Checks a board for win states, and returns the player that won if such a win state exists
	 * @param localBoard
	 * @return
	 */
	int whoWon(char[][] localBoard){
		for(int i=0; i < 7; i++){ 
			for(int j=0; j < 6; j++){
				if(j < 2){
					if(localBoard[i][j] == localBoard[i][j + 1] &&localBoard[i][j + 1] == localBoard[i][j + 2] && localBoard[i][j + 2] == localBoard[i][j + 3])
					{
						if(localBoard[i][j] == playerOneCharacter){
							return 1;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							return 2;
						}
					}
				}
				else{
					if(localBoard[i][2] == localBoard[i][3] &&localBoard[i][3] == localBoard[i][4] && localBoard[i][4] == localBoard[i][5]){
						if(localBoard[i][2] == playerOneCharacter){
							return 1;
						}
						else if(localBoard[i][2] == playerTwoCharacter){
							return 2;
						}
					}
				}
			}
		}
		for(int i =0 ; i < 7; i++){
			for(int j= 0; j < 6; j++){
				if(i < 3){
					if(localBoard[i][j] == localBoard[i + 1][j] &&localBoard[i + 1][j] == localBoard[i + 2][j] && localBoard[i + 2][j] == localBoard[i + 3][j])
					{
						if(localBoard[i][j] == playerOneCharacter){
							return 1;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							return 2;
						}
					}
				}
				else{
					if(localBoard[3][j] == localBoard[4][j] &&localBoard[4][j] == localBoard[5][j] && localBoard[5][j] == localBoard[6][j]){
						if(localBoard[3][j] == playerOneCharacter){
							return 1;
						}
						else if(localBoard[3][j] == playerTwoCharacter){
							return 2;
						}
					}
				}
			}
		}
		for(int i =0 ; i < 7; i++){
			for(int j= 0; j < 6; j++){
				if(i < 3 && j > 3){
					if(localBoard[i][j] == localBoard[i + 1][j - 1] &&localBoard[i + 1][j - 1] == localBoard[i + 2][j - 2] && localBoard[i + 2][j - 2] == localBoard[i + 3][j - 3])
					{
						if(localBoard[i][j] == playerOneCharacter){
							return 1;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							return 2;
						}
					}
				}
				else{
					if(localBoard[3][3] == localBoard[4][2] &&localBoard[4][2] == localBoard[5][1] && localBoard[5][1] == localBoard[6][0]){
						if(localBoard[3][2] == playerOneCharacter){
							return 1;
						}
						else if(localBoard[3][2] == playerTwoCharacter){
							return 2;
						}
					}
				}
			}
		}
		for(int i =0 ; i < 7; i++){
			for(int j= 0; j < 6; j++){
				if(i < 3 && j < 2){
					if(localBoard[i][j] == localBoard[i + 1][j + 1] &&localBoard[i + 1][j + 1] == localBoard[i + 2][j + 2] && localBoard[i + 2][j + 2] == localBoard[i + 3][j + 3])
					{
						if(localBoard[i][j] == playerOneCharacter){
							return 1;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							return 2;
						}
					}
				}
				else{
					if(localBoard[3][2] == localBoard[4][3] &&localBoard[4][3] == localBoard[5][4] && localBoard[5][4] == localBoard[6][5]){
						if(localBoard[3][2] == playerOneCharacter){
							return 1;
						}
						else if(localBoard[3][2] == playerTwoCharacter){
							return 2;
						}
					}
				}
			}
		}
		return 0;
	}
	
	boolean isArrayFull (char[] array){
		if (array[0] == playerOneCharacter){
			return true;
		}
		else if (array[0] == playerTwoCharacter){
			return true;
		}
		else{
			return false;
		}
	}
	
	public Object[] getChildren(Object aB){
		char[][] aBoard = (char[][]) aB;
		//printBoard(aBoard);
		// Build the children
		int numChildren = getNumChildren(aBoard);
		Object[] rv = new Object[numChildren];
		if(numChildren == 0)
			return rv;

		// Which player will be making the moves
		char playerChar = 'x';
		if(whoseTurn(aBoard) == 1)
		{
			playerChar = playerOneCharacter;
		}
		if(whoseTurn(aBoard) == 2){
			playerChar = playerTwoCharacter;
		}
		// Find the blank spaces and make children for them
		int childIndex = 0;
		for(int i = 0; i<= 6; i++){
			 if(isArrayFull(aBoard[i]) == false){
				 char[][] childBoard = new char[7][6]; // build the child array

					// copy the parent into the child board
						for(int k = 0; k < 7; k++){
							for(int l = 0; l < 6; l++){
								childBoard[k][l] = aBoard[k][l];
							}
						}


					// put the new player's char on the board
						for(int j = 0; j < 6; j++){
							if (childBoard[i][j] == playerOneCharacter){
								childBoard[i][j - 1] = playerChar;
								break;
							}
							else if (childBoard[i][j] == playerTwoCharacter){
								childBoard[i][j - 1] = playerChar;
								break;
							}
							else if(j == 5){
								childBoard[i][5] = playerChar;
								break;
							}
						}
						
					rv[childIndex] = childBoard;
					childIndex++;
			 }
			}
		return rv;
			
		}
		
	
	/** 
	 * Same Precondition as in turn taking game
	 * Uses much of the stuff that is in evaluateTree in TurnTakinggGame
	 * (Root is the root of a fully built tree)
	 * However, in this case, the tree has been built to a depth
	 * In this case, the winner variable will hold the Heuristic value output by evaluate board
	 */
	@Override
	int evaluateTree(DSNode root){
		if(root.getNumChildren() == 0){
			root.setWinner(evaluateBoard((char[][]) root.getBoard()));
			return root.getWinner();
		}
		// Assume for now 2 players
				int whoseTurn = whoseTurn((char[][]) root.getBoard());
				int winner = 0;
				for(int i = 0; i < root.getNumChildren(); i++){
					evaluateTree((DSNode)(root.getChildren().get(i)));  // Recursive call
				if(whoseTurn((char[][]) root.getBoard()) == 1){
					if(((DSNode)(root.getChildren().get(i))).getWinner() < root.getWinner()){
						((DSNode)(root.getChildren().get(i))).setWinner(((DSNode)(root.getChildren().get(i))).getWinner());
						winner = ((DSNode)(root.getChildren().get(i))).getWinner();
					}
				  }
				else if(whoseTurn((char[][]) root.getBoard()) == 2){
					if(((DSNode)(root.getChildren().get(i))).getWinner() > root.getWinner()){
						((DSNode)(root.getChildren().get(i))).setWinner(((DSNode)(root.getChildren().get(i))).getWinner());
						winner = ((DSNode)(root.getChildren().get(i))).getWinner();
					}
				  }
				}
				 // end of looping over children
				root.setWinner(winner);
				System.out.println(winner);
				return winner;
	}
	
	/**
	 * point system for the evaluate board Heuristic
	 * designed for a computer that is player 2
	 * A player one win state is given: -2000 points
	 * A player 2 win state is given: +2000 points
	 * These player states are absolutes, so x is set to these values if they occur, and then x is returned
	 * A player one 3 state is worth 50 points
	 * A player 2  3 state is worth 50 points
	 * A player one 2 state is worth 10 points
	 * A player 2 2 state is worth 10 points
	 * @param localBoard
	 * @return
	 */
	int evaluateBoard(char[][] localBoard){
		int y = 0;
		if(whoWon(localBoard) == 1){
			y = -2000;
			return y;
		}
		if(whoWon(localBoard) == 2){
			y = 2000;
			return y;
		}
		else{
			y = evaluateThreeWinState(localBoard) + evaluateTwoWinState(localBoard);
		}
		return y; 
	}
	/**
	 * First checks for a three win "priority state"
	 * A three win priority state occurs when there is three in a row, plus a blank "win" space behind and in front of it
	 * Evaluates the board for any 3 "danger" states
	 * Looks for 3 in a row, then a blank space
	 * @param localBoard
	 * @return
	 */
	int evaluateThreeWinState(char[][] localBoard){
		int x = 0;
		for(int i=0; i < 7; i++){
			for(int j=0; j < 6; j++){
				//if (localBoard[1][5] == localBoard[2][5] && localBoard[2][5] == localBoard[3][5] && localBoard[4][5] == '4'){
					//x = x + 116;
					//return x;
				//}
				if(j < 3){
					if(localBoard[i][j] == localBoard[i][j + 1] &&localBoard[i][j + 1] == localBoard[i][j + 2] && localBoard[i][j + 3] == (char) (i))
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 50;
						}
					}
					else if(j > 0 && localBoard[i][j] == localBoard[i][j + 1] &&localBoard[i][j + 1] == localBoard[i][j + 2] && localBoard[i][j - 1] == (char) (i))
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 50;
						}
					}
				}
				else{
					if(localBoard[i][3] == localBoard[i][4] &&localBoard[i][4] == localBoard[i][5] && localBoard[i][2] == (char)i){
						if(localBoard[i][3] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[i][3] == playerTwoCharacter){
							x = x + 50;
						}
					}
				}
			}
		}
		for(int i =0 ; i < 7; i++){
			for(int j= 0; j < 6; j++){
				if(i < 4 && i > 0){
					if(localBoard[i][j] == localBoard[i + 1][j] &&localBoard[i + 1][j] == localBoard[i + 2][j] && localBoard[i + 3][j] == (char)(i + 3) && localBoard[i - 1][j] == (char) (i - 1))
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 250;
							return x;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 250;
							return x;
						}
					}
				}
				if(i < 4){
					if(localBoard[i][j] == localBoard[i + 1][j] &&localBoard[i + 1][j] == localBoard[i + 2][j] && localBoard[i + 3][j] == (char)(i + 3))
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 50;
						}
					}
					else if(i > 0 && localBoard[i][j] == localBoard[i + 1][j] &&localBoard[i + 1][j] == localBoard[i + 2][j] && localBoard[i - 1][j] == (char) (i - 1))
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 50;
						}
					}
				}
				else{
					if(localBoard[4][j] == localBoard[5][j] &&localBoard[5][j] == localBoard[6][j] && localBoard[3][j] == '3'){
						if(localBoard[4][j] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[3][j] == playerTwoCharacter){
							x = x + 50;
						}
					}
				}
			}
		}
		for(int i =0 ; i < 7; i++){
			for(int j= 0; j < 6; j++){
				if(i < 4 && j > 2){
					if(localBoard[i][j] == localBoard[i + 1][j - 1] &&localBoard[i + 1][j - 1] == localBoard[i + 2][j - 2] && localBoard[i + 3][j - 3] == (char)(i + 3))
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 50;
						}
					}
					else if(i > 0 && j < 5 && localBoard[i][j] == localBoard[i + 1][j - 1] &&
							localBoard[i + 1][j - 1] == localBoard[i + 2][j - 2] && 
							localBoard[i - 1][j + 1] == (char)(i - 1))
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 50;
						}
					}
				}
				else{
					if(localBoard[4][2] == localBoard[5][1] &&localBoard[5][1] == localBoard[6][0] && localBoard[3][3] == '3'){
						if(localBoard[3][2] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[3][2] == playerTwoCharacter){
							x = x + 50;
						}
					}
				}
			}
		}
		for(int i =0 ; i < 7; i++){
			for(int j= 0; j < 6; j++){
				if(i < 4 && j < 3){
					if(i < 0 && j < 0 && localBoard[i][j] == localBoard[i + 1][j + 1] &&localBoard[i + 1][j + 1] == localBoard[i + 2][j + 2] && localBoard[i - 1][j - 1] == (char)(i - 1))
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 50;
						}
					}
					else if(localBoard[i][j] == localBoard[i + 1][j + 1] &&localBoard[i + 1][j + 1] == localBoard[i + 2][j + 2] && localBoard[i + 1][j + 1] == (char)(i + 1))
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 50;
						}
					}
				}
				else{
					if(localBoard[4][3] == localBoard[5][4] &&localBoard[5][4] == localBoard[6][5] && localBoard[3][2] == '3'){
						if(localBoard[3][2] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[3][2] == playerTwoCharacter){
							x = x + 50;
						}
					}
				}
			}
		}
		//System.out.println(x);
		return x;
	}
	
	/**
	 * Evaluates a board for a 2 in a row "danger" state
	 * That is, a state where there is 2 in a row with an empty state around them
	 * Since such a state is potentially innocuous 
	 * (such as a state with x x empty o, where only a three in a row non-danger state can occur from it),
	 * It is only worth 10 points
	 * @param localBoard
	 * @return
	 */
	int evaluateTwoWinState(char[][] localBoard){
		int x = 0;
		for(int i=0; i < 7; i++){
			for(int j=0; j < 6; j++){
				if(j < 4){
					if(localBoard[i][j] == localBoard[i][j + 1] && localBoard[i][j + 2] == (char)i)
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 10;
						}
					}
					else if(j > 0 && localBoard[i][j] == localBoard[i][j + 1] && localBoard[i][j - 1] == (char)i)
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 10;
						}
					}
				}
				else{
					if(localBoard[i][4] == localBoard[i][5] && localBoard[i][3] == (char)i){
						if(localBoard[i][4] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[i][4] == playerTwoCharacter){
							x = x + 10;
						}
					}
				}
			}
		}
		for(int i =0 ; i < 7; i++){
			for(int j= 0; j < 6; j++){
				if(i < 5){
					if(localBoard[i][j] == localBoard[i + 1][j] && localBoard[i + 2][j] == (char)(i + 2))
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 10;
						}
					}
					else if(i > 0 && localBoard[i][j] == localBoard[i + 1][j] && localBoard[i - 1][j] == (char)(i - 1))
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 10;
						}
					}
				}
				else{
					if(localBoard[5][j] == localBoard[6][j] && localBoard[4][j] == '4'){
						if(localBoard[4][j] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[4][j] == playerTwoCharacter){
							x = x + 10;
						}
					}
				}
			}
		}
		for(int i =0 ; i < 7; i++){
			for(int j= 0; j < 6; j++){
				if(i < 5 && j > 1){
					if(localBoard[i][j] == localBoard[i + 1][j - 1] &&localBoard[i + 2][j - 2] == (char)(i + 2))
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 10;
						}
					}
					else if(j < 5 && 
							i > 0 && 
							localBoard[i][j] == localBoard[i + 1][j - 1] && 
							localBoard[i - 1][j + 1] == (char)(i - 1))
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 10;
						}
					}
				}
				else{
					if(localBoard[5][1] == localBoard[6][0] && localBoard[3][3] == '3'){
						if(localBoard[5][1] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[5][1] == playerTwoCharacter){
							x = x + 10;
						}
					}
				}
			}
		}
		for(int i =0 ; i < 7; i++){
			for(int j= 0; j < 6; j++){
				if(i < 5 && j < 4){
					if(i > 0 && j > 0 &&localBoard[i][j] == localBoard[i + 1][j + 1] && localBoard[i - 1][j - 1] == (char)(i - 1))
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 10;
						}
					}
					else if(localBoard[i][j] == localBoard[i + 1][j + 1] &&localBoard[i + 2][j + 2] == (char)(i + 2))
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 10;
						}
					}
				}
				else{
					if(localBoard[5][4] == localBoard[6][5] && localBoard[4][3] == '4'){
						if(localBoard[5][4] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[5][4] == playerTwoCharacter){
							x = x + 10;
						}
					}
				}
			}
		}
		return x;
	}
	
	
	
	/**
	 * getNumChildren looks at each column of the board
	 * If those columns aren't full, then a move can be made there
	 * It adds each empty column for the total number of child boards
	 * @param aB
	 * @return
	 */
	public int getNumChildren(Object aB){
		char[][] aBoard = (char[][])aB;
		
		// If the game is over, we have no children
		if(isGameOver(aBoard))
			return 0;
		
		int nc = 0;		// number of children
		for(int i = 0; i< 7; i++){
			if(isArrayFull(aBoard[i]) == false){
				nc++;
			}
		}
		return nc;
	}
	
	/**
	 * isGameOver takes a board, and checks if the game is over
	 * First checks if all columns are full
	 * Then checks if anyone won
	 * @param board
	 * @return
	 */
	boolean isGameOver(char[][] localBoard){
			// Check to see if the board is full
			char[][] board = new char[7][6];
			for(int i =0; i < 7; i++){
				for(int j = 0; j < 6; j++){
					board[i][j] = (char)localBoard[i][j];
				}
			}
			boolean full = true;
			for(int i = 0; i <= 6; i++){
				for(int j = 0; j < 6; j++){
				if(board[i][j] != playerOneCharacter && 
						board[i][j] != playerTwoCharacter) {
					full = false;
					break; // exit the innermost loop
				}
				}
			}
			if(full) 
				return true;

			// Check to see if somebody won
			if(whoWon(board) == 1)
				return true;
			if(whoWon(board) == 2)
				return true;

			return false;
	}
}
