package ds2016;

public class TUnit 
{
	
	public final static String[] maleNameList = {"Cosmos", "Damien", "William", "Gunther", "Vance", "Naruhoudo", "Tanaka", "Bob", "Simon"};
	public final static String[] femaleNameList ={"Aerith", "Tomoko", "Karin", "Natalie", "Cassandra", "Artemis", "Venus", "Nia"};
	public final static String[] lastNameList ={"Grant", "Nova", "Yoshimori", "Yamato", "Law", "Judge", "Pope", "Spencer", "Lancaster"};
	
	public final static int HP       = 0;
	public final static int AIM      = 1;
	public final static int MOVEMENT = 2;
	public final static int ARMOR    = 3;
	public final static int DEFENSE  = 4;
	public final static int CRIT     = 5;
	public final static int POWER    = 6;
	public final static int RANGE    = 7;
	public final static int PROX     = 8;
	
	public final static int NUM_STATS = 9;
	
	String name;
	
	boolean overwatchActive;
	boolean hunkerdownActive;
	
	// note: currently unused...
	int totalActions;
	private int remainingActions;
	
	int[] uStats;
	
	int damage = 0;
	
	Coord pos = new Coord(0, 0);
	
	int inCover = 0; // 0 = no cover, 1 = half, 2 = full
	DSArrayList<Compass> coverDir = new DSArrayList<Compass>(); // direction(s) protected by cover
	DSArrayList<Integer> coverLvl = new DSArrayList<Integer>();
	
	public TUnit(){
	uStats = new int[NUM_STATS];
	
	uStats[HP]       = 5;
	uStats[AIM]      = 60;
	uStats[MOVEMENT] = 4;
	uStats[ARMOR]    = 0;
	uStats[DEFENSE]  = 0;
	uStats[CRIT]     = 20;
	uStats[POWER]    = 3;
	uStats[RANGE]    = 6;
	uStats[PROX]     = 30; // positive means bonus when getting close, negative means penalty when getting close
	
	totalActions = 10;
	remainingActions = totalActions;
	}
	
	public int getStat(int which)
	{
		int rv = 0;
		if(which < NUM_STATS && which >= 0)
			rv = uStats[which];
		return rv;
	}
	
	public void setStat(int which, int amount)
	{
		if(which < NUM_STATS && which >= 0)
			this.uStats[which] = amount;
	}
	
	public void overwatch(){
		this.overwatchActive = true;
	}
	
	public void hunkerdown(){
		this.hunkerdownActive = false;
	}
	
	public void resetState(){
		this.overwatchActive  = false;
		this.hunkerdownActive = false;
	}
	
	public int getActions()
	{
		return remainingActions;
	}
	
	public void decreaseActions()
	{
		decreaseActions(1);
	}
	
	public void decreaseActions(int amount)
	{
		this.remainingActions -= amount;
		
		if(remainingActions <= 0)
			remainingActions = 0;
	}
	
	public void resetActions()
	{
		this.remainingActions = this.totalActions;
	}
	
	public boolean isDead()
	{
		boolean rv = false;
		if(damage >= uStats[HP])
			rv = true;
		return rv;
	}
	
	public boolean isInFiringRange(Coord pos)
	{
		return !(distanceTo(pos) > getStat(RANGE)); 
	}
	
	public boolean isInFiringRange(int x, int y)
	{
		return isInFiringRange(new Coord(x, y));
	}
	
	public boolean isInRange(int x, int y)
	{		
		return isInRange(new Coord(x, y));
	}
	
	public boolean isInRange(Coord pos)
	{
		return !(distanceTo(pos) > getStat(MOVEMENT));
	}
	
	/**
	 * isFlankedBy
	 * 
	 * Tells us whether the unit would be flanked by an enemy if the enemy's position is (x, y)
	 * Uses an alternate version of the same method to check against each individual flank
	 * for cases when a unit is protected by multiple cover directions, and combine the results with "and".
	 * @param x
	 * @param y
	 * @return
	 */
	
	public boolean isFlankedBy(int x, int y){
		boolean rv = true;
		if(inCover == 0)
			rv = false;
		else
		{
			for(int i = 0; i < coverDir.getSize(); i++)
			{
				rv = (rv && isFlankedBy(x, y, coverDir.get(i)));	
			}	
		}
		
		// Checking for "flank through corners" incident
		// Probably not the best way...
		
		if(adjacentTo(new Coord(x, y)))
		{
			
			int flag = 0;
			if(x > pos.getX())
			{
				if(y > pos.getY())
				{
					for(int i = 0; i < coverDir.getSize(); i++)
						if(coverDir.get(i) == Compass.SOUTH || coverDir.get(i) == Compass.EAST)
							flag++;
						
				}
				else if(y < pos.getY())
				{
					for(int i = 0; i < coverDir.getSize(); i++)
						if(coverDir.get(i) == Compass.NORTH || coverDir.get(i) == Compass.EAST)
							flag++;
				}
						
			}
			else if(x < pos.getX())
			{
				if(y > pos.getY())
				{
					for(int i = 0; i < coverDir.getSize(); i++)
						if(coverDir.get(i) == Compass.SOUTH || coverDir.get(i) == Compass.WEST)
							flag++;
						
				}
				else if(y < pos.getY())
				{
					for(int i = 0; i < coverDir.getSize(); i++)
						if(coverDir.get(i) == Compass.NORTH || coverDir.get(i) == Compass.WEST)
							flag++;
				}
						
			}
			
			if(flag > 1)rv = false;
		}
		
		return rv;
	}
	
	/**
	 * Tells us if this unit is flanked by a given unit
	 * @param unit
	 * @return
	 */
	
	public boolean isFlankedBy(TUnit unit){
		return isFlankedBy(unit.getPos().getX(), unit.getPos().getY());
	}
	
	public boolean isFlankedBy(int x, int y, Compass coverDir)
	{
		boolean rv = false;
		
		if(inCover == 0) // if unit not in cover, they aren't flanked (just out in the open)
			rv = false;
		else if(adjacentTo(new Coord(x, y)))
			rv = true;
		else if(coverDir == Compass.NORTH)
		{
			if(y >= this.pos.getY())
				rv = true;
		}
		else if(coverDir == Compass.SOUTH)
		{
			if(y <= this.pos.getY())
				rv = true;
		}
		else if(coverDir == Compass.WEST)
		{
			if(x >= this.pos.getX())
				rv = true;
		}
		else if(coverDir == Compass.EAST)
		{
			if(x <= this.pos.getX())
				rv = true;
		}
		
		
		return rv;
	}
	
	public boolean adjacentTo(TUnit unit)
	{
		return adjacentTo(unit.getPos());
	}
	
	public boolean adjacentTo(Coord pos)
	{
		boolean rv = false;
		
		int x1, y1, x2, y2;
		x1 = this.pos.getX();
		y1 = this.pos.getY();
		
		x2 = pos.getX();
		y2 = pos.getY();
		if(Math.max(Math.abs(x1-x2), Math.abs(y1-y2)) == 1)
			rv = true;
			
		return rv;
	}
	
	public int chanceToHit(TUnit target)
	{
		int rv;
		
		Compass dir1, dir2;
		
		if(pos.getX() == target.getPos().getX())
			dir1 = null;
		else if(pos.getX() < target.getPos().getX())
			dir1 = Compass.WEST;
		else 
			dir1 = Compass.EAST;
		
		if(pos.getY() == target.getPos().getY())
			dir2 = null;
		else if(pos.getY() < target.getPos().getY())
			dir2 = Compass.NORTH;
		else
			dir2 = Compass.SOUTH;
		
		double hunkerdownBonus = (hunkerdownActive)? Tactics.HUNKERDOWN_MULT : 1.0;
		
		
		int coverBonus;
		
		// Reaction fire ignores cover, taking a fixed aim penalty instead
		if(overwatchActive)
			coverBonus = Tactics.REACTION_PENALTY;
		else
		{
			// If flanked, no cover bonus
			coverBonus =(target.isFlankedBy(this))? 
					0 :
						(int)(Math.max(target.getCoverBonus(dir1), target.getCoverBonus(dir2)) * hunkerdownBonus);
		}
		
		int d = (adjacentTo(target))? 0 : (distanceTo(target) - 1);
		
		int proxBonus = (int)(Math.max(Math.abs(this.getStat(PROX)) - 
				(Tactics.PROX_BONUS_FALLOFF * d), 0) * Math.signum(this.getStat(PROX)));
		
		/*
		 * Take the base aim plus proximity bonus (decreases as distance to target increases)...
		 * Subtract the enemy's defense stat and cover bonus
		 * 
		 */
		
		rv = this.getStat(AIM) + proxBonus - target.getStat(DEFENSE) - coverBonus;
		
		return rv;
	}
	
	public int chanceToCrit(TUnit enemy)
	{
		
		int rv = this.uStats[CRIT] - enemy.uStats[DEFENSE];
		
		if(enemy.isFlankedBy(this))
			rv += Tactics.FLANK_CRIT_BONUS;
		else if(enemy.hunkerdownActive && (enemy.inCover > 0))
			rv = 0;
		
		return rv;
	}
	
	public void checkCover(TBoard board){
		// Reset cover
		inCover  = 0;
		coverDir = new DSArrayList<Compass>();
		coverLvl = new DSArrayList<Integer>();
		
		Coord[] spacesToCheck = new Coord[4];
		
		spacesToCheck[0] = pos.newShift( 0, -1); // up
		spacesToCheck[1] = pos.newShift( 0,  1); // down
		spacesToCheck[2] = pos.newShift(-1,  0); // left
		spacesToCheck[3] = pos.newShift( 1,  0); // right
		
		//System.out.println("pos: " + pos.getX() + ", " + pos.getY());
		
		Compass[] dirs = new Compass[4];
		
		dirs[0] = Compass.NORTH;
		dirs[1] = Compass.SOUTH;
		dirs[2] = Compass.WEST;
		dirs[3] = Compass.EAST;
		
		int i = 0;
		
		for(Coord space: spacesToCheck)
		{
			//System.out.println("check: " + space.getX() + ", " + space.getY());
			if(space.getY() >= board.height || space.getX() >= board.width || space.getY() < 0 || space.getX() < 0)
				continue;
			
			char target = board.field[space.getY()][space.getX()];
			//System.out.println("Char: " + target);
			int thisCover = 0;
			
			if(target == Tactics.HALF_COVER)
				thisCover = 1;
			else if(target == Tactics.FULL_COVER)
				thisCover = 2;
			
			if(inCover < thisCover)
				inCover = thisCover;
			
			if(thisCover > 0) // if there's cover in this direction, add it to the list
			{
				coverDir.add(dirs[i]);
				coverLvl.add(thisCover);
			}
			
			i++;
		}
		
	}
	
	public int getCoverBonus(Compass dir)
	{
		int rv = 0;
		
		if(dir != null)
		{
			for(int i = 0; i < coverDir.getSize(); i++)
			{
				if(coverDir.get(i) == dir)
				{
					rv = (coverLvl.get(i) == 2)? Tactics.FULL_COVER_BONUS : Tactics.HALF_COVER_BONUS;
					break;
				}
			}
		}
		return rv;
	}
	
	public void moveTo(int x, int y)
	{
		this.pos.set(x, y);
	}
	
	public void moveTo(Coord location)
	{
		this.pos.set(location.getX(), location.getY());
	}
	
	public void moveBy(int x, int y)
	{
		this.pos.shift(x, y);
	}
	
	public void shootAt(TUnit enemy)
	{
		int rand = Tactics.randRange(0, 100);
		
		int chanceToHit = this.chanceToHit(enemy);
		
		System.out.printf(Tactics.MSG_SHOT, name, enemy.name, chanceToHit);
		
		if(Tactics.DEBUG_MESSAGES)
			System.out.printf("(Rolled a %d)", rand);
		
		// miss
		if(rand >= chanceToHit)
		{
			System.out.printf(Tactics.MSG_MISS);
			return;
		}
		
		// reroll 
		rand = Tactics.randRange(0, 100);
		
		int chanceToCrit = chanceToCrit(enemy);
		
		boolean crit = (rand <= chanceToCrit)? true : false;
		
		if(crit)
			System.out.printf(Tactics.MSG_CRIT);
		
		int damageDealt = (int)(this.uStats[POWER] * (crit? Tactics.CRIT_MULTIPLIER : 1.0));
		
		enemy.takeDamage(damageDealt);
		
		// Hitting an enemy removes their overwatch
		if(enemy.overwatchActive)
		{
			enemy.resetState();
			
			System.out.printf(Tactics.MSG_OVERWATCH_REMOVED, enemy.name);
		}
		
		if(enemy.getHP()<=0)
			System.out.printf(Tactics.MSG_DEATH, enemy.name);
	}
	
	public void takeDamage(int amount)
	{
		this.damage += amount;
		System.out.printf(Tactics.MSG_DAMAGE, name, amount, this.getHP());
	}
	
	public int getHP()
	{
		return Math.max(this.uStats[HP] - this.damage, 0);
	}
	
	public Coord getPos()
	{
		return pos;
	}
	
	public int distanceTo(TUnit unit)
	{
		return distanceTo(unit.getPos());
	}
	
	public int distanceTo(Coord pos)
	{
		int x1, y1, x2, y2;
		x1 = pos.getX();
		y1 = pos.getY();
		
		x2 = this.pos.getX();
		y2 = this.pos.getY();
		
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
	
	public static int distanceTo(Coord pos1, Coord pos2)
	{
		int x1, y1, x2, y2;
		x1 = pos1.getX();
		y1 = pos1.getY();
		
		x2 = pos2.getX();
		y2 = pos2.getY();
		
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
	
}
