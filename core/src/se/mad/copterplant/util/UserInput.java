package se.mad.copterplant.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
/**
 * This class is for checking the user input.
 * 
 * @author DavidSkeppstedt
 *
 */
public class UserInput {
	public static boolean LEFT,RIGHT,UP,DOWN,ACTION;
	/**
	 * Poll the user input.
	 */
	public static void POLL_USER_INPUT(){

		if (Gdx.input.isKeyPressed(Keys.A)||Gdx.input.isKeyPressed(Keys.LEFT)) {
			LEFT = true;
		}else {
			LEFT = false;
		}

		if (Gdx.input.isKeyPressed(Keys.D)||Gdx.input.isKeyPressed(Keys.RIGHT)) {
			RIGHT = true;
		}else {
			RIGHT = false;
		}


		if (Gdx.input.isKeyPressed(Keys.W)||Gdx.input.isKeyPressed(Keys.UP)) {
			UP = true;
		}else {
			UP = false;
		}

		if (Gdx.input.isKeyPressed(Keys.S)||Gdx.input.isKeyPressed(Keys.DOWN)) {
			DOWN = true;
		}else {
			DOWN = false;
		}
	}
}