package se.mad.copterplant.actor;

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
public class Player extends Actor{
	
	static float[] v = new float[] {
	        50, 000,
	        3, 100,
	        300, 200,
	        345, 1,
	        200, 34,
	        200, 110,
	        100, 130,
	        100, 43,
	    };
	
	public Player(Vector2 pos) {

		super(pos, new Vector2(0,0), new Polygon(v));
		
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void update(float delta) {
		setPos(getPos().add(1, 0));
		setPos(getPos().add(0, 1));
		
	}

	@Override
	public void draw(ShapeRenderer renderer) {
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.CYAN);
		
		renderShape(renderer, getPos());
		renderer.setColor(Color.GREEN);
		renderer.rect(getCollisionBox().x, getCollisionBox().y,getCollisionBox().width, getCollisionBox().height);
		
		renderer.end();
	}
}
