package se.mad.copterplant.level;

import java.util.LinkedList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;

import se.mad.copterplant.level.BinaryCollection.BinaryArrayList;
import se.mad.copterplant.level.BinaryCollection.BinaryArrayMatrix;
import se.mad.copterplant.screens.GameScreen;

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

	//TODO: Fix comment
	public boolean isFilled(int x, int y){
		return matrix.getBoolean(y, x);
	}

	public void fillBlock(int x, int y){
		matrix.setTrue(y, x);
	}

	public void areaFill(int count){
		
	}

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
				System.out.println(delta);

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
		
		areaFill(counter);
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