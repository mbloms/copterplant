package se.mad.copterplant.actor;

import se.mad.copterplant.level.VisualMap;
import se.mad.copterplant.screens.GameScreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ball extends Actor  implements Collidable{

	private float moveTimer = 0.5f;
	private VisualMap vmap;
	
	public Ball(Vector2 pos,VisualMap vmap) {
		super(pos);
		this.vmap = vmap;
	}


	@Override
	public void init() {
		setVel(new Vector2());
		setShape(16);
		setShapeType(ShapeType.Filled);
		setColor(Color.CYAN);
		setVel(new Vector2(2,1));
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
			
			if (GameScreen.vMap.map.isFilled((int)grid.x,(int) grid.y)){
				setVel(getVel().scl(-1));
			}else {
				setPos(newPos);
			}
			
			if (!VisualMap.BoundsRect.contains(getCollisionBox())) {
				collide(null);
			}
			
			
			if(Path.isColliding(this.getCollisionBox())){
				setVel(getVel().scl(-1));
			}

			setPos(newPos);
					
	}

	@Override
	public void draw(ShapeRenderer renderer) {
		renderer.setColor(Color.GREEN);
		drawActor(renderer);
		renderer.begin(ShapeType.Line);
			renderer.rect(getCollisionBox().x,getCollisionBox().y,getCollisionBox().width,getCollisionBox().height);
		renderer.end();
	}


	@Override
	public void collide(Actor other) {
	}
	
	@Override
	public boolean isColliding(Actor other) {
		return getCollisionBox().contains(other.getCollisionBox());
	}
}