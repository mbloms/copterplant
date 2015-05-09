package se.mad.copterplant.actor;

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
		
	private boolean visit = false; //TODO Get from field
	private float speed = 4;
	
	public Player(Vector2 pos) {

		super(pos);
		
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update(float delta) {
		if(!visit){
			setVel(new Vector2(0, 0));
		}
		
		if (UserInput.RIGHT){
			setVel(new Vector2(speed, 0));
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
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.CYAN);
		
		drawActor(renderer);
		renderer.setColor(Color.GREEN);
		renderer.rect(getCollisionBox().x, getCollisionBox().y,getCollisionBox().width, getCollisionBox().height);
		
		renderer.end();
	}

	@Override
	public void collide(Actor other) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isColliding(Actor other) {
		
		return false;
	}
}
