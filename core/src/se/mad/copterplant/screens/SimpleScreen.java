package se.mad.copterplant.screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
/**
 * An abstract implementation of a game screen. A screen can be
 * something like a menu screen, settings screen or a gameplay screen.
 * @author DavidSkeppstedt
 */
public abstract class SimpleScreen extends ScreenAdapter {
	
	private Game game;
	protected OrthographicCamera camera;

	public SimpleScreen(Game game) {
		this.setGame(game);		
		init();
	}
	/**
	 * This method is for all the stuff that needs to be 
	 * initialized in the beginning. This is like libGDX create()
	 */
	public abstract void init();
	/**
	 * Use update(delta) to update object states the needs update every frame
	 * @param delta - the delta speed of the frames.
	 */
	public abstract void update(float delta);
	/**
	 * Use draw() to draw all the things in your screen.
	 */
	public abstract void draw() ;

	public void render(float delta) {
		update(delta);
		draw();
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}
}