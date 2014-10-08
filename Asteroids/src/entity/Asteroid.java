package entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import javax.imageio.ImageIO;

import gamestate.LevelState;

/**
 * Created by gareth on 10/4/14.
 */
public class Asteroid extends MapObject {
	
	private int durability;
    private int size;
    private int value;
    private AffineTransform at;
    private LevelState state;

    public Asteroid(LevelState state, double[] position, double[] velocity,
    				double angularVelocity, int size, int durability) {
    	angle = Math.random() * (2 * Math.PI);
    	this.angularVelocity = angularVelocity;
    	this.state = state;
    	this.position = position;
    	this.velocity = velocity;
    	this.size = size;
    	if (size == 2) radius = 32;
    	else radius = 16;
    	try {
			String img;
			if (size == 2) img = "/resources/asteroids/asteroidLarge2.png";
			else img = "/resources/asteroids/asteroidSmall2.png";
    		image = ImageIO.read(getClass().getResourceAsStream(img));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
    public void update() {
    	super.update();
    	at = AffineTransform.getRotateInstance(angle, position[0] + radius, position[1] + radius);
    }
    
    public void draw(Graphics2D g) {
    	super.draw(g);
    	AffineTransform temp = g.getTransform();
    	if (at == null) update();
    	g.transform(at);
    	g.drawImage(image, (int)position[0], (int)position[1], null);
    	g.setTransform(temp);
    }

    public void hit(int damage) {
    	durability -= damage;
    	if (durability <= 0) {
    		remove();
    	}
    }
    
	private void remove() {
		if (size > 1) {
			for (int i = 0; i < 2; i++) {
				double rand = (Math.random() * 4 - 2) * 2;
				state.addAsteroid(state, 
								  new double[] {position[0], position[1]}, 
								  new double[] {rand * velocity[0], 
												rand * velocity[1]}, 
								  rand * angularVelocity, 
								  (size - 1), 
								  1);			}
		}
		state.removeAsteroid(this);
	}
}
