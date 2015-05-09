package se.mad.copterplant.world;

import se.mad.copterplant.actor.Actor;
import se.mad.copterplant.actor.Collidable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Wall extends Actor implements Collidable {

	

	public Wall(Vector2 pos, Vector2 vel, float width, float height) {
		super(pos);
		setShape(width,height);
		setShapeType(ShapeType.Filled);
		setColor(Color.WHITE);
		
		
	}

	@Override
	public void collide(Actor other) {
		System.out.println("Ouch!: " + other.getPos());
	}

	@Override
	public boolean isColliding(Actor other) {
		
		return false;
	}

	@Override
	public void init() {
		

	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void draw(ShapeRenderer renderer) {
		drawActor(renderer);

	}

}
