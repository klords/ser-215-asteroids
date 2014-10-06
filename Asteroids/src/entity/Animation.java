package entity;

import java.awt.image.BufferedImage;

public class Animation {
	private BufferedImage[] framesArray;
	private int currentFrame;
	private long startTime;
	private long delay;
	private boolean hasPlayed;
	
	public void Animation(){
		hasPlayed = false;
	}
	public void update(){
		if (delay == -1){
			return;
		}
		long elapsed = (System.nanoTime()-startTime)/1000000;
		if (elapsed > delay){
			currentFrame++;
			startTime = System.nanoTime();//reset timer
		}
		if (currentFrame == framesArray.length){
			currentFrame = 0;
			hasPlayed = true;
		}
	}
	public void setFrames(BufferedImage[] frames){
		this.framesArray = frames;
		currentFrame = 0;
		startTime = System.nanoTime();//reset timer after setting frames
		hasPlayed = false;//determines if animation has played
	}
	public void setDelay(long d){
		delay = d;					//accounts for animation delay
	}
	public void setFrame(int i){
		currentFrame = i;			//sets the current frame int val
		
	}
	public int getFrame(){
		return currentFrame;		//returns current frame
	}
	public boolean getPlayed(){
		return hasPlayed;
	}
	public BufferedImage getCurrentFrame(){
		return framesArray[currentFrame];
	}
		
}

