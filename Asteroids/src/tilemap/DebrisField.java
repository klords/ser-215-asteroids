package tilemap;

import java.awt.Graphics2D;
import java.util.ArrayList;

import main.GamePanel;
import main.Drawable;

public class DebrisField implements Drawable{

	private ArrayList<Images> debrisField;
	private int fieldHeight;
	private static final double autoScrollAmt = 0.17;
	
	public DebrisField() {
		debrisField = new ArrayList<Images>();
		debrisField.add(new Images("/resources/asteroids/debrisField0_1.png"));
		fieldHeight = debrisField.get(0).getHeight();
		System.out.println(fieldHeight);
		debrisField.get(0).setAutoScroll(0, autoScrollAmt);
		debrisField.get(0).setPosition(0, GamePanel.HEIGHT - fieldHeight);
		System.out.println(GamePanel.HEIGHT / fieldHeight);
		for (int i = 1; i < (GamePanel.HEIGHT / fieldHeight) + 1; i++) {
			debrisField.add(new Images("/resources/asteroids/debrisField0_1.png"));
			debrisField.get(i).setPosition(0, GamePanel.HEIGHT - (fieldHeight * (i + 1)));
			debrisField.get(i).setAutoScroll(0, autoScrollAmt);
		}
	}

	@Override
	public void draw(Graphics2D g) {
		for (Images i : debrisField) {
			i.draw(g);
		}
	}

	@Override
	public void update() {
		for (Images i : debrisField) {
			i.update();
			if (i.getY() >= GamePanel.HEIGHT) i.setPosition(0, -fieldHeight);
		}
	}
	
}
