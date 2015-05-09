package se.mad.copterplant.actor;

public interface Collidable {
	/**
	 * This method could be executed when two collidable actors collide.
	 * @param Collidable other The collidable actor that is colliding with this one.
	 */
	public void collide(Actor other);
	/**
	 * Checks whatever two collidable actors are colliding and how.
	 * @param other
	 * @return true if they are colliding.
	 */
	public boolean isColliding(Actor other);
}
