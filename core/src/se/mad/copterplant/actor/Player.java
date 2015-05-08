package se.mad.copterplant.actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import se.mad.copterplant.util.UserInput;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.sun.scenario.effect.impl.Renderer.RendererState;

/**
 * 
 * 
 * @author Andreas Brommund
 *
 */
public class Player extends Actor implements Collidable{
	
	private float speed = 4;
	
	public Player(Vector2 pos) {
		super(pos);
	}

	@Override
	public void init() {
		setShape(50f);
		setShapeType(ShapeType.Filled);
		setColor(Color.RED);
	}

	@Override
	public void update(float delta) {
		
		if (UserInput.RIGHT){
			setVel(new Vector2(speed, 0));
			setColor(Color.BLUE);
		}
		if (UserInput.LEFT){
			setVel(new Vector2(-speed, 0));	
		}
		if (UserInput.UP){
			setVel(new Vector2(0, speed));
		}
		if (UserInput.DOWN){
			setVel(new Vector2(0, -speed));
		}
		setPos(getPos().add(getVel()));
	}

	@Override
	public void draw(ShapeRenderer renderer) {
		drawActor(renderer);
	}

	@Override
	public void collide(Collidable other) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isColliding(Actor other) {
		// TODO Auto-generated method stub
		return false;
	}
}
