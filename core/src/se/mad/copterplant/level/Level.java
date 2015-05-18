package se.mad.copterplant.level;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Level {
	protected String filepath;
	protected LevelMap levelMap;
	protected VisualMap visualMap;
	protected LevelTimer levelTimer;
	
	public Level(String filepath) {
		this.filepath = filepath;
	}
	public abstract void update(float delta);
	public abstract void draw(ShapeRenderer renderer);
	
	
	public LevelMap getLevelMap() {
		return levelMap;
	}
	public VisualMap getVisualMap() {
		return visualMap;
	}
	public LevelTimer getLevelTimer() {
		return levelTimer;
	}
}
