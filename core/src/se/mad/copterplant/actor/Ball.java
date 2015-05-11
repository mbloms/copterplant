package se.mad.copterplant.actor;

import se.mad.copterplant.level.VisualMap;
import se.mad.copterplant.screens.GameScreen;
import se.mad.copterplant.util.Settings;
import se.mad.copterplant.util.UserInput;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Ball extends Actor  implements Collidable{

	private float moveTimer = 0.5f;
	public Ball(Vector2 pos) {
		super(pos);
		
	}


	@Override
	public void init() {
		setVel(new Vector2());
		setShape(16);
		setShapeType(ShapeType.Filled);
		setColor(Color.CYAN);
		setVel(new Vector2(0,1));
	}

	@Override
	public void update(float delta) {
			Vector2 newPos = new Vector2(
										(int)Math.rint(getPos().x + getVel().x),
										(int)Math.rint(getPos().y + getVel().y)
										);
			Vector2 oldPos = getPos();
			Vector2 grid = newPos.cpy();
			
			
			grid.sub(320,96);
			grid.x /=32;
			grid.y /=32; 

			System.out.println(grid);
			
			if (GameScreen.vMap.map.isFilled((int)grid.x,(int) grid.y)){
				setVel(getVel().scl(-1));
			}
			setPos(newPos);
			
			
			if (!VisualMap.BoundsRect.contains(getCollisionBox())) {
				collide(null);
			}
			
			
			
			
	}

	@Override
	public void draw(ShapeRenderer renderer) {
		renderer.setColor(Color.GREEN);
		drawActor(renderer);
	}


	@Override
	public void collide(Actor other) {
		setVel(getVel().scl(-1));
	}

	@Override
	public boolean isColliding(Actor other) {
		return getCollisionBox().overlaps(other.getCollisionBox());
	}
}