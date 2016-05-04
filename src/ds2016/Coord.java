package ds2016;

public class Coord 
{
	int x;
	int y;
	
	public Coord()
	{
		x = 0;
		y = 0;
	}
	
	public Coord(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public void set(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void shift(int x, int y)
	{
		this.x += x;
		this.y += y;
	}
	
	public Coord newShift(int x, int y)
	{
		Coord rv = new Coord(this.x, this.y);
		
		rv.shift(x, y);
		
		return rv;
	}
	
	public boolean sameAs(Coord other)
	{
		return (other.getX() == x && other.getY() == y);
	}
	
	@Override public String toString(){
		return "(" + this.x + ", " + this.y + ")";
	}
	
}
