package ds2016;

class Minesweeper extends SinglePlayerGame {
	void getPlayerMove();
	boolean isGameOver();
	void doEndgameStuff();
	int  whoseTurn(Object localBoard);
	int  whoWon(Object board);
	
	@Override
	Object[] getChildren(Object board) {
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
		// TODO Auto-generated method stub
		
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
