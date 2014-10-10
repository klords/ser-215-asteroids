package gamestate;

import java.awt.Graphics2D;

import entity.PlayerShip;
import tilemap.HUD;
import tilemap.Images;


public class Level1State extends LevelState {
	
	public Level1State(GameStateManager gsm, PlayerShip player){
		
		super(gsm, player);
		
		numAsteroids = 3;
		asteroidHP = 1;
		numEnemies = 2;
		enemyHP = 2;
		infectedEnemyHP = 3;
		
		try {
			bg = new Images("/resources/backgrounds/level1bg.png");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

    @Override
	public void init() {
		
		player.setState(this);
		
		super.init();
		
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
