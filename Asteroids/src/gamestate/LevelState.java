package gamestate;

import java.awt.Graphics2D;
import java.util.ArrayList;

import tilemap.DebrisField;
import tilemap.Images;
import entity.Asteroid;
import entity.Enemy;
import entity.PlayerShip;

public abstract class LevelState extends GameState {

	protected Images bg;
	protected PlayerShip player;
	protected DebrisField debrisField;
	protected ArrayList<Enemy> enemies;
	protected ArrayList<Asteroid> asteroids;
	protected int numAsteroids;
	
	public LevelState(GameStateManager gsm, PlayerShip player) {
	
		this.gsm = gsm;
		this.player = player;
		debrisField = new DebrisField();
		
		asteroids = new ArrayList<Asteroid>();
		enemies = new ArrayList<Enemy>();
	}

	@Override
	public void init() {}

	@Override
	public void update() {
		
		// update debris
		debrisField.update();
		
		// update asteroids
		if (!asteroids.isEmpty()) {
			for (int i = 0; i < asteroids.size(); i++) {
				asteroids.get(i).update();
			}
		}
		
		// update player
		player.update();
		
	}

	@Override
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		// draw debris
		debrisField.draw(g);
		
		// draw asteroids
		if (!asteroids.isEmpty()) {
			for (int i = 0; i < asteroids.size(); i++) {
				asteroids.get(i).draw(g);
			}
		}
		
		// draw player
		player.draw(g);
		
	}

	@Override
	public void keyPressed(int k) {
		player.keyPressed(k);
	}

	@Override
	public void keyReleased(int k) {
		player.keyReleased(k);
	}

	public void removeAsteroid(Asteroid a) {
		asteroids.remove(a);
	}

}
