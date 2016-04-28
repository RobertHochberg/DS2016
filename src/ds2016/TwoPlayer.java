package ds2015;

import java.util.HashMap;
import java.util.function.Function;

import ds2015.Game.WinState;
import ds2015.Othello.BoardAndTurn;

/**
 * TwoPlayer class
 * Abstract class describing all turn-based
 * two-player games
 */

public abstract class TwoPlayer extends Game {
	/**
	 * turn describes whose turn it is, 1 or 2
	 */
	int turn;

	/**
	 * The players of the game
	 */
	Player[] playah;

	// Constructor
	public TwoPlayer(){
		super();
		numPlayers = 2;

		// We create "3" players so that we can refer to
		// playah[1] playah[2]. playah[0] is a "dummy."
		playah = new Player[3];

		// We have to actually build the Player objects, too...
		for(int i = 0; i < 3; i++)
			playah[i] = new Player();
	}
	
	/**
	 * A field of the TwoPlayer class 
	 * Maps Strings (representing boards) to their nodes in the tree
	 */
	HashMap<String, DSNode> nodeBucket = new HashMap<String, DSNode>();
	
	/**
	 * Turns any bucketable game board and turn into a String
	 * @param board
	 * @return a string representation of the board and turn
	 */
	abstract String stringify(Object board);

	/**
	 * Build the game tree to a given depth from a given board
	 *
	 * @param board is a game board
	 * @param depth is how far down the tree to build
	 *
	 * Fun fact: A tree is fully described by its root node
	 */
	DSNode<Object> treeify(Object board, int depth){
		// Check to see if we've already treeified this board
		String h = stringify(board);
		if(nodeBucket.containsKey(h))
			return nodeBucket.get(h);
		
		DSNode<Object> vader = new DSNode(board);
		vader.parent = null;

		// If we are at the bottom of our tree, just wrap the board
		// into a node and return
		if(depth != 0){
			// Otherwise, build the tree below this node
			vader.children = new DSArrayList<DSNode<Object>>();

			// treeify the children, and settle paternity
			DSArrayList<Object> luke = getChildren(board);
			for(int i = 0; i < luke.numItems; i++){
				DSNode<Object> n = treeify(luke.get(i), depth - 1);
				n.parent = vader;
				vader.children.add(n);
			}
		}
		nodeBucket.put(h, vader);
		return vader;
	}


	/**
	 * Create an array list of child board
	 *
	 * @param board  The input board, of which to find children
	 */
	abstract DSArrayList<Object> getChildren(Object board);

	/**
	 * Returns the value of a game board.
	 * @param myBoard the board to evaluate
	 * @return the value
	 * 
	 * Player 1 wants large values, Player 2 prefers small value.
	 */
	abstract int evalufyBoard(Object myBoard);

	/**
	 * Associates a node with its "evalufyNode" value 
	 */
	HashMap<DSNode, Integer> valueBucket = new HashMap<DSNode, Integer>();
	
	/**
	 * Computes the game value of the board within the DSNode
	 * 
	 * @param myNode The node to evaluate
	 * @return the value
	 * 
	 * If the node is a leaf of the tree, return the value of the
	 * board in the node. Otherwise, return the:
	 *    * highest value if it is Player 1's turn
	 *    *  lowest value if it is Player 2's turn.
	 *    
	 * Precondition: This DSNode is part of a tree
	 */
	int evalufyNode(DSNode<Object> myNode){
		if(valueBucket.containsKey(myNode))
			return valueBucket.get(myNode);
		
		int rv = 0;

		if(myNode.children == null || 
				myNode.children.numItems == 0 ) // leaf node
			rv = evalufyBoard(myNode.value);
		else{		// Internal node
			int numChildren = myNode.children.numItems;
			int turn = getTurn(myNode.value);
			rv = evalufyNode(myNode.children.get(0));
			for(int i = 1; i < numChildren; i++){
				int val = evalufyNode(myNode.children.get(i));
				if((turn == 1 && val > rv) || 
						(turn == 2 && val < rv))
					rv = val;
			}
		} 
		valueBucket.put(myNode, rv);
		return rv;
	}

	/**
	 * Computes the game value of the board within the DSNode
	 * 
	 * @param myNode The node to evaluate
	 * @param evalufier A function object that assigns a value to a board
	 * 
	 * @return the value
	 * 
	 * If the node is a leaf of the tree, return the value of the
	 * board in the node. Otherwise, return the:
	 *    * highest value if it is Player 1's turn
	 *    *  lowest value if it is Player 2's turn.
	 *    
	 * Precondition: This DSNode is part of a tree
	 */
	int evalufyNodeWithEvalufier(DSNode<Object> myNode,
			Function<Object, Integer> evalufier){
		int rv = 0;

		if(myNode.children == null || 
				myNode.children.numItems == 0 ) // leaf node
			rv = evalufier.apply(myNode.value);
		else{		// Internal node
			int numChildren = myNode.children.numItems;
			int turn = getTurn(myNode.value);
			rv = rv = evalufyNodeWithEvalufier(myNode.children.get(0), 
					evalufier);
			for(int i = 1; i < numChildren; i++){
				int val = evalufyNodeWithEvalufier(myNode.children.get(i), 
						evalufier);
				if((turn == 1 && val > rv) || 
						(turn == 2 && val < rv))
					rv = val;
			}
		} 
		
		return rv;
	}

	/**
	 * Asks the game subclass to tell us whose turn it is.
	 * 
	 * @param board
	 * @return 1 or 2 for whose turn it is
	 * 
	 * Returns 1 or 2 depending on the state of the board
	 */
	abstract int getTurn(Object board);

	int headToHead(Function<Object, Integer> p1, 
			Function<Object, Integer> p2, int numGames){

		// We count wins for Player 1 - wins for Player 2
		int numWins = 0;
		for(int i = 0; i < numGames; i++){
			WinState state = WinState.STILLGOING;
			while(state == WinState.STILLGOING){
				drawBoard();
				state = winCheck();
				if(state == WinState.STILLGOING)
					getHeadToHeadMove(p1, p2);
			}
			if(state == WinState.PLAYER1) numWins++;
			if(state == WinState.PLAYER2) numWins--;
			reset();
			System.out.println("So far: " + numWins);
		}
		return numWins;
	}
	
	abstract void getHeadToHeadMove(Function<Object, Integer> p1, 
			Function<Object, Integer> p2);
	
	/**
	 * Resets the game to its start state
	 */
	abstract void reset();

}
