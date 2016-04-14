/**
 * AlternatingGame class
 *
 * Defines functionality for games where two players take turns
 */

package ds2016;

abstract class AlternatingGame extends TurnTakingGame {
	int whoseTurn = 1;		// The player whose turn it is.

	// isHuman[1] is true if Player 1 is a human, false otherwise
	boolean[] isHuman = {false, true, false}; // initialize to human vs. computer 

	void playGame(){
		boolean gameOver = false;
		while(!gameOver){
			drawBoard();
			getPlayerMove();
			gameOver = isGameOver();
			if(gameOver)
				doEndgameStuff();
		}
		drawBoard();
	}

	void getPlayerMove(){
		if(isHuman[whoseTurn] == true)
			getHumanMove();
		else
			getComputerMove();
	}

	/**
	 * Gets the computer player's move
	 *
	 * Picks a winning move, if available.
	 * Otherwise, pick a tie move.
	 *
	 */
	void getSmartComputerMove(){
		Object board = getBoard();
		Object[] children = getChildren(board);
		Object newBoard = children[0];
		
		// Assume for now 2 players
		int winner = 3 - whoseTurn;
		for(int i = 0; i < children.length; i++){
			DSNode childTree = buildTree(children[i]);
			int childVal = evaluateTree(childTree);  // Recursive call
			if(whoseTurn == 1){
				if(winner == 2 && childVal != 2){
					winner = childVal;
					newBoard = children[i];
				}
				else if(winner == 0 && childVal == 1){
					winner = childVal;
					newBoard = children[i];
				}
			} else 
				if(winner == 1 && childVal != 1){
					winner = childVal;
					newBoard = children[i];
				}
				else if(winner == 0 && childVal == 2){
					winner = childVal;
					newBoard = children[i];
				}
		} // end of looping over children
		
		if(winner != 0)
			System.out.println("Player " + winner + " has the win!");
		
		// board = newBoard; // This won't work right.
		// We make our move by copying the newBoard into board
		setBoard(newBoard);
		whoseTurn = 3 - whoseTurn;
	}
	
	
	abstract void setBoard(Object nb);
	abstract Object getBoard();

	/**
	 * Simply declares who won, if anybody.
	 */
	void doEndgameStuff(){
		int winner = whoWon();
		if(winner == 0)
			System.out.println("It was a tie!");
		else
			System.out.printf("Player %d won the game\n", winner);
	}




	abstract void drawBoard();
	abstract void getHumanMove();
	abstract void getComputerMove();
	abstract int  whoWon();
}
