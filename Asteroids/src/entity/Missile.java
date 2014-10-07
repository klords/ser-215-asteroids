package entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Created by gareth on 10/4/14.
 */
public class Missile extends MapObject {
    
	private int lifespan;
	private long lifeTimer;
	private PlayerShip player;

    public Missile(PlayerShip player, double[] position, double[] velocity) {
    	lifespan = 500;
    	angularVelocity = 0;
    	this.position = position;
    	this.velocity = velocity;
    	lifeTimer = System.nanoTime();
    	this.player = player;
    	try {
			image = ImageIO.read(getClass().getResourceAsStream("/resources/missiles/normalMissile.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }
    
    public void update() {
    	super.update();
    	if ((System.nanoTime() - lifeTimer) / 1000000 > lifespan) {
    		player.removeMissile(this);
    	}
    }
    
    public void draw(Graphics2D g) {
    	super.draw(g);
		g.drawImage(image, (int)position[0], (int)position[1], null);
    }
    
}
