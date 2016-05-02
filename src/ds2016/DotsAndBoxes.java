package ds2016;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Jack Baumann
 * Plays a game of Dots and Boxes versus a computer or between two humans.
 */
public class DotsAndBoxes extends SemiAlternatingGame {

	char[][] board;
	int boardSize = 5;  //(boardSize + 1) / 2 = the number of dots on each row and column
	byte difficulty = 3; // 1 = getDumbComputerMove(), 2 = getSmartComputerMove(), 3 = getHeuristicComputerMove()
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
		board[0][0] = '1';
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
		/*char[][] b = (char[][]) localBoard;
		int count = 0;
		for(char[] i: b){
			for(char c: i){
				if(c == '-' || c == '|')
					count++;
			}
		}
		return count%2+1;*/
		return ((char[][])localBoard)[0][0];
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
		if(b[0][0] == '1')
			p1--;
		else if(b[0][0] == '2')
			p2--;

		if(p1 > p2)
			return 1;
		else if(p1 < p2)
			return 2;
		else
			return 0;
	}

	@Override
	Object[] getChildren(Object localBoard) {
		char[][] lb = (char[][]) localBoard;
		DSArrayList<char[][]> l = new DSArrayList<char[][]>();
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				if(lb[row][col] == ' '){
					char[][] b = new char[boardSize][boardSize];
					for(int r = 0; r < boardSize; r++)
						for(int c = 0; c < boardSize; c++)
							b[r][c] = lb[r][c];
					
					if(row % 2 == 0)
						b[row][col] = '-';
					else
						b[row][col] = '|';
					
					if(!playAgain(b, b[0][0])){
						if(b[0][0] == '1')
							b[0][0] = '2';
						else
							b[0][0] = '1';
					}
					l.add(b);
				}
			}
		}
		System.out.println(l);
		return l.toArray();
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
		if(!isGameOver())
			System.out.println("It is Player " + whoseTurn + "'s turn.");
		System.out.print("  ");
		for(int x = 1; x <= boardSize; x++)
			System.out.print(x);
		System.out.print("\n1 ");
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				if(row == 0 && col == 0)
					System.out.print("*");
				else
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
		do{
			int r, c;
			System.out.print("Please enter a row to make a move at: ");
			r = scan.nextInt();
			System.out.print("Please enter a column to make a move at: ");
			c = scan.nextInt();
			while(r < 1 || r > boardSize || c < 1 || c > boardSize|| board[--r][--c] != ' '){
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
		}while(playAgain(board, whoseTurn) && !isGameOver());
		whoseTurn = 3 - whoseTurn;
		if(whoseTurn == 1)
			board[0][0] = '1';
		else
			board[0][0] = '2';
	}

	@Override
	void getComputerMove() {
		do{
			if(difficulty == 1)
				getDumbComputerMove();
			else if(difficulty == 2)
				getSmartComputerMove();
			else
				getHeuristicComputerMove();
		}while(playAgain(board, whoseTurn) && !isGameOver());
		whoseTurn = 3 - whoseTurn;
		if(whoseTurn == 1)
			board[0][0] = '1';
		else
			board[0][0] = '2';
	}

	/*
	 * Updates the 0s on the board to 1s and 2s and returns true if the last play filled in a block.
	 * Otherwise it returns false so that the next player can play. 
	 */
	public boolean playAgain() {
		boolean rv = false;
		
		char[][] b = new char[boardSize][boardSize];
		for(int r = 0; r < boardSize; r++)
			for(int c = 0; c < boardSize; c++)
				b[r][c] = board[r][c];

		updateBoard();
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				if(b[row][col] == '0' && (board[row][col] == '1' || board[row][col] == '2')){
					rv = true;
				}
			}
		}

		if(rv)
			drawBoard();

		return rv;
	}
	
	/*
	 * Updates the 0s on the board to 1s and 2s and returns true if the last play filled in a block.
	 * Otherwise it returns false so that the next player can play. 
	 */
	@Override
	public boolean playAgain(Object localBoard, int player) {
		boolean rv = false;

		char[][] lb = (char[][]) localBoard;
		char[][] b = new char[boardSize][boardSize];
		for(int r = 0; r < boardSize; r++)
			for(int c = 0; c < boardSize; c++)
				b[r][c] = lb[r][c];

		lb = (char[][]) updateBoard(board, whoseTurn);
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				if(b[row][col] == '0' && (lb[row][col] == '1' || lb[row][col] == '2')){
					rv = true;
				}
			}
		}

		if(rv)
			drawBoard();

		return rv;
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
		if(board[0][0] == '1')
			p1--;
		else if(board[0][0] == '2')
			p2--;

		if(p1 > p2)
			return 1;
		else if(p1 < p2)
			return 2;
		else
			return 0;
	}

	@Override
	String toString(Object board) {
		return Arrays.toString((char[][])board);
	}

	@Override
	int heuristicEvaluation(Object board) {
		int w = 0;
		char[][] b = (char[][])board;
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				if(!(row == 0 && col == 0)){
					if(b[row][col] == '0'){
						int lineCount = 0;
						if(b[row-1][col] == '-')
							lineCount++;
						if(b[row+1][col] == '-')
							lineCount++;
						if(b[row][col-1] == '|')
							lineCount++;
						if(b[row][col+1] == '|')
							lineCount++;
						if(lineCount == 3){
							if(whoseTurn(b) == 1)
								w+=5;
							else
								w-=5;
						}
					}
					else if(b[row][col] == '1')
						w+=10;
					else if(b[row][col] == '2')
						w-=10;
				}
			}
		}
		return w;
	}
}