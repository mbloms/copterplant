package se.mad.copterplant.actor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public abstract class Actor {
	private Vector2 pos;
	private Vector2 vel;
	private Shape shape;
	private Shape collisionBox;
	
	public Actor(float posX, float posY, float velX, float velY) {
		setPos(new Vector2(posX, posY));
		setPos(new Vector2(velX, velY));
		shape = new Shape();
		collisionBox = new Shape();
		
	}
	
	public abstract void init();
	public abstract void update(float delta);
	public abstract void draw(SpriteBatch  batch);

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
}
