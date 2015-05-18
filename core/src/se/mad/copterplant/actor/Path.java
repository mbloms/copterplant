package se.mad.copterplant.actor;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Andreas Brommund
 *
 */
public class Path {
	private LinkedList<PathObject> path;

	private Player player;

	// Remove the path stuff
	private boolean destroyPath;
	private int removeIndex;
	// Timer stuff
	private float deltaTime;
	private float startTimer;

	/**
	 * Start a new path
	 * 
	 * @param the start pos of the path.
	 * @param player
	 */
	public Path(Vector2 pos, Player player) {
		path = new LinkedList<PathObject>();
		path.add(new PathObject(pos));

		this.player = player;

		destroyPath = false;
		removeIndex = 0;
		deltaTime = .03f; // In seconds
		startTimer = 0;

	}

	/**
	 * Add the node last to the the path.
	 * 
	 * @param node
	 */
	public void addNode(Vector2 node) {
		path.addLast(new PathObject(node));
	}

	/**
	 * Return the path object list.
	 * 
	 * @return LinkedList<PathObject>
	 */
	public LinkedList<PathObject> getPath() {
		return path;
	}

	/**
	 * Draw the path as circles.
	 * 
	 * @param renderer
	 * @param playerPos
	 * @param playerVel
	 */
	public void draw(ShapeRenderer renderer) {
		for (PathObject p : path) {
			p.draw(renderer);
		}
	}

	/**
	 * Check collision between a rectangle and the path, if it is a collision
	 * the method is return true.
	 * 
	 * @param Rectangle
	 * @return true or false
	 */
	public boolean isColliding(Rectangle rect) {
		if (path == null) {
			player.die();
			return false;
		}
		Iterator<PathObject> it = path.iterator();
		int i = 0;
		while (it.hasNext()) {
			if (rect.overlaps(it.next().getCollisionBox())) {
				it.remove();

				if (!destroyPath) {
					destroyPath = true;
					removeIndex = i - 1;
				}
				return true;
			}
			i++;
		}
		return false;
	}

	private void destroyPath() {

		if (removeIndex == path.size()) {
			player.die();
			return;
		}

		if (removeIndex < 0) {
			removeIndex = 0;
		}

		if (path.size() > 0) {
			if (removeIndex > 0) {
				path.remove(removeIndex);
				removeIndex--;
			}
			path.remove(removeIndex);
		} else {
			destroyPath = false;
			removeIndex = 0;
		}
	}

	/**
	 * Update the Path
	 * 
	 * @param delta
	 */
	public void update(float delta) {
		if (startTimer <= 0) {
			if (destroyPath) {
				destroyPath();
			}
			startTimer = deltaTime;
		} else {
			startTimer -= delta;
		}
	}

	public class PathObject {
		private Rectangle collisionBox;
		private Vector2 pos;
		private float radius = 16;

		/**
		 * A new path object
		 * 
		 * @param the pos of the path object.
		 */
		public PathObject(Vector2 pos) {
			collisionBox = new Rectangle(pos.x - radius, pos.y - radius,
					radius * 2, radius * 2);
			this.pos = pos;
		}

		/**
		 * Draw the path object
		 * 
		 * @param renderer
		 */
		public void draw(ShapeRenderer renderer) {
			renderer.begin(ShapeType.Filled);
			renderer.setColor(Color.BLUE);
			renderer.circle(pos.x, pos.y, radius);
			renderer.end();

			//TODO Draw collision box
			//renderer.begin(ShapeType.Line);
			//renderer.setColor(Color.YELLOW);
			//renderer.rect(collisionBox.x, collisionBox.y, collisionBox.width,collisionBox.height);
			//renderer.end();
		}

		/**
		 * Get the collision box of the path object
		 */
		public Rectangle getCollisionBox() {
			return collisionBox;
		}

		/**
		 * Get the position of the path object.
		 *
		 * @return Vector2
		 */
		public Vector2 getPos() {
			return pos;
		}
	}
}
