package ds2016;

/**
 * all the messy code for selecting the right char for a given situation
 * @author jph
 *
 */
public class LinkChars {
	// the char for "nothing is here"
	private static final char BLANK = ' ';
	// chars for empty spaces and the space the pawn is in
	private static final char DOT = '○';
	private static final char PLAYER = '●';
	
	// chars for every direction
	private static final int BASESTRAIT = '\u2190';
	private static final int BASEDIAG = '\u2196'; // DIAG stands for diagonal
	private static final int BASEDOUBLE = '\u2927';
	
	private static final char LEFT = (char)(BASESTRAIT+0);
	private static final char UP = (char)(BASESTRAIT+1);
	private static final char RIGHT = (char)(BASESTRAIT+2);
	private static final char DOWN = (char)(BASESTRAIT+3);
	
	private static final char UPLEFT = (char)(BASEDIAG+0);
	private static final char UPRIGHT = (char)(BASEDIAG+1);
	private static final char DOWNRIGHT = (char)(BASEDIAG+2);
	private static final char DOWNLEFT = (char)(BASEDIAG+3);
	
	private static final char DOUBLEUP = (char)(BASEDOUBLE+0);
	//private static final char DOUBLERIGHT = (char)(BASEDOUBLE+1);
	//private static final char DOUBLEDOWN = (char)(BASEDOUBLE+2);
	//private static final char DOUBLELEFT = (char)(BASEDOUBLE+3);
	
	//private static final char DOUBLEUP = 'X';
	private static final char DOUBLERIGHT = 'X';
	private static final char DOUBLEDOWN = 'X';
	private static final char DOUBLELEFT = 'X';
	
	// this character will be used if something goes wrong in some methods
	private static final char ERROR = '!';
	
	// the goal characters
	public static final char GOAL1 = '⁀';
	public static final char GOAL2 = '‿';
	
	/**
	 * translate from the index of a direction in a LinkSpace to a unicode charicter
	 * @param idx
	 * @return 
	 */
	public int fromIdx(int idx) {
		// the idx values are designed to match the unicode arrow values
		// as much as possible
		if(idx < 4) {
			return idx;
		} else {
			return idx+2;
		}
	}

	public static char getHorArrow(LinkType link_type) {
		// no link
		if(link_type == LinkType.NONE) {
			return BLANK;
		// a link from this space (which is to the left to the other one)
		} else if(link_type == LinkType.FROM) {
			return RIGHT;
		// a link from the other space
		} else {
			return LEFT;
		}
	}

	public static char getVerArrow(LinkType link_type) {
		// no link
		if(link_type == LinkType.NONE) {
			return BLANK;
		// a link from this space (which is bellow above the other one)
		} else if(link_type == LinkType.FROM) {
			return DOWN;
		// a link from the other space
		} else {
			return UP;
		}
	}

	public static char getPlayerChar() {
		return PLAYER;
	}

	public static char getDotChar() {
		return DOT;
	}

	public static char getDiagonal(LinkType left_link, LinkType right_link) {
		// see LinkType for an explination of what is going on here
		// DOUBLE something is two diagonal arrows pointing in that general direction
		// since there is no way for the cases to fall thew to each other
		// I am leaving out the break statements for code cleanlyness
		switch (left_link) {
			case NONE: switch (right_link) {
				case NONE: return BLANK;
				case FROM: return DOWNLEFT;
				case TO: return UPRIGHT;
				default: return ERROR;
			}
			case FROM: switch (right_link) {
				case NONE: return DOWNRIGHT;
				case FROM: return DOUBLEDOWN;
				case TO: return DOUBLERIGHT;
				default: return ERROR;
			}
			case TO: switch (right_link) {
				case NONE: return UPLEFT;
				case FROM: return DOUBLELEFT;
				case TO: return DOUBLEUP;
				default: return ERROR;
			}
			default: return ERROR;
		}
	}
}
