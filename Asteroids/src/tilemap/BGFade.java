package tilemap;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.Drawable;

public class BGFade implements Drawable {

	private int backAlpha;
	private Color backColor;
	private int[] position;
	private int[] size;
	private boolean complete;
	private boolean isFadeIn;
	private static final int ALPHA_INC = 23;
	private static final int MAX_ALPHA = 215;
	private static final int NUM_STAGES = 4;
	private static final int FPS = 60;
	private int animIter;
	private int curCtr;
	
	public BGFade() {
		this(new int[] {0, 0}, 
			 new int[] {GamePanel.WIDTH, GamePanel.HEIGHT}, 
			 new Color(3, 0, 13)
			 );
	}
	
	public BGFade(int[] position, int[] size, Color color) {
		this.position = position;
		this.size = size;
		backAlpha = 0;
		backColor = new Color(color.getRGB(), true);
		complete = true;
		isFadeIn = false;
	}
	
	public BGFade(int x, int y, int width, int height, Color color) {
		this(new int[] {x, y}, 
			 new int[] {width, height}, 
			 color
			 );
	}
	
	public boolean isDone() {
		return complete;
	}
	
	private void animInit() {
		complete = false;
		curCtr = animIter = FPS /NUM_STAGES;
	}
	
	public void fadeIn() {
		isFadeIn = true;
		animInit();
	}

	public void fadeOut() {
		isFadeIn = false;
		animInit();
	}
	
	@Override
	public void draw(Graphics2D g) {
		Color temp = g.getColor();
		g.setColor(backColor);
		g.fillRect(position[0], position[1], size[0], size[1]);
		g.setColor(temp);
	}

	@Override
	public void update() {
		
		if (!complete) {
			
			// calculate new alpha
			if (curCtr > 0) curCtr--;
			else {
				if (isFadeIn) {
					if (backAlpha < MAX_ALPHA - ALPHA_INC) { 
						backAlpha += ALPHA_INC;
						curCtr = animIter;
					}
					else {
						backAlpha = MAX_ALPHA;
						complete = true;
					}
				}
				else {
					if (backAlpha > 0 + ALPHA_INC) {
						backAlpha -= ALPHA_INC;
						curCtr = animIter;
					}
					else {
						backAlpha = 0;
						complete = true;
					}
				}
			}
			
			// mix new alpha into color
			backColor = new Color(backColor.getRed(), 
								  backColor.getGreen(),
								  backColor.getBlue(),
								  backAlpha);
			
		}
		
	}
	
}
