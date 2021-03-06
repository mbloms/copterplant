package se.mad.copterplant.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * The actor class handle the position velocity and the collision box. You can
 * also specify which shape the actor should have.
 * 
 * @author Andreas Brommund
 *
 */
public abstract class Actor {
	private Vector2 pos;
	private Vector2 vel;
	private Rectangle collisionBox;
	private Color color;
	private Shape shape;

	/**
	 * Create actor, the velocity is zero as default.
	 * 
	 * @param pos
	 */
	protected Actor(Vector2 pos) {
		setUpActor(pos);
	}

	/**
	 * Set the shape to a rectangle.
	 * 
	 * @param width
	 * @param height
	 */
	protected void setShape(float width, float height) {
		shape = new Shape(width, height);
		collisionBox = new Rectangle(pos.x, pos.y, width, height);
	}

	/**
	 * Set the shape to a circle
	 * 
	 * @param radius
	 */
	protected void setShape(float radius) {
		shape = new Shape(radius);
		collisionBox = new Rectangle(pos.x, pos.y, radius * 2, radius * 2);
	}

	/**
	 * Set the shape to a polygon
	 * 
	 * @param polygon
	 */
	protected void setShape(Polygon polygon) {
		shape = new Shape(polygon);
		collisionBox = polygon.getBoundingRectangle();
	}

	private void updateCollisionbox() {
		switch (shape.type) {
		case Polygon:
			collisionBox = shape.getPolygon().getBoundingRectangle();
			break;
		case Rectangle:
			collisionBox.x = pos.x;
			collisionBox.y = pos.y;
			break;
		case Circle:
			collisionBox.setCenter(pos.x, pos.y);
			break;
		}
	}

	private void setUpActor(Vector2 pos) {
		setPos(pos);
		setVel(new Vector2(0, 0));
		init();
	}

	/**
	 * Get a copy of the position vector.
	 * 
	 * @return
	 */
	public Vector2 getPos() {

		return new Vector2(pos);
	}

	/**
	 * Set the position of the actor.
	 * 
	 * @param pos
	 */
	public void setPos(Vector2 pos) {
		this.pos = pos;
		if (shape != null) {
			updateCollisionbox();
		}

	}

	/**
	 * Get a copy of the velocity vector.
	 */
	public Vector2 getVel() {
		return new Vector2(vel);
	}

	/**
	 * Get the velocity in x.
	 */
	public float getVelX() {
		return vel.x;
	}

	/**
	 * Get the velocity in y.
	 */
	public float getVelY() {
		return vel.y;
	}

	/**
	 * Set the velocity.
	 * 
	 * @param vel
	 */
	public void setVel(Vector2 vel) {
		this.vel = vel;
	}

	/**
	 * Return the render color. The default color is black
	 * 
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Set the render color. The default color is black
	 * 
	 * @return
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Return the collision box, you use to detect collision.
	 *
	 * @return collision box
	 */
	public Rectangle getCollisionBox() {
		return collisionBox;
	}

	/**
	 * Render the shape.
	 * 
	 * @param renderer
	 */
	protected void drawActor(ShapeRenderer renderer) {
		if (color == null) {
			color = Color.BLACK;
		}
		shape.renderShape(renderer, pos, color);
	}

	/**
	 * Set the shape type, the shape type is line as default.
	 * 
	 * @param shapeType
	 */
	protected void setShapeType(ShapeType shapeType) {
		shape.setShapeType(shapeType);
	}

	/**
	 * Is always called after the constructor.
	 */
	protected abstract void init();

	/**
	 * Update the actor
	 * 
	 * @param delta
	 */
	public abstract void update(float delta);

	/**
	 * Draw the actor stuff.
	 * 
	 * @param renderer
	 */
	public abstract void draw(ShapeRenderer renderer);
}
