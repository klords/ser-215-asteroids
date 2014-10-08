package entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import javax.imageio.ImageIO;

import gamestate.GameState;
import gamestate.Level1State;

/**
 * Created by gareth on 10/4/14.
 */
public class Asteroid extends MapObject {
    private int size;
    private int value;
    private AffineTransform at;
    private Level1State state;

    public Asteroid(Level1State state, double[] position, double[] velocity,
    				double angularVelocity) {
    	width = 32;
    	height = 32;
    	angle = Math.random() * (2 * Math.PI);
    	this.angularVelocity = angularVelocity;
    	this.state = state;
    	this.position = position;
    	this.velocity = velocity;
    	try {
			image = ImageIO.read(getClass().getResourceAsStream("/resources/asteroids/asteroidLarge2.png"));
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
    
    public void remove() {
    	state.removeAsteroid(this);
    }
    
    public void update() {
    	super.update();
    	at = AffineTransform.getRotateInstance(angle, position[0] + width, position[1] + height);
    }
    
    public void draw(Graphics2D g) {
    	super.draw(g);
    	AffineTransform temp = g.getTransform();
    	if (at == null) update();
    	g.transform(at);
    	g.drawImage(image, (int)position[0], (int)position[1], null);
    	g.setTransform(temp);
    }
}
