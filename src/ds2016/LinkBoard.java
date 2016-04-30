package ds2016;

import java.util.Arrays;

/**
 * stores the data for a game of link
 * 
 * @author jph
 *
 */
public class LinkBoard {
	
	// backing[x][y] is the space at (x,y) on the board
	private LinkSpace[][] backing;

	// where the pawn is
	private int x;
	private int y;
	
	// whose turn it is
	private int turn;
	
	// where the goals are placed, goals[i] is the i'th players goal
	// player one's goal should be at the top
	private static int[][] goals;
	
	// the size of the board
	private int width;
	private int height;
	
	/**
	 * default initiate
	 */
	public LinkBoard(int init_width, int init_height) {
		width = init_width;
		height = init_height;
		backing = new LinkSpace[width][height];
		x = width/2;
		y = height/2;
		turn = 1;
		initBoard();
		goals = new int[][] {{}, {width/2,0}, {width/2,height-1}};
	}
	
	/**
	 * initiate a board from known field values
	 * @param init_backing is the matrix which repents the board
	 * @param init_x is the starting x of the pawn
	 * @param init_y is the starting y of the pawn
	 * @param init_turn is the current turn
	 */
	public LinkBoard(LinkSpace[][] init_backing, int init_x, int init_y, int init_turn, int[][] init_goals) {
		// copy the board
		backing = init_backing;
		// set the other values
		x = init_x;
		y = init_y;
		turn = init_turn;
		goals = init_goals;
		width = backing.length;
		height = backing[0].length;
	}
	
	/**
	 * put blank LinkSpaces into all spaces
	 */
	private void initBoard() {
		for(int x = 0; x < backing.length; x++) {
			for(int y = 0; y < backing[0].length; y++) {
				backing[x][y] = new LinkSpace();
			}
		}
	}

	/**
	 * Determine if a move is valid
	 * @param direc is the Direction of the move
	 * @return whether the move is valid
	 */
	public boolean checkMove(Direction direc) {
		// if the pawn has already moved between this space and the end space, no sale
		if(backing[x][y].isLinked(direc))
			return false;
		// if the x value is off the board, no sale
		if(direc.endX(x) < 0 || direc.endX(x) >= backing.length)
			return false;
		// if the y value is off the board, nope
		if(direc.endY(y) < 0 || direc.endY(y) >= backing[0].length)
			return false;
		// the move passes
		return true;
	}
	
	/**
	 * perform a move
	 * @param direc is the direction of the move
	 */
	public void move(Direction direc) {
		// alert this space that this move has been made
		backing[x][y].LinkFrom(direc);
		// change the pawn position
		x = direc.endX(x);
		y = direc.endY(y);
		// tell the new space that this move has been made
		backing[x][y].LinkTo(direc.reverse());
	}
	
	/**
	 * check if a goal has been made
	 * @return 0 if not, whose goal it is (as an int) if one has been made
	 */
	public int checkGoal() {
		// check if the pawn is in any goal
		for(int i = 1; i < goals.length; i++) {
			if(x == goals[i][0] && y == goals[i][1]) {
				// return the goal it is in
				return i;
			}
		}
		// no goal found
		return 0;
	}

	public int getTurn() {
		return turn;
	}

	/**
	 * make a copy of this board
	 * @return the copy
	 */
	public LinkBoard copy() {
		LinkSpace[][] new_backing = new LinkSpace[getWidth()][getHeight()];
		for(int x = 0; x < getWidth(); x++) {
			for(int y = 0; y < getHeight(); y++) {
				new_backing[x][y] = backing[x][y].copy();
			}
		}
		// the goals will not be changed, so reusing this objects goals array is fine
		return new LinkBoard(new_backing,x,y,turn,goals);
	}

	/**
	 * check if there should be a rebound
	 * @return the result
	 */
	public boolean isRebound() {
		// their should be a rebound if this space has already been occupied by the pawn
		// which means the link made when moving here is not the only one
		// which means their are at least two links on the current space
		if(x == 0 && y == 2) {
			x = x;
		}
		return backing[x][y].hasLinks(2);
	}
	
	/**
	 * check if there are any open moves
	 * @return the result
	 */
	public boolean hasOpen() {
		return backing[x][y].hasOpen();
	}

	/**
	 * make it the other players turn
	 */
	public void switchTurn() {
		turn = 3 - turn;
	}
	
	/**
	 * @return the width of the board
	 */
	public int getWidth() {
		return backing.length;
	}
	
	/**
	 * @return the height of the board
	 */
	public int getHeight() {
		return backing[0].length;
	}

	/**
	 * @param get_x is the x of the space under consideration 
	 * @param get_y is the y of that space
	 * @return -1 if that space is not linked to the one to its right, the idx of the arrow to be drawn if it is
	 */
	public char getHorArrow(int get_x, int get_y) {
		LinkSpace spc = backing[get_x][get_y];
		return spc.getHorArrow();
	}

	/**
	 * @param get_x is the x of the space under consideration 
	 * @param get_y is the y of that space
	 * @return -1 if that space is not linked to the one below it, the idx of the arrow to be drawn if it is
	 */
	public char getVerArrow(int get_x, int get_y) {
		LinkSpace spc = backing[get_x][get_y];
		return spc.getVerArrow();
	}
	
	/**
	 * @return the pawn's x position
	 */
	public int plrX() {
		return x;
	}
	
	/**
	 * @return the pawn's y position
	 */
	public int plrY() {
		return y;
	}

	/**
	 * @param whoseTurn it is
	 */
	public void setTurn(int whoseTurn) {
		turn = whoseTurn;
	}

	public char getDiagArrow(int get_x, int get_y) {
		// the diagonal arrow codes for (get_x,get_y) and the space to the right of that
		LinkSpace spc1 = backing[get_x][get_y];
		LinkSpace spc2 = backing[get_x+1][get_y];
		// making them into one code
		return LinkChars.getDiagonal(spc1.downRightLinkType(),spc2.downLeftLinkType());
	}

	public char getDotChar(int get_x,int get_y) {
		// if the pawn is on that space
		if(get_x == x && get_y == y) {
			return LinkChars.getPlayerChar();
		} else {
			return LinkChars.getDotChar();
		}
	}
	
	public int blankCount() {
		return backing[x][y].blankCount();
	}

	public int[] blankLinks() {
		return backing[x][y].blankLinks();
	}

	public int hruisitc() {
		return -(x-goals[1][1]);
	}
	
	public LinkBoard inhumanCopy() {
		LinkBoard result = copy();
		result.inhumanize();
		return result;
	}
	
	public void inhumanize() {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				backing[x][y].inhumanize();
			}
		}
	}
	
	/**
	 * return a String with enough information to recreate all of the game except the graphics
	 */
	public String toString() {
		// must contain whose turn it is and were the players are
		// the numbers should never be out of range for chars
		String result = "" + (char)(turn) + (char)(x) + (char)(y);
		
		// the data from the spaces
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				result += backing[x][y].toChar();
			}
		}
		
		return result;
	}

	public int getGoal1X() {
		return goals[1][0];
	}
	
	public int getGoal2X() {
		return goals[2][0];
	}
}
