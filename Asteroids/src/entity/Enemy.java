package entity;

import java.io.IOException;
import javax.imageio.ImageIO;
import gamestate.LevelState;

/**
 * Created by gareth on 10/4/14.
 */
public class Enemy extends Entity {
    
	//AI timers
	private long shootTimer;
	private long velTimer;
	
	private int value;

    public Enemy(LevelState levelState) {
    	currentState = levelState;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


	public void turn(int a) {
		
	}

	public void shoot() {
		
	}

	@Override
	protected void thrust(boolean b) {
		
	}
}
