package se.mad.copterplant.level;

import se.mad.copterplant.level.BinaryCollection.BinaryArrayList;
import se.mad.copterplant.level.BinaryCollection.BinaryArrayMatrix;

/**
 * Sry för svenska David.
 * Kodskelett för spelplanen.
 * 
 * Be careful to note the difference in standards when referring to an element
 * in a matrix and referring to a position in a coordinate system. 
 * @author Mikael Blomstrand
 *
 */
public class LevelMap {
	BinaryArrayMatrix matrix;
	int width;
	int height;
	
	public LevelMap(int width, int height){
		this.width = width;
		this.height = height;
		matrix = new BinaryArrayMatrix(height, width);
	}
	
	/**
	 * Use with caution! Returns A POINTER to the list element.
	 * Changes to it will affect the list in the matrix.
	 * @param i The row to return
	 * @return A pointer to the specified list object.
	 */
	public BinaryArrayList getRowList(int i){
		return matrix.getRowList(i);
	}
	
	public boolean isFilled(int x, int y){
		return matrix.getBoolean(y, x);
	}
	
	public void areaFill(){
		//TODO
	}
	
	public void fillTrack(){
		//TODO
	}
	
	public void parseString(){
		//TODO
	}
	
	/**
	 * Calculates how much of the map is filled.
	 * @return A double with the quote "filled area" divided by "total area".
	 */
	public double percentageFilled(){
		//TODO
		
		return -1.0;
	}
}