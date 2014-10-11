package gamestate;

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import entity.PlayerShip;
import entity.Sounds;
import main.GamePanel;
import tilemap.BGFade;

public class GameOverState extends GameState {

	private boolean isIntro;
	private long introTimer;
	private BGFade bgfade;
	private Font GOFont;
	private int GOSize;
	private Color GOColor;
	private int GOAlpha;
	private double[] GOPos;
	private static final String GO_STR = "Game Over";
	private Font inputFont;
	private static final int INPUT_SIZE = 20;
	private Color inputColor;
	private int inputAlpha;
	private double[] inputPos;
	private String inputStr;
	private Color selectionColor;
	private char[] input;
	private PlayerShip player;
    private boolean repeat;
	private LevelState currentState;
	private String[][] scores;
	private boolean displayGO;
	private boolean getInput;
	private boolean displayInput;
	private int currentSelection;
	
	public GameOverState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		bgfade = new BGFade();
		
	}

	@Override
	public void init() {
		
		currentState = (LevelState) gsm.getState(gsm.getPrevState());
		player = currentState.player;
		
		bgfade.fadeIn();
		
		GOSize = 50;
		GOFont = new Font("Arial", Font.BOLD, GOSize);
		GOAlpha = 0;
		GOColor = new Color(255, 255, 255, GOAlpha);
		
		inputFont = new Font("Consolas", Font.PLAIN, INPUT_SIZE);
		inputAlpha = 0;
		inputColor = new Color(255, 255, 255, inputAlpha);
		
		selectionColor = new Color(0, 0, 255, inputAlpha);
		
		input = "___".toCharArray();
		
		isIntro = true;
		introTimer = System.nanoTime();
		
		scores = GameOverState.readScores();
		
		displayGO = false;
		displayInput = false;
		
		if (player.getScore() >= Integer.parseInt(scores[9][1]))
			getInput = true;
		else
			getInput = false;

        if (gsm.getCurrentPlayer() + 1 < gsm.getNumPlayers()) {
            repeat = true;
        } else {
            repeat = false;
        }
		
		currentSelection = 0;
		
	}

	@Override
	public void update() {
		
		currentState.update();
		bgfade.update();
		
		if (isIntro) {
			if ((System.nanoTime() - introTimer) / 1000000 > 2000) {
				showGO();
			}
		}
		else if (getInput) showInput();
		
		if (displayGO && GOAlpha < 255) {
			GOAlpha++;
			GOColor = new Color(GOColor.getRed(), 
								GOColor.getGreen(), 
								GOColor.getBlue(), 
								GOAlpha);
		}
		
		if (GOAlpha == 180) {
			Sounds gameover = new Sounds("/Resources/sounds/gameover.wav");
            gameover.play();
			isIntro = false;
		}
		
		if (displayInput && inputAlpha < 255) {
			inputAlpha++;
			inputColor = new Color(inputColor.getRed(), 
								   inputColor.getGreen(), 
								   inputColor.getBlue(), 
								   inputAlpha);
			selectionColor = new Color(selectionColor.getRed(),
									   selectionColor.getGreen(),
									   selectionColor.getBlue(),
									   inputAlpha);	
		}
	}

	@Override
	public void draw(Graphics2D g) {
		
		Font tempF = g.getFont();
		Color tempC = g.getColor();
		
		currentState.draw(g);
		bgfade.draw(g);
		
		g.setColor(GOColor);
		g.setFont(GOFont);
		GOPos = new double[] {
					(GamePanel.WIDTH / 2) 
					- (g.getFontMetrics().stringWidth(GO_STR) / 2),
					300
				};
		g.drawString(GO_STR, (int)GOPos[0], (int)GOPos[1]);
		
		g.setColor(inputColor);
		g.setFont(inputFont);
		inputStr = "Please enter your initials:";
		inputPos = new double[] {
				(GamePanel.WIDTH / 2) 
				- (g.getFontMetrics().stringWidth(inputStr) / 2) - 10,
				400
			};
		g.drawString(inputStr, (int)inputPos[0], (int)inputPos[1]);
		for (int i = 0; i < 3; i++) {
			if (i == currentSelection) g.setColor(selectionColor);
			else g.setColor(inputColor);
			g.drawString(String.valueOf(input[i]), 
						(int)inputPos[0] 
						+ g.getFontMetrics().stringWidth(inputStr)
						+ (i * 15), 
						400);
		}
		
		g.setFont(tempF);
		g.setColor(tempC);
	}

	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER) {
			if (getInput) {
				addScore();
			}
            if (repeat) {
                gsm.setCurrentPlayer(1);
                gsm.setState(GameStateManager.SHIPSELECTSTATE);
            } else {
                gsm.setCurrentPlayer(0);
                gsm.setState(GameStateManager.MENUSTATE);
            }
		}
		if (getInput) {
			if ((k >= KeyEvent.VK_A && k <= KeyEvent.VK_Z) || 
				(k >= KeyEvent.VK_0 && k <= KeyEvent.VK_9)) {
				input[currentSelection] = KeyEvent.getKeyText(k).charAt(0);
				currentSelection++;
			}
			if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_UP) {
				currentSelection--;
			}
			if (k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_DOWN) {
				currentSelection++;
			}
			if (k == KeyEvent.VK_BACK_SPACE) {
				input[currentSelection] = '_';
				currentSelection--;
			}
			if (k == KeyEvent.VK_DELETE) {
				input[currentSelection] = '_';
			}
			if (currentSelection > 2) currentSelection = 2;
			if (currentSelection < 0) currentSelection = 0;
		}
	}

	@Override
	public void keyReleased(int k) {}

	public static String[][] readScores() {
		
		BufferedReader br;
		String highScores[][] = new String[10][];
		
		try{
			br = new BufferedReader(
				new InputStreamReader(
						GameOverState.class.getResourceAsStream(
								"/Resources/highScores"
								)
						)
				);
		
			for (int i = 0; i < 10; i++) {
				try {
					highScores[i] = br.readLine().trim().split(",");
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try {
				br.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	
		return highScores;
		
	}
	
	private void addScore() {
		
		// format initial input for storage
		String input = String.valueOf(this.input);
		input.replaceAll("_", " ");

		// add score to array
		for (int i = 0; i < 10; i++) {
			if (player.getScore() >= Integer.parseInt(scores[i][1])) {
				for (int j = 8; j >= i; j--) {
					scores[j + 1] = scores[j];
				}
				scores[i] = new String[] {input, 
										  String.valueOf(player.getScore())
										  };
				break;
			}
		}
		
		// rewrite score list
		try {
			PrintWriter pw = new PrintWriter(
					new File(
							getClass().getResource(
									"/Resources/highScores"
									).toURI()
							)
					);
			for (int i = 0; i < 10; i++) {
				pw.printf("%1$s,%2$s", scores[i][0], scores[i][1]);
				if (i < 9) pw.println();
			}
			pw.close();
		} catch (FileNotFoundException fnfex) {
			fnfex.printStackTrace();
		} catch (URISyntaxException uriex) {
            uriex.printStackTrace();
        }
		
	}
	
	private void showGO() {
		displayGO = true;
	}
	
	private void showInput() {
		displayInput = true;
	}
	
}
