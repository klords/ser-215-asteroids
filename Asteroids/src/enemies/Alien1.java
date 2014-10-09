package enemies;
import entity.Missile;

import java.util.ArrayList;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;
import entity.Asteroid;
import entity.Missile;
import gamestate.LevelState;

/**
 * Created by gareth on 10/5/14.
 */
public class Alien1 extends Enemy {
	
	private int width;
	private int height;
	private int radius;
	private double accelSpeed;
	
	private AffineTransform at;
	private BufferedImage image;
	private BufferedImage drawImage;
	
	//animations
	private ArrayList<BufferedImage[]> animationArrayList;
	
	//animation frames
	private int framesCount = 4;
	
	//spawn info
	private boolean invulnerable;
	private boolean isSpawning;
	private long spawnTimer;
	
	//lives
	private int lives;
	private boolean isDead;
	
	//missile
	private int missileDamage;
	private ArrayList<Missile> missiles;
	private int maxMissiles;
	
	//movement
	private double angularVelocity;
	private double friction;
	private double[] velocity;
	private boolean isAccelerating;
	
	//position
	private double x;
	private double y;
	
	//direction
	private double angle;
	
	//state info
	private LevelState currentState;
	
    public Alien1() {
    	lives = 3;
		width = 32;
		height = 48;
		accelSpeed = 0.2;
		angle = 0;
		angularVelocity = 0;
		friction = 0.0125;
		velocity = new double[] {0, 0};
		maxMissiles = 3;
		missileDamage = 1;
		missiles = new ArrayList<Missile>();
		invulnerable = false;
		
		x = GamePanel.WIDTH / 2 - (width / 2);
		y = GamePanel.HEIGHT / 2 - (height / 2);
		
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/resources/enemies/alienShip.png"));//load alien1
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		drawImage = image;
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
	
	public void spawn() {
		isDead = false;
		spawnTimer = System.nanoTime();
		invulnerable = isSpawning = true;
		x = GamePanel.WIDTH / 2 - radius / 2;
		y = GamePanel.HEIGHT / 2 - radius / 2;
		angle = 0;
		angularVelocity = 0;
		velocity = new double [] {0, 0};
		thrust(true);
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
									new double[] {(x + 8) + (Math.cos(Math.toRadians(angle - 90)) * 27), 
												  (y + 16) + (Math.sin(Math.toRadians(angle - 90)) * 27)},
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
		
		// draw actual ship with rotation
		at = AffineTransform.getRotateInstance(Math.toRadians(angle), 
											   x + (width / 2), 
											   y + (height / 2));
		AffineTransform temp = g.getTransform();	// backup original transform
		g.transform(at);							// transform graphics context to rotate image
		if (!(isSpawning && ((((System.nanoTime() - spawnTimer) / 1000000) % 250) <= 125))) {
			g.drawImage(drawImage, (int) x, (int) y, null);
		}
		g.setTransform(temp);						// set transform back to original
		
		// draw additional ship image for seamless wrap-around effect
		if (y < 0 && y > -20) {
			at = AffineTransform.getRotateInstance(Math.toRadians(angle), 
												   (int) x + (width / 2), 
												   y + GamePanel.HEIGHT + (height / 2));
			g.transform(at);
			g.drawImage(drawImage, (int) x, (int) (GamePanel.HEIGHT + y), null);
			g.setTransform(temp); 
		}
		else if (y > GamePanel.HEIGHT - height && y < GamePanel.HEIGHT - 10) {
			at = AffineTransform.getRotateInstance(Math.toRadians(angle), 
												   (int) x + (width / 2), 
												   y - GamePanel.HEIGHT + (height / 2));
			g.transform(at);
			g.drawImage(drawImage, (int) x, (int) (y - GamePanel.HEIGHT), null);
			g.setTransform(temp);
		}
		if (x < 10 && x > -10) {
			at = AffineTransform.getRotateInstance(Math.toRadians(angle), 
												   (int) x + GamePanel.WIDTH + (width / 2), 
												   y + (height / 2));
			g.transform(at);
			g.drawImage(drawImage, (int) (GamePanel.WIDTH + x), (int) y, null);
			g.setTransform(temp);
		}
		else if (x > GamePanel.WIDTH - height && x < GamePanel.WIDTH) {
			at = AffineTransform.getRotateInstance(Math.toRadians(angle), 
												   (int) x - GamePanel.WIDTH + (width / 2), 
												   y + (height / 2));
			g.transform(at);
			g.drawImage(drawImage, (int) (x - GamePanel.WIDTH), (int) y, null);
			g.setTransform(temp);
		}
		
		// draw missiles
		if (!missiles.isEmpty()) {
			for (int i = 0; i < missiles.size(); i++) {
				missiles.get(i).draw(g);
			}
		}
		
	}
public void update(){
		
		if (isSpawning) {
			if ((System.nanoTime() - spawnTimer) / 1000000 >= 215) {
				isSpawning = false;
				invulnerable = false;
			}
		}
		
		// update position
		x += velocity[0];
		y += velocity[1];
		
		// check if completely off-screen, place appropriately
		if (x <= -10) x = GamePanel.WIDTH + x;
		else if (x >= GamePanel.WIDTH) x = x - GamePanel.WIDTH;
		if (y <= -20) y = GamePanel.HEIGHT + y;
		else if (y >= GamePanel.HEIGHT - 10) y = y - GamePanel.HEIGHT;
		
		// check collisions
		if (!invulnerable) {
			for (int i = 0; i < currentState.getAsteroids().size(); i++) {
				Asteroid a = currentState.getAsteroids().get(i);
				if (Alien1.getDistance(x + radius, 
										   y + radius, 
										   a.getPosition()[0] + a.getRadius(), 
										   a.getPosition()[1] + a.getRadius()) 
					<= radius + a.getRadius()) {
					loseLife();
				}
			}
		}
		
		// update angle
		angle += angularVelocity;
		
		// calculate new velocity with friction
		velocity[0] *= (1 - friction);
		velocity[1] *= (1 - friction);
		
		// acceleration - velocity calculation and animation update
		if (isAccelerating) {
			velocity[0] = accelSpeed * Math.cos(Math.toRadians(angle - 90)) + velocity[0];
			velocity[1] = accelSpeed * Math.sin(Math.toRadians(angle - 90)) + velocity[1];
		}
		
		
		
		// draw missiles
		if (!missiles.isEmpty()) {
			for (int i = 0; i < missiles.size(); i++) {
				missiles.get(i).update();
			}
		}
		
	}
	
	private void loseLife() {
		lives--;
		isDead = true;
	}

	
	public static double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
}
