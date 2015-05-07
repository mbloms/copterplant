package se.mad.copterplant.actor;

import com.badlogic.gdx.math.MathUtils;

public class Box implements Collidable {
	private int id;
	public Box() {
		id = MathUtils.random(10);
	}

	@Override
	public void collide(Collidable other) {
		System.out.println("I am colliding!");

	}

	@Override
	public boolean isColliding(Collidable other) {
		Box x = (Box)other;
		return x.id%2==0;
	}

}
