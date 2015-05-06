package se.mad.copterplant.actor;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
		setPos(pos);
		new Rectangle(vel.x, vel.y, width, height);
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
		setPos(pos);
		new Rectangle(vel.x, vel.y, radius*2, radius*2);
	}
	
	
	/**
	 * Create a polygon shape to the actor, you need to specify a velocity.
	 * 
	 * @param pos
	 * @param vel
	 * @param vertices
	 */
	protected Actor(Vector2 pos, Vector2 vel, float[] vertices){
		super(vertices);
		setPos(pos);
		new Rectangle(vel.x, vel.y, 100, 100); //TODO Calculate width
	}

	private void updateCollisionbox(){
		collisionBox.x = pos.x;
		collisionBox.y = pos.y;
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
