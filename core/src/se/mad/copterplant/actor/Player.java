package se.mad.copterplant.actor;

import java.util.LinkedList;

import se.mad.copterplant.util.UserInput;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * 
 * @author Andreas Brommund
 *
 */
public class Player extends Actor implements Collidable{
		
	private boolean visit = true; //TODO Get from field
	private boolean creatingPath;
	private Vector2 prevNodeVel; 
	
	private float speed = 4;
	
	LinkedList<Vector2> path; 
	
	public Player(Vector2 pos) {
		super(pos);
	}

	@Override
	public void init() {
		setShape(50f);
		setShapeType(ShapeType.Filled);
		setColor(Color.RED);
		
		path = new LinkedList<Vector2>();
		creatingPath = false;
	}

	@Override
	public void update(float delta) {
		
		if(visit){
			setVel(new Vector2(0, 0));
			if(creatingPath){
				creatingPath= false;
				path.addLast(getPos());
				if(path.getFirst().equals(path.get(1))){
					path.removeFirst();
				}
				System.out.println(path); // TODO Send to filed
				path.clear();
			}
		}else{
			if(!creatingPath){
				creatingPath = true; 
				prevNodeVel = getVel();
				path.addLast(getPos());
			}
		}
		
		if (UserInput.RIGHT){
			setVel(new Vector2(speed, 0));
		}
		if (UserInput.LEFT){
			setVel(new Vector2(-speed, 0));	
		}
		if (UserInput.UP){
			setVel(new Vector2(0, speed));
		}
		if (UserInput.DOWN){
			setVel(new Vector2(0, -speed));
		}
		
		if(creatingPath&&(prevNodeVel.x != getVel().x || prevNodeVel.y != getVel().y)){
			prevNodeVel = getVel();
			path.addLast(getPos());
		}
		setPos(getPos().add(getVel()));
	}

	@Override
	public void draw(ShapeRenderer renderer) {
		drawActor(renderer);
		
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.GREEN);
		renderer.rect(getCollisionBox().x, getCollisionBox().y,getCollisionBox().width, getCollisionBox().height);
		
		renderer.end();
	}

	@Override
	public void collide(Collidable other) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isColliding(Actor other) {
		// TODO Auto-generated method stub
		return false;
	}
}