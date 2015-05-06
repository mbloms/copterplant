package se.mad.copterplant.actor;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public abstract class Actor extends Shape{
	private Vector2 pos;
	private Vector2 vel;
	private Rectangle collisionBox;
	
	public Actor(Vector2 pos, Vector2 vel, float width, float height) {
		super(width, height);
		setPos(pos);
		setPos(vel);
		new Rectangle(vel.x, vel.y, width, height);
	}
	public Actor(Vector2 pos, Vector2 vel, float radius){
		super(radius);
		setPos(pos);
		setPos(vel);
		new Rectangle(vel.x, vel.y, radius*2, radius*2);
	}
	
	public Actor(Vector2 pos, Vector2 vel, float[] vertices){
		super(vertices);
		setPos(pos);
		setPos(vel);
		new Rectangle(vel.x, vel.y, 100, 100); //TODO Calculate width
	}
	
	public abstract void init();
	public abstract void update(float delta);
	public abstract void draw(ShapeRenderer  renderer);

	public Vector2 getPos() {
		return pos;
	}


	public void setPos(Vector2 pos) {
		this.pos = pos;
	}


	public Vector2 getVel() {
		return vel;
	}


	public void setVel(Vector2 vel) {
		this.vel = vel;
	}
	
	public Rectangle getCollisionBox() {
		return collisionBox;
	}
	
	public void updateCollisionbox(){
		collisionBox.x = pos.x;
		collisionBox.y = pos.y;
	}
}
