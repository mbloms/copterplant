package se.mad.copterplant.level;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Level {
	protected String filepath;
	protected LevelMap levelMap;
	protected VisualMap visualMap;
	protected LevelTimer levelTimer;
	private float winCondition;
	protected boolean win;
	private int nrOfBalls;
	
	public Level(String filepath) {
		this.filepath = filepath;
		win = false; 
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
	public float getWinCondition() {
		return winCondition;
	}
	/**
	 * Set the win condition in percent (0 to 100)
	 * @param winCondition
	 */
	public void setWinCondition(float winCondition) {
		if(winCondition >= 0 && winCondition <= 100){
			this.winCondition = winCondition;
		}else{
			throw new IllegalArgumentException("The value must be in the interval 0 to 100. winCondition="+winCondition);
		}
	}
	/**
	 * Return true if you have passed the level.
	 */
	public boolean isPassed(){
		return win;
	}
	
	/**
	 * Return true if you have lost. 
	 */
	public boolean isDead(){
		return levelTimer.currentScore() <= 0;
	}
	
	/**
	 * Return how many percent you have filled and how many you need to fill.
	 * Index 0 = current percentage filled
	 * Index 1 = win condition
	 * @return string[] 
	 */
	public String[] getMapStatus(){
		String[] s = new String[2];
		s[0] = ""+(int)getLevelMap().percentageFilled();
		s[1] = ""+(int)winCondition;
		return s;
	}
	/**
	 * @return the nrOfBalls
	 */
	public int getNrOfBalls() {
		return nrOfBalls;
	}
	/**
	 * @param nrOfBalls the nrOfBalls to set
	 */
	public void setNrOfBalls(int nrOfBalls) {
		this.nrOfBalls = nrOfBalls;
	}
}
