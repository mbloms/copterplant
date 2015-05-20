package se.mad.copterplant.screens;

import se.mad.copterplant.Copterplant;
import se.mad.copterplant.util.GLUtil;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InfoScreen extends SimpleScreen {
	SpriteBatch sb;
	Texture info;
	public InfoScreen(Game game) {
		super(game);
	}

	@Override
	public void init() {
		sb = new SpriteBatch();
		info = new Texture(Gdx.files.internal("info.png"));
	}

	@Override
	public void update(float delta) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			getGame().setScreen(new MenuScreen(getGame()));
		}
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		Copterplant.CAMERA.update();
		GLUtil.CLEAR_Window(52,138,160,1);
		sb.setProjectionMatrix(Copterplant.CAMERA.combined);
		sb.begin();
			sb.draw(info, 0, 0);
		sb.end();

	}
	
	@Override
	public void dispose() {
		super.dispose();
		sb.dispose();
		info.dispose();
	}
	
	

}
