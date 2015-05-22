package se.mad.copterplant.level.levels;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import se.mad.copterplant.level.Level;
import se.mad.copterplant.level.LevelMap;
import se.mad.copterplant.level.LevelTimer;
import se.mad.copterplant.level.VisualMap;

public class Level02 extends Level {

	public Level02(String filepath) {
		super(filepath);
		levelTimer = new LevelTimer(100, 1);
		levelMap = new LevelMap(20, 20,this);
		visualMap = new VisualMap(this);
		levelTimer.start();
		setWinCondition(95);
		setNrOfBalls(5);
	}

	@Override
	public void update(float delta) {
		levelTimer.update(delta);

		if (getLevelMap().percentageFilled() >= getWinCondition()) {
			win = true;
			levelTimer.stop();

		}
	}

	@Override
	public void draw(ShapeRenderer renderer) {
		visualMap.draw(renderer);
	}

}
