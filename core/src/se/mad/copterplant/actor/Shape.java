package se.mad.copterplant.actor;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Shape {
	
	private enum Type{
		Circle, Polygon, Rectangle
	}
 	final private Type type;
	
 	private float width, height, radius;
 	private float[] vertices;
 	
	public Shape(float width, float height) {
		type = Type.Rectangle;
		this.width = width;
		this.height = height;
	}
	
	public Shape(float radius){
		type = Type.Circle; 
		this.radius = radius;
	}
	
	public Shape(float[] vertices){
		type = Type.Polygon;
		this.vertices = vertices; 
	}
	
	protected void renderShape(ShapeRenderer  renderer, float x, float y){
		switch (type) {
		case Circle:
			renderer.circle(x, y, radius);
			break;
		case Rectangle:
			renderer.rect(x, y, width, height);
			break;
		case Polygon:
			for(int i = 0;i < vertices.length;i++){
				if(i%2 == 0){
					vertices[i] *= x;
				}else{
					vertices[i] *= y;
				}
			}
			renderer.polygon(vertices);
			break;
		}
	}
	
	
}
