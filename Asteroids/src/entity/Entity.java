package entity;

import java.util.ArrayList;

/**
 * Created by gareth on 10/4/14.
 */
public abstract class Entity extends MapObject {
    private double fireRate;
    private ArrayList<Missile> missiles;
    private double turnSpeed;
    private int lives;
    private int health;
    private boolean isDead;
    private int acceleration;
    private int deceleration;
    private Animation deathAnimation;
    private boolean normalFiring;

    public Entity() {

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

    public double getTurnSpeed() {
        return turnSpeed;
    }

    public void setTurnSpeed(double turnSpeed) {
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

    public int getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
    }

    public int getDeceleration() {
        return deceleration;
    }

    public void setDeceleration(int deceleration) {
        this.deceleration = deceleration;
    }

    public Animation getDeathAnimation() {
        return deathAnimation;
    }

    public void setDeathAnimation(Animation deathAnimation) {
        this.deathAnimation = deathAnimation;
    }

    public boolean isNormalFiring() {
        return normalFiring;
    }

    public void setNormalFiring(boolean normalFiring) {
        this.normalFiring = normalFiring;
    }

    public abstract void thrust();

    public abstract void turn(int a);

    public abstract void shoot();
}
