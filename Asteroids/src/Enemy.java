

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entity.Entity;
import gamestate.LevelState;

/**
 * Created by gareth on 10/4/14.
 */
public abstract class Enemy extends Entity {
    private int value;
    private int durability;
    private int size;
    private AffineTransform at;
    private LevelState state;
    private Random angleGenerator;

    public Enemy() {

    	angle = Math.cos(angleGenerator.nextInt((2-1)+1));
    	
    	this.angularVelocity = angularVelocity;
    	this.state = state;
    	this.position = position;
    	this.velocity = velocity;
    	this.size = size;
    	if (size == 2) radius = 32;
    	else radius = 16;
    	
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
    		//remove();
    	}
    }
    
	//public void remove() {
		//state.removeAlien(this);
	//}

    public int getValue1() {
        return value;
    }

    public void setValue1(int value) {
        this.value = value;
    }

	public void thrust(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void turn(int a) {
		// TODO Auto-generated method stub
		
	}

	public void shoot() {
		// TODO Auto-generated method stub
		
	}
}
