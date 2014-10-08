package gamestate;

import java.awt.Graphics2D;

import entity.Asteroid;
import entity.PlayerShip;
import tilemap.Images;
import main.GamePanel;


public class Level1State extends LevelState {
	
	public Level1State(GameStateManager gsm, PlayerShip player){
		
		super(gsm, player);
		
		numAsteroids = 3;
		
		try {
			bg = new Images("/resources/backgrounds/level1bg.png");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void init() {
		
		super.init();
		
		for (int i = 0; i < numAsteroids; i++) {
			Asteroid a = new Asteroid(this, 
									  new double[] {Math.random() * GamePanel.WIDTH, Math.random() * GamePanel.HEIGHT}, 
									  new double[] {Math.random(), Math.random()}, 
									  Math.random() / 30);
			asteroids.add(a);
		}
		
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
	@Override
	public void keyPressed(int k) {
		super.keyPressed(k);
	}
	
	@Override
	public void keyReleased(int k) {
		super.keyReleased(k);
	}
	
}
