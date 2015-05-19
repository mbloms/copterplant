package se.mad.copterplant.screens;

import se.mad.copterplant.Copterplant;
import se.mad.copterplant.math.Projection;
import se.mad.copterplant.util.GLUtil;
import se.mad.copterplant.util.UserInput;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SatSceen extends SimpleScreen {
	
	ShapeRenderer renderer;
	Rectangle rectA,rectB;
	Vector2 MTV;
	private boolean stop = false;

	float vel = 5;
	
	public SatSceen(Game game) {
		super(game);
	}

	@Override
	public void init() {
		renderer = Copterplant.RENDERER;
		rectA = new Rectangle(200,400,64,64);

		
		rectB = new Rectangle(300,200,64,64);

	}

	@Override
	public void update(float delta) {
		
		UserInput.POLL_USER_INPUT();
		boolean collide = checkCollision(rectA, rectB);
		//System.out.println("Collide:" + collide);
		if (!collide) {
			if (!stop ){
				if (UserInput.RIGHT){
					rectB.x +=vel;
				}
				if (UserInput.LEFT){
					rectB.x -=vel;
				}
				
				if (UserInput.UP){
					rectB.y +=vel;
				}
				if (UserInput.DOWN){
					rectB.y -=vel;
				}	
			}
		}else {
		
			if (MTV != null) {
				Vector2 currMTV = MTV.cpy();
				System.out.println(currMTV);
				rectB.x += currMTV.x;
				rectB.y += currMTV.y;
			}
		}
	}
	
	
	private boolean checkCollision(Rectangle A,Rectangle B) {
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
		
		//If we get here, no gaps where found and we are sure we have a collision.
		MTV = smallest.scl((float) overlap);
		return true;
	}

	private Vector2[] generateNormals(Rectangle rect) {
		Vector2[] axis = new Vector2[4];//As we are dealing with rectangle, we can always assume 4 sides.
		Vector2[] verts = getVerticies(rect);
		for (int i = 0; i<verts.length; i++) {
			//current vertex
			Vector2 p1 = verts[i];
			Vector2 p2;
			if (i + 1 == verts.length) {
				p2 = verts[0];
			}else {
				p2 = verts[i+1];
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
	
	
	

	@Override
	public void draw() {
		Copterplant.CAMERA.update();
		GLUtil.CLEAR_Window(Color.WHITE);
		Copterplant.RENDERER.setProjectionMatrix(Copterplant.CAMERA.combined);
		
		renderer.begin(ShapeType.Filled);
		
		renderer.setColor(Color.RED);
		renderer.rect(rectA.getX(), rectA.getY(), rectA.width, rectA.height);

		renderer.setColor(Color.GREEN);
		renderer.rect(rectB.getX(), rectB.getY(), rectB.width, rectB.height);
		
		renderer.end();
		
		
		
		renderer.begin(ShapeType.Line);
		
		renderer.setColor(Color.BLACK);
		renderer.rect(rectA.getX(), rectA.getY(), rectA.width, rectA.height);

		renderer.setColor(Color.BLACK);
		renderer.rect(rectB.getX(), rectB.getY(), rectB.width, rectB.height);
		
	
		
		
		renderer.end();
		
		
	}

}
