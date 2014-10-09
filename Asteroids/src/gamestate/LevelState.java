package gamestate;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import main.GamePanel;
import tilemap.DebrisField;
import tilemap.Images;
import tilemap.HUD;
import entity.Asteroid;
import entity.Enemy;
import entity.PlayerShip;

public abstract class LevelState extends GameState {

	protected Images bg;
	protected HUD hud;
	protected PlayerShip player;
	protected DebrisField debrisField;
	protected ArrayList<Enemy> enemies;
	protected ArrayList<Asteroid> asteroids;
	protected int numAsteroids;
	protected int asteroidHP;
	protected int numEnemies;
	protected int enemyHP;
	protected int infectedEnemyHP;
	protected Random rand;
	
	public LevelState(GameStateManager gsm, PlayerShip player) {
	
		this.gsm = gsm;
		this.player = player;
		debrisField = new DebrisField();
		
	}

	public GameStateManager getGSM() {
		return gsm;
	}
	
	public boolean getDebugCollision() {
		return gsm.getDebugCollision();
	}
	
	@Override
	public void init() {
		
		asteroids = new ArrayList<Asteroid>();
		enemies = new ArrayList<Enemy>();
		hud = new HUD(player);
				
		for (int i = 0; i < numAsteroids; i++) {
			double newX = Math.random() * GamePanel.WIDTH;
			while (newX > (GamePanel.WIDTH / 4) && newX < (3 * GamePanel.WIDTH / 4)) newX = Math.random() * GamePanel.WIDTH;
			double newY = Math.random() * GamePanel.HEIGHT;
			while (newY > (GamePanel.HEIGHT / 4) && newY < (3 * GamePanel.HEIGHT / 4)) newY = Math.random() * GamePanel.HEIGHT; 
			Asteroid a = new Asteroid(this, 
									  new double[] {Math.random() * GamePanel.WIDTH, Math.random() * GamePanel.HEIGHT}, 
									  new double[] {Math.random(), Math.random()}, 
									  Math.random() / 30, 2, asteroidHP);
			addAsteroid(a);
		}
		for (int i =0;i < numEnemies;i++){
			Enemy a = new Enemy(this);
			addEnemy(a);
		}
		
		player.spawn();
		
	}

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
		
		if (player.isDead()) {
			if (player.getLives() <= 0) {
				//game over
				gsm.setState(GameStateManager.MENUSTATE);
			}
			else {
				player.spawn();
			}
		}
		
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
	
		// draw HUD
		hud.draw(g);
		
	}

	@Override
	public void keyPressed(int k) {
		player.keyPressed(k);
	}

	@Override
	public void keyReleased(int k) {
		player.keyReleased(k);
	}

	public ArrayList<Asteroid> getAsteroids() {
		return asteroids;
	}
	
	public void addAsteroid(Asteroid a) {
		asteroids.add(a);
	}
	public void addEnemy(Enemy a){
		enemies.add(a);
	}
	
	public void addAsteroid(LevelState state, double[] position, 
							double[] velocity, double angularVelocity, 
							int size, int asteroidHP) {
		asteroids.add(new Asteroid(state, 
								   position, 
								   velocity, 
								   angularVelocity, 
								   size, 
								   asteroidHP));
	}
	
	
	public void removeAsteroid(Asteroid a) {
		asteroids.remove(a);
	}
	public void removeAlien(Enemy enemy){
		enemies.remove(enemy);
	}

}
