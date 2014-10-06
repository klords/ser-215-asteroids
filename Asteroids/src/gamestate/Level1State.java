package gamestate;

import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import tilemap.Images;
import main.GamePanel;


public class Level1State extends GameState{
	Images level1bg;
	Images redShip;
	private int xDir;
	private int yDir;
	private int y;
	private int x;
	
	private static int keyCode;	//holds keyCode value for keyListener
	
	public Level1State(GameStateManager gsm){
		this.gsm=gsm;
		
		try {
			level1bg = new Images("/resources/backgrounds/level1bg.png");
			redShip = new Images("/resources/ships/redShip.png");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(int i) {
		if (i==KeyEvent.VK_RIGHT){
			//	player.setRight(true);
			}
		
		if (i==KeyEvent.VK_LEFT){
			//player.setLeft(true);
			}
	
		
	}

	@Override
	public void keyReleased(int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		level1bg.draw(g);
		redShip.draw(g);
		
	}
	
	public void move(){
		x+= xDir;
		y+= yDir;
		if (x==-20){
			x=GamePanel.WIDTH;
		}
		if (x==GamePanel.WIDTH){
			x=-20;
		}
		if (y==-20){
			y=GamePanel.HEIGHT;
		}
		if (y==GamePanel.HEIGHT){
			y=-20;
		}
	}
	
	

}
