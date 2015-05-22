package se.mad.copterplant.level.levels;

import se.mad.copterplant.level.Level;
import se.mad.copterplant.level.LevelMap;
import se.mad.copterplant.level.LevelTimer;
import se.mad.copterplant.level.VisualMap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Level01 extends Level {
	
	public Level01(String filepath) {
		super(filepath);
		levelTimer = new LevelTimer(100, 1);
		levelMap = new LevelMap(20, 20,this);
		visualMap = new VisualMap(this);
		levelTimer.start();
		setWinCondition(85);
		setNrOfBalls(3);
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
	}

}
