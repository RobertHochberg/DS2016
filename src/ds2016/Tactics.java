package ds2016;

import java.util.ArrayList;
import java.util.Scanner;

public class Tactics extends AlternatingGame 
{

	// Debug
	public final static boolean DEBUG_MESSAGES = true;
	
	// Messages
	public final static String MSG_UNIT_MOVE  = "Player %d moving %s (at position %d, %d)%nSelect destination within %d tiles:";
	public final static String MSG_TARGET     = "Player %d selecting target for %s (at position %d, %d)%n"+
												"Valid targets:%n";
	public final static String MSG_YOU_WON    = "Player %d won!%n";
	public final static String MSG_BAD_INPUT  = "Invalid input.%n";
	public final static String MSG_TOO_FAR    = "Can't move that far!%n";
	public final static String MSG_NONEMPTY_SPACE
											  = "That space is taken!%n";
	public final static String MSG_INVALID_TARGET
											  = "Target number not on the list.%n";
	public final static String MSG_PRESHOT    = "%d: %s at position (%d, %d) / chance to hit: %d / chance to crit: %d / flanked: %b;%n";
	
	public final static String MSG_OVERWATCH  = "%s takes a reaction shot against %s!%n";
	public final static String MSG_OVERWATCHING
											  = "%s waits for enemy movement.%n";
	public final static String MSG_OVERWATCH_REMOVED
											  = "%s lost their concentration!";
	public final static String MSG_HUNKERDOWN = "%s makes the most of the cover.%n";
	public final static String MSG_HUNKERFAIL = "Can't hunker down without cover!%n";
	
	public final static String MSG_SHOT       = "%s shoots at %s with %d%% accuracy!%n";
	public final static String MSG_MISS       = "It's a miss!%n";
	public final static String MSG_CRIT       = "Critical hit!%n";
	public final static String MSG_DAMAGE     = "%s took %d damage! (Which left them with %d health)%n";
	public final static String MSG_DEATH      = "%s was killed!%n";
	
	// Game Constants
	public final static int     DEF_CRIT_CHANCE    = 10;
	public final static double  CRIT_MULTIPLIER    = 2.0;
	public final static double  HUNKERDOWN_MULT    = 2.0;
	public final static int     PROX_BONUS_FALLOFF =  7;
	public final static int     FLANK_CRIT_BONUS   = 50;
	public final static int     REACTION_PENALTY   = 10;
	
	// Anything this close or closer to the line between two units blocks line of sight
	public final static int     LOS_DISTANCE       = 2;
	
	public final static int     FULL_COVER_BONUS = 40; // bonus to defense in cover
	public final static int     HALF_COVER_BONUS = 20;
	
	// AI Config
	public final static int     AI_FULL_COVER_VALUE =  50;
	public final static int     AI_HALF_COVER_VALUE =  25;
	public final static int     AI_FLANK_VALUE      = 100;
	public final static int     AI_FLANKED_VALUE    = -50;
	
	// Console Output Chars
	
	public final static char    BLANK_SPACE = ' ';
	public final static char    PONE_UNIT   = 'X';
	public final static char    PTWO_UNIT   = 'O';
	public final static char    HALF_COVER  = '-';
	public final static char    FULL_COVER  = '=';
	public final static char    VALID_MOVE  = '+';
	
	// Instance Variables
	
	TBoard theBoard;
	
	// false is computer, true is human
	boolean[] isHuman = {false, true, true};
	
	public Tactics(){
		theBoard = new TBoard(10, 10);
		// Test Defaults
		theBoard.field[2][4] = Tactics.FULL_COVER;
		theBoard.field[2][5] = Tactics.FULL_COVER;
		theBoard.field[2][6] = Tactics.FULL_COVER;
		theBoard.field[3][3] = Tactics.HALF_COVER;
		theBoard.field[3][2] = Tactics.HALF_COVER;
		theBoard.field[3][7] = Tactics.HALF_COVER;
		
		theBoard.field[5][2] = Tactics.HALF_COVER;
		
		theBoard.field[7][4] = Tactics.FULL_COVER;
		theBoard.field[7][5] = Tactics.FULL_COVER;
		theBoard.field[7][6] = Tactics.FULL_COVER;
		theBoard.field[6][3] = Tactics.HALF_COVER;
		theBoard.field[6][8] = Tactics.HALF_COVER;
		theBoard.field[6][9] = Tactics.HALF_COVER;
		
		// Add Units
		theBoard.player1Units.add(new TUnit());
		theBoard.player1Units.add(new TUnit());
		theBoard.player1Units.add(new TUnit());
		
		theBoard.player2Units.add(new TUnit());
		theBoard.player2Units.add(new TUnit());
		theBoard.player2Units.add(new TUnit());
		
		TUnit p1u1 = theBoard.player1Units.get(0);
		TUnit p1u2 = theBoard.player1Units.get(1);
		TUnit p1u3 = theBoard.player1Units.get(2);
		
		TUnit p2u1 = theBoard.player2Units.get(0);
		TUnit p2u2 = theBoard.player2Units.get(1);
		TUnit p2u3 = theBoard.player2Units.get(2);
		
		p1u1.name = "Bob";
		p1u2.name = "Aerith";
		p1u3.name = "Joe";
		
		p2u1.name = "Tomoko";
		p2u2.name = "Karin";
		p2u3.name = "Fear";
		
		
		
		// Move Units
		p1u1.moveTo(4, 9);
		p1u2.moveTo(5, 9);
		p1u3.moveTo(6, 9);
		
		p2u1.moveTo(4, 0);
		p2u2.moveTo(5, 0);
		p2u3.moveTo(6, 0);
		
	}
	
	@Override
	void setBoard(Object nb) {
		theBoard = (TBoard) nb;
	}

	@Override
	Object getBoard() {
		return theBoard;
	}

	@Override
	void drawBoard() {

		char[][] field = theBoard.render();
		
		drawBoard(field);
	}
	
	void drawBoard(char[][] field)
	{
		// Header
		System.out.print("  ");
		for(int i = 0; i < theBoard.width; i++)
			System.out.print(i + " ");
		System.out.printf("%n");
		
		// Field
		for(int i = 0; i < theBoard.height; i++)
		{
			for(int p = 0; p < theBoard.width; p++)
			{
				if(p == 0)
					System.out.print(i + " " + field[i][p] + " ");
				else
					System.out.print(field[i][p] + " ");
			}
			System.out.printf("%n");	
		}
		
		// Footer
		
		for(int i = 0; i < 10; i++)
			System.out.print("-");
		System.out.printf("%n");
		
		sleep(1000);
	}
	
	void drawBoard(ArrayList<Coord> spaces, char c)
	{
		char[][] field = theBoard.render();
		
		for(int i = 0; i < spaces.size(); i++)
		{
			Coord pos = spaces.get(i);
			int x, y;
			x = pos.getX();
			y = pos.getY();
			if(field[y][x] == Tactics.BLANK_SPACE)
				field[y][x] = c;
		}
		
		drawBoard(field);
		
	}
	
	void sleep(int time)
	{
		try {
		    Thread.sleep(time);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	@Override
	void getHumanMove() {
		
		int us   = theBoard.whoseTurn - 1;
		int them = theBoard.whoIsNext() - 1;
		
		// Build a an array list for all ally units (units on our team) and all enemy units (units on the other team)
		
		DSArrayList<TUnit> allyList  = theBoard.playerUnits.get(us);
		DSArrayList<TUnit> enemyList = theBoard.playerUnits.get(them);
		
		// Used in a loop to do things for each individual unit
		
		TUnit unit;
		
		// Update cover
		checkCoverForAll(allyList);
		checkCoverForAll(enemyList);
		
		// For each unit under our control...
		for(int i = 0; i < allyList.getSize(); i++)
		{
			
			unit = allyList.get(i);
			
			// If the unit was in a special state, reset it so it can take its turn
			unit.resetState();
			
			// Find all valid moves that the unit can make
			ArrayList<Coord> validMoves = getValidMoves(unit);
			
			// This overloaded version of drawBoard lets us show the player where the unit can move
			drawBoard(validMoves, VALID_MOVE);
			
			// Wait for player input, move unit accordingly
			respondToMovementInput(unit, enemyList, validMoves);
			
			if(unit.isDead()) // killed by reaction fire
				continue;
			
			// Wait for player input, perform action accordingly			
			respondToActionInput(unit, enemyList);
			
		} // end of for loop over units
		
		theBoard.checkUnits();
		theBoard.nextTurn();
		
		//scanner.close();
	}
	
	void checkCoverForAll(DSArrayList<TUnit> unitList)
	{
		for(int i = 0; i < unitList.getSize(); i++)
		{
			unitList.get(i).checkCover(theBoard);
		}
	}
	
	ArrayList<Coord> getValidMoves(TUnit unit)
	{
		ArrayList<Coord> space = new ArrayList<Coord>();
		
		space.add(unit.getPos());
		
		return theBoard.findValidMoves(space, unit.getStat(TUnit.MOVEMENT));
	}
	
	void respondToActionInput(TUnit unit, DSArrayList<TUnit> enemyList)
	{
		TUnit target = null;
		
		while(target == null)
		{
			System.out.printf(MSG_TARGET,
					theBoard.whoseTurn, unit.name, unit.getPos().getX(), unit.getPos().getY());
			
			// Here we use Java's built-in ArrayList so that we can use the .contains() method
			ArrayList<TUnit> validTargets = new ArrayList<TUnit>();
			
			for(int z = 0; z < enemyList.getSize(); z++)
			{
				TUnit enemy = enemyList.get(z);
				if(enemy.isInFiringRange(unit.pos) && theBoard.lineOfSight(unit.getPos(), enemy.getPos()))
				{
					System.out.printf(MSG_PRESHOT, 
							z, enemy.name, enemy.getPos().getX(), enemy.getPos().getY(), unit.chanceToHit(enemy),
							unit.chanceToCrit(enemy),
							enemy.isFlankedBy(unit));
					validTargets.add(enemy);
				}
			}
			
			System.out.print("Select target or action: ");
			
			Scanner scan = new Scanner(System.in);
			
			String input = null;
			
			input = scan.next();
			
			if(input.matches("[0-9]*"))
			{
			
				int which;
				
				which = Integer.parseInt(input);
				
				if(which >= 0 && which < enemyList.getSize())
				{
					target = enemyList.get(which);
					if(!validTargets.contains(target))
					{
						System.out.printf(MSG_INVALID_TARGET);
						target = null;	
					}
				}
				else
				{
					System.out.printf(MSG_INVALID_TARGET);
					drawBoard();
				}
			}
			else if(input.trim().toLowerCase().matches("overwatch"))
			{
				unit.overwatch();
				
				System.out.printf(MSG_OVERWATCHING, unit.name);
				
				break;
			}
			else if(input.trim().toLowerCase().matches("hunkerdown"))
			{
				
				if(unit.inCover > 0)
				{
					unit.hunkerdown();
					
					System.out.printf(MSG_HUNKERDOWN, unit.name);
					
					break;
				}
				else
				{
					System.out.printf(MSG_HUNKERFAIL);
				}
			}
			else
			{
				System.out.printf(MSG_BAD_INPUT);
			}
			
		}
		
		if(target != null)
			unit.shootAt(target);
		
		theBoard.checkUnits();
	}
	
	void respondToMovementInput(TUnit unit, DSArrayList<TUnit> enemyList, ArrayList<Coord> validMoves)
	{
		
		Scanner scanner = new Scanner(System.in);
		
		scanner.useDelimiter("\n");
		
		Coord targetPos = null;
		
		while(targetPos == null)
		{
		
			System.out.printf(MSG_UNIT_MOVE,
					theBoard.whoseTurn, unit.name, unit.getPos().getX(), unit.getPos().getY(), unit.getStat(TUnit.MOVEMENT));
			
			String input = null;
			
			input = scanner.next();
			
			if(input.trim().toLowerCase().matches("stay"))
			{
				targetPos = new Coord(unit.pos.getX(), unit.pos.getY());
			}
			else targetPos = parseInputMove(input.trim().toLowerCase());
			
			if(targetPos != null)
			{
				if(targetPos.getX() < 0 || targetPos.getY() < 0)
					targetPos = null;
				else if(targetPos.getX() >= theBoard.width || targetPos.getY() >= theBoard.height)
					targetPos = null;
				// we still need this check because half cover is counted as "passable"
				// according to validMoves
				else if(!theBoard.isBlank(targetPos) && unit.distanceTo(targetPos) > 0)
				{
					System.out.printf(MSG_NONEMPTY_SPACE);
					targetPos = null;
				}
				else
				{
					// Check the player's input against the list of valid moves
					boolean moveIsValid = false;
					
					for(int ii = 0; ii < validMoves.size(); ii++)
						moveIsValid = moveIsValid || (targetPos.sameAs(validMoves.get(ii)));
					
					if(!moveIsValid)
						targetPos = null;
				}
			}
			
			if(targetPos == null)
			{
				System.out.printf(MSG_BAD_INPUT);
				drawBoard(validMoves, VALID_MOVE);
			}
			
		} // end of while loop
		
		checkForOverwatch(unit, targetPos, enemyList);
		
		unit.moveTo(targetPos);
		unit.checkCover(theBoard);
		
		drawBoard();
	}
	
	Coord parseInputMove(String data) {
		Coord rv;
		
		rv = null;
		
		String[] parts = data.split(" ");
		
		String exp = "[0-9]*";
		
		
		if(parts.length == 2)
			if(parts[0].matches(exp) && parts[1].matches(exp))
				rv = new Coord(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
		
		return rv;	
	}

	void checkForOverwatch(TUnit unit, Coord targetPos, DSArrayList<TUnit> enemyList)
	{
		// Check for overwatch shots
		if(unit.distanceTo(targetPos) > 0)
		{
			for(int z = 0; z < enemyList.getSize(); z++)
			{
				TUnit thisGuy = enemyList.get(z);
				if(thisGuy.overwatchActive && 
						((thisGuy.isInFiringRange(unit.getPos()) && theBoard.lineOfSight(thisGuy.getPos(), unit.getPos())) || 
								(thisGuy.isInFiringRange(targetPos)) && theBoard.lineOfSight(thisGuy.getPos(), targetPos))
						)
				{
					System.out.printf(MSG_OVERWATCH, thisGuy.name, unit.name);
					thisGuy.shootAt(unit);
					
					thisGuy.resetState(); // remove overwatch
					
					sleep(1000);
					
				}
			}
		}
	}
	
	@Override
	void getComputerMove() {
		getDumbComputerMove();
	}
	
	void getDumbComputerMove()
	{
		
	}

	@Override
	boolean isGameOver() {		
		boolean rv = false;
		
		theBoard.checkUnits();
		
		if(theBoard.player1Units.getSize() == 0)
			rv = true;
		if(theBoard.player2Units.getSize() == 0)
			rv = true;
		
		return rv;
	}

	@Override
	int whoseTurn(Object localBoard) {
		TBoard board = (TBoard) localBoard;
		
		return board.whoseTurn;
	}
	
	@Override
	int whoWon() {
		return whoWon(theBoard);
	}

	@Override
	int whoWon(Object board) {
		
		TBoard thisBoard = (TBoard) board;
		
		// This 0 should never get returned, but if it does that means that all units on both teams died,
		// which means it's a draw.
		int rv = 0;
		if(thisBoard.player1Units.getSize() == 0)
			rv = 2;
		else if(thisBoard.player2Units.getSize() == 0)
			rv = 1;
		
		return rv;
			
	}

	@Override
	Object[] getChildren(Object board) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static int randRange(int min, int max)
	{
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	public String toString(Object board)
	{
		TBoard thisBoard = (TBoard) board;
		
		return thisBoard.render().toString();
		
	}
}
