package gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import main.GamePanel;

public class HighScoreState extends GameState {

	private static final String scoresPath = "/resources/highScores";
	private BufferedReader br;
	private String[][] scores;
	private int width;
	private int height;
	private Font titleFont;
	private Font infoFont;
	private Color infoColor;
	private Color windowColor;
	private long flashTimer;
	
	public HighScoreState(GameStateManager gsm) {
		this.gsm = gsm;
		width = (int) (GamePanel.WIDTH * .8);
		height = (int) (GamePanel.HEIGHT * .8);
		titleFont = new Font("Consolas", Font.PLAIN, 30);
		infoFont = new Font("Consolas", Font.PLAIN, 25);
		infoColor = Color.WHITE;
		windowColor = new Color(0, 3, 14, 210);
	}

	@Override
	public void init() {
		try {
			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(scoresPath)));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		scores = new String[10][];
		for (int i = 0; i < 10; i++) {
			try {
				scores[i] = br.readLine().trim().split(",");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		flashTimer = System.nanoTime();
	}

	@Override
	public void update() {
		gsm.getState(GameStateManager.MENUSTATE).update();
	}

	@Override
	public void draw(Graphics2D g) {
		gsm.getState(GameStateManager.MENUSTATE).draw(g);
		Color tempC = g.getColor();
		Font tempF = g.getFont();
		int windowX = GamePanel.WIDTH / 10;
		int windowY = GamePanel.HEIGHT / 10;
		int windowCenter = GamePanel.WIDTH / 2;
		g.setColor(windowColor);
		g.fillRoundRect(windowX, windowY, width, height, 50, 50);
		g.setColor(Color.WHITE);
		g.drawRoundRect(windowX, windowY, width, height, 50, 50);
		g.setFont(titleFont);
		g.setColor(infoColor);
		int titleY = windowY + g.getFontMetrics().getHeight() + 10;
		int colA = windowX + (width / 4);
		int colB = windowX + (3 * width / 4);
		g.drawString("High Scores", windowCenter - g.getFontMetrics().stringWidth("High Scores") / 2, titleY);
		g.drawString("Player", colA - g.getFontMetrics().stringWidth("Player") / 2, titleY + 50);
		g.drawString("Score", colB - g.getFontMetrics().stringWidth("Score") / 2, titleY + 50);
		if ((System.nanoTime() - flashTimer) / 1000000 % 2000 <= 1000) {
			g.drawString("Press ESC to close", windowCenter - g.getFontMetrics().stringWidth("Press ESC to close") / 2, windowY + height - 25);
		}
		g.setFont(infoFont);
		for (int i = 0; i < 10; i++) {
			g.drawString(scores[i][0], colA - g.getFontMetrics().stringWidth(scores[i][0]) / 2, titleY + 90 + (i * 30));
			g.drawString(scores[i][1], colB - g.getFontMetrics().stringWidth(scores[i][1]) / 2, titleY + 90 + (i * 30));
		}
		g.setColor(tempC);
		g.setFont(tempF);
	}

	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ESCAPE) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}

	@Override
	public void keyReleased(int k) {}

}
