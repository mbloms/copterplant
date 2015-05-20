package se.mad.copterplant.level.levels;

import se.mad.copterplant.level.Level;
import se.mad.copterplant.level.LevelMap;
import se.mad.copterplant.level.LevelTimer;
import se.mad.copterplant.level.VisualMap;
import se.mad.copterplant.util.Settings;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Level01 extends Level {
	private SpriteBatch batcher;
	private BitmapFont font;
	
	public Level01(String filepath) {
		super(filepath);
		levelTimer = new LevelTimer(100, 1);
		levelMap = new LevelMap(20, 20,this);
		visualMap = new VisualMap(this);
		levelTimer.start();
		batcher = new SpriteBatch();
		font = new BitmapFont();
		setWinCondition(90);
	}

	@Override
	public void update(float delta) {
		levelTimer.update(delta);
		
		if(getLevelMap().percentageFilled() >= getWinCondition()){
			win = true;
			levelTimer.stop();
			
		}
	}
	
	@Override
	public void draw(ShapeRenderer renderer) {
		visualMap.draw(renderer);
		batcher.begin();
			font.draw(batcher, "Score: " +  levelTimer.currentScore(), Settings.GAME_WIDTH - 200, Settings.GAME_HEIGHT-100);
		batcher.end();	
	}

}
