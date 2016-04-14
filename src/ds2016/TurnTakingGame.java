/**
 * TurnTakingGame class
 *
 * Defines functionality common to all
 * turn-taking games
 */

package ds2016;

abstract class TurnTakingGame{
	int numberOfPlayers;

	abstract void getPlayerMove();
	abstract boolean isGameOver();
	abstract void doEndgameStuff();
	abstract int  whoseTurn(Object localBoard);
	abstract int  whoWon(Object board);

	/**
	 * Builds a game tree and returns the DSNode that 
	 * is the root of that tree
	 */
	DSNode buildTree(Object board){
		// root is the DSNode we we return
		DSNode root = new DSNode< Object >();
		//root.board = board; // bad, violates encapsulation
		root.setBoard(board); // good

		// We get all boards obtainable from this board in 1 move
		Object[] ch = getChildren(board);
		// System.out.println(ch.length);
		for(int i = 0; i < ch.length; i++){
			DSNode childNode = buildTree(ch[i]);
			root.addChild(childNode);
		}
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
		int winner = 3 - whoseTurn;
		for(int i = 0; i < root.getNumChildren(); i++){
			evaluateTree((DSNode)(root.getChildren().get(i)));  // Recursive call
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

}
