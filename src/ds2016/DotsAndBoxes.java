package ds2016;

import java.util.Scanner;

/**
 * @author Jack Baumann
 * Plays a game of Dots and Boxes versus a computer or between two humans.
 */
public class DotsAndBoxes extends SemiAlternatingGame {

	char[][] board;
	int boardSize = 3;  //(boardSize + 1) / 2 = the number of dots on each row and column\
	byte difficulty = 1; // 1 = getDumbComputerMove(), 2 = getSmartComputerMove()
	Scanner scan = new Scanner(System.in);

	public DotsAndBoxes() {
		board = new char[boardSize][boardSize];
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				if((row % 2 == 0) && (col % 2 == 0)){
					board[row][col] = '*';
				}
				else if((row % 2 == 1) && (col % 2 == 1)){
					board[row][col] = '0';
				}
				else{
					board[row][col] = ' ';
				}
			}
		}
	}

	@Override
	boolean isGameOver() {
		for(char[] i: board)
			for(char c: i)
				if(c == ' ')
					return false;
		return true;
	}

	@Override
	int whoseTurn(Object localBoard) {
		char[][] b = (char[][]) localBoard;
		int count = 0;
		for(char[] i: b){
			for(char c: i){
				if(c == '-' || c == '|')
					count++;
			}
		}
		return count%2+1;
	}

	@Override
	int whoWon(Object localBoard) {
		char[][] b = (char[][]) localBoard;
		int p1 = 0, p2 = 0;
		for(char[] i: b){
			for(char c: i){
				if(c == '1')
					p1++;
				else if(c == '2')
					p2++;
			}
		}
		if(p1 > p2)
			return 1;
		else if(p1 < p2)
			return 2;
		return 0;
	}

	@Override
	Object[] getChildren(Object board) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	void setBoard(Object nb) {
		board = (char[][]) nb;
	}

	@Override
	Object getBoard() {
		return board;
	}

	@Override
	void drawBoard() {
		int y = 2;
		System.out.print("  ");
		for(int x = 1; x <= boardSize; x++)
			System.out.print(x);
		System.out.print("\n1 ");
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				System.out.print(board[row][col]);
				if(col == boardSize - 1 && y <= boardSize){
					System.out.print("\n" + y++ + " ");
				}
				else if(col == boardSize - 1){
					System.out.print("\n  ");
				}
			}
		}
		System.out.println("");
	}

	@Override
	void getHumanMove() {
		int r, c;
		System.out.print("Please enter a row to make a move at: ");
		r = scan.nextInt();
		System.out.print("Please enter a column to make a move at: ");
		c = scan.nextInt();
		while(board[--r][--c] != ' '){
			System.out.println("That  move was invalid, please try again.  The numbers above and to the left of the board correspond to the proper values.");
			System.out.print("Please enter a row to make a move at: ");
			r = scan.nextInt();
			System.out.print("Please enter a column to make a move at: ");
			c = scan.nextInt();
		}
		if(r % 2 == 0)
			board[r][c] = '-';
		else
			board[r][c] = '|';
		whoseTurn = 3 - whoseTurn;
	}

	@Override
	void getComputerMove() {
		do{
			if(difficulty == 1)
				getDumbComputerMove();
			else
				getSmartComputerMove();
		}while(playAgain(board, whoseTurn));
		whoseTurn = 3 - whoseTurn;
	}
	
	/*
	 * Updates the 0s on the board to 1s and 2s and returns true if the last play filled in a block.
	 * Otherwise it returns false so that the next player can play. 
	 */
	@Override
	public boolean playAgain(Object localBoard, int player) {
		char[][] b = (char[][]) localBoard;
		updateBoard();
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				if(board[row][col] != b[row][col]){
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * Updates the 0s on the board to 1s and 2s depending on whoseTurn it is
	 */
	private void updateBoard() {
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				if(board[row][col] == '0'){
					if(board[row][col-1] == '|' && board[row][col+1] == '|' && board[row-1][col] == '-' && board[row+1][col] == '-'){
						if(whoseTurn == 1)
							board[row][col] = '1';
						else
							board[row][col] = '2';
					}
				}
			}
		}
	}

	/*
	 * Updates the 0s on the given board to 1s and 2s depending on the given player
	 */
	public Object updateBoard(Object localBoard, int player) {
		char[][] b = (char[][]) localBoard;
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				if(b[row][col] == '0'){
					if(b[row][col-1] == '|' && b[row][col+1] == '|' && b[row-1][col] == '-' && b[row+1][col] == '-'){
						if(player == 1)
							b[row][col] = '1';
						else
							b[row][col] = '2';
					}
				}
			}
		}
		return b;
	}

	/*
	 * Takes a random available move from all of the available moves.
	 */
	private void getDumbComputerMove() {
		DSArrayList<int[]> l = new DSArrayList<int[]>();
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				if(board[row][col] == ' '){
					int[] c = new int[2];
					c[0] = row;
					c[1] = col;
					l.add(c);
				}
			}
		}
		int[] r = l.get((int)(Math.random() * l.getSize()));
		if(r[0] % 2 == 0)
			board[r[0]][r[1]] = '-';
		else
			board[r[0]][r[1]] = '|';
	}

	@Override
	int whoWon() {
		int p1 = 0, p2 = 0;
		for(char[] i: board){
			for(char c: i){
				if(c == '1')
					p1++;
				else if(c == '2')
					p2++;
			}
		}
		if(p1 > p2)
			return 1;
		else if(p1 < p2)
			return 2;
		return 0;
	}
}