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
	 * Number of elements in the DSArrayList, and the location where the next
	 * entry should go
	 */
	private int size;

	/**
	 * Constructor
	 */
	public DSArrayList() {
		size = 10;
		jays = (J[]) (new Object[size]);
	}

	/**
	 * Simply insert something at the end of the DSArrayList
	 */
	public void add(J thingToAdd){
		for (int i = 0; i < this.jays.length; i++){
			if (this.jays[i] == null) {
				this.jays[i] = thingToAdd;
				break;
			}
		}

	}

	/**
	 * Returns a specific item from the array
	 * 
	 * @param idx The index of the item to return
	 */
	public J get(int idx){
		return this.jays[idx];
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
		for (int i = idx; i < this.jays.length - 1; i++){
			this.jays[i] = this.jays[1 + i];
		}
		this.jays [this.jays.length - 1] = null;
	}

	/**
	 * Returns the last item in the DSArrayList and removes it
	 * @return 
	 */
	public J pop(){
		J lastItem = jays[jays.length - 1];
		J[] newJays = (J[])(new Object[jays.length]);
		for(int i = 0; i < jays.length - 1; i++){
			newJays[i] = jays[i];
		}
		size--;
		jays = newJays;
		return lastItem;

	}
	
	public int getSize(){
		return size;
	}
	
	/**
	 * Insert an item into the Arraylist at the specified location
	 * @param idx
	 * @param thingToAdd
	 */
	public J insert(J thingToAdd, int idx){
		J rval = null;
		for (int i = 0; i < this.jays.length - 1; i++){
			if (this.jays[i + 1] == null) {
				rval = this.jays[i];
				this.jays[i] = null;
				break;
				}
		}
		if (rval == null) {
			rval = this.jays [this.jays.length - 1];
			this.jays [this.jays.length - 1] = null;
		}
		return rval;
	}
	
	/**
	 * 	 
	 **/
	public J[] toArray(){
		J[] rv = (J[])(new Object[size]);
		// copy into this new array
		for(int i = 0; i < size; i++)
			rv[i] = jays[i];
		return rv;
	}
}
