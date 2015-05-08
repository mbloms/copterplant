package se.mad.copterplant.level;

public class BinaryArrayMatrix {
	private int size;
	private BinaryArrayList[] rows;
	
	/**
	 * Constructs a Binary Matrix with the specified capacity.
	 * The rows are represented by Binary Lists.
	 * These rows are then placed in an array.
	 * @param capacity
	 */
	public BinaryArrayMatrix(int capacity){
		this.size = capacity;
		rows = new BinaryArrayList[capacity];
		for (int i = 0; i < rows.length; i++) {
			rows[i] = new BinaryArrayList(capacity);
		}
	}
	
	public boolean getBoolean(int i, int j){
		return rows[i].getBoolean(j);
	}
	
	public void setTrue(int i, int j){
		rows[i].setTrue(j);
	}
	
	public void setFalse(int i, int j){
		rows[i].setFalse(j);
	}
}
