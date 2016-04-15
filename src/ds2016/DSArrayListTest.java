package ds2016;

/**
 * Implementation of ArrayList
 * 
 * Resizable array of objects. Generic. It's really just an array. But, we want
 * to endow it with extra functionality.
 * 
 * @author DS Students, via homework assignments
 *
 */
public class DSArrayListTest<J> {
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
	public DSArrayListTest() {
		jays = (J[]) (new Object[10]);
		size = 0;
	}

	/**
	 * Simply insert something at the end of the DSArrayList
	 * 
	 * @return The item added
	 */
	public J add(J thingToAdd) {
		if (size == jays.length) { // We need to re-size the array
			System.out.println("Re-sizing " + size);
			int currentLength = jays.length;
			int newLength = 2 * currentLength;
			J[] newJays = (J[]) (new Object[newLength]);
			// copy the old array into the new array
			for (int i = 0; i < currentLength; i++)
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
	 * @param idx
	 *            The index of the item to return
	 */
	public J get(int idx) {
		return jays[idx];
	}

	/**
	 * Set an item to a particular value
	 * 
	 * @param idx
	 *            The index of the item to change
	 */
	public void set(int idx, J newValue) {
	}

	/**
	 * Remove an item from the DSArrayList and close the gap
	 * 
	 * 
	 */
	public void remove(int idx) {
		J[] newJays = (J[]) (new Object[jays.length]);
		if (idx > 0 && idx < jays.length) {
			for (int i = 0; i < idx; i++) {
				if (i != idx) {
					newJays[i] = jays[i];
				}
			}
		}
		jays = newJays;
		size--;
	}

	/**
	 * Returns the last item in the DSArrayList and removes it
	 * 
	 * @return
	 */
	public J pop() {
		J lastItem = jays[jays.length];
		J[] newJays = (J[]) (new Object[jays.length]);
		for (int i = 0; i < jays.length - 1; i++) {
			newJays[i] = jays[i];
		}
		jays = newJays;
		size--;
		return lastItem;
	}

	public int getSize() {
		return size;
	}

	/**
	 * Insert an item into the Arraylist at the specified location
	 * 
	 * @param idx
	 * @param thingToAdd
	 */
	public void insert(J thingToAdd, int idx) {
		if (idx > 0 && idx < jays.length) {
			int currentLength = jays.length;
			int newLength = currentLength++;
			J[] newJays = (J[]) (new Object[newLength]);
			for (int i = 0; i < newLength; i++) {
				if (idx != i) {
					newJays[i] = jays[i];
				} else {
					newJays[i] = thingToAdd;
				}
			}
			jays = newJays;
		}
	}
}
