package ds2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
	
	public char[][] loadField(String filepath)
	{
		ArrayList<ArrayList<Character>> field = new ArrayList<>();
		
		boolean abort = false;
		
		try { 
			FileReader f = new FileReader(filepath);
			BufferedReader reader = new BufferedReader(f);
			String line = reader.readLine();
			while (line != null) {	
				String[] parts = line.split(" ");
				field.add(new ArrayList<>());
				for(int i = 0; i < parts.length; i++){
					if(parts[i].length() > 0)
						field.get(field.size()-1).add(parts[i].toCharArray()[0]);
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
			abort = true;
		}
		
		if(abort) return new char[0][0];
		
		char[][] rv = new char[field.size()][field.get(0).size()];
		
		for(int x = 0; x < field.size(); x++)
		{
			ArrayList<Character> line = field.get(x);
			for(int y = 0; y < line.size(); y++)
			{
				char c = line.get(y);
				// 0 means a blank space
				if(c == '0')
					rv[x][y] = Tactics.BLANK_SPACE;
				else
					rv[x][y] = c;
			}
		}
		
		return rv;
	}
	
	public int nextTurn(){
		this.whoseTurn = (this.whoseTurn == 1)? 2 : 1;
		
		return whoseTurn;
	}
	
	public int whoIsNext(){
		return (this.whoseTurn == 1)? 2 : 1;
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
		
		for(int i = 0; i < player1Units.getSize(); i++)
			if(!player1Units.get(i).isDead())
				p1units.add(player1Units.get(i));
		
		for(int i = 0; i < player2Units.getSize(); i++)
			if(!player2Units.get(i).isDead())
				p2units.add(player2Units.get(i));
		
		player1Units = p1units;
		player2Units = p2units;
		
	}
	
	public int howMuchOverwatch(TUnit unit, Coord targetPos, DSArrayList<TUnit> enemyList)
	{
	
		int amount = 0;
		
		
		if(!targetPos.sameAs(unit.getPos()))
			for(int i = 0; i < enemyList.getSize(); i++)
			{
				TUnit enemy = enemyList.get(i);
				if(enemy.isDead())
					continue;
				if(enemy.isInFiringRange(unit.getPos()) && this.lineOfSight(enemy.getPos(), unit.getPos()))
					amount++;
				else
				{
					Coord oldPos = new Coord(unit.getPos().getX(), unit.getPos().getY());
					unit.moveTo(targetPos);
					
					if(enemy.isInFiringRange(unit.getPos()) && this.lineOfSight(enemy.getPos(), unit.getPos()))
						amount++;
					
					unit.moveTo(oldPos);
				}
			}
		
		return amount;
		
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
				if(u.isDead())
					continue;
				
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
		
		ArrayList<Coord> lastRv = new ArrayList<Coord>();
		
		for(Coord space : rv)
		{
			boolean alreadyAdded = false;
			for(Coord other : lastRv)
			{
				if(space.sameAs(other))
				{
					alreadyAdded = true;
					break;
				}
			}
			if(!alreadyAdded)
				lastRv.add(space);
		}
		
		return lastRv;
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
		
		boolean adjacent = (Math.max(Math.abs(a-c), Math.abs(b-d)) == 1);
		
		if(adjacent)
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
