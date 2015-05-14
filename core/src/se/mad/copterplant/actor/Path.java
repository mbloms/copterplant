package se.mad.copterplant.actor;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * @author Andreas Brommund
 *
 */
public class Path{
	private static LinkedList<PathObject> path;
	
	public Path(){
		path = new LinkedList<PathObject>();
	}
	
	/**
	 * Add the node last to the the path.
	 * @param node
	 */
	public void addNode(Vector2 node){
		path.addLast(new PathObject(node));
	}
	
	public LinkedList<PathObject> getPath(){
		return path;
	}
	
	/**
	 * Draw the path as circles. 
	 * @param renderer
	 * @param playerPos
	 * @param playerVel
	 */
	public void draw(ShapeRenderer renderer,Vector2 playerPos,Vector2 playerVel) {
		for(PathObject p:path){
			p.draw(renderer);
		}
	}
	/**
	 * Check collision between a rectangle and the path, if it is a collision the method is return true.
	 * @param Rectangle
	 * @return true or false
	 */
	public static boolean isColliding(Rectangle rect){
		if(path == null){
			return false;
		}
		for(PathObject p:path){
			if(rect.overlaps(p.getCollisionBox())){
				return true; 
			}
		}
		return false;
	}
	
	public class PathObject{
		private Rectangle collisionBox; 
		private Vector2 pos;
		private float radius = 16;
		
		public PathObject(Vector2 pos) {
			collisionBox = new Rectangle(pos.x-radius,pos.y-radius,radius*2,radius*2);
			this.pos = pos;
		}
		
		public void draw(ShapeRenderer renderer){
			renderer.begin(ShapeType.Filled);
			renderer.setColor(Color.BLUE);
			renderer.circle(pos.x, pos.y, radius);
			renderer.end();
			
			renderer.begin(ShapeType.Line);
			renderer.setColor(Color.YELLOW);
			renderer.rect(collisionBox.x,collisionBox.y,collisionBox.width,collisionBox.height);
			renderer.end();
		}
		
		public Rectangle getCollisionBox() {
			return collisionBox;
		}
		
		public Vector2 getPos() {
			return pos;
		}
	}
}