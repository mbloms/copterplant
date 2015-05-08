package se.mad.copterplant.actor;

import se.mad.copterplant.util.UserInput;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Ball extends Actor  implements Collidable{

	
	public Ball(Vector2 pos) {
		super(pos, new Vector2(0,0),25);
	}


	@Override
	public void init() {
		setVel(new Vector2(MathUtils.random(5,5),5));
	}

	@Override
	public void update(float delta) {
		setPos(getPos().add(getVel()));
		if (UserInput.DOWN)
			setVel(new Vector2(getVel().x,-1));
		
	}

	@Override
	public void draw(ShapeRenderer renderer) {
		renderer.begin(ShapeType.Line);
		renderShape(renderer, getPos());
		renderer.end();
	}


	@Override
	public void collide(Collidable other) {
		Actor a = (Actor)other;
		if(a.getPos().y+a.getCollisionBox().height < this.getPos().y || 
		   a.getPos().y > this.getPos().y
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
