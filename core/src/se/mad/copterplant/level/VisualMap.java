package se.mad.copterplant.level;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
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
	Texture pixmaptex;
	SpriteBatch spritebatch;

	public VisualMap() {
		map = new LevelMap(20, 20);
		BoundsRect = new Rectangle(X_OFFSET*TILE_SIZE,Y_OFFSET*TILE_SIZE,
				MAP_WIDTH*TILE_SIZE,MAP_HEIGTH*TILE_SIZE);
	}

	public void calculateMap(){
	}

	/**
	 * Draws the map using a tile size of 32 pixels.
	 * Will render the grid with rectangles.
	 * @param renderer
	 */
	public void draw(ShapeRenderer renderer){
		renderer.setColor(Color.GREEN);
		renderer.begin(ShapeType.Line);
		renderer.rect(X_OFFSET*TILE_SIZE,Y_OFFSET*TILE_SIZE,
				MAP_WIDTH*TILE_SIZE,MAP_HEIGTH*TILE_SIZE);
		renderer.end();

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




	}
}