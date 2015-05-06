package se.mad.copterplant.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import se.mad.copterplant.Copterplant;
import se.mad.copterplant.util.Settings;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Settings.GAME_NAME;
		config.width = Settings.GAME_WIDTH;
		config.height =Settings.GAME_HEIGHT;
		new LwjglApplication(new Copterplant(), config);
	}
}
