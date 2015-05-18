package se.mad.copterplant.level;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * This is the visual representation of the leveldata stored in a backend 
 * instance of the LevelMap class. 
 * @author DavidSkeppstedt
 *
 */
public class VisualMap {

	public static int TILE_SIZE = 32;
	public static int MAP_WIDTH = 20;
	public static int MAP_HEIGTH = 20;
	public static int X_OFFSET = 10;
	public static int Y_OFFSET = 3;
	public LevelMap map;
	public static Rectangle BoundsRect;
	private ArrayList<Rectangle> boundingBoxes;
	Texture pixmaptex;
	SpriteBatch spritebatch;

	public VisualMap() {
		boundingBoxes = new ArrayList<>();
		map = new LevelMap(20, 20,this);
		BoundsRect = new Rectangle(X_OFFSET*TILE_SIZE,Y_OFFSET*TILE_SIZE,
				MAP_WIDTH*TILE_SIZE,MAP_HEIGTH*TILE_SIZE);
		
		for (int x = 0; x<MAP_WIDTH; x++) {
			map.fillBlock(x, MAP_HEIGTH-1); // in my eyes this is essentialy zero in an array but everything is backwards.
			map.fillBlock(x, 0); // in my eyes this is MAP_HEIGTH-1 but everything is bakwards.
			//map.fillBlock(x, 1); // in my eyes this is MAP_HEIGTH-1 but everything is bakwards.
		}
		
		for (int y = 1; y<MAP_HEIGTH-1; y++) {
			map.fillBlock(0, y); // in my eyes this is essential zero in an array but everything is backwards.
			map.fillBlock(MAP_WIDTH-1, y); // in my eyes this is MAP_HEIGTH-1 but everything is bakwards.
		}
		
		
		map.fillBlock(10, 10);
		
		updateBoundingBoxes();
	}
	
	
	public ArrayList<Rectangle> getBoundingBoxes(){
		return boundingBoxes;
	}
	
	public void updateBoundingBoxes(){
		boundingBoxes.clear();
		for (int x = 0; x < MAP_WIDTH; x++) {
			for (int y = 0; y<MAP_HEIGTH;y++){
				if (map.isFilled(x, y)) {
					boundingBoxes.add(new Rectangle(X_OFFSET*TILE_SIZE+x*TILE_SIZE,Y_OFFSET*TILE_SIZE+y*TILE_SIZE,TILE_SIZE,TILE_SIZE));
				}
			}
		}
	}
	
	/**
	 * Draws the map using a tile size of 32 pixels.
	 * Will render the grid with rectangles.
	 * @param renderer
	 */
	public void draw(ShapeRenderer renderer){
		//TODO Draw collision box
		//renderer.setColor(Color.GREEN);
		//renderer.begin(ShapeType.Line);
		//renderer.rect(X_OFFSET*TILE_SIZE,Y_OFFSET*TILE_SIZE,
		//		MAP_WIDTH*TILE_SIZE,MAP_HEIGTH*TILE_SIZE);
		//renderer.end();

		renderer.setColor(Color.GREEN);
		renderer.begin(ShapeType.Filled);


		for (int x = 0; x < MAP_WIDTH; x++) {
			for (int y = 0; y<MAP_HEIGTH;y++){
				if (map.isFilled(x, y)) {
					renderer.rect(X_OFFSET*TILE_SIZE+x*TILE_SIZE,Y_OFFSET*TILE_SIZE+y*TILE_SIZE,TILE_SIZE,TILE_SIZE);	
				}
			}
		}
		renderer.end();
		
		//TODO Draw collision box
		//for(Rectangle r:boundingBoxes){
		//	renderer.setColor(Color.RED);
		//	renderer.begin(ShapeType.Line);
		//		renderer.rect(r.x,r.y,r.width,r.height);
		//	renderer.end();
		//}	
	}
	
	
	
	
	/**
	 * Takes a Vector2 screen position and returns an int array with the position
	 * relative the level. The position is returned in grid space, which means that 
	 * resulting positions is integers.
	 * As the position is relative it could also be outside the actual map/level. 
	 * This is merely a coordination system transformation. 
	 * The first element in the returned array is the x position and the last element is the y position.
	 * @param position a screen position.
	 * @return array of integers where the first element is the x-pos and the last is the y-pos.
	 */
	public static int[] ScreenToLevelCoordinates(Vector2 position) {
		int[] levelPos = new int[2];
		position.sub(X_OFFSET*TILE_SIZE, Y_OFFSET*TILE_SIZE);
		int gridX = (int)Math.round(position.x/TILE_SIZE);
		int gridY = (int)Math.round(position.y/TILE_SIZE);
		levelPos[0] = gridX;
		levelPos[1] = gridY;
		return levelPos;
	}
	/**
	 * Transforms a level coordinate to a screen coordinate.
	 * This is relative to the bottom-left corner of the the screen.
	 * @param x position in grid/level space.
	 * @param y position in grid/level space
	 * @return a vector with the same coordinates but in screen space.
	 */
	public static Vector2 LevelCoordinatesToScreen(int x, int y){
		Vector2 screenPos = new Vector2();
		float screenX = (X_OFFSET + x)*TILE_SIZE;
		float screenY = (Y_OFFSET + y)*TILE_SIZE;
		screenPos.x = screenX;
		screenPos.y = screenY;
		return screenPos;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}