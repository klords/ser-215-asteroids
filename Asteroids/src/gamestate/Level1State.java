package gamestate;

import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.ArrayList;

import entity.Asteroid;
import entity.Enemy;
import entity.PlayerShip;
import tilemap.Images;
import main.GamePanel;


public class Level1State extends GameState{
	
	Images level1bg;
	PlayerShip player;
	private int xDir;
	private int yDir;
	private int y;
	private int x;
	private ArrayList<Enemy> enemies;
	private ArrayList<Asteroid> asteroids;
	
	//private static int keyCode;	//holds keyCode value for keyListener
	
	public Level1State(GameStateManager gsm){
		this.gsm=gsm;
		
		try {
			level1bg = new Images("/resources/backgrounds/level1bg.png");
			player = new PlayerShip();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		asteroids = new ArrayList<Asteroid>();
		enemies = new ArrayList<Enemy>();
		init();
	}

	@Override
	public void init() {
		for (int i = 0; i < 3; i++) {
			Asteroid a = new Asteroid(this, 
									  new double[] {Math.random() * GamePanel.WIDTH, Math.random() * GamePanel.HEIGHT}, 
									  new double[] {Math.random(), Math.random()}, 
									  Math.random() / 30);
			asteroids.add(a);
		}
	}

	@Override
	public void keyPressed(int i) {
		player.keyPressed(i);
	}

	@Override
	public void keyReleased(int i) {
		player.keyReleased(i);
	}

	@Override
	public void update() {
		player.update();
		if (!asteroids.isEmpty()) {
			for (int i = 0; i < asteroids.size(); i++) {
				asteroids.get(i).update();
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		level1bg.draw(g);
		if (!asteroids.isEmpty()) {
			for (int i = 0; i < asteroids.size(); i++) {
				asteroids.get(i).draw(g);
			}
		}
		player.draw(g);
		
	}

	public void removeAsteroid(Asteroid a) {
		asteroids.remove(a);
	}
	
}
