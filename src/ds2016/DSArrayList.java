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
	 * Constructor
	 */
	public DSArrayList(){
		jays = (J[])(new Object[10]);
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
<<<<<<< HEAD
		J[] newJays = (J[])(new Object[jays.length]);
		if(idx > 0 && idx < jays.length){
			for(int i = 0; i < idx; i++){
			if(i != idx){
				newJays[i] = jays[i];
				}
			}	
		}
		jays = newJays;
		size--;
	}	
=======
		for (int i = idx; i < this.jays.length - 1; i++){
			this.jays[i] = this.jays[1 + i];
		}
		this.jays [this.jays.length - 1] = null;
	}

>>>>>>> master
	/**
	 * Returns the last item in the DSArrayList and removes it
	 * @return 
	 */
	public J pop(){
<<<<<<< HEAD
		J lastItem = jays[jays.length];
		J[] newJays = (J[])(new Object[jays.length]);
		for(int i = 0; i < jays.length - 1; i++){
			newJays[i] = jays[i];
		}
		jays = newJays;
		size--;
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
	public void insert(J thingToAdd, int idx){
		if(idx > 0 && idx < jays.length){
			int currentLength = jays.length;
			int newLength = currentLength++;
			J[] newJays = (J[])(new Object[newLength]);
			for(int i = 0; i < newLength; i++){
				if(idx != i){
					newJays[i] = jays[i];
				}
				else{
					newJays[i] = thingToAdd;
				}
			}
			jays = newJays;
		}
=======
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
>>>>>>> master
	}
}
