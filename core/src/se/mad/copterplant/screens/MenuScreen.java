package se.mad.copterplant.screens;

import se.mad.copterplant.Copterplant;
import se.mad.copterplant.util.GLUtil;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class MenuScreen extends SimpleScreen{
	Stage stage;
	TextButton play;
	Label nameLabel;
	BitmapFont font;
	Skin skin;
	Table table;
	public MenuScreen(Game game) {
		super(game);
	}

	@Override
	public void init() {
		stage = new Stage();
		table = new Table();
		
		Gdx.input.setInputProcessor(stage);
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		play = new TextButton("Play!", skin);
		nameLabel = new Label("CopterPlant", skin);
		nameLabel.setFontScale(2);
		table.setFillParent(true);
		table.setDebug(false);
		stage.addActor(table);
		table.add(nameLabel);
		table.add().row();
		table.add().height(100).row();
		table.add(play).width(200).height(110);
		play.addListener(new ChangeListener() {
		    public void changed (ChangeEvent event, Actor actor) {
		    	getGame().setScreen(new GameScreen(getGame()));
		    }
		});
	}

	@Override
	public void update(float delta) {
		stage.act(delta);
		
	}

	@Override
	public void draw() {
		Copterplant.CAMERA.update();
		GLUtil.CLEAR_Window(52,138,160,1);

		stage.draw();


	}
	
	@Override
	public void hide() {
		super.hide();
		Gdx.input.setInputProcessor(null);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		stage.dispose();
		
		
	}
	
	
	
	

}
