package ds2016;

import java.util.HashMap;
import java.util.Scanner;

public class Mines
{
	// cannot be larger than 26
	int HEIGHT = 20;	// the height of the board array
	int j = 20;	// the width of the board array
	char FIELD = '*';
	char FLAG = 'F'; // \u2691
	char EMPTY = '-';
	char BOMB = 'B';
	char EXPLOSION = 'X';
	int bombUpperBound = (int) Math.floor(HEIGHT * j / 5);
	int bombLowerBound = (int) Math.floor(HEIGHT * j / 10);
//	int numBombs = 0;
	int numBombs = (int) Math.floor(Math.random() * ((bombUpperBound - bombLowerBound) + 1) + bombLowerBound);
	// board is the board that is visible to the player
	char [][] board = new char [HEIGHT][j];
	// back board is the board contains ALL of the information of the game
	char [][] backBoard = new char [HEIGHT][j];
	Scanner scanner;
	HashMap<Integer, Character> IndexHash;
	HashMap<Character, Integer> ReturnHash;
	char lastMoveBack;
	char lastMoveBoard;
	boolean gameOver = false;
	int lastMoveCol;
	int lastMoveRow;

	public static void main(String args[]){

		Mines m =  new Mines();
		m.playGame();
		//		m.drawBackBoard();
		//		m.drawBoard();
	}

	// constructor
	public Mines()
	{
		scanner = new Scanner(System.in);

		for(int x = 0; x < j; x++)
		{
			for(int y = 0; y < HEIGHT; y++)
			{
				if(x != 0 && y != 0){
					board[x][y] = FIELD;
					backBoard[x][y] = FIELD;
				}
			}
		}
		IndexHash = new HashMap<>();
		ReturnHash = new HashMap<>();


		IndexHash.put(0, ' ');
		IndexHash.put(1, 'a');
		IndexHash.put(2, 'b');
		IndexHash.put(3, 'c');
		IndexHash.put(4, 'd');
		IndexHash.put(5, 'e');
		IndexHash.put(6, 'f');
		IndexHash.put(7, 'g');
		IndexHash.put(8, 'h');
		IndexHash.put(9, 'i');
		IndexHash.put(10, 'j');
		IndexHash.put(11, 'k');
		IndexHash.put(12, 'l');
		IndexHash.put(13, 'm');
		IndexHash.put(14, 'n');
		IndexHash.put(15, 'o');
		IndexHash.put(16, 'p');
		IndexHash.put(17, 'q');
		IndexHash.put(18, 'r');
		IndexHash.put(19, 's');
		IndexHash.put(20, 't');
		IndexHash.put(21, 'u');
		IndexHash.put(22, 'v');
		IndexHash.put(23, 'w');
		IndexHash.put(24, 'x');
		IndexHash.put(25, 'y');
		IndexHash.put(26, 'z');

		ReturnHash.put(' ', 0);
		ReturnHash.put('a', 1);
		ReturnHash.put('b', 2);
		ReturnHash.put('c', 3);
		ReturnHash.put('d', 4);
		ReturnHash.put('e', 5);
		ReturnHash.put('f', 6);
		ReturnHash.put('g', 7);
		ReturnHash.put('h', 8);
		ReturnHash.put('i', 9);
		ReturnHash.put('j', 10);
		ReturnHash.put('k', 11);
		ReturnHash.put('l', 12);
		ReturnHash.put('m', 13);
		ReturnHash.put('n', 14);
		ReturnHash.put('o', 15);
		ReturnHash.put('p', 16);
		ReturnHash.put('q', 17);
		ReturnHash.put('r', 18);
		ReturnHash.put('s', 19);
		ReturnHash.put('t', 20);
		ReturnHash.put('u', 21);
		ReturnHash.put('v', 22);
		ReturnHash.put('w', 23);
		ReturnHash.put('x', 24);
		ReturnHash.put('y', 25);
		ReturnHash.put('z', 26);

		//		System.out.println("The ReturnHash val of a is; " + ReturnHash.get('a'));

		placeBombs(backBoard);
		placeNumbers(backBoard);
		placeIndex(board);
		placeIndex(backBoard);

	}
	// draws the board that is visible to the player

	void drawBoard(char[][] b)
	{
		for(int x = 0; x < j; x++)
		{
			for(int y = 0; y < HEIGHT; y++)
			{
				System.out.print(b[x][y]);
				System.out.print(" ");
				// prints the appropriate character from the backBoard onto the visible board
				// flags only exist on visible board

				//			 if(backBoard[i][j] == FIELD)
				//				 board[i][j] = FIELD;
				//			 if(backBoard[i][j] == FLAG)
				//				 board[i][j] = FLAG;
				//			 if(backBoard[i][j] == EMPTY)
				//				 board[i][j] = EMPTY;

				//		System.out.println("bombUpperBound = " + bombUpperBound);
				//		System.out.println("bombLowerBound = " + bombLowerBound);
				//		System.out.println("numBombs = " + numBombs);
				//		System.out.println("The space of backBoard[2][2] contains: " + backBoard[2][2] );

			}
			System.out.println("");
		}
	}


	// places bombs at random positions on the backBoard
	void placeBombs(char[][] localB)
	{
		for(int b = 0; b < numBombs; b++)
		{
			int randX = randXPos();
			int randY = randYPos();
			//			System.out.println("randBombPos = " + randY + ", " + randX);
			for(int x = 0; x < j; x++)
			{
				for(int y = 0; y < HEIGHT; y++)
				{
					// the bomb size of array != its index
					//				backBoard[x][y] = BOMB;

					// places the bombs at random positions
					// places the numbers in relation to the bombs

					localB[randX][randY] = BOMB;
				}
			}
		}
	}
	// outputs a random X position in an array that is not 0
	int randXPos(){
		int randX = (int) Math.floor(Math.random() * (j - 1) + 1);
		return randX;

	}
	// outputs a random Y position in an array
	int randYPos(){
		int randY = (int) Math.floor(Math.random() * (HEIGHT - 1) + 1); 
		return randY;
	}

	void placeNumbers(char[][] localB)
	{
		for(int x = 1; x < j; x++)
		{
			for(int y = 1; y < HEIGHT; y++)
			{
				int numBombsNear = 0;
				if(localB[x][y] != BOMB)
				{
					// UpLeftCorner
					if(x == 1 && y == 1){
						if(localB[x+1][y] == BOMB)
							numBombsNear++;
						if(localB[x][y+1] == BOMB)
							numBombsNear++;
						if(localB[x+1][y+1] == BOMB)
							numBombsNear++;
						localB[x][y] = intToChar(numBombsNear);
						//						System.out.println("This space is: " + board[x][y]);
						//						System.out.println("This space is: " + board[1][1]);
						//						System.out.println("The number of Bombs near: " + x + ", " + y + " is: " + numBombsNear);
					}
					//BottomLeftCorner
					if(x == 1 && y == HEIGHT - 1)
					{
						if(localB[x+1][y] == BOMB)
							numBombsNear++;
						if(localB[x][y-1] == BOMB)
							numBombsNear++;
						if(localB[x+1][y-1] == BOMB)
							numBombsNear++;
						localB[x][y] = intToChar(numBombsNear);
						//						System.out.println("The number of Bombs near: " + x + ", " + y + " is: " + numBombsNear);
					}
					//LeftColumn
					if(x == 1 && y != 1 && y != HEIGHT - 1){
						if(localB[x+1][y] == BOMB)
							numBombsNear++;
						if(localB[x][y+1] == BOMB)
							numBombsNear++;
						if(localB[x][y-1] == BOMB)
							numBombsNear++;
						if(localB[x+1][y+1] == BOMB)
							numBombsNear++;
						if(localB[x+1][y-1] == BOMB)
							numBombsNear++;
						localB[x][y] = intToChar(numBombsNear);
						//						System.out.println("The number of Bombs near: " + x + ", " + y + " is: " + numBombsNear);
					}
					//TopRow
					if(y == 1 && x != 1 && x != j - 1){
						if(localB[x+1][y] == BOMB)
							numBombsNear++;
						if(localB[x-1][y] == BOMB)
							numBombsNear++;
						if(localB[x][y+1] == BOMB)
							numBombsNear++;
						if(localB[x+1][y+1] == BOMB)
							numBombsNear++;
						if(localB[x-1][y+1] == BOMB)
							numBombsNear++;
						localB[x][y] = intToChar(numBombsNear);
						//						System.out.println("The number of Bombs near: " + x + ", " + y + " is: " + numBombsNear);
					}
					//BottomRight
					if(x == j - 1 && y == HEIGHT - 1){
						if(localB[x-1][y] == BOMB)
							numBombsNear++;
						if(localB[x][y-1] == BOMB)
							numBombsNear++;
						if(localB[x-1][y-1] == BOMB)
							numBombsNear++;
						localB[x][y] = intToChar(numBombsNear);
						//						System.out.println("The number of Bombs near: " + x + ", " + y + " is: " + numBombsNear);
					}
					//TopRight
					if(y == 1 && x == j - 1)
					{
						if(localB[x-1][y] == BOMB)
							numBombsNear++;
						if(localB[x][y+1] == BOMB)
							numBombsNear++;
						if(localB[x-1][y+1] == BOMB)
							numBombsNear++;
						localB[x][y] = intToChar(numBombsNear);
						//						System.out.println("The number of Bombs near: " + x + ", " + y + " is: " + numBombsNear);
					}
					//RightColumn
					if(x == j - 1 && y != 1 && y != HEIGHT - 1){
						if(localB[x-1][y] == BOMB)
							numBombsNear++;
						if(localB[x][y+1] == BOMB)
							numBombsNear++;
						if(localB[x][y-1] == BOMB)
							numBombsNear++;
						if(localB[x-1][y+1] == BOMB)
							numBombsNear++;
						if(localB[x-1][y-1] == BOMB)
							numBombsNear++;
						localB[x][y] = intToChar(numBombsNear);
						//						System.out.println("The number of Bombs near: " + x + ", " + y + " is: " + numBombsNear);
					}
					//BottomRow
					if(y == HEIGHT - 1 && x != 1 && x != j - 1){
						if(localB[x+1][y] == BOMB)
							numBombsNear++;
						if(localB[x-1][y] == BOMB)
							numBombsNear++;
						if(localB[x][y-1] == BOMB)
							numBombsNear++;
						if(localB[x+1][y-1] == BOMB)
							numBombsNear++;
						if(localB[x-1][y-1] == BOMB)
							numBombsNear++;
						localB[x][y] = intToChar(numBombsNear);
						//						System.out.println("The number of Bombs near: " + x + ", " + y + " is: " + numBombsNear);
					}
					if(y != 1 && y != HEIGHT - 1 && x != 1 && x != j - 1)
					{
						if(localB[x+1][y] == BOMB)
							numBombsNear++;
						if(localB[x-1][y] == BOMB)
							numBombsNear++;
						if(localB[x][y+1] == BOMB)
							numBombsNear++;
						if(localB[x][y-1] == BOMB)
							numBombsNear++;
						if(localB[x+1][y+1] == BOMB)
							numBombsNear++;
						if(localB[x-1][y+1] == BOMB)
							numBombsNear++;
						if(localB[x+1][y-1] == BOMB)
							numBombsNear++;
						if(localB[x-1][y-1] == BOMB)
							numBombsNear++;
						localB[x][y] = intToChar(numBombsNear);
						//						System.out.println("The number of Bombs near: " + x + ", " + y + " is: " + numBombsNear);
					}
				}
			}
		}
	}

	char intToChar(int i){
		String b = Integer.toString(i);
		char c = stringToChar(b);
		return c;
	}
	char stringToChar(String s)
	{
		char c = s.charAt(0);
		return c;
	}

	void placeIndex(char[][] b)
	{
		for(int x = 0; x < j; x++)
		{
			//			if(x <= 9)
			//				b[x][0] = intToChar(x);
			//			b[x][0] = (char) ((char)x + 96);
			char r = IndexHash.get(x);
			//			System.out.println("The indexX is : " + IndexHash.get(x));
			b[x][0] = r;
		}
		for(int y = 0; y < HEIGHT; y++)
		{	
			//			if(y <= 9)
			//				b[0][y] = intToChar(y);
			//			b[0][y] = (char) ((char)y + 96);
			char s = IndexHash.get(y);
			//			System.out.println("The indexY is : " + y);
			b[0][y] = s;

		}
	}

	void getMove(){
		System.out.print("Please enter your desired column: ");
		String columnString = scanner.next();
		char columnChar = stringToChar(columnString);
		if(ReturnHash.get(columnChar) < j)
		{	
			System.out.print("Please enter your desired row: ");
			String rowString = scanner.next();
			char rowChar = stringToChar(rowString);
			lastMoveCol = ReturnHash.get(columnChar);
			if(ReturnHash.get(rowChar) < HEIGHT)
			{
				lastMoveRow = ReturnHash.get(rowChar);
				board[lastMoveRow][lastMoveCol] = 
						backBoard[lastMoveRow][lastMoveCol];
				lastMoveBoard = board[lastMoveRow][lastMoveCol];
			}
			else {
				System.out.println("Invalid move! Please try again.");
				getMove();
			}
		}
		else{
			System.out.println("Invalid move! Please try again.");
			getMove();
		}
		////////////
		//		System.out.println(lastMove);
		//		System.out.println("the column val is: " + columnChar);
		//		System.out.println("the row val is: " + rowChar);
		//		System.out.println("New the ReturnHash val of a is: " + ReturnHash.get('a'));
		//		System.out.println("returnHashCol is: " + ReturnHash.get(columnChar));
		//		System.out.println("returnHashRow is: " + ReturnHash.get(rowChar));
		//////////

	}

	int numFields(char[][] b){
		int numField = 0;
		for(int x = 0; x < j; x++)
		{
			for(int y = 0; y < HEIGHT; y++)
			{
				if(b[x][y] == FIELD)
					numField++;
			}
		}
		return numField;
	}

	boolean isBoardWon()
	{
		if(numBombs == numFields(board))
		{
			gameOver = true;
			return gameOver;
		}
		else
		{
			gameOver = false;
			return gameOver;
		}
	}

	boolean isGameLost(){
		if(lastMoveBoard == BOMB)
			return true;
		return false;
	}

	void playGame(){
		while(!gameOver){
			System.out.println("Is 9 a number? " + isNumber('9'));
			System.out.println("Is T a number? " + isNumber('T'));
			System.out.println("");
			drawBoard(backBoard);
			System.out.println("");
			drawBoard(board);
			getMove();
			revealSpaces();
			if(isBoardWon() == true)
				doEndGameStuff();
			if(isGameLost() == true)
				doEndGameStuff();
		}
		drawBoard(backBoard);
	}

	void doEndGameStuff()
	{
		if(isBoardWon() == true){
			gameOver = true;
			System.out.println("Congragulations you won!");

		}

		if(isGameLost() == true){
			gameOver = true;
			System.out.println("You died!");

		}
	}
	
	boolean isNumber(char c)
	{
		if(c >= '0' && c <= '9')
			return true;
		return false;
	}
	
	// If you select a 0 all of the surrounding numbers will reveal
	void revealSpaces()
	{
		{
			for(int x = 1; x < j; x++)
			{
				for(int y = 1; y < HEIGHT; y++)
				{
					if(board[x][y] == 0)
					{
						do
						{// UpLeftCorner
							if(x == 1 && y == 1){
								if(isNumber(board[x+1][y]))
									board[x+1][y] = backBoard[x+1][y];
								if(isNumber(board[x][y+1]))
									board[x][y+1] = backBoard[x][y+1];
								if(isNumber(board[x+1][y+1]))
									board[x+1][y+1] = backBoard[x+1][y+1];
							}
							//BottomLeftCorner
							if(x == 1 && y == HEIGHT - 1)
							{
								if(isNumber(board[x+1][y]))
									board[x+1][y] = backBoard[x+1][y];
								if(isNumber(board[x][y-1]))
									board[x][y-1] = backBoard[x][y-1];
								if(isNumber(board[x+1][y-1]))
									board[x+1][y-1] = backBoard[x+1][y-1];
							}
							//LeftColumn
							if(x == 1 && y != 1 && y != HEIGHT - 1){
								if(isNumber(board[x+1][y]))
									board[x+1][y] = backBoard[x+1][y];
								if(isNumber(board[x][y+1]))
									board[x][y+1] = backBoard[x][y+1];
								if(isNumber(board[x][y-1]))
									board[x][y-1] = backBoard[x][y-1];
								if(isNumber(board[x+1][y+1]))
									board[x+1][y+1] = backBoard[x+1][y+1];
								if(isNumber(board[x+1][y-1]))
									board[x+1][y-1] = backBoard[x+1][y-1];
							}
							//TopRow
							if(y == 1 && x != 1 && x != j - 1){
								if(isNumber(board[x+1][y]))
									board[x+1][y] = backBoard[x+1][y];
								if(isNumber(board[x-1][y]))
									board[x-1][y] = backBoard[x-1][y];
								if(isNumber(board[x][y+1]))
									board[x][y+1] = backBoard[x][y+1];
								if(isNumber(board[x+1][y+1]))
									board[x+1][y+1] = backBoard[x+1][y+1];
								if(isNumber(board[x-1][y+1]))
									board[x-1][y+1] = backBoard[x-1][y+1];
							}
							//BottomRight
							if(x == j - 1 && y == HEIGHT - 1){
								if(isNumber(board[x-1][y]))
									board[x-1][y] = backBoard[x-1][y];
								if(isNumber(board[x][y-1]))
									board[x][y-1] = backBoard[x][y-1];
								if(isNumber(board[x-1][y-1]))
									board[x-1][y-1] = backBoard[x-1][y-1];
							}
							//TopRight
							if(y == 1 && x == j - 1)
							{
								if(isNumber(board[x-1][y]))
									board[x-1][y] = backBoard[x-1][y];
								if(isNumber(board[x][y+1]))
									board[x][y+1] = backBoard[x][y+1];
								if(isNumber(board[x-1][y+1]))
									board[x-1][y+1] = backBoard[x-1][y+1];
							}
							//RightColumn
							if(x == j - 1 && y != 1 && y != HEIGHT - 1){
								if(isNumber(board[x-1][y]))
									board[x-1][y] = backBoard[x-1][y];
								if(isNumber(board[x][y+1]))
									board[x][y+1] = backBoard[x][y+1];
								if(isNumber(board[x][y-1]))
									board[x][y-1] = backBoard[x][y-1];
								if(isNumber(board[x-1][y+1]))
									board[x-1][y+1] = backBoard[x-1][y+1];
								if(isNumber(board[x-1][y-1]))
									board[x-1][y-1] = backBoard[x-1][y-1];
							}
							//BottomRow
							if(y == HEIGHT - 1 && x != 1 && x != j - 1){
								if(isNumber(board[x+1][y]))
									board[x+1][y] = backBoard[x+1][y];
								if(isNumber(board[x-1][y]))
									board[x-1][y] = backBoard[x-1][y];
								if(isNumber(board[x][y-1]))
									board[x][y-1] = backBoard[x][y-1];
								if(isNumber(board[x+1][y-1]))
									board[x+1][y-1] = backBoard[x+1][y-1];
								if(isNumber(board[x-1][y-1]))
									board[x-1][y-1] = backBoard[x-1][y-1];
							}

							// the Rest
							if(y != 1 && y != HEIGHT - 1 && x != 1 && x != j - 1)
							{
								if(isNumber(board[x+1][y]))
									board[x+1][y] = backBoard[x+1][y];
								if(isNumber(board[x-1][y]))
									board[x-1][y] = backBoard[x-1][y];
								if(isNumber(board[x][y+1]))
									board[x][y+1] = backBoard[x][y+1];
								if(isNumber(board[x][y-1]))
									board[x][y-1] = backBoard[x][y-1];
								if(isNumber(board[x+1][y+1]))
									board[x+1][y+1] = backBoard[x+1][y+1];
								if(isNumber(board[x-1][y+1]))
									board[x-1][y+1] = backBoard[x-1][y+1];
								if(isNumber(board[x+1][y-1]))
									board[x+1][y-1] = backBoard[x+1][y-1];
								if(isNumber(board[x-1][y-1]))
									board[x-1][y-1] = backBoard[x-1][y-1];
							}
						} while(board[x][y] == 0);
					}
				}
			}
		}
	}
}


