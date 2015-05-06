package se.mad.copterplant.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class GLUtil {

	public static void CLEAR_Window(Color color){
		Gdx.gl.glClearColor(color.r,color.g,color.b,color.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	public static void CLEAR_Window(float r,float g, float b,float a){
		Gdx.gl.glClearColor(r/255.0f, g/255.0f, b/255.0f,a/255.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
