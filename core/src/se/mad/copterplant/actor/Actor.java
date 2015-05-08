package se.mad.copterplant.actor;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * 
 * The actor class handle the position velocity and the collision box. 
 * You can also specify which shape the actor should have. 
 * @author Andreas Brommund
 *
 */

public abstract class Actor{
	private Vector2 pos;
	private Vector2 vel;
	private Rectangle collisionBox;
	Shape shape;
	
	/**
	 * Create actor, the velocity is zero as default.
	 * 
	 * @param pos
	 */
	protected Actor(Vector2 pos){
		setUpActor(pos);
	}

	/**
	 * Set the shape to a rectangle.
	 * @param width
	 * @param height
	 */
	protected void setShape(float width, float height){
		shape = new Shape(width,height);
		collisionBox = new Rectangle(pos.x, pos.y, width, height);
	}
	
	/**
	 * Set the shape to a circle
	 * @param radius
	 */
	protected void setShape(float radius){
		shape = new Shape(radius);
		collisionBox = new Rectangle(pos.x, pos.y, radius*2, radius*2);
	}
	/**
	 * Set the shape to a polygon
	 * @param polygon
	 */
	protected void setShape(Polygon polygon){
		shape = new Shape(polygon);
		collisionBox = polygon.getBoundingRectangle();
	}
	private void updateCollisionbox(){
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

	private void setUpActor(Vector2 pos){
		setPos(pos);
		setVel(new Vector2(0, 0));
		init();
	}
	public Vector2 getPos() {
		
		return pos;
	}


	public void setPos(Vector2 pos) {
		this.pos = pos;
		if(shape != null){
			updateCollisionbox();
		}
		
	}


	public Vector2 getVel() {
		return vel;
	}


	public void setVel(Vector2 vel) {
		this.vel = vel;
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
	 * @param renderer
	 */
	protected void drawActor(ShapeRenderer renderer){
		shape.renderShape(renderer, pos);
	}
	
	/**
	 * Set the shape type, the shape type is line as default. 
	 * @param shapeType
	 */
	protected void setShapeType(ShapeType shapeType){
		shape.setShapeType(shapeType);
	}
	
	public abstract void init();
	public abstract void update(float delta);
	public abstract void draw(ShapeRenderer  renderer);
}
