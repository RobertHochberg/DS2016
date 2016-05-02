package ds2016;

/**
 * Implementation of ArrayList
 * 
 * Resizable array of objects. Generic.
 * It's really just an array. But, we want to endow it with
 * extra functionality.
 *  
 * @author DS Students, via homework assignments
 *
 */
public class DSArrayList< J > {
	/**
	 * The backing array. Stores the (references to) objects
	 */
	private J[] jays;
	
/**
 * Number of elements in the DSArrayList, and the location
 * where the next entry should go
 */
	private int size = 0;
	
	private final int INITIAL_SIZE = 10;
	/**
	 * Constructor
	 */
	public DSArrayList(){
		jays = (J[])(new Object[INITIAL_SIZE]);
	}
	
	/**
	 * Simply insert something at the end of the DSArrayList
	 * 
	 * @return The item added
	 */
	
	public J add(J thingToAdd){
		if(size == jays.length){ // We need to re-size the array
			//System.out.println("Re-sizing " + size);
			int currentLength = jays.length;
			int newLength = 2 * currentLength;
			J[] newJays = (J[])(new Object[newLength]);
			// copy the old array into the new array
			for(int i = 0; i < currentLength; i++)
				newJays[i] = jays[i];
			jays = newJays;
		}
			
		jays[size] = thingToAdd;
		size++;
		
		
		return thingToAdd;
	}
	
	/**
	 * Returns a specific item from the array
	 * 
	 * @param idx The index of the item to return
	 */
	public J get(int idx){
		
		if(idx <= this.size && idx >= 0)
			return this.jays[idx];
		else 
			return null;
	}
	
	/**
	 * Set an item to a particular value
	 * 
	 * @param idx The index of the item to change
	 */
	public void set(int idx, J newValue){
		if(idx >= this.size || idx < 0)
			return;
		
		this.jays[idx] = newValue;
	}
	/**
	 * Remove an item from the DSArrayList and close the gap
	 * 
	 * 
	 */
	public void remove(int idx){
		// If the given index is greater than the size of the array, exit
		// Alternatively, we could throw an error
		if(idx >= this.size) return;
		
		// set the indicated spot to null
		this.jays[idx] = null;
		
		// If the index is less than size - 1, we need to rearrange the array
		if(idx < size - 1)
		{
			this.removeGaps();
		}
		else // otherwise just decrement the size, since we only removed the last entry
		{
			this.size--;
		}
	}
	
	public void removeGaps(){
		J[] tempArray = (J[])(new Object[this.jays.length]);
		
		// Keep track of how big the tempArray gets
		int p = 0;
		
		// Note: we loop to global size, not jays.length
		// This is because we know that everything after jays[size] will be null
		// And thus does not need to be copied
		
		for(int i = 0; i < this.size; i++)
		{
			if(jays[i] != null)
				tempArray[p++] = jays[i];
		}
		
		this.jays = tempArray;
		
		// Note: this method will filter out ALL null entries in the array
		// Therefore, we set size to p rather than using size-- to ensure
		// that the size is the same as the number of items in the new array
		this.size = p;
	}
	
	/**
	 * Returns the last item in the DSArrayList and removes it
	 */
	public J pop(){
		J rval = this.jays[this.size - 1];

		// call the remove method to avoid re-writing the same functionality
		this.remove(this.size - 1);
		
		return rval;
	}
	
	/**
	 * Return an array holding copies of this DSArrayList's objects
	 */
	public J[] toArray(){
		J[] rv = (J[])(new Object[size]);
		// copy into this new array
		for(int i = 0; i < size; i++)
			rv[i] = jays[i];
		return rv;
	}
	
	public int getSize(){
		return this.size;
	}
	
	/**
	 * Inserts an item at the given position and readjusts the array
	 * @param idx
	 * @param thingToInsert
	 */
	public void insert(int idx, J thingToInsert)
	{
		if(idx == size)
			// we already have a function for inserting something at the end of the list
			this.add(thingToInsert);
		else if(idx > size || idx < 0) // exit if index out of bounds
			return;
		
		int p = 0;
		
		J[] tempArray = (J[])(new Object[this.jays.length + 1]);
		
		for(int i = 0; i < this.size + 1; i++)
		{
			if(i == idx)
			{
				tempArray[p++] = thingToInsert;
				tempArray[p++] = this.jays[i];
			}
			else
				tempArray[p++] = this.jays[i];
		}
		
		this.jays = tempArray;
		this.size = p - 1;
	}
}
