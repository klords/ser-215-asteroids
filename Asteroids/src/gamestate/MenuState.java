package gamestate;

import java.awt.Graphics2D;
import java.awt.event.*;

import entity.Sounds;
import main.GamePanel;
import tilemap.DebrisField;
import tilemap.HUD;
import tilemap.Images;

public class MenuState extends GameState{
		
	// background images
	private Images mainbg;
	private DebrisField debrisField;
	
	// menu icons and selection
	private Images title;
	private Images[] start;
	private Images[] highScores;
	private Images[] exitGame;
	private int currentChoice;

    private Sounds music;
	
	public MenuState(GameStateManager gsm){
		this.gsm = gsm;
		
		try {
			
			mainbg = new Images("/Resources/backgrounds/mainbg.png");
			debrisField = new DebrisField();
            music = new Sounds("/Resources/sounds/menumusic.wav");

			title = new Images("/Resources/backgrounds/asteroidsTitle.png");
			title.setPosition((GamePanel.WIDTH / 2) - (title.getWidth() / 2), 125);
			start = new Images[] {new Images("/Resources/backgrounds/startButton.png"),
								  new Images("/Resources/backgrounds/startButtonFade.png")};
			start[0].setPosition((GamePanel.WIDTH / 2) - (start[0].getWidth() / 2), 275);
			start[1].setPosition((GamePanel.WIDTH / 2) - (start[1].getWidth() / 2), 275);
			highScores = new Images[] {new Images("/Resources/backgrounds/hiscoreButton.png"),
									   new Images("/Resources/backgrounds/hiscoreButtonFade.png")};
			highScores[0].setPosition((GamePanel.WIDTH / 2) - (highScores[0].getWidth() / 2), 325);
			highScores[1].setPosition((GamePanel.WIDTH / 2) - (highScores[1].getWidth() / 2), 325);
			exitGame = new Images[] {new Images("/Resources/backgrounds/exitButton.png"),
									 new Images("/Resources/backgrounds/exitButtonFade.png")};
			exitGame[0].setPosition((GamePanel.WIDTH / 2) - (exitGame[0].getWidth() / 2), 385);
			exitGame[1].setPosition((GamePanel.WIDTH / 2) - (exitGame[1].getWidth() / 2), 385);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void init() {
		currentChoice = 0;
        music.stop();
	    music.loop();
	}

    public void selection(){
		if (currentChoice==0){
			gsm.setState(GameStateManager.PLAYERSELECTSTATE);
		}
		if (currentChoice==1){
			gsm.setState(GameStateManager.HIGHSCORESTATE);
            music.stop();
		}
		if (currentChoice==2){
            music.stop();
			System.exit(0);
		}
	}
	
	@Override
	public void update() {

		debrisField.update();

	}
	
	@Override
	public void draw(Graphics2D g) {
		
		mainbg.draw(g);//draw the background
		debrisField.draw(g);//draw debris field
		
		title.draw(g); // draw main icon
		
		//draw start button
		if (currentChoice == 0) start[0].draw(g);
		else start[1].draw(g);
		
		// draw high score button
		if (currentChoice == 1) highScores[0].draw(g);
		else highScores[1].draw(g);
		
		// draw quit button
		if (currentChoice == 2) exitGame[0].draw(g);
		else exitGame[1].draw(g);

	}
	
	@Override
	public void keyPressed(int k) {
		
		switch (k) {
			case KeyEvent.VK_UP:
				if (--currentChoice < 0) currentChoice = 2;
				break;
			case KeyEvent.VK_DOWN:
				if (++currentChoice > 2) currentChoice = 0;
				break;
			case KeyEvent.VK_ENTER:
				selection();
				return;
		}
		
	}
	
	@Override
	public void keyReleased(int k) {}
	
}
