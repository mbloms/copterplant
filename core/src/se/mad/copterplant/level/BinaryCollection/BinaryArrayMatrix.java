package se.mad.copterplant.level.BinaryCollection;

/**
 * @author Mikael Blomstrand
 * @version 2015-05-09
 */
public class BinaryArrayMatrix {
	private int m;
	private int n;
	private BinaryArrayList[] rows;
	
	/**
	 * Constructs a Binary Matrix with the specified capacity.
	 * The rows are represented by Binary Lists.
	 * These rows are then placed in an array.
	 * @param capacity
	 */
	public BinaryArrayMatrix(int capacity){
		this.m = capacity;
		this.n = capacity;
		rows = new BinaryArrayList[capacity];
		for (int i = 0; i < rows.length; i++) {
			rows[i] = new BinaryArrayList(capacity);
		}
	}
	
	/**
	 * Constructs a Binary Matrix with the specified number of columns and rows.
	 * The rows are represented by Binary Lists.
	 * These rows are then placed in an array.
	 * @param n Number of columns
	 * @param m Number of rows
	 */
	public BinaryArrayMatrix(int m, int n){
		this.m = m;
		this.n = n;
		rows = new BinaryArrayList[m];
		for (int i = 0; i < rows.length; i++) {
			rows[i] = new BinaryArrayList(n);
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
		if (m<=i||i<0){
			throw new IllegalArgumentException(i+" is out of bounds.");
		}
		else if (n<=j||j<0){
			throw new IllegalArgumentException(j+" is out of bounds.");
		}
	}
}