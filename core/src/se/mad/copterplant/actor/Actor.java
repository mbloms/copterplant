package se.mad.copterplant.actor;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

public abstract class Actor extends Shape{
	private Vector2 pos;
	private Vector2 vel;
	private Rectangle collisionBox;
	
	/**
	 * Create a rectangle shape to the actor, you need to specify a velocity.
	 * 
	 * @param pos
	 * @param vel
	 * @param width
	 * @param height
	 */
	protected Actor(Vector2 pos, Vector2 vel, float width, float height) {
		super(width, height);
		collisionBox = new Rectangle(pos.x, pos.y, width, height);
		setUpActor(pos,vel);
	}
	
	/**
	 * Create a circle shape to the actor, you need to specify a velocity.
	 * 
	 * @param pos
	 * @param vel
	 * @param radius
	 */
	protected Actor(Vector2 pos, Vector2 vel, float radius){
		super(radius);
		collisionBox = new Rectangle(pos.x, pos.y, radius*2, radius*2);
		setUpActor(pos,vel);
	}
	
	
	/**
	 * Create a polygon shape to the actor, you need to specify a velocity.
	 * 
	 * @param pos
	 * @param vel
	 * @param vertices
	 */
	protected Actor(Vector2 pos, Vector2 vel, Polygon vertices){
		super(vertices);
		collisionBox = vertices.getBoundingRectangle();
		setUpActor(pos,vel);
	}

	private void updateCollisionbox(){
		switch (type) {
		case Polygon:
			collisionBox = getPolygon().getBoundingRectangle();
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

	private void setUpActor(Vector2 pos, Vector2 vel){
		setPos(pos);
		setVel(vel);
		init();
	}
	public Vector2 getPos() {
		
		return pos;
	}


	public void setPos(Vector2 pos) {
		this.pos = pos;
		updateCollisionbox();
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
	
	public abstract void init();
	public abstract void update(float delta);
	public abstract void draw(ShapeRenderer  renderer);
}
