package se.mad.copterplant.actor;

import se.mad.copterplant.level.VisualMap;
import se.mad.copterplant.screens.GameScreen;
import se.mad.copterplant.util.Settings;
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
	private boolean creatingPath;

	private float speed = 1;
	private float moveTimer;
	private float moveTimerStart = 0.1f;

	private Path path;


	public Player(Vector2 pos) {
		super(pos);
	}

	@Override
	public void init() {
		moveTimer = moveTimerStart;
		setShape(16);
		setShapeType(ShapeType.Filled);
		setColor(Color.RED);

		creatingPath = false;
		
		getCollisionBox().width -= 5;
		getCollisionBox().height -=5;
		getCollisionBox().x +=5;
		getCollisionBox().y +=5;
	}

	@Override
	public void update(float delta) {

		Vector2 temp = getPos().sub(320,96);
		temp.x /= 32;
		temp.y /= 32;
		

		
		if (!VisualMap.BoundsRect.contains(getCollisionBox())) {
			setVel(new Vector2(0, 0));
		}
		
		
		if(GameScreen.vMap.map.isFilled((int)temp.x,(int)temp.y)){
			setVel(new Vector2(0, 0));
			
			if(creatingPath){
				creatingPath= false;
				path.addNode(getPos());
				if (path != null) {
					
					GameScreen.vMap.map.fillTrack(path.getPath());
				}
				path = null;
			}
		}else{
			if(!creatingPath){
				
				creatingPath = true;
				path = new Path();
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

		setPos(getPos().add(getVel()));

		if(creatingPath){
			path.addNode(getPos());
		}
		if (moveTimer < 0) {
			temp.x +=getVelX();
			temp.y +=getVelY(); 
			temp.x = (int)temp.x*32 + 320 + 16;
			temp.y = (int)temp.y*32 +96 + 16;
			
			if (temp.x > 320+32*20-16) {
				temp.x = 320+32*20-16;
			}
			
			if (temp.y > 96 + 32*20-16){
				temp.y = 96 + 32*20-16;
			}

			setPos(temp);
			moveTimer = moveTimerStart;
		}else {
			moveTimer -=delta; 
		}
	}

	@Override
	public void draw(ShapeRenderer renderer) {

		if(creatingPath && path != null){
			path.draw(renderer, getPos(), getVel());
		}

		drawActor(renderer);
		
		renderer.begin(ShapeType.Line);
			renderer.rect(getCollisionBox().x,getCollisionBox().y,getCollisionBox().width,getCollisionBox().height);
		renderer.end();
	}

	@Override
	public void collide(Actor other) {
		System.out.println("Collision!");
		setPos(new Vector2(10*32,Settings.GAME_HEIGHT/2+16));
		creatingPath = false;
	}

	@Override
	public boolean isColliding(Actor other) {
		return this.getCollisionBox().overlaps(other.getCollisionBox());
	}
}
