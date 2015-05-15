package se.mad.copterplant.level.BinaryCollection;
/**
 * BinaryList implementation using arrays.
 * @author Mikael Blomstand
 * @version 2015-05-09
 */
public class BinaryArrayList {
	
	private int size;
	private int[] segments;
	
	/**
	 * Constructs a Binary List with the specified capacity.
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
	 * @return the bits boolean value.
	 */
	public boolean getBoolean(int i){
		checkOutOfBounds(i);
		if (((segments[i/32] >>> (i % 32)) & 1) == 1){
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
	 * Sets all bits between 'from' and 'to' to true.
	 * @param from
	 * @param to
	 */
	public void setTrue(int from, int to){
		checkOutOfBounds(from);
		checkOutOfBounds(to);
		if(from/32 == to/32){
			segments[from/32] |= ((~0)<<from%32 & (~0)>>>to%32);
		}
		else{
			segments[from/32] |= ((~0)<<from%32);
			int mid = from/32 + 1;
			while(mid<to){
				segments[mid] = ~0;
				mid++;
			}
			segments[to/32] |= ((~0)>>>to%32);
		}
	}
	
	public void setAllTrue(){
		for (int i = 0; i < segments.length; i++) {
			segments[i] = ~0;
		}
	}
	
	/**
	 * Calculates and returns the position of the first bit equal to 1 in the list.
	 * @return The position of the first bit equal to 1 or -1 if all values are 0. 
	 */
	public int firstTrueBit(){
		int i = 32;
		int seg = 0;
		while(seg<segments.length){
			i = Integer.numberOfTrailingZeros(segments[seg]);
			if(i!=32){
				return i + seg*32;
			}
			seg++;
		}
		return -1;
	}
	
	/**
	 * Calculates and returns the position of the first bit equal to 0 in the list.
	 * @return The position of the first bit equal to 0 or -1 if all values are 1.
	 */
	public int firstFalseBit(){
		int i = 32;
		int seg = 0;
		while(seg<segments.length){
			i = Integer.numberOfTrailingZeros(~segments[seg]);
			if(i!=32){
				return i + seg*32;
			}
			seg++;
		}
		return -1;
	}
	
	/**
	 * Calculates and returns the position of the last bit equal to 1 in the list.
	 * @return The position of the last bit equal to 1 or -1 if all values are 0. 
	 */
	public int lastTrueBit(){
		int i = -1;
		int seg = segments.length-1;
		while(0<=seg){
			i = 31-Integer.numberOfLeadingZeros(segments[seg]);
			if(i!=-1){
				return i + seg*32;
			}
			seg--;
		}
		return -1;
	}
	
	/**
	 * Calculates and returns the position of the last bit equal to 0 in the list.
	 * @return The position of the last bit equal to 0 or -1 if all values are 1.
	 */
	public int lastFalseBit(){
		int i = -1;
		int seg = segments.length-1;
		while(0<=seg){
			i = 31-Integer.numberOfLeadingZeros(~segments[seg]);
			if(i!=-1){
				return i + seg*32;
			}
			seg--;
		}
		return -1;
	}
	
	public int firstTrueBitAfter(int fromIndex){
		try{
			checkOutOfBounds(fromIndex);
		}
		catch(Exception e){
			e.printStackTrace();
			if(fromIndex<0){
				firstTrueBit();
			}
			else{
				return -1;
			}
		}
		int i = 32;
		fromIndex++;
		int seg = fromIndex/32;
		int toCheck = (~0)<<(fromIndex%32);
		i = Integer.numberOfTrailingZeros(segments[seg]&toCheck);
		if(i!=32){
			return i + seg*32;
		}
		seg++;
		
		while(seg<segments.length){
			i = Integer.numberOfTrailingZeros(segments[seg]);
			if(i!=32){
				return i + seg*32;
			}
			seg++;
		}
		return -1;
		
	}
	
	public int lastTrueBitBefore(int fromIndex){
		try{
			checkOutOfBounds(fromIndex);
		}
		catch(Exception e){
			e.printStackTrace();
			if(fromIndex>size){
				lastTrueBit();
			}
			else{
				return -1;
			}
		}
		checkOutOfBounds(fromIndex);
		int i = -1;
		int seg = (fromIndex-1)/32;
		int toCheck = (-1)>>>(32 - (fromIndex%32));
		
		i = 31-Integer.numberOfLeadingZeros(segments[seg]&toCheck);
		if(i!=-1){
			return i + seg*32;
		}
		seg--;
		
		while(0<=seg){
			i = 31-Integer.numberOfLeadingZeros(segments[seg]);
			if(i!=-1){
				return i + seg*32;
			}
			seg--;
		}
		return -1;
	}
	
	public int firstFalseBitAfter(int fromIndex){
		try{
			checkOutOfBounds(fromIndex);
		}
		catch(Exception e){
			e.printStackTrace();
			if(fromIndex<0){
				firstTrueBit();
			}
			else{
				return -1;
			}
		}
		int i = 32;
		fromIndex++;
		int seg = fromIndex/32;
		int toCheck = (~0)<<(fromIndex%32);
		i = Integer.numberOfTrailingZeros(~(segments[seg]|~toCheck));
		if(i!=32){
			return i + seg*32;
		}
		seg++;
		
		while(seg<segments.length){
			i = Integer.numberOfTrailingZeros(~segments[seg]);
			if(i!=32){
				return i + seg*32;
			}
			seg++;
		}
		return -1;
	}

	public int lastFalseBitBefore(int fromIndex){
		try{
			checkOutOfBounds(fromIndex);
		}
		catch(Exception e){
			e.printStackTrace();
			if(fromIndex>size){
				lastTrueBit();
			}
			else{
				return -1;
			}
		}
		checkOutOfBounds(fromIndex);
		int i = -1;
		int seg = (fromIndex)/32;
		int toCheck = (~0)<<(fromIndex%32);
		
		i = 31-Integer.numberOfLeadingZeros(~(segments[seg]|toCheck));
		if(i!=-1){
			return i + seg*32;
		}
		seg--;
		
		while(0<=seg){
			i = 31-Integer.numberOfLeadingZeros(~segments[seg]);
			if(i!=-1){
				return i + seg*32;
			}
			seg--;
		}
		return -1;
	}
	
	/**
	 * Checks if i is out of the lists bounds. 
	 * @param i Index
	 * @throws IllegalArgumentException
	 */
	private void checkOutOfBounds(int i) throws IllegalArgumentException{
		if (size<=i||i<0){
			throw new IllegalArgumentException(i+" is out of bounds.");
		}
	}
	
}
