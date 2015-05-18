package se.mad.copterplant.screens;
import java.util.ArrayList;
import java.util.Arrays;

import se.mad.copterplant.Copterplant;
import se.mad.copterplant.actor.Actor;
import se.mad.copterplant.actor.Ball;
import se.mad.copterplant.actor.Collidable;
import se.mad.copterplant.actor.Player;
import se.mad.copterplant.level.LevelTimer;
import se.mad.copterplant.level.VisualMap;
import se.mad.copterplant.level.levels.Level01;
import se.mad.copterplant.util.GLUtil;
import se.mad.copterplant.util.Settings;
import se.mad.copterplant.util.UserInput;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;



public class GameScreen extends SimpleScreen {
	private Level01 level;
	private Player player;
	private Ball ball;


	public GameScreen(Game game) {
		super(game);
	}

	@Override
	public void init() {
		level = new Level01("");
		player = new Player(VisualMap.LevelCoordinatesToScreen(0, 5));
		ball = new Ball(VisualMap.LevelCoordinatesToScreen(1, 1),level.getVisualMap());
	}

	@Override
	public void update(float delta) {
		UserInput.POLL_USER_INPUT();
		player.update(delta);
		ball.update(delta);
		level.update(delta);
		
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
		level.draw(Copterplant.RENDERER);
		ball.draw(Copterplant.RENDERER);
		player.draw(Copterplant.RENDERER);
	}

}
