package se.mad.copterplant.level.BinaryCollection;

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
	
	/**
	 * If the specified bit in the matrix 1, return true, else, return false.
	 * @param i The bits row.
	 * @param j The bits column.
	 * @return the bits boolean value.
	 */
	public boolean getBoolean(int i, int j){
		checkOutOfBounds(i, j);
		return rows[i].getBoolean(j);
	}
	
	/**
	 * Set the bit in row i, column j to 1.
	 * @param i The bits row.
	 * @param j The bits column.
	 */
	public void setTrue(int i, int j){
		checkOutOfBounds(i, j);
		rows[i].setTrue(j);
	}
	
	/**
	 * Set the bit in row i, column j to 0.
	 * @param i The bits row.
	 * @param j The bits column.
	 */
	public void setFalse(int i, int j){
		checkOutOfBounds(i, j);
		rows[i].setFalse(j);
	}
	
	/**
	 * Checks if i or j is out of the lists bounds. 
	 * @param i row
	 * @param j column
	 * @throws IllegalArgumentException
	 */
	private void checkOutOfBounds(int i, int j) throws IllegalArgumentException{
		if (size<=i||i<0){
			throw new IllegalArgumentException(i+" is out of bounds.");
		}
		else if (size<=j||j<0){
			throw new IllegalArgumentException(j+" is out of bounds.");
		}
	}
}