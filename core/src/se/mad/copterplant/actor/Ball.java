package se.mad.copterplant.actor;

import se.mad.copterplant.level.VisualMap;
import se.mad.copterplant.screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ball extends Actor implements Collidable {

	private VisualMap vmap;
	private boolean collided = false;
	private Rectangle nextRectangle;

	public Ball(Vector2 pos, VisualMap vmap) {
		super(pos);
		this.vmap = vmap;
	}

	@Override
	public void init() {
		setVel(new Vector2());
		setShape(16);
		setShapeType(ShapeType.Filled);
		setColor(Color.CYAN);
		setVel(new Vector2(-1, 0));

	}

	@Override
	public void update(float delta) {
		collided = false;
		Vector2 oldPos = getPos();
		Vector2 newPos = getPos().add(getVel());
		Vector2 rectPos = getPos().sub(getCollisionBox().width / 2,
				getCollisionBox().height / 2);
		rectPos.add(getVel());

		Vector2 deltaMove = newPos.cpy().sub(oldPos);

		if (!vmap.BoundsRect.contains(getCollisionBox())) {
			setVel(getVel().scl(-1));
			collided = true;
		}

		Path.isColliding(this.getCollisionBox());

		Vector2 leftBottom, leftTop;
		leftBottom = new Vector2(rectPos.x, rectPos.y);

		leftTop = new Vector2(rectPos.x, rectPos.y + getCollisionBox().height);

		Vector2 rightBottom, rightTop;
		rightBottom = new Vector2(rectPos.x + getCollisionBox().width,
				rectPos.y);
		rightTop = new Vector2(rectPos.x + getCollisionBox().width, rectPos.y
				+ getCollisionBox().height);

		// rectangle
		Rectangle nextCollisionBox = new Rectangle(rectPos.x, rectPos.y,
				getCollisionBox().width, getCollisionBox().height);
		nextRectangle = new Rectangle(rectPos.x, rectPos.y,
				getCollisionBox().width, getCollisionBox().height);

		for (Rectangle rect : vmap.getBoundingBoxes()) {

			if (nextCollisionBox.overlaps(rect)) {

				boolean leftTopCollision = rect.contains(leftTop);
				boolean leftBottomCollision = rect.contains(leftBottom);

				boolean rightTopCollision = rect.contains(rightTop);
				boolean rightBottomCollision = rect.contains(rightBottom);

				if (leftTopCollision) {
					//System.out.println("left top");
				}

				if (leftBottomCollision) {
					//System.out.println("left bottom");
				}
				if (rightTopCollision) {
					//System.out.println("right top");
				}
				if (rightBottomCollision) {
					//System.out.println("right bottom");
				}

				if ((leftTopCollision && leftBottomCollision)
						|| (rightTopCollision && rightBottomCollision)) {
					setVel(getXVelReflection());
					collided = true;
					break;
				}

				if ((leftTopCollision && rightTopCollision)
						|| (leftBottomCollision && rightBottomCollision)) {
					setVel(getYVelReflection());
					collided = true;
					break;
				}

				if (leftTopCollision) {
					Vector2 temp = leftTop.cpy();
					temp.y -=deltaMove.y; 

					for (int y = 1; y < getCollisionBox().height-1; y++) {
						temp.y -= 1;
						if (rect.contains(temp)) {
							//System.out.println(temp + ":" + leftTop);
							//System.out.println("Found on Left vertical side");
							setVel(getXVelReflection());
							collided = true;
							
							break;
						}
					}

					if (!collided) {
						temp = leftTop.cpy();
						for (int x = 1; x < getCollisionBox().width; x++) {
							temp.x += 1;
							if (rect.contains(temp)) {
								setVel(getYVelReflection());
								collided = true;
								
								break;

							}

						}
					}

				} // Left top

				if (leftBottomCollision) {
					Vector2 temp = leftBottom.cpy();
					if (deltaMove.y < 0) {
						temp.y -= deltaMove.y;
					} else {
						temp.y += deltaMove.y;
					}

					for (int y = 1; y < getCollisionBox().height; y++) {
						temp.y += 1;
						if (rect.contains(temp)) {
							setVel(getXVelReflection());
							collided = true;
							break;
						}
					}

					if (!collided) {

						temp = leftBottom.cpy(); // reset vector

						for (int x = 1; x < getCollisionBox().width; x++) {
							temp.x += 1;


							if (rect.contains(temp)) {
								setVel(getYVelReflection());
								collided = true;
								break;
							}
						}

					}

				} // Left bottom 
				
				
				
				
				
				if (rightBottomCollision) {
					Vector2 temp = rightBottom.cpy();
					temp.y -= deltaMove.y;
					for (int y = 1; y<getCollisionBox().height;y++) {
						temp.y +=1;
						if (rect.contains(temp)){
							setVel(getXVelReflection());
							collided = true;
							break;
						}
					}
					
					if (!collided) {
						temp = rightBottom.cpy();
						for (int x = 1; x<getCollisionBox().width; x++) {
							if (rect.contains(temp)){
								setVel(getYVelReflection());
								collided = true;
								break;
							}
						}		
					}	
				}
				
				
				
				if (rightTopCollision) {
					Vector2 temp = rightTop.cpy();
					//first check vertical
					temp.y -=deltaMove.y;
					for (int y =0; y <getCollisionBox().height;y++) {
						temp.y -=1;
						if (rect.contains(temp)){
							setVel(getXVelReflection());
							collided = true;
							break;
						}
						
						
					}
					
					if (!collided){
						temp = rightTop.cpy();
						for (int x = 1; x<getCollisionBox().width; x++) {
							temp.x -=1; 
							if (rect.contains(temp)){
								setVel(getYVelReflection());
								collided = true;
								break;
							}
						}
					}
					
					
					
				}
				
				
				
				

				// Break out of foreach loop
				if (collided) {
					break;
				}

				// If everything else fails just do a complete reflection of the
				// vel.

				System.err.println("Did not catch the right position :( ");
				this.setVel(this.getVel().scl(-1));
				collided = true;
				break;
			}
		}

		if (!collided)
			setPos(newPos);
		else {
			oldPos.add(getVel());
			setPos(oldPos);
		}

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
		renderer.setColor(Color.GREEN);
		drawActor(renderer);
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.RED);
		renderer.rect(nextRectangle.x, nextRectangle.y, nextRectangle.width,
				nextRectangle.height);
		// renderer.setColor(Color.WHITE);
		// renderer.rect(getCollisionBox().x,getCollisionBox().y,getCollisionBox().width,getCollisionBox().height);
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
