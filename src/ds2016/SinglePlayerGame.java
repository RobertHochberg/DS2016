package ds2016;

abstract class SinglePlayerGame {
	int numberOfPlayers;

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
}
