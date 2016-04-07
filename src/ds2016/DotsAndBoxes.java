package ds2016;

/**
 * @author Jack Baumann
 * Plays a game of Dots and Boxes versus a computer or between two humans.
 */
public class DotsAndBoxes extends AlternatingGame {
	
	char[][] board;
	int boardSize = 9;
	
	public DotsAndBoxes() {
		board = new char[boardSize][boardSize];
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				if((row % 2 == 0) && (col % 2 == 0)){
					board[row][col] = '*';
				}
				else if((row % 2 == 1) && (col % 2 == 1)){
					board[row][col] = '1';
				}
				else{
					board[row][col] = ' ';
				}
			}
		}
	}

	@Override
	void getPlayerMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	void doEndgameStuff() {
		// TODO Auto-generated method stub
		
	}

	@Override
	int whoseTurn(Object localBoard) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	int whoWon(Object board) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	Object[] getChildren(Object board) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	void setBoard(Object nb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	Object getBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	void drawBoard() {
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				System.out.print(board[row][col]);
				if(col == boardSize - 1)
					System.out.print("\n");
			}
		}
	}

	@Override
	void getHumanMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void getComputerMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	int whoWon() {
		// TODO Auto-generated method stub
		return 0;
	}
}