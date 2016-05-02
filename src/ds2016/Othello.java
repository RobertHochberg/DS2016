package ds2015;

/* *
 * The game of Othello
 *
 * @author The Spring 2015 Discrete Structures class at the University of Dallas
 */

import java.util.Scanner;
import java.util.function.Function;

public class Othello extends TwoPlayer {
	final static char BLACK = 0x25CF; // Player 2
	final static char WHITE = 0x25CB; // Player 1
	final static char BOARD = ' ';//0x2613; // Blank Space

	// Tell the compiler that our "board" variable will
	// be an array of arrays of char's, and whose turn it is
	// char[][] board;
	class BoardAndTurn{	// Another inner class
		char[][] b;
		int turn;

		public BoardAndTurn(){
			b = new char[8][8];
			turn = 1;
		}

		public String toString(){
			String s = "" + turn;
			int numMoves = 0;
			for(int i = 0; i < 8; i++)
				for(int j = 0; j < 8; j++){
					s = s + b[i][j];
					if(b[i][j] != BOARD)
						numMoves++;
				}
			if(numMoves > 16)
				return ""; // code for "don't hash"
			else
				return s;
		}
	}

	BoardAndTurn board;

	// We'll use a Scanner to get input from human players
	Scanner scanner;

	// Global variable to count consecutive skips of a player
	int numConsecutiveSkips;

	// Constructor: Here's how you build one of these...
	public Othello(){
		super();
		// A board is an 8x8 array of characters
		// Let's build one.
		// * = black, 0 = white, . = blank
		// White goes first.
		board = new BoardAndTurn();
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				board.b[i][j] = BOARD;
			}
		}
		board.b[3][3] = WHITE;  // char in Java is denoted with single-quotes
		board.b[3][4] = BLACK;
		board.b[4][3] = BLACK;
		board.b[4][4] = WHITE;

		// Say whose turn it is when the game begins
		board.turn = 1;
		numConsecutiveSkips = 0;

		// We'll build the Scanner
		scanner = new Scanner(System.in);

		// Player1 is a human, Player2 is a computer
		playah[1].type = Player.PlayerType.COMPUTER;
		playah[2].type = Player.PlayerType.HUMAN;
	}


	void drawBoard(){
		System.out.println();
		System.out.println("     THE BOARD    ");
		System.out.println(" | 0 1 2 3 4 5 6 7");
		for(int i = 0; i < 8; i++){
			System.out.print(i + "|");
			for(int j = 0; j < 8; j++){
				System.out.print(" " + board.b[i][j]);
			}
			System.out.println("");
		}
		System.out.println("");
	}


	void drawBoard(BoardAndTurn myBoard){
		System.out.println();
		System.out.println("     THE BOARD    ");
		System.out.println(" | 0 1 2 3 4 5 6 7");
		for(int i = 0; i < 8; i++){
			System.out.print(i + "|");
			for(int j = 0; j < 8; j++){
				System.out.print(" " + myBoard.b[i][j]);
			}
			System.out.println("");
		}
		System.out.println("");
	}


	WinState winCheck(){
		if(noValidMoves())
			return findWinner();
		else
			return WinState.STILLGOING;
	}

	/**
	 * Returns true if there are no valid moves for 
	 * either player, false otherwise
	 */
	boolean noValidMoves(){
		int copyOfTurn = board.turn;	// copy to restore later
		for(board.turn = 1; board.turn <= 2; board.turn++){
			for(int i = 0; i < 8; i++){	// for each row
				for(int j = 0; j < 8; j++){	// for each col
					if(board.b[i][j] != BOARD) // move along if there is already a piece there.
						continue;
					int numFlips = potentialFlips(i, j);
					if(numFlips > 0){
						board.turn = copyOfTurn;
						return false; // There are moves!
					}
				}
			}
		}

		board.turn = copyOfTurn;	// restore the global to its original state
		return true;
	}

	/**
	 * Returns true if there are no valid moves for 
	 * either player, false otherwise
	 */
	boolean noValidMovesForCurrentPlayer(){
		for(int i = 0; i < 8; i++){	// for each row
			for(int j = 0; j < 8; j++){	// for each col
				if(board.b[i][j] != BOARD) // move along if there is already a piece there.
					continue;
				int numFlips = potentialFlips(i, j);
				if(numFlips > 0){
					return false; // There are moves!
				}
			}
		}

		return true;  // Checked them all. No moves.
	}

	/**
	 * Returns the winner of a game that is over
	 */
	WinState findWinner(){
		int numPlayer1 = 0;
		int numPlayer2 = 0;
		for(int i = 0; i < 8; i++){	// for each row
			for(int j = 0; j < 8; j++){	// for each col
				if(board.b[i][j] == WHITE)
					numPlayer1++;
				if(board.b[i][j] == BLACK)
					numPlayer2++;
			}
		}

		if(numPlayer1 > numPlayer2)
			return WinState.PLAYER1;
		if(numPlayer2 > numPlayer1)
			return WinState.PLAYER2;
		return WinState.DRAW;
	}


	/**
	 * Makes a move for one of the players
	 */
	void getMove(){
		if(playah[board.turn].type == Player.PlayerType.HUMAN)
			getHumanMove();
		else
			getComputerMove();

		board.turn = 3 - board.turn;  // Switch whose turn it is
	}



	/**
	 * Gets a move for playing head to head
	 * @param p1 the evalufyBoard method for Player 1
	 * @param p2 the evalufyBoard method for Player 2
	 */
	void getHeadToHeadMove(Function<Object, Integer> p1, 
			Function<Object, Integer> p2){
		int best = -1;		  // best we've found so far
		int numMaybeMoves = 0;

		DSArrayList<BoardAndTurn> maybeMoves = 
				new DSArrayList<BoardAndTurn>();

		DSNode<Object> root = treeify(board, 5);

		if(root.children == null || 
				root.children.numItems == 0 ){ // leaf node
			System.out.println("Player " + board.turn + " can't move. Pass.");
			board.turn = 3 - board.turn;
			return;
		}

		else{		// Internal node
			int numChildren = root.children.numItems;
			int turn = getTurn(root.value);
			Function<Object, Integer> evalufier = turn == 1 ? p1 : p2;
			best = evalufyNodeWithEvalufier(root.children.get(0), evalufier);
			maybeMoves = new DSArrayList<BoardAndTurn>();
			maybeMoves.add((BoardAndTurn)root.children.get(0).value);
			for(int i = 1; i < numChildren; i++){
				int val = evalufyNodeWithEvalufier(root.children.get(i), evalufier);
				if((turn == 1 && val > best) || 
						(turn == 2 && val < best)){
					best = val;
					maybeMoves = new DSArrayList<BoardAndTurn>();
					numMaybeMoves = 0;
				}
				if(val == best){
					maybeMoves.add((BoardAndTurn)root.children.get(i).value);
					numMaybeMoves++;
				} 
			}
		} 

		// Pick a random move from among the (equally) best
		int moveIndex = (int)(Math.random()*numMaybeMoves);
		board = maybeMoves.get(moveIndex);
	}





	/**
	 * Gets the human player's move
	 *
	 * To accomplish this I will make the getHumanMove a while loop
	 * The loop will continue asking the human for a move and return 
	 * "Move is invalid, try again" as long as the human continues to input 
	 * invalid moves, once a valid move is input the while loop is ended and the
	 * game continues.
	 *
	 * @author Alex Borse
	 */
	void getHumanMove(){
		boolean go = true;
		while(go){
			// Get the move from the human
			System.out.print("Your move: ");
			int row = scanner.nextInt();
			int col = scanner.nextInt();
			// If the potential number of flips is greater than zero 
			// continue as normal
			if(potentialFlips(row, col) > 0){
				// Put the chip on the board
				board.b[row][col] = (board.turn == 1) ? WHITE : BLACK;
				// Process the flips
				processFlips(row, col);
				go = false;
			}
			else
				System.out.println("Move is invalid, try again");
		}
	}



	/**
	 * The computer selects a move. Selects from among those
	 * moves that create the most flips.
	 */
	void getComputerMove(){
		int best = -1;		  // best we've found so far
		int numMaybeMoves = 0;

		DSArrayList<BoardAndTurn> maybeMoves = 
				new DSArrayList<BoardAndTurn>();

		DSNode<Object> root = treeify(board, 6);

		if(root.children == null || 
				root.children.numItems == 0 ){ // leaf node
			getComputerMoveGreedy();
			return;
		}

		else{		// Internal node
			int numChildren = root.children.numItems;
			int turn = getTurn(root.value);
			best = evalufyNode(root.children.get(0));
			maybeMoves = new DSArrayList<BoardAndTurn>();
			maybeMoves.add((BoardAndTurn)root.children.get(0).value);
			System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVV");
			System.out.println("value of board below is " + best);
			drawBoard((BoardAndTurn)root.children.get(0).value);
			for(int i = 1; i < numChildren; i++){
				int val = evalufyNode(root.children.get(i));
				System.out.println("value of board below is " + val);
				drawBoard((BoardAndTurn)root.children.get(i).value);
				if((turn == 1 && val > best) || 
						(turn == 2 && val < best)){
					best = val;
					maybeMoves = new DSArrayList<BoardAndTurn>();
					numMaybeMoves = 0;
				}
				if(val == best){
					maybeMoves.add((BoardAndTurn)root.children.get(i).value);
					numMaybeMoves++;
				} 
			}
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		} 
		/*	
	// If max == 0, then the computer can't move:
	if(max == 0){
	System.out.println("Computer can't move. Pass.");
	return;
	}
		 */

		int moveIndex = (int)(Math.random()*numMaybeMoves);
		/*
	  int besti = maybeMoves.get(moveIndex).row;
	  int bestj = maybeMoves.get(moveIndex).col;
	  board.b[besti][bestj] = (board.turn == 1) ? WHITE : BLACK;
	  // Process the flips
	  processFlips(besti, bestj);
		 */
		board = maybeMoves.get(moveIndex);
		board.turn = 3 - board.turn;
	}



	/**
	 * The computer selects a move. Selects from among those
	 * moves that create the most flips.
	 */
	void getComputerMoveGreedy(){
		int max = -1;		  // best we've found so far
		DSArrayList<BoardPos> maybeMoves = 
				new DSArrayList<BoardPos>();

		// Search the board for the best move
		int numMaybeMoves = 0;
		for(int i = 0; i < 8; i++){	// for each row
			for(int j = 0; j < 8; j++){	// for each col
				if(board.b[i][j] != BOARD) // move along if there is already a piece there.
					continue;
				int numFlips = potentialFlips(i, j);
				if(numFlips > max){
					maybeMoves = new DSArrayList<BoardPos>(64);
					max = numFlips;
					numMaybeMoves = 0;
				}
				if(numFlips == max){
					BoardPos bp = new BoardPos();
					bp.row = i;
					bp.col = j;
					maybeMoves.add(bp);
					numMaybeMoves++;
				} 
			}
		}

		// If max == 0, then the computer can't move:
		if(max == 0){
			System.out.println("Computer can't move. Pass.");
			return;
		}

		// make the move
		// peek
		for(int i = 0; i < numMaybeMoves; i++)
			System.out.printf("MaybeMove:(%d, %d)\n", 
					maybeMoves.get(i).row,
					maybeMoves.get(i).col);
		int moveIndex = (int)(Math.random()*numMaybeMoves);
		int besti = maybeMoves.get(moveIndex).row;
		int bestj = maybeMoves.get(moveIndex).col;
		board.b[besti][bestj] = (board.turn == 1) ? WHITE : BLACK;
		// Process the flips
		processFlips(besti, bestj);
	}


	/**
	 * Holds a (row, col) pair representing a board position
	 */
	class BoardPos{ // Inner class
		int row;
		int col;
	}   

	/**
	 * @override the method from the TwoPlayer class
	 */
	int evalufyBoard(Object myBoard){
		BoardAndTurn bat = (BoardAndTurn)myBoard;
		int rv = 0;

		for(int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				if (bat.b[i][j] == WHITE)
					rv = rv + 1;
				else if (bat.b[i][j] == BLACK)
					rv = rv -1;
			}
		}

		return rv;
	}  

	/**
	 * @override the method from the TwoPlayer class
	 */
	int evalufyBoardWithEvalufier(Object myBoard,
			Function<Object, Integer> evalufier){
		BoardAndTurn bat = (BoardAndTurn)myBoard;
		int rv = 0;

		for(int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				if (bat.b[i][j] == WHITE)
					rv = rv + 1;
				else if (bat.b[i][j] == BLACK)
					rv = rv -1;
			}
		}

		return rv;
	}

	/**
	 * Generates a DSArrayList containing all the child boards
	 *
	 * @param board is the parent game board, whose children we find.
	 */
	DSArrayList<Object> getChildren(Object b){
		BoardAndTurn myBoard = (BoardAndTurn)b;
		//drawBoard(myBoard);
		//System.out.println("***********************" + myBoard.turn);
		// Instantiate the DSArrayList that we will return
		DSArrayList<Object> children = new DSArrayList<Object>();

		// Search the board for all legal moves
		for(int i = 0; i < 8; i++){	// for each row
			for(int j = 0; j < 8; j++){	// for each col
				if(myBoard.b[i][j] != BOARD) // move along if there is already a piece there.
					continue;
				int numFlips = potentialFlips(i, j, myBoard);
				if(numFlips > 0){ // This is a legal move
					BoardAndTurn child = new BoardAndTurn();
					child.turn = 3 - myBoard.turn;
					for(int ii = 0; ii < 8; ii++)	// for each row
						for(int jj = 0; jj < 8; jj++)	// for each col
							child.b[ii][jj] = myBoard.b[ii][jj]; // copy board
					child.b[i][j] = (myBoard.turn == 1) ? WHITE : BLACK;
					processFlips(child, i, j);
					children.add(child);
					//drawBoard(child);
				} 
			}
		}
		return children;
	}



	/**
	 * Returns the number of chips that the (row, col) move
	 * would flip, if we made that move.
	 * @param row  The row of the new move
	 * @param col  The column of the new move
	 */
	int potentialFlips(int row, int col){
		int total = 0;
		for(int dx = -1; dx <= 1; dx++)
			for(int dy = -1; dy <= 1; dy++)
				if(dx != 0 || dy != 0)
					total = total + numFlips(row, col, dx, dy);
		return total;
	}



	/**
	 * Returns the number of chips that the (row, col) move
	 * would flip, if we made that move. Takes a turn parameter
	 * 
	 * @param row  The row of the new move
	 * @param col  The column of the new move
	 * @param turn Whose turn it is to move now
	 */
	int potentialFlips(int row, int col, BoardAndTurn myBoard){
		int total = 0;
		for(int dx = -1; dx <= 1; dx++)
			for(int dy = -1; dy <= 1; dy++)
				if(dx != 0 || dy != 0)
					total = total + numFlips(row, col, dx, dy, myBoard);
		return total;
	}

	/**
	 * Takes the new move and flips the chips appropriately
	 * on the board.
	 * @param row  The row of the new move
	 * @param col  The column of the new move
	 */
	void processFlips(int row, int col){
		for(int dx = -1; dx <= 1; dx++)
			for(int dy = -1; dy <= 1; dy++)
				if(dx != 0 || dy != 0)
					if(shouldFlip(row, col, dx, dy))
						doFlip(row, col, dx, dy);

	}

	/**
	 * Takes the new move and flips the chips appropriately
	 * on the board.
	 * @param row  The row of the new move
	 * @param col  The column of the new move
	 */
	void processFlips(BoardAndTurn myBoard, int row, int col){
		for(int dx = -1; dx <= 1; dx++)
			for(int dy = -1; dy <= 1; dy++)
				if(dx != 0 || dy != 0)
					if(shouldFlip(myBoard, row, col, dx, dy))
						doFlip(myBoard, row, col, dx, dy);

	}


	/**
	 * returns true if there are chip flips in the (dx, dy) direction
	 *
	 * @param row  The row where the move is made
	 * @param col  the col where the move is made
	 * @param dx   the x-direction that we check for flips -1, 0 or 1
	 * @param dy   the y-direction that we check for flips -1, 0 or 1
	 */
	boolean shouldFlip(int row, int col, int dx, int dy){
		int i = row + dy;
		int j = col + dx;
		char myChar = board.b[row][col];
		int numToFlip = 0;
		while(i >= 0 && i <= 7 && j >= 0 && j <= 7){
			if(board.b[i][j] == BOARD)
				return false;
			else if(board.b[i][j] != myChar)
				numToFlip++;
			else if(board.b[i][j] == myChar && numToFlip > 0)
				return true;
			i += dy;
			j += dx;
		}
		return false;
	}


	/**
	 * returns true if there are chip flips in the (dx, dy) direction
	 *
	 * @param row  The row where the move is made
	 * @param col  the col where the move is made
	 * @param dx   the x-direction that we check for flips -1, 0 or 1
	 * @param dy   the y-direction that we check for flips -1, 0 or 1
	 */
	boolean shouldFlip(BoardAndTurn myBoard, int row, int col, int dx, int dy){
		int i = row + dy;
		int j = col + dx;
		char myChar = myBoard.b[row][col];
		int numToFlip = 0;
		while(i >= 0 && i <= 7 && j >= 0 && j <= 7){
			if(myBoard.b[i][j] == BOARD)
				return false;
			else if(myBoard.b[i][j] != myChar)
				numToFlip++;
			else if(myBoard.b[i][j] == myChar && numToFlip > 0)
				return true;
			i += dy;
			j += dx;
		}
		return false;
	}



	/**
	 * Performs flips in the (dx, dy) direction
	 *
	 * By the time this method is called, we know that there will
	 * be moves to be made in the (dx, dy) direction
	 *
	 * @param row  The row where the move is made
	 * @param col  the col where the move is made
	 * @param dx   the x-direction that we do flips -1, 0 or 1
	 * @param dy   the y-direction that we do flips -1, 0 or 1
	 */
	void doFlip(int row, int col, int dx, int dy){
		int i = row + dy;
		int j = col + dx;
		char myChar = board.b[row][col];
		char otherChar = myChar == WHITE ? BLACK : WHITE;
		while(board.b[i][j] == otherChar){
			board.b[i][j] = myChar;
			i += dy;
			j += dx;
		}
	}



	/**
	 * Performs flips in the (dx, dy) direction
	 *
	 * By the time this method is called, we know that there will
	 * be moves to be made in the (dx, dy) direction
	 *
	 * @param row  The row where the move is made
	 * @param col  the col where the move is made
	 * @param dx   the x-direction that we do flips -1, 0 or 1
	 * @param dy   the y-direction that we do flips -1, 0 or 1
	 */
	void doFlip(BoardAndTurn myBoard, int row, int col, int dx, int dy){
		int i = row + dy;
		int j = col + dx;
		char myChar = myBoard.b[row][col];
		char otherChar = myChar == WHITE ? BLACK : WHITE;
		while(myBoard.b[i][j] == otherChar){
			myBoard.b[i][j] = myChar;
			i += dy;
			j += dx;
		}
	}


	/**
	 * Counts flips in the (dx, dy) direction
	 *
	 * May return 0 if there are no flips in the indicated direction
	 *
	 * @param row  The row where the move is made
	 * @param col  the col where the move is made
	 * @param dx   the x-direction that we check for flips -1, 0 or 1
	 * @param dy   the y-direction that we check for flips -1, 0 or 1
	 */
	int numFlips(int row, int col, int dx, int dy){
		int i = row + dy;
		int j = col + dx;
		char myChar = board.turn == 1 ? WHITE : BLACK;
		int numToFlip = 0;
		while(i >= 0 && i <= 7 && j >= 0 && j <= 7){
			if(board.b[i][j] == BOARD)
				return 0;
			else if(board.b[i][j] != myChar)
				numToFlip++;
			else if(board.b[i][j] == myChar && numToFlip == 0)
				return 0;
			else if(board.b[i][j] == myChar && numToFlip > 0)
				return numToFlip;
			i += dy;
			j += dx;
		}
		return 0;
	}


	/**
	 * Counts flips in the (dx, dy) direction
	 *
	 * May return 0 if there are no flips in the indicated direction
	 *
	 * @param row  The row where the move is made
	 * @param col  the col where the move is made
	 * @param dx   the x-direction that we check for flips -1, 0 or 1
	 * @param dy   the y-direction that we check for flips -1, 0 or 1
	 * @param turn the player whose turn we presume it to be
	 */
	int numFlips(int row, int col, int dx, int dy, BoardAndTurn myBoard){
		int i = row + dy;
		int j = col + dx;
		char myChar = myBoard.turn == 1 ? WHITE : BLACK;
		int numToFlip = 0;
		while(i >= 0 && i <= 7 && j >= 0 && j <= 7){
			if(myBoard.b[i][j] == BOARD)
				return 0;
			else if(myBoard.b[i][j] != myChar)
				numToFlip++;
			else if(myBoard.b[i][j] == myChar && numToFlip == 0)
				return 0;
			else if(myBoard.b[i][j] == myChar && numToFlip > 0)
				return numToFlip;
			i += dy;
			j += dx;
		}
		return 0;
	}


	int getTurn(Object myBoard){
		BoardAndTurn b = (BoardAndTurn)myBoard;
		return b.turn;
	}

	/**
	 * @override
	 */
	public void reset(){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				board.b[i][j] = BOARD;
			}
		}
		board.b[3][3] = WHITE;  
		board.b[3][4] = BLACK;
		board.b[4][3] = BLACK;
		board.b[4][4] = WHITE;

		board.turn = 1;	
	}




	/**
	 * Returns #whites - #blacks
	 * @param myBoard the current board to evaluate
	 * 
	 * @return #white chips - # black chips
	 */
	static int chipCount(Object myBoard){
		BoardAndTurn bat = (BoardAndTurn)myBoard;
		int rv = 0;

		for(int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				if (bat.b[i][j] == WHITE)
					rv = rv + 1;
				else if (bat.b[i][j] == BLACK)
					rv = rv -1;
			}
		}

		return rv;
	}  




	/**
	 * Returns  the honorableTeacher score of a board
	 * @param myBoard the current board to evaluate
	 * 
	 * @return #white chips - # black chips
	 */
	static int honorableTeacher(Object myBoard){
		BoardAndTurn bat = (BoardAndTurn)myBoard;
		double numBlanks = 64;

		// Count certain types of squares
		int corners = 0;
		int edges   = 0;
		int inner   = 0; // middle 4x4 region
		int badRing = 0; // inset one from the outside

		for(int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				// First compute the value of this square
				int value = 0;
				if (bat.b[i][j] == WHITE)
					value = 1;
				else if (bat.b[i][j] == BLACK)
					value = -1;
				if(value == 0)
					continue; // No chip on this square

				numBlanks -= 1;  // one less blank square
				if((i == 0 || i == 7) && (j == 0 || j == 7))// corner
					badRing += value;
				if((i <= 1 || i >= 6) && (j <= 1 || j >= 6))// near corner
					corners += value;
				else if(i == 0 || i == 7   ||  j == 0 || j == 7) // edge
					edges += value;
				else if(i >= 2 && i <= 5   &&  j >= 2 && j <= 5) // innner
					inner += value;
				else
					badRing += value;
			}
		}
		edges -= corners;	// we included corners in our original count

		int CVal = 5;
		int EVal = 3;
		int IVal = 1;
		int BVal = -1;

		int Threshold = 10;
		double factor = numBlanks < Threshold ? 1 : numBlanks / Threshold;
		double rvdouble = 
				(1 + CVal * factor) * corners +
				(1 + EVal * factor) * edges +
				(1 + IVal * factor) * inner +
				(1 + BVal * factor) * badRing;

		return (int)rvdouble;
	}  


	/**
	 * Henderson's Function
	 * @param myBoard the board to evaluate
	 * @return the value of the board, according to Henderson
	 * 
	 * The ugliness works in its favor, I promise.
	 */
	static int hendersonAndAlex(Object myBoard){
		BoardAndTurn bat = (BoardAndTurn)myBoard;
		int rv = 0;
		int np = 0;
		for(int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				if(bat.b[i][j] != BOARD)
					np += 1;
			}
		}
		if(np > 16){
			for(int i = 0; i < 8; i++){
				for (int j = 0; j < 8; j++){
					if (bat.b[i][j] == WHITE)
						rv = rv + 1;
					else if (bat.b[i][j] == BLACK)
						rv = rv -1;
				}
			}
		}
		else{
			for(int i = 0; i < 8; i++){
				for (int j = 0; j < 8; j++){
					if (bat.b[i][j] == WHITE)
						rv = rv + 1;
					else if (bat.b[i][j] == BLACK)
						rv = rv -1;
				}
			}
			for(int i = 0; i < 8; i+=7){
				for(int j = 0; j < 8; j+=7){
					if (bat.b[i][j] == WHITE)
						rv += 4;
					if (bat.b[i][j] == BLACK)
						rv -= 4;
				}
			}
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j+=7){
					if (bat.b[i][j] == WHITE)
						rv += 2;
					if (bat.b[i][j] == BLACK)
						rv -= 2;
				}
			}
			for(int i = 0; i < 8; i+=7){
				for(int j = 0; j < 8; j++){
					if (bat.b[i][j] == WHITE)
						rv += 2;
					if (bat.b[i][j] == BLACK)
						rv -= 2;
				}
			}
		}
		return rv;
	}   

	static int calebAndElizabeth(Object myBoard){
		BoardAndTurn bat = (BoardAndTurn)myBoard;
		int rv = 0;
		int add;

		for(int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				add = 1;
				if (i==0 || i==7){
					add =+ 2;
				}
				if (j==0 || j==7){
					add =+ 2;
				}
				if (bat.b[i][j] == WHITE)
					rv = rv + add;
				else if (bat.b[i][j] == BLACK)
					rv = rv - add;
			}
		}
		return rv;
	}


	static int robertAndFaith(Object myBoard){
		BoardAndTurn bat = (BoardAndTurn)myBoard;
		int rv = 0;
		int positionValue = 0;

		/** Evaluates the positional strength of each square.
		 * A large number is good for white, a small number is good for black.
		 */
		if(bat.b[0][0] == WHITE || bat.b[7][0] == WHITE || bat.b[0][7] == WHITE ||
				bat.b[7][7] == WHITE)positionValue = positionValue + 30;
		else if(bat.b[0][0] == BLACK || bat.b[7][0] == BLACK || bat.b[0][7] == BLACK ||
				bat.b[7][7] == BLACK)positionValue = positionValue - 30;
		if(bat.b[1][0] == WHITE || bat.b[6][0] == WHITE || bat.b[0][1] == WHITE ||
				bat.b[7][1] == WHITE || bat.b[0][6] == WHITE || bat.b[7][6] == WHITE ||
				bat.b[1][7] == WHITE || bat.b[6][7] == WHITE)positionValue = positionValue - 14;
		else if(bat.b[1][0] == BLACK || bat.b[6][0] == BLACK || bat.b[0][1] == BLACK ||
				bat.b[7][1] == BLACK || bat.b[0][6] == BLACK || bat.b[7][6] == BLACK ||
				bat.b[1][7] == BLACK || bat.b[6][7] == BLACK)positionValue = positionValue + 14;
		if(bat.b[2][0] == WHITE || bat.b[5][0] == WHITE || bat.b[0][2] == WHITE ||
				bat.b[7][2] == WHITE || bat.b[0][5] == WHITE || bat.b[7][5] == WHITE ||
				bat.b[2][7] == WHITE || bat.b[5][7] == WHITE)positionValue = positionValue + 5;
		else if(bat.b[2][0] == BLACK || bat.b[5][0] == BLACK || bat.b[0][2] == BLACK ||
				bat.b[7][2] == BLACK || bat.b[0][5] == BLACK || bat.b[7][5] == BLACK ||
				bat.b[2][7] == BLACK || bat.b[5][7] == BLACK)positionValue = positionValue - 5;
		if(bat.b[3][0] == WHITE || bat.b[4][0] == WHITE || bat.b[0][3] == WHITE ||
				bat.b[7][3] == WHITE || bat.b[0][4] == WHITE || bat.b[7][4] == WHITE ||
				bat.b[3][7] == WHITE || bat.b[4][7] == WHITE)positionValue = positionValue + 4;
		else if(bat.b[3][0] == BLACK || bat.b[4][0] == BLACK || bat.b[0][3] == BLACK ||
				bat.b[7][3] == BLACK || bat.b[0][4] == BLACK || bat.b[7][4] == BLACK ||
				bat.b[3][7] == BLACK || bat.b[4][7] == BLACK)positionValue = positionValue - 4;
		if(bat.b[1][1] == WHITE || bat.b[6][1] == WHITE || bat.b[1][6] == WHITE ||
				bat.b[6][6] == WHITE)positionValue = positionValue - 8;
		else if(bat.b[1][1] == BLACK || bat.b[6][1] == BLACK || bat.b[1][6] == BLACK ||
				bat.b[6][6] == BLACK)positionValue = positionValue + 8;
		if(bat.b[2][1] == WHITE || bat.b[5][1] == WHITE || bat.b[1][2] == WHITE ||
				bat.b[6][2] == WHITE || bat.b[1][5] == WHITE || bat.b[6][5] == WHITE ||
				bat.b[2][6] == WHITE || bat.b[5][6] == WHITE || bat.b[3][3] == WHITE || 
				bat.b[4][3] == WHITE || bat.b[3][4] == WHITE || bat.b[4][4] == WHITE)positionValue = positionValue + 3;
		else if(bat.b[2][1] == BLACK || bat.b[5][1] == BLACK || bat.b[1][2] == BLACK ||
				bat.b[6][2] == BLACK || bat.b[1][5] == BLACK || bat.b[6][5] == BLACK ||
				bat.b[2][6] == BLACK || bat.b[5][6] == BLACK || bat.b[3][3] == BLACK || 
				bat.b[4][3] == BLACK || bat.b[3][4] == BLACK || bat.b[4][4] == BLACK)positionValue = positionValue - 3;
		if(bat.b[3][1] == WHITE || bat.b[4][1] == WHITE || bat.b[1][3] == WHITE ||
				bat.b[6][3] == WHITE || bat.b[1][4] == WHITE || bat.b[6][4] == WHITE ||
				bat.b[3][6] == WHITE || bat.b[4][6] == WHITE)positionValue = positionValue + 2;
		else if(bat.b[3][1] == BLACK || bat.b[4][1] == BLACK || bat.b[1][3] == BLACK ||
				bat.b[6][3] == BLACK || bat.b[1][4] == BLACK || bat.b[6][4] == BLACK ||
				bat.b[3][6] == BLACK || bat.b[4][6] == BLACK)positionValue = positionValue - 2;
		if(bat.b[2][2] == WHITE || bat.b[3][2] == WHITE || bat.b[4][2] == WHITE ||
				bat.b[5][2] == WHITE || bat.b[2][3] == WHITE || bat.b[5][3] == WHITE ||
				bat.b[2][4] == WHITE || bat.b[5][4] == WHITE || bat.b[2][5] == WHITE || 
				bat.b[3][5] == WHITE || bat.b[4][5] == WHITE || bat.b[5][5] == WHITE)positionValue = positionValue + 1;
		else if(bat.b[2][2] == BLACK || bat.b[3][2] == BLACK || bat.b[4][2] == BLACK ||
				bat.b[5][2] == BLACK || bat.b[2][3] == BLACK || bat.b[5][3] == BLACK ||
				bat.b[2][4] == BLACK || bat.b[5][4] == BLACK || bat.b[2][5] == BLACK || 
				bat.b[3][5] == BLACK || bat.b[4][5] == BLACK || bat.b[5][5] == BLACK)positionValue = positionValue - 1;

		// The player with the fewest chips in the early game has an advantage
		int whiteCount = 0;
		int blackCount = 0;
		for(int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				if (bat.b[i][j] == WHITE)
					whiteCount++;
				else if (bat.b[i][j] == BLACK)
					blackCount++;
			}
		}
		int totalCount = whiteCount + blackCount;
		boolean earlyGame = (totalCount < 40);
		int countValue = 0;
		if(earlyGame){
			countValue = blackCount - whiteCount;
		}

		rv = positionValue + countValue;

		return rv;
	}


	/**
	 * @override the method from the TwoPlayer class
	 */
	static int aidanAndJohn(Object myBoard){
		BoardAndTurn bat = (BoardAndTurn)myBoard;
		int rv = 0;

		for(int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				if     (bat.b[0][0] == WHITE || bat.b[0][7] == WHITE || 
						bat.b[7][0] == WHITE || bat.b[7][7] == WHITE)
					rv = rv - 10 ;
				if        (bat.b[0][1] == WHITE || bat.b[0][6] == WHITE || bat.b[1][0] == WHITE || // Surrounding the Corner
						bat.b[1][1] == WHITE || bat.b[1][6] == WHITE || bat.b[1][7] == WHITE ||
						bat.b[6][0] == WHITE || bat.b[6][1] == WHITE || bat.b[6][6] == WHITE ||
						bat.b[6][7] == WHITE || bat.b[7][1] == WHITE || bat.b[7][6] == WHITE)
					rv = rv - 40;
				if     (bat.b[0][2] == WHITE || bat.b[0][5] == WHITE || bat.b[2][0] == WHITE || //3rd from Corner on Border
						bat.b[2][7] == WHITE || bat.b[5][0] == WHITE || bat.b[5][7] == WHITE ||
						bat.b[7][2] == WHITE || bat.b[7][5] == WHITE)
					rv = rv + 20;
				if     (bat.b[1][2] == WHITE || bat.b[1][5] == WHITE || bat.b[2][1] == WHITE || // 3rd from Border 1 inside
						bat.b[2][6] == WHITE || bat.b[2][2] == WHITE || bat.b[2][5] == WHITE ||
						bat.b[2][6] == WHITE || bat.b[5][1] == WHITE || bat.b[5][2] == WHITE ||
						bat.b[5][5] == WHITE || bat.b[5][6] == WHITE || bat.b[6][2] == WHITE ||
						bat.b[6][5] == WHITE)
					rv = rv + 5;
				else if (bat.b[i][j] == WHITE)
					rv = rv - 5;

				if     (bat.b[0][0] == BLACK || bat.b[0][7] == BLACK ||  // CORNERS
						bat.b[7][0] == BLACK || bat.b[7][7] == BLACK)
					rv = rv + 10 ;
				if     (bat.b[0][1] == BLACK || bat.b[0][6] == BLACK || bat.b[1][0] == BLACK || // Surrounding the Corner
						bat.b[1][1] == BLACK || bat.b[1][6] == BLACK || bat.b[1][7] == BLACK ||
						bat.b[6][0] == BLACK || bat.b[6][1] == BLACK || bat.b[6][6] == BLACK ||
						bat.b[6][7] == BLACK || bat.b[7][1] == BLACK || bat.b[7][6] == BLACK)
					rv = rv + 40;
				if     (bat.b[0][2] == BLACK || bat.b[0][5] == BLACK || bat.b[2][0] == BLACK || //3rd from Corner on Border
						bat.b[2][7] == BLACK || bat.b[5][0] == BLACK || bat.b[5][7] == BLACK ||
						bat.b[7][2] == BLACK || bat.b[7][5] == BLACK)
					rv = rv - 20;
				if     (bat.b[1][2] == BLACK || bat.b[1][5] == BLACK || bat.b[2][1] == BLACK || // 3rd from Border 1 inside
						bat.b[2][6] == BLACK || bat.b[2][2] == BLACK ||    bat.b[2][5] == BLACK ||
						bat.b[2][6] == BLACK || bat.b[5][1] == BLACK || bat.b[5][2] == BLACK ||
						bat.b[5][5] == BLACK || bat.b[5][6] == BLACK ||    bat.b[6][2] == BLACK ||
						bat.b[6][5] == BLACK)
					rv = rv - 5;
				else if (bat.b[i][j] == BLACK)
					rv = rv + 5;

			}
		}

		return rv;
	}


	@Override
	/**
	 * Returns a string representation of the board and turn
	 */
	String stringify(Object board) {
		BoardAndTurn bat = (BoardAndTurn)board;
		String rv = "" + bat.turn;
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				rv += bat.b[i][j];

		return rv;
	}


}
