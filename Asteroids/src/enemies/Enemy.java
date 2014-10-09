package enemies;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import gamestate.LevelState;

/**
 * Created by gareth on 10/4/14.
 */
public class Enemy extends Entity {
    private int value;

    public Enemy(LevelState levelState, double[] ds, double[] ds2, double d, int i, int enemyHP) {
    	try {
			String img;
			if (size == 2) img = "/resources/enemies/alienShip.png";
			else img = "/resources/enemies/infectedAlien.png";
    		image = ImageIO.read(getClass().getResourceAsStream(img));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	public void thrust() {
		// TODO Auto-generated method stub
		
	}
}
