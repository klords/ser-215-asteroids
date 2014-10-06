package entity;
import java.util.ArrayList;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.*;
import tilemap.*;

public class PlayerShip {
	private int width;
	private int height;
	private double accelSpeed;
	private Image redShip;
	private String currentShip;
	private String[] shipArray={"redShip","blueShip","GreenShip"} ;
	public PlayerShip(){
		width = 32;
		height = 48;
		accelSpeed = 0.5;
		
		try{
			BufferedImage acceleration = ImageIO.read(getClass().getResourceAsStream("/resources/ships/redShip.png"));//load the current ship
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	//animations
	private ArrayList<BufferedImage[]> animationArrayList;
	//animation frames
	private int framesCount = 4;
	private static final int REDACCEL =0;	//call redship sprite sheet
	private static final int BLUEACCEL = 1;	//call blue sprite sheet
	private static final int GREENACCEL = 2;//call green sprite sheet
	
	//lives
	private int lives;
	private boolean isDead;
	
	//missile
	private boolean isFiring;
	private int missileDamage;
	
	//movement
	private boolean isAccelerating;
	private boolean isTurningRight;
	private boolean isTurningLeft;
	
	public void setAccelerating(){
		isAccelerating = true;
	}
	public void update(){
	}
}
