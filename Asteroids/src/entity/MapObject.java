package entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.Drawable;
import main.GamePanel;

public abstract class MapObject implements Drawable {
	
	protected int width;
	protected int height;
	protected double velocity[];
	protected double angle;
	protected double angularVelocity;
	protected double[] position;
	protected double radius;
	protected BufferedImage image;
	protected AffineTransform at;

	public double[] getVelocity() {
		return velocity;
	}
	
	public void setVelocity(double[] velocity) {
		this.velocity = velocity;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public double getAngularVelocity() {
		return angularVelocity;
	}
	
	public void setAngularVelocity(double angularVelocity) {
		this.angularVelocity = angularVelocity;
	}
	
	public double[] getPosition() {
		return position;
	}
	
	public void setPosition(double[] position) {
		this.position = position;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public void update() {
		angle += angularVelocity;
		
		for (int i = 0; i <= 1; i++) {
			position[i] += velocity[i];
		}
		
		if (position[0] < 0) position[0] = GamePanel.WIDTH - position[0];
		else if (position[0] > GamePanel.WIDTH) position[0] = position[0] - GamePanel.WIDTH;
		if (position[1] < 0) position[1] = GamePanel.HEIGHT - position[1];
		else if (position[1] > GamePanel.HEIGHT) position[1] = position[1] - GamePanel.HEIGHT;
		
 	}
	
	public void draw(Graphics2D g) {
	}
}
