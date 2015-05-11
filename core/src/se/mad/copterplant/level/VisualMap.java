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
import com.badlogic.gdx.math.Vector2;

/**
 * This is the visual representation of the leveldata stored in a backend 
 * instance of the LevelMap class. 
 * @author DavidSkeppstedt
 *
 */
public class VisualMap {

	private final int TILE_SIZE = 32;
	private final int MAP_WIDTH = 20;
	private final int MAP_HEIGTH = 20;
	private final int X_OFFSET = 10;
	private final int Y_OFFSET = 3;
	public LevelMap map;
	Texture pixmaptex;
	SpriteBatch spritebatch;

	public VisualMap() {
		
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
	}
}