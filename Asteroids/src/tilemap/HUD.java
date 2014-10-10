package tilemap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.Drawable;
import entity.PlayerShip;

public class HUD implements Drawable {

	private PlayerShip player;
	private BufferedImage image;
    private BufferedImage livesImage;
	private AffineTransform at;
	private Font font;
	private Color color;
	
	public HUD() {
		this(null);
	}
	
	public HUD(PlayerShip player) {
		this.player = player;
		font = new Font("Consolas", Font.PLAIN, 25);
		color = Color.WHITE;
		image = player.getImage();
        livesImage = player.getLivesImage();
		at = AffineTransform.getScaleInstance(0.6, 0.6);
	}

	@Override
	public void draw(Graphics2D g) {
		Color tempC = g.getColor();
		Font tempF = g.getFont();
		AffineTransform tempAT = g.getTransform();
		g.setColor(color);
		g.setFont(font);
		int drawX = (GamePanel.WIDTH / 10) - (g.getFontMetrics().stringWidth("Lives") / 2);
		g.drawString("Lives", drawX, 30);
		g.setTransform(at);
		for (int i = 0; i < player.getLives(); i++) {
			g.drawImage(livesImage, 75 + (i * (livesImage.getWidth() + 10)), 60, null);
		}
		g.setTransform(tempAT);
		drawX = (9 * GamePanel.WIDTH / 10) - (g.getFontMetrics().stringWidth("Score") / 2);
		g.drawString("Score", drawX, 30);
		drawX = (9 * GamePanel.WIDTH / 10) - (g.getFontMetrics().stringWidth(String.valueOf(player.getScore())) / 2);
		g.drawString(String.valueOf(player.getScore()), drawX, 55);
		g.setColor(tempC);
		g.setFont(tempF);
		
	}

	@Override
	public void update() {
        livesImage = player.getLivesImage();

    }

}
