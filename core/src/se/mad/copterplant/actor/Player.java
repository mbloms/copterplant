package se.mad.copterplant.actor;

import se.mad.copterplant.level.Level;
import se.mad.copterplant.level.VisualMap;
import se.mad.copterplant.level.levels.Level01;
import se.mad.copterplant.screens.GameScreen;
import se.mad.copterplant.util.Settings;
import se.mad.copterplant.util.UserInput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 *
 *
 * @author Andreas Brommund
 *
 */
public class Player extends Actor implements Collidable {
	private boolean creatingPath;

	private float speed = 1;
	private float moveTimer;
	private float moveTimerStart = 0.1f;
	
	private float radius;

	private Path path;
	private Level currentLevel;

	/**
	 *
	 * @param pos is the start pos of the player.
	 */
	public Player(Vector2 pos,Level currentLevel) {
		super(pos);
		this.currentLevel = currentLevel;
	}

	@Override
	public void init() {
		radius = 12;
		moveTimer = moveTimerStart;
		setShape(radius);
		setShapeType(ShapeType.Line);
		setColor(Color.WHITE);

		creatingPath = false;

		getCollisionBox().width -= 5;
		getCollisionBox().height -= 5;
		getCollisionBox().x += 5;
		getCollisionBox().y += 5;
	}

	@Override
	public void update(float delta) {

		Vector2 temp = getPos().sub(320, 96);
		temp.x /= 32;
		temp.y /= 32;

		if ( currentLevel.getLevelMap().isFilled((int) temp.x, (int) temp.y)) {
			setVel(new Vector2(0, 0));
			
			if (creatingPath) {
				creatingPath = false;
				path.addNode(getPos());
				if (path != null) {
					currentLevel.getLevelMap().fillTrack(path.getPath());
					int gridPos[] = VisualMap.ScreenToLevelCoordinates(GameScreen.ball[0].getPos());
					currentLevel.getLevelMap().areaFill(gridPos[0],gridPos[1]);
				}
				path = null;
			}
		} else {
			if (!creatingPath) {

				creatingPath = true;
				path = new Path(getPos(), this);
			}
		}

		if (UserInput.RIGHT) {
			setVel(new Vector2(speed, 0));
		}
		if (UserInput.LEFT) {
			setVel(new Vector2(-speed, 0));
		}
		if (UserInput.UP) {
			setVel(new Vector2(0, speed));
		}
		if (UserInput.DOWN) {
			setVel(new Vector2(0, -speed));
		}

		if (moveTimer < 0) {
			temp.x += getVelX();
			temp.y += getVelY();
			temp.x = (int) temp.x * 32 + 320 + 16;
			temp.y = (int) temp.y * 32 + 96 + 16;

			if (temp.x > 320 + 32 * 20 - 16) {
				temp.x = 320 + 32 * 20 - 16;
			}

			if (temp.y > 96 + 32 * 20 - 16) {
				temp.y = 96 + 32 * 20 - 16;
			}

			
			
			if (creatingPath) {
				path.addNode(getPos());
			}
			setPos(temp);
			isCollidingPath(getCollisionBox());
			moveTimer = moveTimerStart;
		} else {
			moveTimer -= delta;
		}
		if (path != null) {
			path.update(delta);
		}

	}

	@Override
	public void draw(ShapeRenderer renderer) {

		if (creatingPath && path != null) {
			path.draw(renderer);
		}

		Gdx.gl.glLineWidth(400);
		drawActor(renderer);
		Gdx.gl.glLineWidth(1);
		//TODO Draw collision box
		//renderer.begin(ShapeType.Line);
		//renderer.rect(getCollisionBox().x, getCollisionBox().y,getCollisionBox().width, getCollisionBox().height);
		//renderer.end();
	}

	@Override
	public void collide(Actor other) {
		System.out.println("Collision!");
		die();
	}

	@Override
	public boolean isColliding(Actor other) {
		return this.getCollisionBox().overlaps(other.getCollisionBox());
	}

	/**
	 * Kill the player, the method resets the player to default values.
	 */
	public void die() {
		setPos(new Vector2(10 * 32, Settings.GAME_HEIGHT / 2 + 16));
		creatingPath = false;
		path = null;
	}

	/**
	 * Check if a object is colliding with players path.
	 *
	 * @return true if colliding
	 */
	public boolean isCollidingPath(Rectangle rect) {
		if (path == null) {
			return false;
		}
		return path.isColliding(rect);
	}
}
