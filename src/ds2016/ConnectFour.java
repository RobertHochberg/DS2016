package ds2016;
//Author: Michael Bolot DS2016
import java.util.Scanner;

public class ConnectFour extends AlternatingGame {
	//Fields
	char[][] board = new char[7][6];
	char playerOneCharacter = 'X'; // used from tictactoe for simplicities' sake
	char playerTwoCharacter = 'O';
	
	Scanner scanner;
	

	public ConnectFour() {
		scanner = new Scanner(System.in);
		for(int i = 0; i <=6; i++){
			for(int j = 0; j <= 5; j++){
				board[i][j] = (char) ('0' + i + 1);
			}
		}
	}

	@Override
	void setBoard(Object nb) {
		board = (char[][])nb;
	}

	@Override
	Object getBoard() {
		return board;
	}

	@Override
	void drawBoard() {
		// TODO Auto-generated method stub
		System.out.println("---------------");
		System.out.println("|" + board[0][0] + "|" + board[1][0] + "|" + board[2][0] + "|" + board[3][0] + "|" + board[4][0] + "|" + board[5][0] + "|" + board[6][0] + "|");
		System.out.println("---------------");
		System.out.println("|" + board[0][1] + "|" + board[1][1] + "|" + board[2][1] + "|" + board[3][1] + "|" + board[4][1] + "|" + board[5][1] + "|" + board[6][1] + "|");
		System.out.println("---------------");
		System.out.println("|" + board[0][2] + "|" + board[1][2] + "|" + board[2][2] + "|" + board[3][2] + "|" + board[4][2] + "|" + board[5][2] + "|" + board[6][2] + "|");
		System.out.println("---------------");
		System.out.println("|" + board[0][3] + "|" + board[1][3] + "|" + board[2][3] + "|" + board[3][3] + "|" + board[4][3] + "|" + board[5][3] + "|" + board[6][3] + "|");
		System.out.println("---------------");
		System.out.println("|" + board[0][4] + "|" + board[1][4] + "|" + board[2][4] + "|" + board[3][4] + "|" + board[4][4] + "|" + board[5][4] + "|" + board[6][4] + "|");
		System.out.println("---------------");
		System.out.println("|" + board[0][5] + "|" + board[1][5] + "|" + board[2][5] + "|" + board[3][5] + "|" + board[4][5] + "|" + board[5][5] + "|" + board[6][5] + "|");
		System.out.println("---------------");
	}

	@Override
	void getHumanMove() {
		System.out.println("Pick a column:");
		if(whoWon(board) != 0){
			System.out.println("Player " + whoWon(board) +" has won!");
		}
		int theMove = scanner.nextInt();
		char[] theArray = new char[6];
		if (theMove < 8){
			theArray = board[theMove - 1];
		}
		else{
			System.out.println("That is not a valid move, please enter an int between 1 and 7");
			getHumanMove();
		}
		
		if(isArrayFull(theArray) == false && whoWon(board) == 0){
		for(int i = 0; i <= 5; i++){
			if(theArray[i] == playerOneCharacter){
				if (whoseTurn(board) == 1){
					theArray[i - 1] = playerOneCharacter;
					break;
				}
				else{
					theArray[i - 1] = playerTwoCharacter;
					break;
				}
			}
			else if(theArray[i] == playerTwoCharacter){
				if (whoseTurn(board) == 1){
					theArray[i - 1] = playerOneCharacter;
					break;
				}
				else{
					theArray[i - 1] = playerTwoCharacter;
					break;
				}
			}
			else if (i == 5 && theArray[5] != playerOneCharacter && theArray[5] != playerTwoCharacter){
				if (whoseTurn(board) == 1){
					theArray[5] = playerOneCharacter;
					break;
				}
				else{
					theArray[5] = playerTwoCharacter;
					break;
				}
			}
		}
		whoseTurn = 3 - whoseTurn;
		}
		else{
			if(isArrayFull(theArray) == true){
			System.out.println("That collumn is full, pick another");
			getHumanMove();
			}
			else{
				System.out.println("Player " + whoWon(board) +" has won!");
			}
		}
	}
	
	DSNode buildTree(Object board, int depth){
		// root is the DSNode we we return
		DSNode root = new DSNode< Object >();
		//root.board = board; // bad, violates encapsulation
		root.setBoard(board); // good

		// We get all boards obtainable from this board in 1 move
		Object[] ch = getChildren(board);
		// System.out.println(ch.length);
		if(depth == 0){
			return root;
		}
		
		for(int i = 0; i < ch.length; i++){
			DSNode childNode = buildTree(ch[i] , depth - 1);
			root.addChild(childNode);
		}
		return root;
	}

	@Override
	void getComputerMove() {
		
	}

	@Override
	int whoWon() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	int whoseTurn(Object localBoard) {
		board = (char[][])localBoard;
		int totalMoves = 0;
		for (int i = 0; i <= 5; i++){
			if (board[0][i] == playerOneCharacter){
				totalMoves += 1;
			}
			if (board[0][i] == playerTwoCharacter){
				totalMoves += 1;
			}	
		}
		
		for (int i = 0; i <= 5; i++){
			if (board[1][i] == playerOneCharacter){
				totalMoves += 1;
			}
			if (board[1][i] == playerTwoCharacter){
				totalMoves += 1;
			}	
		}
		
		for (int i = 0; i <= 5; i++){
			if (board[2][i] == playerOneCharacter){
				totalMoves += 1;
			}
			if (board[2][i] == playerTwoCharacter){
				totalMoves += 1;
			}	
		}
		
		for (int i = 0; i <= 5; i++){
			if (board[3][i] == playerOneCharacter){
				totalMoves += 1;
			}
			if (board[3][i] == playerTwoCharacter){
				totalMoves += 1;
			}	
		}
		
		for (int i = 0; i <= 5; i++){
			if (board[4][i] == playerOneCharacter){
				totalMoves += 1;
			}
			if (board[4][i] == playerTwoCharacter){
				totalMoves += 1;
			}	
		}
		
		for (int i = 0; i <= 5; i++){
			if (board[5][i] == playerOneCharacter){
				totalMoves += 1;
			}
			if (board[5][i] == playerTwoCharacter){
				totalMoves += 1;
			}	
		}
		for (int i = 0; i <= 5; i++){
			if (board[6][i] == playerOneCharacter){
				totalMoves += 1;
			}
			if (board[6][i] == playerTwoCharacter){
				totalMoves += 1;
			}	
		}
		System.out.println(totalMoves);
		if (totalMoves == 0){
			return 1;
		}
		
		if (totalMoves % 2 == 0){
			return 1;
		}
		
		else{
			return 2;
		}
	}

	@Override
	int whoWon(Object newBoard) {
		return whoWon((char[][])newBoard);
	}
	
	//1 for player one win
	//2 for player 2 win
	//0 for tie/no win
	int whoWon(char[][] localBoard){
		for(int i=0; i < 6; i++){
			for(int j=0; j < 6; j++){
				if(j < 2){
					if(localBoard[i][j] == localBoard[i][j + 1] &&localBoard[i][j + 1] == localBoard[i][j + 2] && localBoard[i][j + 2] == localBoard[i][j + 3])
					{
						if(localBoard[i][j] == playerOneCharacter){
							return 1;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							return 2;
						}
					}
				}
				else{
					if(localBoard[i][2] == localBoard[i][3] &&localBoard[i][3] == localBoard[i][4] && localBoard[i][4] == localBoard[i][5]){
						if(localBoard[i][2] == playerOneCharacter){
							return 1;
						}
						else if(localBoard[i][2] == playerTwoCharacter){
							return 2;
						}
					}
				}
			}
		}
		for(int i =0 ; i < 7; i++){
			for(int j= 0; j < 6; j++){
				if(i < 3){
					if(localBoard[i][j] == localBoard[i + 1][j] &&localBoard[i + 1][j] == localBoard[i + 2][j] && localBoard[i + 2][j] == localBoard[i + 3][j])
					{
						if(localBoard[i][j] == playerOneCharacter){
							return 1;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							return 2;
						}
					}
				}
				else{
					if(localBoard[3][j] == localBoard[4][j] &&localBoard[4][j] == localBoard[5][j] && localBoard[5][j] == localBoard[6][j]){
						if(localBoard[3][j] == playerOneCharacter){
							return 1;
						}
						else if(localBoard[3][j] == playerTwoCharacter){
							return 2;
						}
					}
				}
			}
		}
		for(int i =0 ; i < 7; i++){
			for(int j= 0; j < 6; j++){
				if(i < 3 && j > 3){
					if(localBoard[i][j] == localBoard[i + 1][j - 1] &&localBoard[i + 1][j - 1] == localBoard[i + 2][j - 2] && localBoard[i + 2][j - 2] == localBoard[i + 3][j - 3])
					{
						if(localBoard[i][j] == playerOneCharacter){
							return 1;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							return 2;
						}
					}
				}
				else{
					if(localBoard[3][3] == localBoard[4][2] &&localBoard[4][2] == localBoard[5][1] && localBoard[5][1] == localBoard[6][0]){
						if(localBoard[3][2] == playerOneCharacter){
							return 1;
						}
						else if(localBoard[3][2] == playerTwoCharacter){
							return 2;
						}
					}
				}
			}
		}
		for(int i =0 ; i < 7; i++){
			for(int j= 0; j < 6; j++){
				if(i < 3 && j < 2){
					if(localBoard[i][j] == localBoard[i + 1][j + 1] &&localBoard[i + 1][j + 1] == localBoard[i + 2][j + 2] && localBoard[i + 2][j + 2] == localBoard[i + 3][j + 3])
					{
						if(localBoard[i][j] == playerOneCharacter){
							return 1;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							return 2;
						}
					}
				}
				else{
					if(localBoard[3][2] == localBoard[4][3] &&localBoard[4][3] == localBoard[5][4] && localBoard[5][4] == localBoard[6][5]){
						if(localBoard[3][2] == playerOneCharacter){
							return 1;
						}
						else if(localBoard[3][2] == playerTwoCharacter){
							return 2;
						}
					}
				}
			}
		}
		return 0;
	}
	
	boolean isArrayFull (char[] array){
		if (array[0] == playerOneCharacter){
			return true;
		}
		else if (array[0] == playerTwoCharacter){
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	Object[] getChildren(Object board) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	int evaluateTree(DSNode root){
		buildTree(root, 6);
		return 0;
	}
	
	//point system for the evaluate board Heuristic
	//designed for a computer that is player 2
	// A player one win state is given: -2000 points
	// A player 2 win state is given: +2000 points
	// These player states are absolutes, so x is set to these values if they occur, and then x is returned
	// A player one 3 state is worth 50 points
	// A player 2  3 state is worth 50 points
	// A player one 2 state is worth 10 points
	// A player 2 2 state is worth 10 points
	
	int evaluateBoard(char[][] localBoard){
		int y = 0;
		if(whoWon(localBoard) == 1){
			y = -2000;
			return y;
		}
		if(whoWon(localBoard) == 2){
			y = 2000;
			return y;
		}
		else{
			y = evaluateThreeWinState(localBoard) + evaluateTwoWinState(localBoard);
		}
		return y; 
	}
	
	int evaluateThreeWinState(char[][] localBoard){
		int x = 0;
		for(int i=0; i < 6; i++){
			for(int j=0; j < 6; j++){
				if(j < 3){
					if(localBoard[i][j] == localBoard[i][j + 1] &&localBoard[i][j + 1] == localBoard[i][j + 2] && localBoard[i][j + 3] == i)
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 50;
						}
					}
					else if(j > 0 && localBoard[i][j] == localBoard[i][j + 1] &&localBoard[i][j + 1] == localBoard[i][j + 2] && localBoard[i][j - 1] == i)
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 50;
						}
					}
				}
				else{
					if(localBoard[i][3] == localBoard[i][4] &&localBoard[i][4] == localBoard[i][5] && localBoard[i][2] == i){
						if(localBoard[i][3] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[i][3] == playerTwoCharacter){
							x = x + 50;
						}
					}
				}
			}
		}
		for(int i =0 ; i < 7; i++){
			for(int j= 0; j < 6; j++){
				if(i < 4){
					if(localBoard[i][j] == localBoard[i + 1][j] &&localBoard[i + 1][j] == localBoard[i + 2][j] && localBoard[i + 3][j] == i + 3)
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 50;
						}
					}
					else if(localBoard[i][j] == localBoard[i + 1][j] &&localBoard[i + 1][j] == localBoard[i + 2][j] && localBoard[i - 1][j] == i - 1)
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 50;
						}
					}
				}
				else{
					if(localBoard[4][j] == localBoard[5][j] &&localBoard[5][j] == localBoard[6][j] && localBoard[3][j] == 3){
						if(localBoard[4][j] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[3][j] == playerTwoCharacter){
							x = x + 50;
						}
					}
				}
			}
		}
		for(int i =0 ; i < 7; i++){
			for(int j= 0; j < 6; j++){
				if(i < 4 && j > 2){
					if(localBoard[i][j] == localBoard[i + 1][j - 1] &&localBoard[i + 1][j - 1] == localBoard[i + 2][j - 2] && localBoard[i + 3][j - 3] == i + 3)
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 50;
						}
					}
					else if(localBoard[i][j] == localBoard[i + 1][j - 1] &&localBoard[i + 1][j - 1] == localBoard[i + 2][j - 2] && localBoard[i - 1][j + 1] == i - 1)
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 50;
						}
					}
				}
				else{
					if(localBoard[4][2] == localBoard[5][1] &&localBoard[5][1] == localBoard[6][0] && localBoard[3][3] == 3){
						if(localBoard[3][2] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[3][2] == playerTwoCharacter){
							x = x + 50;
						}
					}
				}
			}
		}
		for(int i =0 ; i < 7; i++){
			for(int j= 0; j < 6; j++){
				if(i < 4 && j < 3){
					if(localBoard[i][j] == localBoard[i + 1][j + 1] &&localBoard[i + 1][j + 1] == localBoard[i + 2][j + 2] && localBoard[i - 1][j - 1] == i - 1)
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 50;
						}
					}
					else if(localBoard[i][j] == localBoard[i + 1][j + 1] &&localBoard[i + 1][j + 1] == localBoard[i + 2][j + 2] && localBoard[i + 1][j + 1] == i + 1)
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 50;
						}
					}
				}
				else{
					if(localBoard[4][3] == localBoard[5][4] &&localBoard[5][4] == localBoard[6][5] && localBoard[3][2] == 3){
						if(localBoard[3][2] == playerOneCharacter){
							x = x - 50;
						}
						else if(localBoard[3][2] == playerTwoCharacter){
							x = x + 50;
						}
					}
				}
			}
		}
		return x;
	}
	
	int evaluateTwoWinState(char[][] localBoard){
		int x = 0;
		for(int i=0; i < 6; i++){
			for(int j=0; j < 6; j++){
				if(j < 4){
					if(localBoard[i][j] == localBoard[i][j + 1] && localBoard[i][j + 2] == i)
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 10;
						}
					}
					else if(j > 0 && localBoard[i][j] == localBoard[i][j + 1] && localBoard[i][j - 1] == i)
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 10;
						}
					}
				}
				else{
					if(localBoard[i][4] == localBoard[i][5] && localBoard[i][3] == i){
						if(localBoard[i][4] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[i][4] == playerTwoCharacter){
							x = x + 10;
						}
					}
				}
			}
		}
		for(int i =0 ; i < 7; i++){
			for(int j= 0; j < 6; j++){
				if(i < 5){
					if(localBoard[i][j] == localBoard[i + 1][j] && localBoard[i + 2][j] == i + 2)
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 10;
						}
					}
					else if(localBoard[i][j] == localBoard[i + 1][j] && localBoard[i - 1][j] == i - 1)
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 10;
						}
					}
				}
				else{
					if(localBoard[5][j] == localBoard[6][j] && localBoard[4][j] == 4){
						if(localBoard[4][j] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[4][j] == playerTwoCharacter){
							x = x + 10;
						}
					}
				}
			}
		}
		for(int i =0 ; i < 7; i++){
			for(int j= 0; j < 6; j++){
				if(i < 5 && j > 1){
					if(localBoard[i][j] == localBoard[i + 1][j - 1] &&localBoard[i + 2][j - 2] == i + 2)
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 10;
						}
					}
					else if(localBoard[i][j] == localBoard[i + 1][j - 1] && localBoard[i - 1][j + 1] == i - 1)
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 10;
						}
					}
				}
				else{
					if(localBoard[5][1] == localBoard[6][0] && localBoard[3][3] == 3){
						if(localBoard[5][1] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[5][1] == playerTwoCharacter){
							x = x + 10;
						}
					}
				}
			}
		}
		for(int i =0 ; i < 7; i++){
			for(int j= 0; j < 6; j++){
				if(i < 5 && j < 4){
					if(localBoard[i][j] == localBoard[i + 1][j + 1] && localBoard[i - 1][j - 1] == i - 1)
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 10;
						}
					}
					else if(localBoard[i][j] == localBoard[i + 1][j + 1] &&localBoard[i + 2][j + 2] == i + 2)
					{
						if(localBoard[i][j] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[i][j] == playerTwoCharacter){
							x = x + 10;
						}
					}
				}
				else{
					if(localBoard[5][4] == localBoard[6][5] && localBoard[4][3] == 4){
						if(localBoard[5][4] == playerOneCharacter){
							x = x - 10;
						}
						else if(localBoard[5][4] == playerTwoCharacter){
							x = x + 10;
						}
					}
				}
			}
		}
		return x;
	}
}
