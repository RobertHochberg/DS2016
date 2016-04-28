package ds2016;

import java.util.ArrayList;

public class TBoard 
{
	DSArrayList<TUnit> player1Units;
	DSArrayList<TUnit> player2Units;
	
	char[][] field;
	
	int width  = 10;
	int height = 10;
	
	int whoseTurn = 1;
	
	DSArrayList<DSArrayList<TUnit>> playerUnits = new DSArrayList<DSArrayList<TUnit>>(); 
	
	public TBoard()
	{
		this(10, 5, new DSArrayList<TUnit>(), new DSArrayList<TUnit>());
	}
	
	public TBoard(int width, int height)
	{
		this(width, height, new DSArrayList<TUnit>(), new DSArrayList<TUnit>());
	}
	
	public TBoard(int width, int height, DSArrayList<TUnit> player1Units, DSArrayList<TUnit> player2Units){
		this.width = width;
		this.height = height;
		
		this.player1Units = player1Units;
		this.player2Units = player2Units;
		
		field = new char[height][width];
		
		for(int i = 0; i < height; i++)
			for(int ii = 0; ii < width; ii++)
				this.field[i][ii] = Tactics.BLANK_SPACE;
		
		playerUnits.add(this.player1Units);
		playerUnits.add(this.player2Units);
	}
	
	/**
	 * Loads a field from a file
	 * @param filepath
	 */
	
	public void loadField(String filepath)
	{
		// placeholder
		return;
	}
	
	public void nextTurn(){
		this.whoseTurn = (this.whoseTurn == 1)? 2 : 1;
	}
	
	public int whoIsNext(){
		return 3 - this.whoseTurn;
	}
	
	public boolean isBlank(Coord pos)
	{
		int x, y;
		x = pos.getX();
		y = pos.getY();
		
		char[][] field = this.render();
		
		if(field[y][x] != Tactics.BLANK_SPACE)
			return false;
		else
			return true;
	}
	
	/**
	 * Check to see if all the units are still alive
	 */
	
	public void checkUnits(){
		TUnit unit;
		
		DSArrayList<TUnit> p1units = new DSArrayList<TUnit>();
		DSArrayList<TUnit> p2units = new DSArrayList<TUnit>();
		
		// copy the arrays to make them safe to iterate through while removing things from the original array
		for(int i = 0; i < player1Units.getSize(); i++)
			p1units.add(player1Units.get(i));
		
		for(int i = 0; i < player2Units.getSize(); i++)
			p2units.add(player2Units.get(i));
		
		
		// Combine both lists into a stacked list so that we can iterate through it
		ArrayList<DSArrayList<TUnit>> punits = new ArrayList<DSArrayList<TUnit>>();
		
		punits.add(p1units);
		punits.add(p2units);
		
		// for every list of units inside punits...
		int z = 0;
		for(DSArrayList<TUnit> unitList: punits)
		{			
			for(int i = 0; i < unitList.getSize(); i++)
			{
				unit = unitList.get(i);
				
				// playerUnits is a list of lists; get the appropriate list and remove the specified unit
				if(unit.getHP() <= 0)
					playerUnits.get(z).remove(i);
			}
			z++;
		}
	}
	
	public boolean anyUnitInFiringRange(Coord pos)
	{
		boolean rv = false;
		
		for(int z = 0; z < this.playerUnits.getSize(); z++)
		{
			DSArrayList<TUnit> theArray = this.playerUnits.get(z);
			for(int i = 0; i < theArray.getSize(); i++)
			{
				TUnit u = theArray.get(i);
				rv = rv || u.isInFiringRange(pos);
				
				// if at least one unit is in range, we know to return true
				if(rv)
					break;
				
			}
		}
		
		return rv;
	}
	
	/**
	 * Finds all of the spaces that a unit could move from if they are at position pos, and can move range spaces.
	 * Returns a list of said spaces as an ArrayList.
	 * @param pos
	 * @param range
	 * @return
	 */
	
	public ArrayList<Coord> findValidMoves(ArrayList<Coord> spaces, int range)
	{
		
		if(range == 0)
			return spaces;
		
		ArrayList<Coord> rv = new ArrayList<Coord>();
		
		char[][] field = this.render();
		
		for(int y = 0; y < this.height; y++)
			for(int x = 0; x < this.width; x++)
			{
				// If the space isn't passable...
				if(field[y][x] != Tactics.BLANK_SPACE && field[y][x] != Tactics.HALF_COVER)
					continue;
				
				boolean closeEnough = false;
				
				for(int i = 0; i < spaces.size(); i++)
				{
					closeEnough = closeEnough || (TUnit.distanceTo(spaces.get(i), new Coord(x, y)) <= 1);
				}
				
				if(!closeEnough)
					continue;
				
				boolean alreadyAdded = false;
				
				for(int z = 0; z < rv.size(); z++)
				{
					if(rv.get(z).sameAs(new Coord(x, y)))
					{
						alreadyAdded = true;
						break;
					}
				}
				
				if(!alreadyAdded)
					rv.add(new Coord(x, y));
				
			}
		
		//recursive call
		rv.addAll(findValidMoves(rv, range - 1));
		
		rv.addAll(spaces);
		
		return rv;
	}
	
	public boolean lineOfSight(Coord pos1, Coord pos2)
	{	
		boolean rv = true;
		
		char[][] renderedBoard = this.render();
		
		int a, b, c, d;
		a = pos1.getX();
		b = pos1.getY();
		c = pos2.getX();
		d = pos2.getY();
		
		if((a == c && b == d))
			return true;
		
		out:
			for(int t = 0; t < this.height; t++) // y
				for(int s = 0; s < this.width; s++) // x
				{
					// If this space is blank or half cover, don't bother checking because it can't block anything
					if(renderedBoard[t][s] == Tactics.BLANK_SPACE || renderedBoard[t][s] == Tactics.HALF_COVER)
						continue;
					
					// If this space is not between point a and point b
					if(!(((a < s && s < c) || (a > s && s > c)) && ((b < t && t < d) ||
							 (b > t && t > d))))
						continue;
					
					// If this space IS point a or point b
					if((a == s && b == t) || (c == s && d == t))
						continue;
					
					// formula courtesy of Dr. Hochberg
					// Finds the area of the triangle formed from the three points,
					// then uses that to compute the distance between the base of the triangle 
					// and the given third point (the height of the triangle)
					// This gives us the distance from the line between point a (pos1) and point b (pos 2)
					double dist = Math.abs(((a * t) - (b * s) + (s * d) - (t * c) + (b * c) - (a * d)) / 
							Math.sqrt(Math.pow(c - a, 2) + Math.pow(d - b, 2)));
					
					int intDist = (int) Math.ceil(dist); // convert to int
					
					/*
					if(Tactics.DEBUG_MESSAGES)
						System.out.println(dist + " : " + intDist + " / (" + s + ", " + t + ")");
					*/
					
					// this space is blocking line of sight
					if(intDist <= Tactics.LOS_DISTANCE && intDist >= 0)
					{
						rv = false;
						
						break out;
					}
					
				}
		
		return rv;
	}
	
	// Render a given board
	// This rendered board is suitable for printing or scanning,
	// as it contains the base field as well as all of the units
	public char[][] render(TBoard theBoard){
		char[][] field = new char[theBoard.height][theBoard.width];
		
		// copy the base field (obstacles)
		for(int i = 0; i < theBoard.height; i++)
			for(int p = 0; p < theBoard.width; p++)
				field[i][p] = theBoard.field[i][p];
		
		// superimpose the units onto the field
		for(int z = 0; z < theBoard.playerUnits.getSize(); z++)
		{
			DSArrayList<TUnit> theArray = theBoard.playerUnits.get(z);
			
			for(int i = 0; i < theArray.getSize(); i++){
				TUnit u = theArray.get(i);
				if(!u.isDead())
					field[u.pos.getY()][u.pos.getX()] = (z == 0)? Tactics.PONE_UNIT : Tactics.PTWO_UNIT;
			}
		}
		
		return field;
	}
	
	// If the board is told to render itself...
			public char[][] render(){
				return render(this);
			}
}
