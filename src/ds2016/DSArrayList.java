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
		int i = 0;
		for (i = idx; i < this.jays.length; i++){
			if (this.jays[i += 1] == null){
				this.jays[i] = null;
			}
			else{
			this.jays[i] = this.jays[i += 1];
			}
		}
	}

	/**
	 * Removes the last item in DSArray list
	 * Returns the last item*/
	public J pop(){
	int x = this.jays.length - 1;
    J y = this.jays[x];
	remove(x);
	return y;
	}
	
	public void insert(int idx, J var){
		int i = 0;
		for (i = idx; i < this.jays.length; i ++){
			this.jays[i += 1] = this.jays[i];
		}
		this.jays[idx] = var;
	}
}
