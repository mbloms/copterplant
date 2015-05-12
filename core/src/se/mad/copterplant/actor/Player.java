package se.mad.copterplant.actor;

import se.mad.copterplant.level.VisualMap;
import se.mad.copterplant.screens.GameScreen;
import se.mad.copterplant.util.UserInput;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
	
	private Path path;
	
	
	public Player(Vector2 pos) {
		super(pos);
	}

	@Override
	public void init() {
		setShape(32);
		setShapeType(ShapeType.Filled);
		setColor(Color.RED);
	
		creatingPath = false;
	}

	@Override
	public void update(float delta) {
		
		if(Gdx.input.isKeyPressed(Keys.SPACE)){
			visit = !visit;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (VisualMap.BoundsRect.contains(getCollisionBox())){
			visit = false;
		}else {
			visit = true;
		}
		
		if(visit){
			setVel(new Vector2(0, 0));
			if(creatingPath){
				creatingPath= false;
				path.addNode(getPos());
				/*if(path.getFirst().equals(path.get(1))){
					path.removeFirst();
				}*/
				System.out.println(path); // TODO Send to filed

				GameScreen.vMap.map.fillTrack(path.getPath());
				path = null;
			}
		}else{
			if(!creatingPath){
				creatingPath = true; 
				prevNodeVel = getVel();
				path = new Path(getPos());
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
			path.addNode(getPos());
		}
		setPos(getPos().add(getVel()));
	}

	@Override
	public void draw(ShapeRenderer renderer) {
		
		if(creatingPath){
			path.draw(renderer, getPos(), getVel());
		}
		
		drawActor(renderer);
		
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.BLUE);
		renderer.rect(getCollisionBox().x, getCollisionBox().y,getCollisionBox().width, getCollisionBox().height);
		
		renderer.end();
		
		
	}

	@Override
	public void collide(Actor other) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isColliding(Actor other) {
		return false;
	}
}
