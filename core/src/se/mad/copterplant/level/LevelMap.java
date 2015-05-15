package se.mad.copterplant.level;

import java.util.LinkedList;
import java.util.Stack;
import se.mad.copterplant.level.BinaryCollection.BinaryArrayMatrix;
import com.badlogic.gdx.math.Vector2;

/**
 * Sry för svenska David.
 * Kodskelett för spelplanen.
 * 
 * Be careful to note the difference in standards when referring to an element
 * in a matrix and referring to a position in a coordinate system. 
 * @author Mikael Blomstrand
 *
 */
public class LevelMap extends BinaryArrayMatrix{
	int width;
	int height;
	VisualMap vMap;
	
	BinaryArrayMatrix matrix;
	
	public LevelMap(int width, int height, VisualMap vMap){
		super(height, width);
		
		this.width = width;
		this.height = height;
		this.vMap = vMap;
	}

	//TODO: Fix comment
	public boolean isFilled(int x, int y){
		try{
			return getBoolean(y, x);
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Fills the block with the given position.
	 * Returns true if the block was successfully filled. False if it failed.
	 * @param x
	 * @param y
	 * @return true if block was filled.
	 */
	public boolean fillBlock(int x, int y){
		try{
			setTrue(y, x);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return isFilled(x,y);
	}
	
	private class intVector{
		public int x;
		public int y;
		
		public intVector(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	
	public void areaFill(int x, int y){
		BinaryArrayMatrix filledMap = new BinaryArrayMatrix(height, width);
		filledMap.setAllTrue();
		
		Stack<intVector> stack = new Stack<intVector>();
		
		/*
		 * Fyll rad med utgångspunkt i P.
		 * Lägg första tomma blocket över och under raden i stacken.
		 * För alla fyllda block som följs av ett tomt block: lägg det tomma blocket i stacken. (Ovan och under strecket)
		 */
		
		stack.push(new intVector(x, y));
		int first;
		int last;
		int row;
		intVector current;
		
		while(!stack.isEmpty()){
			current = stack.pop();
			first = rows[current.y].lastFalseBitBefore(current.x)+1;
			last = rows[current.y].firstFalseBitAfter(current.x)-1;
			rows[current.y].setTrue(first, last);
		}
		
		
	}
	
	private void scanlineFill(BinaryArrayMatrix matrix, int x, int y){
		
	}

	/**
	 * Fills the path between corners in the input arrays.
	 * @param x An array with the x coordinates of the points where a turn was made.
	 * @param y An array with the y coordinates of the points where a turn was made.
	 */
	/*
	public void fillTrack(int[] x, int[] y){
*/

	public void fillTrack(LinkedList<Vector2> path){
		System.out.println("Began");
		int counter = 0;
		for (int i = 0; i<path.size()-1;i++) {
			Vector2 pos = path.get(i);
			Vector2 next = path.get(i+1);


			Vector2 gridPos = new Vector2((int)(pos.x/32),(int)(pos.y/32));
			Vector2 gridNext = new Vector2((int)(next.x/32),(int)(next.y/32));
			if (VisualMap.BoundsRect.contains(pos) && VisualMap.BoundsRect.contains(next)) {
				Vector2 delta = gridNext.sub(gridPos);
				gridPos.sub(10,3);
				

				if (delta.x > 0) {
					for (int x =0 ; x<Math.abs(delta.x); x++) {
						System.out.println(gridPos.x+x);
						fillBlock((int)gridPos.x + x, (int)gridPos.y);
						counter++;
					}
				}else {

					for (int x =0 ; x<Math.abs(delta.x); x++) {
						System.out.println(gridPos.x-x);
						fillBlock((int)gridPos.x-x, (int)gridPos.y);
						counter++;
					}
				}
				if (delta.y > 0) {
					for (int y =0 ; y<Math.abs(delta.y); y++) {
						System.out.println(gridPos.y+y);
						fillBlock((int)gridPos.x, (int)gridPos.y + y);
						counter++;
					}
				}else {
					for (int y =0 ; y<Math.abs(delta.y); y++) {
						System.out.println(gridPos.y-y);
						fillBlock((int)gridPos.x, (int)gridPos.y - y);
						counter++;
					}	
				}
			}
		}
		
		vMap.updateBoundingBoxes();
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