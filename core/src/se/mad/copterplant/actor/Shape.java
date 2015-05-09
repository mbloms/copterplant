package se.mad.copterplant.actor;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * 
 * Handle and print out the shapes.
 * 
 * @author Andreas Brommund
 *
 */
public class Shape {
	
	protected enum Type{
		Circle, Polygon, Rectangle
	}
 	final protected Type type;
	
 	private float width, height, radius;
 	private Polygon polygon;
 	
	protected Shape(float width, float height) {
		type = Type.Rectangle;
		this.width = width;
		this.height = height;
	}
	
	protected Shape(float radius){
		type = Type.Circle; 
		this.radius = radius;
	}
	
	protected Shape(Polygon polygon){
		type = Type.Polygon;
		this.polygon = polygon;
	}
	
	protected void renderShape(ShapeRenderer  renderer, Vector2 pos){
		switch (type) {
		case Circle:
			renderer.circle(pos.x,pos.y, radius);
			break;
		case Rectangle:
			renderer.rect(pos.x, pos.y, width, height);
			break;
		case Polygon:
			polygon.setPosition(pos.x, pos.y);
			renderer.polygon(polygon.getTransformedVertices());
			break;
		}
	}
	
	/**
	 * Get the polygon you can only get the polygon if the shape of the actor is polygon.
	 * @return polygon
	 *
	 */
	protected Polygon getPolygon(){
		if(type != Type.Polygon){
			throw new NullPointerException("You can't acces the polygon becouse the shape of the actor is of "+type);
		}
		return polygon;
	}
}
