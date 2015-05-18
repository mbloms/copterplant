package se.mad.copterplant.level;

public class LevelTimer {
	private float timeToFinishLevel;
	private int scorePerSecond;
	private float currentTime;
	private boolean countDown;
	public LevelTimer(float timeToFinishLevel, int scorePerSecond) {
		this.timeToFinishLevel = timeToFinishLevel;	
		this.scorePerSecond = scorePerSecond;
		this.currentTime = this.timeToFinishLevel;
		this.countDown = false;
	}
	
	public void start(){
		reset();
		this.countDown = true;
	}
	
	private void reset(){
		currentTime = timeToFinishLevel;
	}
	
	public void stop(){
		this.countDown = false;
	}
	
	public float timeLeft(){
		return timeToFinishLevel - currentTime;
	}
	public int currentScore(){
		return (int)currentTime * scorePerSecond; 
	}
	
	public void update(float delta){
		if (countDown) {
			 currentTime -=delta; 
		}
	}

}
