package enemies;

import java.io.IOException;
import javax.imageio.ImageIO;
import gamestate.LevelState;

import entity.Entity;

/**
 * Created by gareth on 10/4/14.
 */
public class Enemy extends Entity {
    private int value;

    public Enemy(LevelState levelState, double[] ds, double[] ds2, double d, int i, int enemyHP) {
    	
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


	public void turn(int a) {
		// TODO Auto-generated method stub
		
	}

	public void shoot() {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	protected void thrust(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
