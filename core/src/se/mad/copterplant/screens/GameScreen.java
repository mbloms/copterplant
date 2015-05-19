package se.mad.copterplant.screens;

import se.mad.copterplant.Copterplant;
import se.mad.copterplant.actor.Ball;
import se.mad.copterplant.actor.Player;
import se.mad.copterplant.level.VisualMap;
import se.mad.copterplant.level.levels.Level01;
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



public class GameScreen extends SimpleScreen {
	private Level01 level;
	private Player player;
	public static Ball[] ball;
	private boolean playing = true;


	public GameScreen(Game game) {
		super(game);
	}

	@Override
	public void init() {
		level = new Level01("");
		player = new Player(VisualMap.LevelCoordinatesToScreen(0, 10));
		ball = new Ball[1]; //Don't add to many balls (balls < 40)
		for(int i = 0;i < ball.length;i++){
			Vector2 pos = randomPos();

			ball[i] = new Ball(VisualMap.LevelCoordinatesToScreen((int)pos.x, (int)pos.y),level.getVisualMap(),player);
		}
	}

	private Vector2 randomPos(){
		Vector2 pos = new Vector2((int)(Math.random()*18)+1, (int)(Math.random()*18)+1);

		boolean free = false;
		while(!free){ // Check if you can spawn here
			free = true;
			if(Level01.V_MAP.map.isFilled((int)pos.x, (int)pos.y)){// Check if the maps is clear
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
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
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

		level.update(delta);	

	}

	@Override
	public void draw() {
		Copterplant.CAMERA.update();
		GLUtil.CLEAR_Window(Color.BLACK);
		Copterplant.RENDERER.setProjectionMatrix(Copterplant.CAMERA.combined);
		//Here we can render stuff.
		level.draw(Copterplant.RENDERER);
		for(Ball b:ball){
			b.draw(Copterplant.RENDERER);
		}
		player.draw(Copterplant.RENDERER);
		
		
		if (level.isPassed()) {
			SpriteBatch sb= new SpriteBatch();
			BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
			playing = false;
			
			GlyphLayout glyphLayout = new GlyphLayout(font, "YOU WON!!!!!!");
			sb.begin();
			font.draw(sb, glyphLayout, Settings.GAME_WIDTH/2-glyphLayout.width/2, Settings.GAME_HEIGHT/2-glyphLayout.height/2);
			sb.end();
		}
		
		
	}
}
