package se.mad.copterplant.screens;

import se.mad.copterplant.Copterplant;
import se.mad.copterplant.actor.Ball;
import se.mad.copterplant.actor.Player;
import se.mad.copterplant.level.Level;
import se.mad.copterplant.level.VisualMap;
import se.mad.copterplant.level.levels.Level01;
import se.mad.copterplant.level.levels.Level02;
import se.mad.copterplant.util.GLUtil;
import se.mad.copterplant.util.Settings;
import se.mad.copterplant.util.UserInput;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;



public class GameScreen extends SimpleScreen {
	private Level[] level;
	private Player player;
	public static Ball[] ball;
	private boolean playing = true;
	private SpriteBatch sb;
	private BitmapFont font, defaultFont;
	private GlyphLayout glyphLayout;

	private Stage stage;
	private TextButton back;
	private Skin skin;
	private Table table;

	public GameScreen(Game game) {
		super(game);
	}


	private void createUI(){
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		back = new TextButton("Menu",skin);
		table.top().left();
		table.add(back);

		back.addListener(new ChangeListener() {
		    public void changed (ChangeEvent event, Actor actor) {
		    	getGame().setScreen(new MenuScreen(getGame()));
		    }
		});


	}
	@Override
	public void init() {
		createUI();

		level = new Level[2];
		level[0] = new Level01("map.mad");
		level[1] = new Level02("map2.mad");
		player = new Player(VisualMap.LevelCoordinatesToScreen(0, 10),level[Settings.CURRENT_LEVEL]);
		ball = new Ball[level[Settings.CURRENT_LEVEL].getNrOfBalls()]; //Don't add to many balls
		
		for(int i = 0;i < ball.length;i++){
			Vector2 pos = randomPos();

			ball[i] = new Ball(VisualMap.LevelCoordinatesToScreen((int)pos.x, (int)pos.y),level[Settings.CURRENT_LEVEL].getVisualMap(),player);
		}

		sb = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		defaultFont = new BitmapFont(Gdx.files.internal("font1.fnt"));
	}

	private Vector2 randomPos(){
		Vector2 pos = new Vector2((int)(Math.random()*18)+1, (int)(Math.random()*18)+1);

		boolean free = false;
		while(!free){ // Check if you can spawn here
			free = true;
			if(level[Settings.CURRENT_LEVEL].getLevelMap().isFilled((int)pos.x, (int)pos.y)){// Check if the maps is clear
				free = false;
				pos = new Vector2((int)(Math.random()*18)+1, (int)(Math.random()*18)+1);
				continue;
			}
			Vector2 screenPos = VisualMap.LevelCoordinatesToScreen((int)pos.x, (int)pos.y);
			for(Ball b:ball){
				if(b != null){// Check if you the ball is spawning on a ball.
					int radius = (int)b.getRadius();
					if(screenPos.dst(b.getPos().sub(radius, radius))<radius*2){
						free = false;
						pos = new Vector2((int)(Math.random()*18)+1, (int)(Math.random()*18)+1);
						break;
					}
				}
			}

		}
		return pos;
	}
	@Override
	public void update(float delta) {
		stage.act(delta);

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			if(level[Settings.CURRENT_LEVEL].isPassed()){

				Settings.CURRENT_LEVEL++;
				if(level.length <= Settings.CURRENT_LEVEL){
					Settings.CURRENT_LEVEL = 0;
				}
			}
			getGame().setScreen(new GameScreen(getGame()));
		}

		if (!playing) return;
		UserInput.POLL_USER_INPUT();
		player.update(delta);

		for(Ball b:ball){
			b.update(delta);
			if (b.getCollisionBox().overlaps(player.getCollisionBox())){
				player.collide(null);
			}
			for(Ball e:ball){
				if(b.getCollisionBox().overlaps(e.getCollisionBox())&&b != e){
					b.collide(e);
				}
			}
		}

		level[Settings.CURRENT_LEVEL].update(delta);
		if (level[Settings.CURRENT_LEVEL].isPassed()) {
			playing = false;
		}
		if(level[Settings.CURRENT_LEVEL].isDead()){
			playing = false;
		}
	}

	@Override
	public void draw() {
		Copterplant.CAMERA.update();
		GLUtil.CLEAR_Window(Color.BLACK);
		Copterplant.RENDERER.setProjectionMatrix(Copterplant.CAMERA.combined);

		//Here we can render stuff.
		level[Settings.CURRENT_LEVEL].draw(Copterplant.RENDERER);
		for(Ball b:ball){
			b.draw(Copterplant.RENDERER);
		}
		player.draw(Copterplant.RENDERER);
		sb.begin();
		if (level[Settings.CURRENT_LEVEL].isPassed()) {
			glyphLayout = new GlyphLayout(font, "YOU WON!!!!!!");
			font.draw(sb, glyphLayout, Settings.GAME_WIDTH/2-glyphLayout.width/2, Settings.GAME_HEIGHT/2-glyphLayout.height/2);
		}
		if(level[Settings.CURRENT_LEVEL].isDead()){
			glyphLayout = new GlyphLayout(font, "YOU LOST!!!!!!");
			font.draw(sb, glyphLayout, Settings.GAME_WIDTH/2-glyphLayout.width/2, Settings.GAME_HEIGHT/2-glyphLayout.height/2);
		}
		
		
		String[] status = level[Settings.CURRENT_LEVEL].getMapStatus();
		glyphLayout = new GlyphLayout(defaultFont, "You have filled "+status[0]+"%\nWin condition "+status[1]+"%");
		defaultFont.draw(sb, glyphLayout,Settings.GAME_WIDTH/2-glyphLayout.width/2-200, 70);
		glyphLayout = new GlyphLayout(defaultFont, "Score: " +  level[Settings.CURRENT_LEVEL].getLevelTimer().currentScore());
		defaultFont.draw(sb, glyphLayout, Settings.GAME_WIDTH/2-glyphLayout.width/2+200, 70);
		sb.end();


		stage.draw();

	}
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		stage.getViewport().update(width, height, true);
	}



}
