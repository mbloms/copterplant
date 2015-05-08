package se.mad.copterplant.level;

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
		matrix = new BinaryArrayMatrix(height, width);
	}
	
	public boolean isFilled(int x, int y){
		return matrix.getBoolean(y, x);
	}
	
	public void areaFill(){
		
	}
	
	public void fillTrack(){
		
	}
	
	public void parseString(){
		
	}
	
	/**
	 * Calculates how much of the map is filled.
	 * @return A double with the quote "filled area" divided by "total area".
	 */
	public double percentageFilled(){
		
	}
}