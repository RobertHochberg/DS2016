package ds2016;

import java.util.Scanner;

/**
 * Plays the link game between two players
 * 
 * @author jph
 *
 */

public class Link extends AlternatingGame {
	
	// the data for the game
	private LinkBoard board;
	
	// a scanner for reading moves
	private Scanner scanner;
	
	// has there been a rebound since the last draw
	private boolean rebound = false;
	
	/**
	 * Fire up a game o link
	 */
	public Link(int width, int height) {
		board = new LinkBoard(width,height);
		scanner = new Scanner(System.in);
	}
	
	@Override
	public void setBoard(Object nb) {
		board = (LinkBoard)(nb);
	}

	@Override
	public Object getBoard() {
		return board;
	}

	@Override
	public void drawBoard() {
		// clear the screen
		System.out.print("\u001b[2J\u001b[H");
		System.out.flush();
		System.out.println();
		
		// print whether there has been a rebound
		if(rebound) {
			System.out.println("Rebound");
			rebound = false;
		} else {
			System.out.println();
		}
		
		// draw the first goal
		// assume that player ones goal is on top
		for(int i = 0; i < board.getGoal1X(); i++) {
			System.out.print("        ");
		}
		System.out.println(LinkChars.GOAL1);
				
		// for every row
		for(int y = 0; y < board.getHeight(); y++) {
			// for every space
			for(int x = 0; x < board.getWidth(); x++) {
				System.out.print(board.getDotChar(x,y));
				System.out.print(' ');
				System.out.print(board.getHorArrow(x,y));
				System.out.print(' ');
			}
			// vertical and diagonal arrows
			System.out.print('\n');
			for(int x = 0; x < board.getWidth(); x++) {
				System.out.print(board.getVerArrow(x,y));
				System.out.print(' ');
				// draw the diagonal arrows that go to the lower right
				// of (x,y) (if there is even a lower right)
				if(x < board.getWidth()-1 && y < board.getHeight()-1) {
					System.out.print(board.getDiagArrow(x,y));
				}
				System.out.print(' ');
			}
			// next line for the next row
			System.out.print('\n');
		}
		// draw player twos goal
		for(int i = 0; i < board.getGoal2X(); i++) {
			System.out.print("        ");
		}
		System.out.println(LinkChars.GOAL2);
	}
	
	public void drawBoard(Object b) {
		LinkBoard board = (LinkBoard)b;
		// for every row
		for(int y = 0; y < board.getHeight(); y++) {
			// for every space
			for(int x = 0; x < board.getWidth(); x++) {
				System.out.print(board.getDotChar(x,y));
				System.out.print(' ');
				System.out.print(board.getHorArrow(x,y));
				System.out.print(' ');
			}
			// vertical and diagonal arrows
			System.out.print('\n');
			for(int x = 0; x < board.getWidth(); x++) {
				System.out.print(board.getVerArrow(x,y));
				System.out.print(' ');
				// draw the diagonal arrows that go to the lower right
				// of (x,y) (if there is even a lower right)
				if(x < board.getWidth()-1 && y < board.getHeight()-1) {
					System.out.print(board.getDiagArrow(x,y));
				}
				System.out.print(' ');
			}
			// next line for the next row
			System.out.print('\n');
		}
	}

	@Override
	public void getHumanMove() {
		// the direc will be overridden by the end of the method
		Direction direc = null;
		
		// get a valid move
		boolean done = false;
		while(! done) {
			// prompt and read
			System.out.print("Player " + whoseTurn +", enter move: ");
			int move = scanner.nextInt();
			// if input is not meaningless
			if(move > 0 && move < 10 && move != 5) {
				direc = Direction.from_num_pad(move);
				// if everything goes well, keep this move and leave the loop
				if(board.checkMove(direc)) {
					done = true;
				// if the move is invalid, try again
				} else {
					System.out.println("Invalad move.");
				}
			// if it is meaningless
			} else {
				System.out.println("Invalad input.");
			}
		}
		// move
		board.move(direc);
		
		// if the player rebounds (lands on a previous part of the path of the piece) make another move
		if(board.isRebound()) {
			rebound = true;
			board.setTurn(whoseTurn);
		} else {
			updateTurn();
		}
	}

	@Override
	void getComputerMove() {
		// make a smart move
		getSmartComputerMove();
		// if move ended with a rebound, undo the getSmartComputerMove's turn update
		if(board.isRebound()) {
			updateTurn();
			rebound = true;
		} else
			board.setTurn(whoseTurn);
	}
	
	/**
	 * switch the whoseTurn field and make the board match that
	 */
	void updateTurn() {
		whoseTurn = 3 - whoseTurn;
		board.setTurn(whoseTurn);
	}

	@Override
	int whoWon() {
		return board.checkGoal();
	}

	@Override
	boolean isGameOver() {
		return isGameOver(board);
	}

	boolean isGameOver(LinkBoard localBoard) {
		// 0 means no goal has been entered
		if(localBoard.checkGoal() != 0)
			return true;
		// are there moves left
		if(! localBoard.hasOpen())
			return true;
		return false;
	}

	@Override
	int whoseTurn(Object localBoard) {
		return ((LinkBoard)(localBoard)).getTurn();
	}

	@Override
	int whoWon(Object board) {
		return ((LinkBoard)(board)).checkGoal();
	}

	/**
	 * make an array of all possible results of possible moves
	 * rebounds will be counted as a separate move
	 */
	@Override
	Object[] getChildren(Object b) {
		// cast the board
		LinkBoard localBoard = (LinkBoard)b;
		
		// return an empty array if this is the end of the game
		if(isGameOver(localBoard)) {
			return new LinkBoard[0];
		}
		
		// the array from this will be returned
		DSArrayList<LinkBoard> children = new DSArrayList<LinkBoard>();

		// for each direction
		for(int i = 0; i < 8; i++) {
			if(localBoard.checkMove(new Direction(i))) {
				// copy the board
				LinkBoard child = localBoard.copy();
				// make a move in the board
				child.move(new Direction(i));
				// stick this in the array list of results
				children.add(child);
				// if this is not a continuation of a rebound, change the boards turn
				if(! child.isRebound()) {
					child.switchTurn();
				}
			}
		}
		// return an array of the results
		return children.toArray();
	}
	
	public int hruistic(Object board) {
		return ((LinkBoard)(board)).hruisitc();
	}
	
	public String toString(Object oboard) {
		LinkBoard localBoard = (LinkBoard)(oboard);
		return localBoard.toString();
	}
}
