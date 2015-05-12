package se.mad.copterplant.actor;

import java.util.Iterator;
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
	private LinkedList<Vector2> path; 
	private static LinkedList<Rectangle> collisionBox;
	private static LinkedList<Rectangle> collisionBoxTemp;
	float radius = 10;
	
	public Path(Vector2 node){
		path = new LinkedList<Vector2>();
		collisionBox =  new LinkedList<Rectangle>();
		collisionBoxTemp = new LinkedList<Rectangle>();
		path.addLast(node);
	}
	
	/**
	 * Add the node last to the the path.
	 * @param node
	 */
	public void addNode(Vector2 node){
		path.addLast(node);
		updateCollision();
	}
	
	public LinkedList<Vector2> getPath(){
		return path;
	}
	
	private void updateCollision(){
		
		collisionBox.addAll(collisionBoxTemp);
		
		collisionBoxTemp.clear();
	}
	
	/**
	 * Draw the path as circles. 
	 * @param renderer
	 * @param playerPos
	 * @param playerVel
	 */
	public void draw(ShapeRenderer renderer,Vector2 playerPos,Vector2 playerVel) {
		collisionBoxTemp.clear();
		
		renderer.begin(ShapeType.Filled);
		renderer.setColor(Color.BLUE);
		
		Vector2 old = null;
		Iterator<Vector2> it = path.iterator(); 
		while (it.hasNext()){
			
			Vector2 c = it.next();
			if(old != null){
				if(old.x == c.x){//Moving in the Y direction
					int times = (int) (Math.abs(old.y-c.y)/(radius*2)+1);
					float x = old.x; 
					float y = old.y;
					for(int i = 0; i < times;i++){
						renderer.circle(x, y, radius);
						y += Math.signum(c.y-old.y)*radius*2;
					}
				}else{//Moving in the X direction
					int times = (int) (Math.abs(old.x-c.x)/(radius*2)+1);
					float y = old.y; 
					float x = old.x;
					for(int i = 0; i < times;i++){
						renderer.circle(x, y, radius);
						x += Math.signum(c.x-old.x)*radius*2;
					}
				}
					
			}
			old = c;
		}
	
		if(playerVel.x == 0){//Moving in the Y direction
			int times = (int) (Math.abs(old.y-playerPos.y)/(radius*2)+1);
			float x = old.x; 
			float y = old.y;
			for(int i = 0; i < times;i++){
				renderer.circle(x, y, radius);
				collisionBoxTemp.addLast(new Rectangle(x-radius, y-radius, radius*2, radius*2)); //Should not do this here, but I don't want to loop one more time.
				y += Math.signum(playerVel.y)*radius*2;
			}
		}else{//Moving in the X direction
			int times = (int) (Math.abs(old.x-playerPos.x)/(radius*2)+1);
			float y = old.y; 
			float x = old.x;
			for(int i = 0; i < times;i++){
				renderer.circle(x, y, radius);
				collisionBoxTemp.addLast(new Rectangle(x-radius, y-radius, radius*2, radius*2)); //Should not do this here, but I don't want to loop one more time.
				x += Math.signum(playerVel.x)*radius*2;
			}
		}
		renderer.end();
		
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.ORANGE);
		
		for(Rectangle r:collisionBox){
			renderer.rect(r.x, r.y, r.width, r.height);
		}
		for(Rectangle r:collisionBoxTemp){
			renderer.rect(r.x, r.y, r.width, r.height);
		}
	
		renderer.end();
		
	}
	/**
	 * Check collision between a rectangle and the path, if it is a collision the method is return true.
	 * @param Rectangle
	 * @return true or false
	 */
	public static boolean isColliding(Rectangle rect){
		if(collisionBox == null || collisionBoxTemp == null){
			return false;
		}
		for(Rectangle r:collisionBox){
			if(rect.overlaps(r)){
				return true; 
			}
		}
		for(Rectangle r:collisionBoxTemp){
			if(rect.overlaps(r)){
				return true; 
			}	
		}
		
		return false;
	}
	@Override
	public String toString() {
		return path.toString();
	}
}
