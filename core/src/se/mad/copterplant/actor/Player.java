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

	private float speed = 4;

	private Path path;


	public Player(Vector2 pos) {
		super(pos);
	}

	@Override
	public void init() {
		setShape(16);
		setShapeType(ShapeType.Filled);
		setColor(Color.RED);

		creatingPath = false;
	}

	@Override
	public void update(float delta) {

		Vector2 temp = getPos().sub(320,96);
		temp.x /= 32;
		temp.y /= 32;
		System.out.println();

		
		if (!VisualMap.BoundsRect.contains(getCollisionBox())) {
			creatingPath = false;
			setVel(new Vector2(0, 0));
		}
		
		if(GameScreen.vMap.map.isFilled((int)temp.x,(int)temp.y)&&VisualMap.BoundsRect.contains(getCollisionBox())){
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
	}

	@Override
	public void draw(ShapeRenderer renderer) {

		if(creatingPath && path != null){
			path.draw(renderer, getPos(), getVel());
		}

		drawActor(renderer);
	}

	@Override
	public void collide(Actor other) {
		System.out.println("Collision!");
		setPos(new Vector2(10*32,Settings.GAME_HEIGHT/2));
		other.setVel(other.getVel().scl(-1));
	}

	@Override
	public boolean isColliding(Actor other) {
		return this.getCollisionBox().overlaps(other.getCollisionBox());
	}
}
