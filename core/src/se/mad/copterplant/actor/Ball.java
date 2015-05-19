package se.mad.copterplant.actor;

import se.mad.copterplant.level.VisualMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ball extends Actor implements Collidable {

	private VisualMap vmap;
	private boolean collided = false;
	private Rectangle nextRectangle;
	private Player player;
	private float speed;
	private float radius;

	public Ball(Vector2 pos, VisualMap vmap, Player player) {
		super(pos);
		this.vmap = vmap;
		this.player = player;
	}

	@Override
	public void init() {
		radius = 8;
		setVel(new Vector2());

		setShape(radius);
		setPos(getPos().add(radius,radius));

		setShapeType(ShapeType.Line);
		setColor(Color.WHITE);
		speed = 2.8f;
		setRandomVel();


	}

	private void setRandomVel(){
		Vector2 vel = new Vector2((float)Math.random(),(float)Math.random());
		if(vel.len() == 0){
			setRandomVel();
		}
		setVel(vel.nor().scl(speed));
	}

	@Override
	public void update(float delta) {
		player.isCollidingPath(this.getCollisionBox());
		move();
	}

	private void move() {
		collided = false; // starts out with the abillity to move forward.

		Vector2 oldPos = getPos(); // the current position at the center of the ball.
		Vector2 newPos = getPos().add(getVel()); // the new position that we would like to move to. At the center.
		Vector2 deltaMove = newPos.cpy().sub(oldPos); // calculating the delta to know how much of the ball is inside a collidable object.

		Vector2 rectPos = getPos().sub(getCollisionBox().width / 2,getCollisionBox().height / 2); // the rectangular position of the ball. Lower left corner.
		rectPos.add(getVel()); // Changed so that the rectPos is like the next pos but in different coordinates.

		collided = canMove(rectPos, deltaMove); // do collision detection and handling.

		if (!collided) // if we did not collide with anything, it is okay to move forward.
			setPos(newPos);
		else { // else don't and go backwards.
			oldPos.add(getVel());
			setPos(oldPos);
		}
	}

	private boolean canMove(Vector2 rectPos, Vector2 deltaMove) {
		// First build the collision points and the rectangle
		Vector2[] corners = updateCollisionboxCorners(rectPos);
		Vector2 leftBottom = corners[0];
		Vector2 leftTop = corners[1];
		Vector2 rightBottom = corners[2];
		Vector2 rightTop = corners[3];

		nextRectangle = new Rectangle(rectPos.x, rectPos.y,
				getCollisionBox().width, getCollisionBox().height);

		// Check against all things in the world. In this case the walls.
		for (Rectangle rect : vmap.getBoundingBoxes()) {
			if (nextRectangle.overlaps(rect)) { // Make a rough estimate of if we collide with something

				boolean leftTopCollision = rect.contains(leftTop);
				boolean leftBottomCollision = rect.contains(leftBottom);

				boolean rightTopCollision = rect.contains(rightTop);
				boolean rightBottomCollision = rect.contains(rightBottom);

				// Handle the easy parts first. When we hit a block perfectly with both corners.
				if ((leftTopCollision && leftBottomCollision)
						|| (rightTopCollision && rightBottomCollision)) {
					setVel(getXVelReflection()); // handle the velocity change accordingly
					return true;
				}

				if ((leftTopCollision && rightTopCollision)
						|| (leftBottomCollision && rightBottomCollision)) {
					setVel(getYVelReflection());
					return true;
				}
				// Precise check begins here.

				if (leftTopCollision) {
					if (checkVerticalPixels(leftTop, deltaMove.y, rect, false)) {
						return true;
					} else {
						if (checkHorizontalPixels(leftTop, rect, true)) {
							return true;
						}
					}
				}

				if (leftBottomCollision) {
					if (checkVerticalPixels(leftBottom, deltaMove.y, rect, true)) {
						return true;
					} else {
						if (checkHorizontalPixels(leftBottom, rect, true)) {
							return true;
						}
					}
				}

				if (rightBottomCollision) {
					if (checkVerticalPixels(rightBottom, deltaMove.y, rect,
							true)) {
						return true;
					} else {
						if (checkHorizontalPixels(rightBottom, rect, false)) {
							return true;
						}
					}
				}

				if (rightTopCollision) {
					if (checkVerticalPixels(rightTop, deltaMove.y, rect, false)) {
						return true;
					} else {
						if (checkHorizontalPixels(rightTop, rect, false)) {
							return true;
						}
					}
				}
				// If every other check fails, apply standard velocity reflection.
				System.err.println("Could not find right collision");
				this.setVel(this.getVel().scl(-1));
				return true;
			}
		}
		return false;
	}

	private boolean checkVerticalPixels(Vector2 corner, float deltaY,Rectangle currentRectangle, boolean up) {
		Vector2 temp = corner.cpy();
		temp.y -= deltaY;

		for (int y = 1; y < getCollisionBox().height; y++) {
			if (up) {
				temp.y += 1;
			} else {
				temp.y -= 1;
			}

			if (currentRectangle.contains(temp)) {
				setVel(getXVelReflection());
				return true;
			}
		}
		return false;
	}

	private boolean checkHorizontalPixels(Vector2 corner,Rectangle currentRectangle, boolean right) {
		Vector2 temp = corner.cpy();
		for (int x = 1; x < getCollisionBox().width; x++) {
			if (right) {
				temp.x += 1;
			} else {
				temp.x -= 1;
			}

			if (currentRectangle.contains(temp)) {
				setVel(getYVelReflection());
				return true;
			}
		}
		return false;
	}

	private Vector2[] updateCollisionboxCorners(Vector2 rectPos) {
		Vector2[] cornernPositions = new Vector2[4];
		cornernPositions[0] = new Vector2(rectPos.x, rectPos.y); // Left Bottom
		cornernPositions[1] = new Vector2(rectPos.x, rectPos.y + getCollisionBox().height); // Left Top
		cornernPositions[2] = new Vector2(rectPos.x + getCollisionBox().width,rectPos.y); // Right Bottom
		cornernPositions[3] = new Vector2(rectPos.x + getCollisionBox().width,rectPos.y + getCollisionBox().height); // Right top

		return cornernPositions;
	}

	private Vector2 getXVelReflection() {
		Vector2 ref = new Vector2(getVelX() * -1, getVelY());
		return ref;
	}

	private Vector2 getYVelReflection() {
		Vector2 ref = new Vector2(getVelX(), -1 * getVelY());
		return ref;
	}

	@Override
	public void draw(ShapeRenderer renderer) {
		drawActor(renderer);

		//TODO Draw collision box
		//renderer.begin(ShapeType.Line);
		//renderer.setColor(Color.RED);
		//renderer.rect(nextRectangle.x, nextRectangle.y, nextRectangle.width,nextRectangle.height);
		// renderer.setColor(Color.WHITE); renderer.rect(getCollisionBox().x,getCollisionBox().y,getCollisionBox().width,getCollisionBox().height);
		//renderer.end();
	}

	@Override
	public void collide(Actor other) {
		if(other instanceof Ball){
			Vector2 tempVel = other.getVel();
			other.setVel(getVel());
			setVel(tempVel);
		}
	}

	@Override
	public boolean isColliding(Actor other) {
		return getCollisionBox().contains(other.getCollisionBox());
	}

	/**
	 * Get the radius of the ball
	 * @return radius
	 */
	public float getRadius() {
		return radius;
	}
}
