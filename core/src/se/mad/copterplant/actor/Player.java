package se.mad.copterplant.actor;

import se.mad.copterplant.level.VisualMap;
import se.mad.copterplant.level.levels.Level01;
import se.mad.copterplant.screens.GameScreen;
import se.mad.copterplant.util.Settings;
import se.mad.copterplant.util.UserInput;

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

	private Path path;

	/**
	 *
	 * @param pos is the start pos of the player.
	 */
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
		getCollisionBox().height -= 5;
		getCollisionBox().x += 5;
		getCollisionBox().y += 5;
	}

	@Override
	public void update(float delta) {

		Vector2 temp = getPos().sub(320, 96);
		temp.x /= 32;
		temp.y /= 32;

		if (!VisualMap.BoundsRect.contains(getCollisionBox())
				|| Level01.V_MAP.map.isFilled((int) temp.x, (int) temp.y)) {
			setVel(new Vector2(0, 0));
		}

		if (Level01.V_MAP.map.isFilled((int) temp.x, (int) temp.y)) {

			if (creatingPath) {
				creatingPath = false;
				path.addNode(getPos());
				if (path != null) {
					Level01.V_MAP.map.fillTrack(path.getPath());
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

			setPos(temp);
			isCollidingPath(getCollisionBox());
			if (creatingPath) {
				path.addNode(getPos());
			}
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

		drawActor(renderer);

		renderer.begin(ShapeType.Line);
		renderer.rect(getCollisionBox().x, getCollisionBox().y,
				getCollisionBox().width, getCollisionBox().height);
		renderer.end();
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
