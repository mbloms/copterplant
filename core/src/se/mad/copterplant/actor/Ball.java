package se.mad.copterplant.actor;

import se.mad.copterplant.level.VisualMap;
import se.mad.copterplant.math.Projection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ball extends Actor implements Collidable {

	private VisualMap vmap;
	private boolean collided = false;
	private Rectangle nextRectangle,collidedRectangle;
	private Player player;
	private float speed;
	private float radius;
	private Vector2 MTV;

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
		setColor(Color.CYAN);
		speed = 3f;
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

		Vector2 newPos = getPos().add(getVel()); // the new position that we would like to move to. At the center.
		Vector2 rectPos = getPos().sub(getCollisionBox().width / 2,getCollisionBox().height / 2); // the rectangular position of the ball. Lower left corner.
		rectPos.add(getVel()); // Changed so that the rectPos is like the next pos but in different coordinates.
		
		nextRectangle = new Rectangle(rectPos.x, rectPos.y,
				getCollisionBox().width, getCollisionBox().height);
		
		
		collided = checkCollision(nextRectangle); // do collision detection and handling.
	
		
		if (!collided){
			this.setPos(newPos);
		}else {
			//this.setPos(newPos);
			if (MTV != null) {
				Vector2 currMTV = MTV.cpy();
				this.setPos(getPos().add(currMTV));
				
				
				Vector2 currPos = new Vector2(getCollisionBox().x,getCollisionBox().y);
				
				if((currPos.x+getCollisionBox().width) <= collidedRectangle.x ) {
					if (currPos.y<=collidedRectangle.y) {
						setVel(getYVelReflection());
					}else {
						setVel(getXVelReflection());
					}
					
				}

				if ((currPos.y) >= collidedRectangle.y+collidedRectangle.height) {
					setVel(getYVelReflection());
				}
				
				
				if (currPos.x >= collidedRectangle.x) {
					if (currPos.y <= collidedRectangle.y){
						setVel(getYVelReflection());
					}
					
				}
				
				
				if (currPos.x >= collidedRectangle.x +collidedRectangle.width) {
					setVel(getXVelReflection());
				}
				
				
				
				
				
				
			}
		}
		
		
		
	}
	
	private boolean checkCollision(Rectangle nextRectangle){
		for (Rectangle r:vmap.getBoundingBoxes()){
			if (detectCollision(nextRectangle, r)) {
				collidedRectangle = r;
				return true;
			}
		}
		
		return false;
	}
	/**
	 * SAT collision checking.
	 * @param A
	 * @param B
	 * @return
	 */
	private boolean detectCollision(Rectangle A,Rectangle B){

		double overlap = Integer.MAX_VALUE;
		Vector2 smallest = null;
		
		Vector2[] axis1 = generateNormals(A);
		Vector2[] axis2 = generateNormals(B);
		
		for (int i = 0; i<axis1.length; i++) {
			Vector2 normal = axis1[i];
			Projection p1 = Projection.Project(getVerticies(A),normal);
			Projection p2 = Projection.Project(getVerticies(B),normal);
			if (!p1.overlaps(p2)) 
				return false;
			else {
				double currOverlap = p1.getOverlap(p2);
				if (currOverlap<overlap){
					overlap = currOverlap;
					smallest = normal;
				}
			}
		}
		
		for (int i = 0; i<axis2.length; i++) {
			Vector2 normal = axis2[i];
			Projection p1 = Projection.Project(getVerticies(A),normal);
			Projection p2 = Projection.Project(getVerticies(B),normal);
			if (!p1.overlaps(p2)) 
				return false;
			else {
				double currOverlap = p1.getOverlap(p2);
				if (currOverlap<overlap){
					overlap = currOverlap;
					smallest = normal;
				}
			}
		}
		
		MTV = smallest.scl((float) overlap);
		
		Vector2 centerA = new Vector2(A.x,A.y); // shape a center;
		Vector2 centerB = new Vector2(B.x,B.y); // shape a center;
		
		Vector2 centerBtoCenterA = centerB.sub(centerA);
		if (MTV.dot(centerBtoCenterA) > 0) {
			MTV.scl(-1);
		}
		
		//If we get here, no gaps where found and we are sure we have a collision.
		
		return true;
		
	}
	
	private Vector2[] generateNormals(Rectangle rect) {
		Vector2[] axis = new Vector2[4];//As we are dealing with rectangle, we can always assume 4 sides.
		Vector2[] verts = getVerticies(rect);
		for (int i = 0; i<verts.length; i++) {
			//current vertex
			Vector2 p1 = verts[i].cpy();
			Vector2 p2;
			if (i + 1 == verts.length) {
				p2 = verts[0].cpy();
			}else {
				p2 = verts[i+1].cpy();
			}
			
			Vector2 edge = p1.sub(p2);
			Vector2 normal = getNormal(edge);
			axis[i] = normal;
		}
		return axis;
	}
	
	private Vector2 getNormal(Vector2 vec){
		return new Vector2(-vec.y,vec.x);
	}
	
	
	private Vector2[] getVerticies(Rectangle rect){
		Vector2[] verticies = new Vector2[4];//As we are dealing with rectangle, we can always assume 4 sides.
		verticies[0] = new Vector2(rect.x,rect.y); // lower left
		verticies[1] = new Vector2(rect.x,rect.y + rect.height); // left top
		verticies[2] = new Vector2(rect.x + rect.width,rect.y + rect.height); // right top
		verticies[3] = new Vector2(rect.x + rect.width,rect.y); // right bottom
		return verticies;
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

		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.MAGENTA);
			if (collidedRectangle != null)
			renderer.rect(collidedRectangle.x,collidedRectangle.y,collidedRectangle.width,collidedRectangle.height);
		
		renderer.end();
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
