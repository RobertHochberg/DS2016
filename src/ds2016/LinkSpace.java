package ds2016;

/**
 * a space for the game of link
 * tracks where it has been moved to from and where the pawn has moved from it
 * 
 * @author jph
 *
 */
public class LinkSpace {
	
	// which neighbors this has links with
	// 0 means no link
	// 1 means a link from this to the neighbor
	// 2 means a link from the neighbor to this
	private LinkType[] links;
	
	/**
	 * make a blank LinkSpace
	 */
	public LinkSpace() {
		// a link value for the neighbors horizontally, vertically, and diagonally from this
		links = new LinkType[8];
		// and set them all to no link
		for(int i = 0; i < links.length; i++) {
			links[i] = LinkType.NONE;
		}
	}
	
	public LinkSpace(LinkType[] init_links) {
		links = init_links;
	}
	
	/**
	 * @param direc is the Direction to be checked
	 * @return is whether their is a link to the space in that direction
	 */
	public boolean isLinked(Direction direc) {
		return (links[direc.asIdx()] != LinkType.NONE);
	}
	
	/**
	 * make a link from this space in direc
	 */
	public void LinkFrom(Direction direc) {
		links[direc.asIdx()] = LinkType.FROM;
	}
	
	/**
	 * make a link to this space in direc
	 */
	public void LinkTo(Direction direc) {
		links[direc.asIdx()] = LinkType.TO;
	}
	
	/**
	 * check whether this space has ever been occupied
	 */
	public boolean hasLinks() {
		for(LinkType l : links) {
			if(l != LinkType.NONE)
				return true;
		}
		return false;
	}
	
	/**
	 * check that at least min links are on this space
	 */
	public boolean hasLinks(int min) {
		for(LinkType l : links) {
			if(l != LinkType.NONE)
				min --;
		}
		return (min <= 0);
	}
	
	/**
	 * count the links
	 */
	public int blankCount() {
		int result = 0;
		for(LinkType l : links) {
			if(l == LinkType.NONE)
				result++;
		}
		return  result;
	}
	/**
	 * check if there are any neighboring spaces which have not been linked to this
	 */
	public boolean hasOpen() {
		for(LinkType l : links) {
			if(l == LinkType.NONE)
				return true;
		}
		return false;
	}
	
	public char getHorArrow() {
		LinkType link_type = links[Direction.RIGHT];
		return LinkChars.getHorArrow(link_type);
	}

	public char getVerArrow() {
		LinkType link_type = links[Direction.DOWN];
		return LinkChars.getVerArrow(link_type);
	}

	public LinkType downRightLinkType() {
		return links[Direction.DOWNRIGHT];
	}

	public LinkType downLeftLinkType() {
		return links[Direction.DOWNLEFT];
	}

	public int[] blankLinks() {
		int[] result = new int[blankCount()];
		int ri = 0;
		for(int i = 0; i < links.length; i++) {
			if(links[i] == LinkType.NONE) {
				result[ri] = i;
				ri++;
			}
		}
		return result;
	}

	public LinkSpace copy() {
		LinkType[] new_links = new LinkType[links.length];
		for(int i = 0; i < links.length; i++) {
			new_links[i] = links[i];
		}
		return new LinkSpace(new_links);
	}

	/**
	 * remove data which is only used for showing this to a human for hash mapping
	 */
	public void inhumanize() {
		for(int i = 0; i < links.length; i++) {
			if(links[i] != LinkType.NONE) {
				links[i] = LinkType.PRESENT;
			}
		}
	}
	
	public char toChar() {
		// return a char that maps to exactly one arrangement of links for right, downleft, downright, and down
		// the other directions will be handled by the other spaces
		int tempResult = 0;
		if(links[Direction.RIGHT] != LinkType.NONE) {
			tempResult += 1;
		}
		if(links[Direction.DOWNLEFT] != LinkType.NONE) {
			tempResult += 2;
		}
		if(links[Direction.DOWNRIGHT] != LinkType.NONE) {
			tempResult += 4;
		}
		if(links[Direction.DOWN] != LinkType.NONE) {
			tempResult += 8;
		}
		
		// tempResult will be small enough to work
		return (char)(tempResult);
	}
}
