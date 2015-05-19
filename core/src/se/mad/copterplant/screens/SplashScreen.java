package se.mad.copterplant.screens;

import se.mad.copterplant.Copterplant;
import se.mad.copterplant.util.FileUtil;
import se.mad.copterplant.util.GLUtil;
import se.mad.copterplant.util.Settings;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen extends SimpleScreen {
	private float start = 1.5f;
	private float delay = start;
	private float fade = 0;
	private Texture logo;
	private SpriteBatch sb;
	public SplashScreen(Game game) {
		super(game);
	}

	@Override
	public void init() {
		sb = new SpriteBatch();
		logo = new Texture(Gdx.files.internal("mad_logo.png"));

	}

	@Override
	public void update(float delta) {
		if (delay >= 0) {
			delay -=delta;
			fade +=delta*(1.0f/start); 
		}else {
			delay = 0;
			getGame().setScreen(new MenuScreen(this.getGame()));
		}

	}

	@Override
	public void draw() {
		Copterplant.CAMERA.update();
		GLUtil.CLEAR_Window(52,138,160,1);
		sb.setProjectionMatrix(Copterplant.CAMERA.combined);
		sb.begin();
		 	Color c = sb.getColor();
		 	sb.setColor(c.r, c.g, c.b, fade);
			sb.draw(logo, Settings.GAME_WIDTH/2 - logo.getWidth()/2,Settings.GAME_HEIGHT/2-logo.getHeight()/2);
		sb.end();

	}
	
	@Override
	public void dispose() {
		super.dispose();
		sb.dispose();
		logo.dispose();
		
	}

}
