package entity;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.*;

import main.GamePanel;

public class PlayerShip extends Entity {
	
	private int currentShip;
	private String[] shipArray={"redShip","blueShip","greenShip"} ;
	
	//dup image object for screenwrap effect
	private BufferedImage drawImage;
	
	//animations
	private ArrayList<BufferedImage[]> animationArrayList;
	
	//animation frames
	private int framesCount = 4;
	private enum Accel {
		REDACCEL, BLUEACCEL, GREENACCEL
	}
	
	//spawn info
	private boolean isSpawning;
	private long spawnTimer;
	
	public PlayerShip(){
		super();
		lives = 3;
		width = 32;
		//height = 48;
		radius = width / 2;
		accelSpeed = 0.2;
		angle = 0;
		angularVelocity = 0;
		friction = 0.0125;
		velocity = new double[] {0, 0};
		currentShip = 0;
		maxMissiles = 3;
		turnSpeed = 6;
		
		x = GamePanel.WIDTH / 2 - radius;
		y = GamePanel.HEIGHT / 2 - radius;
		
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
	
	public BufferedImage getImage() {
		return drawImage;
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
		thrust(false);
	}
	
	protected void thrust(boolean b){
		if (!isAccelerating) {
			isAccelerating = b;
			animation.setFrames(animationArrayList.get(currentShip));
			animation.setDelay(33);
		}
		else if (!b && isAccelerating) {
			isAccelerating = b;
		}
	}

	public void update(){
		
		super.update();
		
		if (isSpawning) {
			if ((System.nanoTime() - spawnTimer) / 1000000 >= 3000) {
				isSpawning = false;
				invulnerable = false;
			}
		}
		
		// check if completely off-screen, place appropriately
		if (x <= -10) x = GamePanel.WIDTH + x;
		else if (x >= GamePanel.WIDTH) x = x - GamePanel.WIDTH;
		if (y <= -20) y = GamePanel.HEIGHT + y;
		else if (y >= GamePanel.HEIGHT - 10) y = y - GamePanel.HEIGHT;
		
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
		
	}

	@Override
	public void draw(Graphics2D g) {
		
		// draw actual ship with rotation
		at = AffineTransform.getRotateInstance(Math.toRadians(angle), 
											   x, 
											   y);
		AffineTransform temp = g.getTransform();	// backup original transform
		g.transform(at);							// transform graphics context to rotate image
		if (!(isSpawning && ((((System.nanoTime() - spawnTimer) / 1000000) % 250) <= 125))) {
			g.drawImage(drawImage, (int)(x - radius), (int)(y - radius), null);
		}
		if (currentState.getDebugCollision()) {
			g.drawOval((int)(x - radius), (int)(y - radius), (radius * 2), (radius * 2));
			g.drawRect((int)x, (int)y, 1, 1);
		}
		g.setTransform(temp);						// set transform back to original
		
		// draw additional ship image for seamless wrap-around effect
		if (y < 0 && y > -20) {
			at = AffineTransform.getRotateInstance(Math.toRadians(angle), 
												   (int) x + (width / 2), 
												   y + GamePanel.HEIGHT + (height / 2));
			g.transform(at);
			g.drawImage(drawImage, (int) x - radius, (int) (GamePanel.HEIGHT + y) - radius, null);
			g.setTransform(temp); 
		}
		else if (y > GamePanel.HEIGHT - height && y < GamePanel.HEIGHT - 10) {
			at = AffineTransform.getRotateInstance(Math.toRadians(angle), 
												   (int) x + (width / 2), 
												   y - GamePanel.HEIGHT + (height / 2));
			g.transform(at);
			g.drawImage(drawImage, (int) x - radius, (int) (y - GamePanel.HEIGHT) - radius, null);
			g.setTransform(temp);
		}
		if (x < 10 && x > -10) {
			at = AffineTransform.getRotateInstance(Math.toRadians(angle), 
												   (int) x + GamePanel.WIDTH + (width / 2), 
												   y + (height / 2));
			g.transform(at);
			g.drawImage(drawImage, (int) (GamePanel.WIDTH + x) - radius, (int) y - radius, null);
			g.setTransform(temp);
		}
		else if (x > GamePanel.WIDTH - height && x < GamePanel.WIDTH) {
			at = AffineTransform.getRotateInstance(Math.toRadians(angle), 
												   (int) x - GamePanel.WIDTH + (width / 2), 
												   y + (height / 2));
			g.transform(at);
			g.drawImage(drawImage, (int) (x - GamePanel.WIDTH) - radius, (int) y - radius, null);
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
		if (k == KeyEvent.VK_LEFT) turn(-turnSpeed);
		if (k == KeyEvent.VK_RIGHT) turn(turnSpeed);
		if (k == KeyEvent.VK_UP) thrust(true);
		if (k == KeyEvent.VK_SPACE) shoot();
	}

	public void keyReleased(int k) {
		if (k == KeyEvent.VK_LEFT && angularVelocity == -turnSpeed) turn(0);
		if (k == KeyEvent.VK_RIGHT && angularVelocity == turnSpeed) turn(0);
		if (k == KeyEvent.VK_UP) thrust(false);
	}
	
}