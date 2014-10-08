package entity;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.*;

import main.Drawable;
import main.GamePanel;
import gamestate.LevelState;
import gamestate.GameStateManager;

public class PlayerShip implements Drawable {
	
	private int width;
	private int height;
	private int radius;
	private double accelSpeed;
	private int currentShip;
	private String[] shipArray={"redShip","blueShip","greenShip"} ;
	
	private AffineTransform at;
	private BufferedImage image;
	private BufferedImage drawImage;
	
	//animations
	private ArrayList<BufferedImage[]> animationArrayList;
	private Animation animation;
	
	//animation frames
	private int framesCount = 4;
	private enum Accel {
		REDACCEL, BLUEACCEL, GREENACCEL
	}
	
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
	
	
	public PlayerShip(){
		lives = 3;
		width = 32;
		height = 48;
		accelSpeed = 0.2;
		angle = 0;
		angularVelocity = 0;
		friction = 0.0125;
		velocity = new double[] {0, 0};
		currentShip = 0;
		maxMissiles = 3;
		missileDamage = 1;
		missiles = new ArrayList<Missile>();
		invulnerable = false;
		
		x = GamePanel.WIDTH / 2 - (width / 2);
		y = GamePanel.HEIGHT / 2 - (height / 2);
		
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/resources/ships/" + shipArray[currentShip] + ".png"));//load the current ship
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		drawImage = image;
		
		animation = new Animation();
		animationArrayList = new ArrayList<BufferedImage[]>();
		for (Accel a : Accel.values()) {
			BufferedImage[] bi = new BufferedImage[framesCount];
			for (int i = 0; i < framesCount; i++) {
				try {
					bi[i] = ImageIO.read(getClass().getResourceAsStream("/resources/ships/" + shipArray[a.ordinal()] + "A" + i + ".png"));
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			animationArrayList.add(bi);
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
	
	public void spawn() {
		isDead = false;
		spawnTimer = System.nanoTime();
		invulnerable = isSpawning = true;
		x = GamePanel.WIDTH / 2 - radius / 2;
		y = GamePanel.HEIGHT / 2 - radius / 2;
		angle = 0;
		angularVelocity = 0;
		velocity = new double [] {0, 0};
	}
	
	public void thrust(boolean b){
		if (!isAccelerating) {
			isAccelerating = b;
			animation.setFrames(animationArrayList.get(currentShip));
			animation.setDelay(33);
		}
		else if (!b && isAccelerating) {
			isAccelerating = b;
		}
	}
	
	private void turn(int a) {
		angularVelocity = a;
	}

	private void shoot() {
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

	public void update(){
		
		if (isSpawning) {
			System.out.println("Spawning");
			if ((System.nanoTime() - spawnTimer) / 1000000 > 3000) {
				isSpawning = false;
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
				if (PlayerShip.getDistance(x + radius, y + radius, a.getPosition()[0] + a.getRadius(), a.getPosition()[1] + a.getRadius()) <= radius + a.getRadius()) {
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
			animation.update();
		}
		
		// acceleration animation - determine correct frame and draw
		if (isAccelerating && !animation.getPlayed()) {
			drawImage = animation.getCurrentFrame();
		}
		else {
			if (isAccelerating) {
				animation.setFrame(3);
				drawImage = animation.getCurrentFrame();
			}
			else {
				drawImage = image;
			}
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

	@Override
	public void draw(Graphics2D g) {
		
		// draw actual ship with rotation
		at = AffineTransform.getRotateInstance(Math.toRadians(angle), 
											   x + (width / 2), 
											   y + (height / 2));
		AffineTransform temp = g.getTransform();	// backup original transform
		g.transform(at);							// transform graphics context to rotate image
		if (!(isSpawning && ((System.nanoTime() - spawnTimer) / 1000000 % 1000 <= 500)))
			System.out.println("true");
			g.drawImage(drawImage, (int) x, (int) y, null);
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
	
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_LEFT) turn(-6);
		if (k == KeyEvent.VK_RIGHT) turn(6);
		if (k == KeyEvent.VK_UP) thrust(true);
		if (k == KeyEvent.VK_SPACE) shoot();
	}

	public void keyReleased(int k) {
		if (k == KeyEvent.VK_LEFT && angularVelocity == -6) turn(0);
		if (k == KeyEvent.VK_RIGHT && angularVelocity == 6) turn(0);
		if (k == KeyEvent.VK_UP) thrust(false);
	}
	
	public static double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
	
}