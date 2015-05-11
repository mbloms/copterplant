package se.mad.copterplant.screens;
import se.mad.copterplant.Copterplant;
import se.mad.copterplant.actor.Player;
import se.mad.copterplant.util.GLUtil;
import se.mad.copterplant.util.UserInput;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


public class GameScreen extends SimpleScreen {

	private Player player;
	private Ball ball;
	private VisualMap vMap;	
	public GameScreen(Game game) {
		super(game);
	}

	@Override
	public void init() {

		player = new Player(new Vector2(100, 100));
		ball = new Ball(new Vector2(Settings.GAME_WIDTH/2 - 16, Settings.GAME_HEIGHT/2));
		vMap = new VisualMap();

	}

	@Override
	public void update(float delta) {
		UserInput.POLL_USER_INPUT();
		player.update(delta);
		ball.update(delta);
	
	}

	@Override
	public void draw() {
		Copterplant.CAMERA.update();
		GLUtil.CLEAR_Window(Color.RED);
		Copterplant.RENDERER.setProjectionMatrix(Copterplant.CAMERA.combined);
		//Here we can render stuff.
		player.draw(Copterplant.RENDERER);
		ball.draw(Copterplant.RENDERER);
		vMap.draw(Copterplant.RENDERER);
	}

}
