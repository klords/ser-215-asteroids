package gamestate;

import main.*;
import java.awt.Graphics2D;

import tilemap.Images;

public class MenuState extends GameState{
	private Images bg;
	private Images start;
	private Images highScores;
	private Images exitGame;
	private String[] menuOptions = {"Start","High Scores","Exit Game"};
	
	public MenuState(GameStateManager gsm){
		this.gsm = gsm;
		
		try {
			bg = new Images("/Images/menuBG.png");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public void init() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);//draw the background
		
		start.draw(g);//draw
	}
	
	@Override
	public void keyPressed(int i) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(int i) {
		// TODO Auto-generated method stub
		
	}
}
