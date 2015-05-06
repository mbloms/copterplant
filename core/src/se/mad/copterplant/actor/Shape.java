package se.mad.copterplant.actor;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
/**
 * 
 * Handle and print out the shapes.
 * 
 * @author Andreas Brommund
 *
 */
public class Shape {
	
	private enum Type{
		Circle, Polygon, Rectangle
	}
 	final private Type type;
	
 	private float width, height, radius;
 	private float[] vertices;
 	
	protected Shape(float width, float height) {
		type = Type.Rectangle;
		this.width = width;
		this.height = height;
	}
	
	protected Shape(float radius){
		type = Type.Circle; 
		this.radius = radius;
	}
	
	protected Shape(float[] vertices){
		type = Type.Polygon;
		this.vertices = vertices; 
	}
	
	protected void renderShape(ShapeRenderer  renderer, Vector2 pos){
		switch (type) {
		case Circle:
			renderer.circle(pos.x+radius,pos.y+radius, radius);
			break;
		case Rectangle:
			renderer.rect(pos.x, pos.y, width, height);
			break;
		case Polygon:
			for(int i = 0;i < vertices.length;i++){
				if(i%2 == 0){ //TODO it may be the opposite.  
					vertices[i] *= pos.x;
				}else{
					vertices[i] *= pos.y;
				}
			}
			renderer.polygon(vertices);
			break;
		}
	}
	
	
}
