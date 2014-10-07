package gamestate;

import main.*;
import tilemap.*;
import java.awt.Graphics2D;
import java.awt.event.*;

import tilemap.Images;

public class MenuState extends GameState{
	private boolean running;
	private Images mainbg;
	private Images debrisField0;
	private Images start;
	private Images highScores;
	private Images exitGame;
	private String[] menuOptions = {"Start","High Scores","Exit Game"};
	
	private Integer keyCode; //To be used for Menu key event
	private double x;
	private double yDir;
	private double xDir;
	private double y;
	private int currentChoice;
	
	public MenuState(GameStateManager gsm){
		this.gsm = gsm;
		
		try {
			mainbg = new Images("/resources/backgrounds/mainbg.png");
			debrisField0=new Images("/resources/asteroids/debrisField0.png",5.0);
			debrisField0.setAutoScroll(0, 0.25);
			init();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public void init() {
		
	}
	public void selection(){
		if (currentChoice==0){
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if (currentChoice==1){
			//gsm.setState(HIGHSCORESTATE);
		}
		if (currentChoice==2){
			System.exit(0);
		}
	}
	@Override
	public void update() {
		//x+=xDir;
		//y+=yDir;
		debrisField0.update();
		
	}
	@Override
	public void draw(Graphics2D g) {
		mainbg.draw(g);//draw the background
		debrisField0.draw(g);//draw debris field
		
		//start.draw(g);//draw start button
	}
	
	@Override
	public void keyPressed(int i) {
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
}
