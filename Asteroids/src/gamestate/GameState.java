package gamestate;

import java.awt.Graphics2D;
import java.awt.event.*;

public abstract class GameState extends KeyAdapter{
	protected GameStateManager gsm;
	public abstract void init();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public void keyReleased(int i) {
		// TODO Auto-generated method stub
		
	}
	public void keyPressed(int i) {
		// TODO Auto-generated method stub
		
	}
	
}
