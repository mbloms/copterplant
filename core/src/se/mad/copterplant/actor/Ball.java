package se.mad.copterplant.actor;

import se.mad.copterplant.util.UserInput;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Ball extends Actor  implements Collidable{

	
	public Ball(Vector2 pos) {
		super(pos);
	}


	@Override
	public void init() {
		setVel(new Vector2());
		setShape(10);
		setShapeType(ShapeType.Filled);
		setColor(Color.CYAN);
	}

	@Override
	public void update(float delta) {
		setPos(getPos().add(getVel()));
		if (UserInput.DOWN)
			setVel(new Vector2(getVel().x,-1));
		
	}

	@Override
	public void draw(ShapeRenderer renderer) {
		renderer.setColor(Color.GREEN);
		drawActor(renderer);
	}


	@Override
	public void collide(Actor other) {
		if(other.getPos().y+other.getCollisionBox().height < this.getPos().y || 
		   other.getPos().y > this.getPos().y
		){	
			setVel(new Vector2(getVel().x,-1*getVel().y));	
			return;
		}
		
		if (Math.abs(this.getVel().y) > 0) {
				setVel(new Vector2(-1*getVel().x,getVel().y));
		}else {
			setVel(getVel().scl(-1));
		}
	}

	@Override
	public boolean isColliding(Actor other) {
		return getCollisionBox().overlaps(other.getCollisionBox());
	}
}