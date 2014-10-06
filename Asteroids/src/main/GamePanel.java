package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import gamestate.GameStateManager;

import javax.swing.JPanel;

public class GamePanel extends JPanel 
					   implements Runnable, KeyListener, Drawable {
	
	// screen dimensions
	public final static int WIDTH = 800;	//w/h to static -dray
	public final static int HEIGHT = 600;
	
	private GameStateManager gsm;
	
	private Thread thread;
	private boolean isRunning;
	private int FPS = 60;
	private int targetTime = 1000 / FPS;
	
	private BufferedImage image;
	private Graphics2D g;
	
	
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	private void init() {
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		isRunning = true;
		
		gsm = new GameStateManager();
		
	}
	
	
	@Override
	public void run() {
		
		init();
		
		long start;
		long elapsed;
		long wait;
		
		while (isRunning) {
		
			start = System.nanoTime();
			
			update();
			draw(g);
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 1000000;
			if (wait < 0) wait = 0;
			
			try {
				Thread.sleep(wait);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key);
	}

	@Override
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void draw(Graphics2D g) {
		gsm.draw(g);
	}

	private void drawToScreen() {
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		g.dispose();
	}
	
	@Override
	public void update() {
		gsm.update();
	}

	
	
}