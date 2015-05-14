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
		setVel(new Vector2(2,2));

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
				//Lef Top
				if (leftTopCollision) {
					if (checkVerticalPixels(leftTop, deltaMove.y, rect, false)) {
						collided = true;
						break;
					}
					
					if (!collided) {
						if (checkHorizontalPixels(leftTop, rect, true)) {
							collided = true;
							break;
						}
					}

				} // Left top
				//Left Bottom
				if (leftBottomCollision) {
					if (checkVerticalPixels(leftBottom, deltaMove.y, rect, true)) {
						collided = true;
						break;
					}
					
					if (!collided) {
						if (checkHorizontalPixels(leftBottom, rect, true)) {
							collided = true;
							break;
						}
					}

				} // Left bottom 
				
				
				
				
				//Right Bottom
				if (rightBottomCollision) {
					if (checkVerticalPixels(rightBottom, deltaMove.y, rect, true)) {
						collided = true;
						break;
					}
					if (!collided) {
						if (checkHorizontalPixels(rightBottom, rect, false)){
							collided = true;
							break;
						}
					}
				}
				
				
				//Right Top
				if (rightTopCollision) {
					if (checkVerticalPixels(rightTop, deltaMove.y, rect, false)) {
						collided = true;
						break;
					}
					
					if (!collided) {
						if (checkHorizontalPixels(rightTop, rect, false)){
							collided = true;
							break;
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
	
	private void move(){
		collided = false; 																			// starts out with the abillity to move forward.
		
		Vector2 oldPos = getPos(); 																	// the current position at the center of the ball.
		Vector2 newPos = getPos().add(getVel()); 													// the new position that we would like to move to. At the center.
		
		Vector2 rectPos = getPos().sub(getCollisionBox().width / 2,getCollisionBox().height / 2); 	// the rectangular position of the ball. Lower left corner.
		rectPos.add(getVel()); 																		// Changed so that the rectPos is like the next pos but in different coordinates. 
		
		Vector2 deltaMove = newPos.cpy().sub(oldPos); 												// calculating the delta to know how much of the ball is inside a collidable object.
	
		Vector2[] corners = updateCollisionboxCorners(rectPos);	
		Vector2 leftBottom 	= corners[0];
		Vector2 leftTop 	= corners[1];
		Vector2 rightBottom = corners[2];
		Vector2 rightTop 	= corners[3];
		
		Rectangle nextCollisionBox = new Rectangle(rectPos.x, rectPos.y,getCollisionBox().width, getCollisionBox().height);
		nextRectangle = nextCollisionBox;
		
		for (Rectangle rect : vmap.getBoundingBoxes()) {											// iterate over all the collisionboxes in the map
			if (nextCollisionBox.overlaps(rect)) {
				
				boolean leftTopCollision = rect.contains(leftTop);
				boolean leftBottomCollision = rect.contains(leftBottom);

				boolean rightTopCollision = rect.contains(rightTop);
				boolean rightBottomCollision = rect.contains(rightBottom);
				
				//Handle the easy parts first. When we hit a block perfectly with both corners.
				if ((leftTopCollision && leftBottomCollision) || (rightTopCollision && rightBottomCollision)) {
					setVel(getXVelReflection());
					collided = true;
					break;
				}

				if ((leftTopCollision && rightTopCollision) || (leftBottomCollision && rightBottomCollision)) {
					setVel(getYVelReflection());
					collided = true;
					break;
				}
				
				
				//Now onto the hard part.
				if (leftTopCollision) { // Left Top
					collided = checkVerticalPixels(leftTop, deltaMove.y, rect, false);
					
					if (collided) {
						break;
					}else {
						if (checkHorizontalPixels(leftTop, rect, true)) {
							collided = true;
							break;
						}	
					}
				}
				
				//Left Bottom
				if (leftBottomCollision) {
					if (checkVerticalPixels(leftBottom, deltaMove.y, rect, true)) {
						collided = true;
						break;
					}
					
					if (!collided) {
						if (checkHorizontalPixels(leftBottom, rect, true)) {
							collided = true;
							break;
						}
					}

				} // Left bottom 
				
				
				
				
				//Right Bottom
				if (rightBottomCollision) {
					if (checkVerticalPixels(rightBottom, deltaMove.y, rect, true)) {
						collided = true;
						break;
					}
					if (!collided) {
						if (checkHorizontalPixels(rightBottom, rect, false)){
							collided = true;
							break;
						}
					}
				}
				
				
				//Right Top
				if (rightTopCollision) {
					if (checkVerticalPixels(rightTop, deltaMove.y, rect, false)) {
						collided = true;
						break;
					}
					
					if (!collided) {
						if (checkHorizontalPixels(rightTop, rect, false)){
							collided = true;
							break;
						}
					}
					
					
				}
				
				
				
				
				
		
			}//end of overlaps if
		}//end of rectangle for loop
		
		
	}// end of move
	
	private boolean checkVerticalPixels(Vector2 corner, float deltaY, Rectangle currentRectangle,boolean up){
			Vector2 temp = corner.cpy();
			temp.y -= deltaY;
			
			for (int y = 1; y<getCollisionBox().height;y++) {
				if (up){
					temp.y +=1;	
				}else {
					temp.y -=1;
				}
				
				if (currentRectangle.contains(temp)){
					setVel(getXVelReflection());
					return true;
				}
			}
			return false;		
	}
	
	private boolean checkHorizontalPixels(Vector2 corner, Rectangle currentRectangle,boolean right){
		Vector2 temp = corner.cpy();
		for (int x = 1; x<getCollisionBox().width; x++) {
			if (right) {
				temp.x +=1; 
			}else {
				temp.x -=1;
			}
			
			if (currentRectangle.contains(temp)){
				setVel(getYVelReflection());
				return true;
			}
		}
		return false;
	}
	

	private Vector2[] updateCollisionboxCorners(Vector2 rectPos){
		Vector2[] cornernPositions = new Vector2[4];
		cornernPositions[0] = new Vector2(rectPos.x, rectPos.y); // Left Bottom
		cornernPositions[1]  = new Vector2(rectPos.x, rectPos.y + getCollisionBox().height); // Left Top
		cornernPositions[2]  = new Vector2(rectPos.x + getCollisionBox().width,rectPos.y); // Right Bottom
		cornernPositions[3] = new Vector2(rectPos.x + getCollisionBox().width, rectPos.y+ getCollisionBox().height); // Right top
		
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
