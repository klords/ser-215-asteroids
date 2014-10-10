package entity;

import gamestate.LevelState;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * Created by gareth on 10/4/14.
 */
public abstract class Entity extends MapObject {
    
	//position
	protected double x;
    protected double y;
    
    //attacks
	protected double fireRate;
	protected int missileDamage;
    protected ArrayList<Missile> missiles;
    protected int maxMissiles;
    
    //movement
    protected int turnSpeed;
    protected double accelSpeed;
    protected boolean isAccelerating;
    protected double friction;
    
    //properties
    protected int lives;
    protected int health;
    protected boolean isDead;
    protected int score;
    
    protected Animation animation;
    
    //state info
  	protected LevelState currentState;
    
  	protected boolean invulnerable;
  	
    public Entity() {
    	missileDamage = 1;
		missiles = new ArrayList<Missile>();
		invulnerable = false;
		score = 0;
    }

    public int getScore() {
    	return score;
    }

    public void incScore(int value) {
		score += value;
	}
    
    public void setScore(int score) {
        this.score = score;
    }

    public double getFireRate() {
        return fireRate;
    }

    public void setFireRate(double fireRate) {
        this.fireRate = fireRate;
    }

    public ArrayList<Missile> getMissiles() {
        return missiles;
    }

    public void setMissiles(ArrayList<Missile> missiles) {
        this.missiles = missiles;
    }

    public int getTurnSpeed() {
        return turnSpeed;
    }

    public void setTurnSpeed(int turnSpeed) {
        this.turnSpeed = turnSpeed;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public double getAcceleration() {
        return accelSpeed;
    }

    public void setAcceleration(int acceleration) {
        accelSpeed = acceleration;
    }

    public double getFriction() {
        return friction;
    }

    public void setFriction(int deceleration) {
        this.friction = deceleration;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }
    
    public void setState(LevelState ls) {
		currentState = ls;
	}
	
	public LevelState getState() {
		return currentState;
	}

    public void update() {
    	
    	// update position
    	x += velocity[0];
    	y += velocity[1];
    	
    	// check collisions
		if (!invulnerable) {
			for (int i = 0; i < currentState.getAsteroids().size(); i++) {
				Asteroid a = currentState.getAsteroids().get(i);
				if (Entity.getDistance(x, y, 
									   a.getPosition()[0], 
									   a.getPosition()[1]) 
					<= radius + a.getRadius()) {
					loseLife();
				}
			}
		}
    	
    	// update angle
    	angle += angularVelocity;
    	
    	// update missiles
		if (!missiles.isEmpty()) {
			for (int i = 0; i < missiles.size(); i++) {
				missiles.get(i).update();
			}
		}
    }
    
    public void draw(Graphics2D g) {
    	// draw actual ship with rotation
		at = AffineTransform.getRotateInstance(Math.toRadians(angle), 
											   x, 
											   y);
		AffineTransform temp = g.getTransform();	// backup original transform
		g.transform(at);							// transform graphics context to rotate image
    	g.drawImage(image, (int)(x - radius), (int)(y - radius), null);
    	if (currentState.getDebugCollision()) {
			g.drawOval((int)(x - radius), (int)(y - radius), (radius * 2), (radius * 2));
			g.drawRect((int)x, (int)y, 1, 1);
		}
		g.setTransform(temp);	
    }
    
    protected abstract void thrust(boolean b);

    protected void turn(int a) {
		angularVelocity = a;
	}

	protected void shoot() {
		if (missiles.size() < maxMissiles) {
			Missile m = new Missile(this, 
									new double[] {x + (Math.cos(Math.toRadians(angle - 90)) * radius), 
												  y + (Math.sin(Math.toRadians(angle - 90)) * radius)},
									new double[] {(velocity[0] * 1.15)+(14 * Math.cos(Math.toRadians(angle - 90))), 
												  (velocity[1] * 1.15) +(14 * Math.sin(Math.toRadians(angle - 90)))},
									missileDamage);
			missiles.add(m);
            
			Sounds sound = new Sounds("/resources/sounds/shoot.wav");
            sound.play();
            
		}
		
	}

	public void removeMissile(Missile m) {
		missiles.remove(m);
	}
    
	private void loseLife() {
		lives--;
		isDead = true;
	}
	
    public static double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
    
}
