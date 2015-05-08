package se.mad.copterplant.screens;
import java.util.ArrayList;
import se.mad.copterplant.Copterplant;
import se.mad.copterplant.actor.Collidable;
import se.mad.copterplant.util.GLUtil;
import se.mad.copterplant.util.UserInput;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;


public class GameScreen extends SimpleScreen {
	private ArrayList<Collidable> actors;
	public GameScreen(Game game) {
		super(game);
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update(float delta) {
		UserInput.POLL_USER_INPUT();
	}

	@Override
	public void draw() {
		Copterplant.CAMERA.update();
		GLUtil.CLEAR_Window(Color.RED);
		Copterplant.RENDERER.setProjectionMatrix(Copterplant.CAMERA.combined);
		//Here we can render stuff.
	}

}