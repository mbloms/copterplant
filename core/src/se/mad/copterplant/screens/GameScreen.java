package se.mad.copterplant.screens;
import java.util.ArrayList;

import se.mad.copterplant.Copterplant;
import se.mad.copterplant.actor.Actor;
import se.mad.copterplant.actor.Ball;
import se.mad.copterplant.actor.Collidable;
import se.mad.copterplant.actor.Player;
import se.mad.copterplant.level.VisualMap;
import se.mad.copterplant.util.GLUtil;
import se.mad.copterplant.util.Settings;
import se.mad.copterplant.util.UserInput;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;



public class GameScreen extends SimpleScreen {

	private Player player;
	private Ball ball;
	public static VisualMap vMap;
	private ArrayList<Collidable> actors;

	public GameScreen(Game game) {
		super(game);
	}

	@Override
	public void init() {
		actors = new ArrayList<Collidable>();
		player = new Player(new Vector2(10*32+16,32*13 + 16));
		vMap = new VisualMap();
		ball = new Ball(new Vector2(640-32, 96+32*9),vMap,player);
		

		actors.add(ball);

	}

	@Override
	public void update(float delta) {
		UserInput.POLL_USER_INPUT();
		player.update(delta);
		ball.update(delta);
		
		
		if (ball.getCollisionBox().overlaps(player.getCollisionBox())){
			player.collide(null);
		}
		
	
	}

	@Override
	public void draw() {
		Copterplant.CAMERA.update();
		GLUtil.CLEAR_Window(Color.BLACK);
		Copterplant.RENDERER.setProjectionMatrix(Copterplant.CAMERA.combined);
		//Here we can render stuff.
		
		vMap.draw(Copterplant.RENDERER);
		ball.draw(Copterplant.RENDERER);
		player.draw(Copterplant.RENDERER);
	}

}
