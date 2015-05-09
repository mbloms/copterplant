package se.mad.copterplant.screens;
import se.mad.copterplant.Copterplant;
import se.mad.copterplant.actor.Ball;
import se.mad.copterplant.util.GLUtil;
import se.mad.copterplant.util.Settings;
import se.mad.copterplant.util.UserInput;
import se.mad.copterplant.world.Wall;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


public class GameScreen extends SimpleScreen {
	private Ball ball;
	private Wall wallRight,wallLeft;
	private Wall wallUp,wallDown;
	
	public GameScreen(Game game) {
		super(game);
	}

	@Override
	public void init() {
		ball = new Ball(new Vector2(100, 150));
		wallRight = new Wall(new Vector2(Settings.GAME_WIDTH-32-10,0),new Vector2(),32,800);
		wallLeft = new Wall(new Vector2(10, 0),new Vector2(),32,800);
		wallUp = new Wall(new Vector2(0,  Settings.GAME_HEIGHT-32),new Vector2(),1280,32);
		wallDown = new Wall(new Vector2(0, 0),new Vector2(),1280,32);
	}

	@Override
	public void update(float delta) {
		UserInput.POLL_USER_INPUT();
		
		if (ball.isColliding(wallRight)){
			ball.collide(wallRight);
			wallRight.collide(ball);
		}
		
		if (ball.isColliding(wallLeft)){
			ball.collide(wallLeft);
			wallLeft.collide(ball);
		}
		
		if (ball.isColliding(wallUp)){
			ball.collide(wallUp);
			wallLeft.collide(wallUp);
		}
		
		if (ball.isColliding(wallDown)){
			ball.collide(wallDown);
			wallLeft.collide(wallDown);
		}
		
		
		
		ball.update(delta);
		wallRight.update(delta);
		wallLeft.update(delta);
		wallUp.update(delta);
		wallDown.update(delta);
	}

	@Override
	public void draw() {
		Copterplant.CAMERA.update();
		GLUtil.CLEAR_Window(Color.RED);
		Copterplant.RENDERER.setProjectionMatrix(Copterplant.CAMERA.combined);
		//Here we can render stuff.
		ball.draw(Copterplant.RENDERER);
		wallRight.draw(Copterplant.RENDERER);
		wallLeft.draw(Copterplant.RENDERER);
		wallUp.draw(Copterplant.RENDERER);
		wallDown.draw(Copterplant.RENDERER);
	}

}