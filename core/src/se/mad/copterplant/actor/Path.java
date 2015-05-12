package se.mad.copterplant.actor;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * @author Andreas Brommund
 *
 */
public class Path {
	LinkedList<Vector2> path; 
	
	public Path(Vector2 node){
		path = new LinkedList<Vector2>();
		path.addLast(node);
	}
	
	/**
	 * Add the node last to the the path.
	 * @param node
	 */
	public void addNode(Vector2 node){
		path.addLast(node);
	}
	
	public LinkedList<Vector2> getPath(){
		return path;
	}
	
	/**
	 * Draw the path as circles. 
	 * @param renderer
	 * @param playerPos
	 * @param playerVel
	 */
	public void draw(ShapeRenderer renderer,Vector2 playerPos,Vector2 playerVel) {
		renderer.begin(ShapeType.Filled);
		renderer.setColor(Color.BLUE);
		
		Vector2 old = null;
		float radius = 10;
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
				y += Math.signum(playerVel.y)*radius*2;
			}
		}else{//Moving in the X direction
			int times = (int) (Math.abs(old.x-playerPos.x)/(radius*2)+1);
			float y = old.y; 
			float x = old.x;
			for(int i = 0; i < times;i++){
				renderer.circle(x, y, radius);
				x += Math.signum(playerVel.x)*radius*2;
			}
		}
		renderer.end();
		
	}
	
	@Override
	public String toString() {
		return path.toString();
	}
	
	
}
