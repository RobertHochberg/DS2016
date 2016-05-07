/**
 * TurnTakingGame class
 *
 * Defines functionality common to all
 * turn-taking games
 */

package src.ds2016;

import java.util.HashMap;

abstract class TurnTakingGame{
	int numberOfPlayers;

	abstract void getPlayerMove();
	abstract boolean isGameOver();
	abstract void doEndgameStuff();
	abstract int  whoseTurn(Object localBoard);
	abstract int  whoWon(Object board);
	abstract String toString(Object board);


	HashMap<String, DSNode> treeMap = new HashMap<String, DSNode>();

	/**
	 * Builds a game tree and returns the DSNode that 
	 * is the root of that tree
	 */
	DSNode buildTree(Object board, int depth){
		String boardString = toString(board);

		// Check to see if we've already processed this string.
		if(treeMap.containsKey(boardString)){
			return treeMap.get(boardString);
		}

		// root is the DSNode we we return
		DSNode root = new DSNode< Object >();
		//root.board = board; // bad, violates encapsulation
		root.setBoard(board); // good

		// We get all boards obtainable from this board in 1 move
		if (depth != 0) {
			Object[] ch = getChildren(board);
			// System.out.println(ch.length);
			for(int i = 0; i < ch.length; i++){
				DSNode childNode = buildTree(ch[i], depth - 1);
				root.addChild(childNode);
			}
		}

		// Save this work in case we see this board again.
		treeMap.put(boardString, root);

		return root;
	}

	/**
	 * Evaluates all nodes in the tree for who wins
	 * from that position.
	 *
	 * Precondition: root is the root node of a tree
	 *     that has been fully built, so that all 
	 *     leaf nodes in the tree have well-defined
	 *     winners, or are ties.
	 */
	int evaluateTree(DSNode root){
		if(root.getNumChildren() == 0) {
			root.setWinner(whoWon(root.getBoard()));
			return root.getWinner();
		} 

		// Assume for now 2 players
		int whoseTurn = whoseTurn(root.getBoard());
		int winner = 0;
		//int winner = 3 - whoseTurn;
		for(int i = 0; i < root.getNumChildren(); i++){
			if (getNumGridPoints() <= 5) {
				evaluateTree((DSNode)(root.getChildren().get(i)));  // Recursive call
			} else evaluateHeuristic((DSNode)(root.getChildren().get(i))); 
			if(whoseTurn == 1){
				if(winner == 2)
					winner = ((DSNode) root.getChildren().get(i)).getWinner();
				else if(winner == 0 && 
						((DSNode)(root.getChildren().get(i))).getWinner() == 1)
					winner = 1;
			} else {
				if(winner == 1)
					winner = ((DSNode)(root.getChildren().get(i))).getWinner();
				else if(winner == 0 && 
						((DSNode)(root.getChildren().get(i))).getWinner() == 2)
					winner = 2;
			}
		} // end of looping over children

		root.setWinner(winner);
		return winner;
	}

	// returns an array of boards
	abstract Object[] getChildren(Object board);
	abstract int evaluateHeuristic(DSNode board);
	abstract int getNumGridPoints();
}