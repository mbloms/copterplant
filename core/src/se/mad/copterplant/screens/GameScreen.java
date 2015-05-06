package se.mad.copterplant.screens;

import se.mad.copterplant.Copterplant;
import se.mad.copterplant.util.GLUtil;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


public class GameScreen extends SimpleScreen {

	public GameScreen(Game game) {
		super(game);
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void draw() {
		Copterplant.CAMERA.update();
		GLUtil.CLEAR_Window(Color.RED);
		Copterplant.RENDERER.setProjectionMatrix(Copterplant.CAMERA.combined);
		//Here we can render stuff.
	}

}