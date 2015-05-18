package se.mad.copterplant.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * Handle and print out the shapes.
 * 
 * @author Andreas Brommund
 *
 */
public class Shape {

	public enum Type {
		Circle, Polygon, Rectangle
	}

	public Type type;

	private float width, height, radius;
	private Polygon polygon;
	private ShapeType shapeType;

	/**
	 * Set up a rectangle, shape type is line as default.
	 * 
	 * @param width
	 * @param height
	 */
	public Shape(float width, float height) {
		type = Type.Rectangle;
		this.width = width;
		this.height = height;
		this.shapeType = shapeType.Line;
	}

	/**
	 * Set up a circle, shape type is line as default.
	 * 
	 * @param polygon
	 */
	public Shape(float radius) {
		type = Type.Circle;
		this.radius = radius;
		this.shapeType = shapeType.Line;
	}

	/**
	 * Set up a polygon, shape type is line as default.
	 * 
	 * @param polygon
	 */
	public Shape(Polygon polygon) {
		type = Type.Polygon;
		this.polygon = polygon;
		this.shapeType = shapeType.Line;
	}

	/**
	 * Render the shape
	 * 
	 * @param renderer
	 * @param pos
	 * @param color
	 */
	public void renderShape(ShapeRenderer renderer, Vector2 pos, Color color) {
		renderer.begin(shapeType);
		renderer.setColor(color);
		switch (type) {
		case Circle:
			renderer.circle(pos.x, pos.y, radius);
			break;
		case Rectangle:
			renderer.rect(pos.x, pos.y, width, height);
			break;
		case Polygon:
			polygon.setPosition(pos.x, pos.y);
			renderer.polygon(polygon.getTransformedVertices());
			break;
		}
		renderer.end();
	}

	/**
	 * Get the polygon you can only get the polygon if the shape of the actor is
	 * polygon.
	 * 
	 * @return polygon
	 *
	 */
	public Polygon getPolygon() {
		if (type != Type.Polygon) {
			throw new NullPointerException(
					"You can't acces the polygon becouse the shape of the actor is of "
							+ type);
		}
		return polygon;
	}

	/**
	 * Get the shape type of the shape.
	 * 
	 * @return ShapeType
	 */
	public ShapeType getShapeType() {
		return shapeType;
	}

	/**
	 * Set the shape type of the shape.
	 * 
	 * @param shapeType
	 */
	public void setShapeType(ShapeType shapeType) {
		this.shapeType = shapeType;
	}
}
