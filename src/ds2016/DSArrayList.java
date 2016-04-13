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
	private int size;
	/**
	 * Constructor
	 */
	public DSArrayList(){
		jays = (J[])(new Object[10]);
		size = 0;
	}
	
	/**
	 * Simply insert something at the end of the DSArrayList
	 * 
	 * @return The item added
	 */
	public J add(J thingToAdd){
		if(size == jays.length){ // We need to re-size the array
			System.out.println("Re-sizing " + size);
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
		return jays[idx];
	}
	
	/**
	 * Set an item to a particular value
	 * 
	 * @param idx The index of the item to change
	 */
	public void set(int idx, J newValue){}
	
	/**
	 * Remove an item from the DSArrayList and close the gap
	 * 
	 * 
	 */
	public void remove(int idx){
		size--;
		J[] newJays = (J[])(new Object[size]);
		for(int i = 0; i < idx; i++)
			newJays[i] = jays[i];
		for(int j = idx+1; j < size; j++)
			newJays[j-1] = jays[j];
		jays = newJays;
	}
	
	/**
	 * Returns the last item in the DSArrayList and removes it
	 */
	public J pop(){
		J jay = jays[-1]; 
		size--;
		J[] newJays = (J[])(new Object[size]);
		for(int i = 0; i < size; i++)
			newJays[i] = jays[i];
		jays = newJays;
		return jay;
	}
	
	public int getSize(){
		return size;
	}
	
	/**
	 * Insert an item into the Arraylist at the specified location
	 * @param idx
	 * @param thingToAdd
	 */
	public void insert(J thingToAdd, int idx){
		if(size == jays.length){ // We need to re-size the array
			int currentLength = jays.length;
			int newLength = 1 + currentLength;
			J[] newJays = (J[])(new Object[newLength]);
			// copy the old array into the new array
			for(int i = 0; i < currentLength; i++)
				newJays[i] = jays[i];
			jays = newJays;
		}
		for(int y = size-2; y > idx; y--)
			jays[y+1] = jays[y];
		jays[idx] = thingToAdd;
	}
}
