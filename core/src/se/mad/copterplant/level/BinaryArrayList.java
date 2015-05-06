package se.mad.copterplant.level;
/**
 * BinaryList implementation using arrays.
 * @author Mikael Blomstand
 * @version 2015-05-06
 */
public class BinaryArrayList {
	
	private int size;
	private int[] segments;
	
	/**
	 * Constructs a BinaryList with the specified capacity.
	 * The bits are placed in an array with integers.
	 * Every array element can hold 32 integers each.
	 * @param capacity
	 */
	public BinaryArrayList(int capacity){
		this.size=capacity;
		int length;
		
		if(capacity%32!=0){
			length = capacity/32+1;
		}
		else{
			length = capacity/32;
		}
		segments = new int[length];
	}
	
	/**
	 * If the bit at the specified index is 1, return true, else, return false.
	 * 
	 * The bit at index i is in the segment i/32 (integer division).
	 * The position in the segment is i mod 32.
	 * @param i the bits index.
	 * @return 
	 * @return the bits boolean value.
	 */
	public boolean getBoolean(int i){
		checkOutOfBounds(i);
		if (((segments[i/32] >> (i % 32)) & 1) == 1){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Sets the specified bit to 1.
	 * @param i index
	 */
	public void setTrue(int i){
		checkOutOfBounds(i);
		segments[i/32] |= (1 << (i%32));
	}
	/**
	 * Sets the specified bit to 0.
	 * @param i index
	 */
	public void setFalse(int i){
		checkOutOfBounds(i);
		segments[i/32] ^= (1 << (i%32));
	}
	
	/**
	 * Calculates and returns the position of the first bit equal to 1 in the list.
	 * @return The position of the first bit equal to 1 or -1 if all values are 0. 
	 */
	public int firstTrueBit(){
		int i = 32;
		int seg = 0;
		while(seg<size){
			i = Integer.numberOfLeadingZeros(segments[seg]);
			if(i!=32){
				return i + seg*32;
			}
			seg++;
		}
		return -1;
	}
	
	//lastBit
	
	/**
	 * Checks if i is out of the arrays bounds. 
	 * @param i Index
	 * @throws IllegalArgumentException
	 */
	private void checkOutOfBounds(int i) throws IllegalArgumentException{
		if (0<=size||size<=i){
			throw new IllegalArgumentException(size+" is out of bounds.");
		}
	}
	
}
