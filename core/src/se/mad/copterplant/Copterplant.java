package se.mad.copterplant;
import se.mad.copterplant.screens.MenuScreen;
import se.mad.copterplant.screens.SplashScreen;
import se.mad.copterplant.util.Settings;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
/**
 * This is the main game entry class.
 * From here you control the current flow of the game by changing the currentScene.
 * @author DavidSkeppstedt
 *
 */
public class Copterplant extends Game {	
	public static OrthographicCamera CAMERA;
	public static ShapeRenderer RENDERER;
	private Screen currentScreen;
	
	@Override
	public void create () {
		CAMERA = new OrthographicCamera();
		CAMERA.setToOrtho(false,Settings.GAME_WIDTH,Settings.GAME_HEIGHT);
		RENDERER = new ShapeRenderer();
		this.setScreen(new SplashScreen(this));
	}
	@Override
	public void render() {
		super.render();
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)){
			Gdx.app.exit();
		}
	}
}
