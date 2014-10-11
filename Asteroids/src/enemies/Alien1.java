package enemies;
import entity.Missile;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;
import entity.Asteroid;
import entity.Enemy;
import entity.Missile;
import gamestate.LevelState;

/**
 * Created by gareth on 10/5/14.
 */
public class Alien1 extends Enemy {
	
	private AffineTransform at;
	private BufferedImage image;
	
    public Alien1(LevelState currentState) {
    	
    	super(currentState);
		
    	try{
			image = ImageIO.read(getClass().getResourceAsStream("/Resources/enemies/alienShip.png"));//load alien1
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		width = image.getWidth();
		radius = width / 2;
		angle = 0;
		angularVelocity = 0;
		Random rand = new Random();
		velocity = new double[] {Math.random(), 0};
		maxMissiles = 3;
		missileDamage = 1;
		missiles = new ArrayList<Missile>();
		invulnerable = false;
		
		if (rand.nextBoolean()) {
			if (rand.nextBoolean()) {
				x = GamePanel.WIDTH + radius;
				velocity = new double[] {rand.nextDouble() * -5 - 5, 0};
			}
			else {
				x = -radius;
				velocity = new double[] {rand.nextDouble() * 5 + 5, 0};
			}
			y = rand.nextInt(GamePanel.HEIGHT - width) + radius;
		} else {
			if (rand.nextBoolean()) {
				y = GamePanel.HEIGHT + radius;
				velocity = new double[] {0, rand.nextDouble() * -5 - 5};
			}
			else {
				y = -radius;
				velocity = new double[] {0, rand.nextDouble() * 5 + 5};
			}
			x = rand.nextInt(GamePanel.WIDTH - width) + radius;
		}
    }
    
    public void setDead(boolean b) {
		isDead = b;
	}
	
	public boolean getDead() {
		return isDead;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void setState(LevelState ls) {
		currentState = ls;
	}
	
	public LevelState getState() {
		return currentState;
	}
	
	public void thrust(boolean b){
		if (!isAccelerating) {
			isAccelerating = b;
		}
		else if (!b && isAccelerating) {
			isAccelerating = b;
		}
	}
	
	public void turn(int a) {
		angularVelocity = a;
	}

	public void shoot() {
		if (missiles.size() < maxMissiles) {
			Missile m = new Missile(this, 
									new double[] {(x + (Math.cos(Math.toRadians(angle - 90))) * 27), 
												  (y + (Math.sin(Math.toRadians(angle - 90))) * 27)},
									new double[] {(velocity[0] * 1.15)+(14 * Math.cos(Math.toRadians(angle - 90))), 
												  (velocity[1] * 1.15) +(14 * Math.sin(Math.toRadians(angle - 90)))},
									missileDamage);
			missiles.add(m);
		}
		
	}

	public void removeMissile(Missile m) {
		missiles.remove(m);
	}

    public void draw(Graphics2D g) {
		g.drawImage(image, (int)(x - radius), (int)(y - radius), null);
	}
    
    public void update(){
		
		// update position
		x += velocity[0];
		y += velocity[1];
		
		for (int i = 0; i < currentState.getAsteroids().size(); i++) {
			Asteroid a = currentState.getAsteroids().get(i);
			if (Alien1.getDistance(x + radius, 
									   y + radius, 
									   a.getPosition()[0] + a.getRadius(), 
									   a.getPosition()[1] + a.getRadius()) 
				<= radius + a.getRadius()) {
				isDead = true;
			}
		}
		
		// update angle
		angle += angularVelocity;
		
		// draw missiles
		if (!missiles.isEmpty()) {
			for (int i = 0; i < missiles.size(); i++) {
				missiles.get(i).update();
			}
		}
		
	}
	
}
