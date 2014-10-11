package gamestate;

import entity.PlayerShip;
import entity.Sounds;
import main.GamePanel;
import tilemap.DebrisField;
import tilemap.Images;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by gareth on 10/10/14.
 */
public class ShipSelectState extends GameState {
    private int shipChoice;
    private Images[] red;
    private Images[] blue;
    private Images[] green;
    private Images title;
    private Images mainbg;
    private DebrisField debrisField;
    private PlayerShip player;
    private Font font;
    private Font nav;
    private float width;
    private float height;
    private String playerString;
    private Sounds music;

    public ShipSelectState(GameStateManager gsm, PlayerShip player) {
        this.player = player;
        this.gsm = gsm;
        this.font = new Font("Courier", Font.BOLD, 40);
        this.nav = new Font("Courier", Font.PLAIN, 15);
        try {
            music = new Sounds("/Resources/sounds/menumusic.wav");
            mainbg = new Images("/Resources/backgrounds/mainbg.png");
            debrisField = new DebrisField();
            title = new Images("/Resources/backgrounds/asteroidsTitle.png");

            red = new Images[]{new Images("/Resources/ships/redShip.png"), new Images("/Resources/ships/redShipA3.png")};
            blue = new Images[]{new Images("/Resources/ships/blueShip.png"), new Images("/Resources/ships/blueShipA3.png")};
            green = new Images[]{new Images("/Resources/ships/greenShip.png"), new Images("/Resources/ships/greenShipA3.png")};

            // set position of images
            title.setPosition((GamePanel.WIDTH / 2) - (title.getWidth() / 2), (GamePanel.HEIGHT * 1 / 6) - (title.getHeight() / 2));
            red[0].setPosition((GamePanel.WIDTH / 2) - (red[0].getWidth() / 2), (GamePanel.HEIGHT * 3 / 6) - (red[0].getHeight() / 2));
            red[1].setPosition((GamePanel.WIDTH / 2) - (red[0].getWidth() / 2), (GamePanel.HEIGHT * 3 / 6) - (red[0].getHeight() / 2));
            blue[0].setPosition((GamePanel.WIDTH / 2) - (blue[0].getWidth() / 2), (GamePanel.HEIGHT * 4 / 6) - (blue[0].getHeight() / 2));
            blue[1].setPosition((GamePanel.WIDTH / 2) - (blue[0].getWidth() / 2), (GamePanel.HEIGHT * 4 / 6) - (blue[0].getHeight() / 2));
            green[0].setPosition((GamePanel.WIDTH / 2) - (green[0].getWidth() / 2), (GamePanel.HEIGHT * 5 / 6) - (green[0].getHeight() / 2));
            green[1].setPosition((GamePanel.WIDTH / 2) - (green[0].getWidth() / 2), (GamePanel.HEIGHT * 5 / 6) - (green[0].getHeight() / 2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
        shipChoice = 0;
        music.loop();
    }

    public void selection() {
        if (shipChoice==0) {
            player.setCurrentShip(0);
            music.stop();
            gsm.setState(GameStateManager.LEVEL1STATE);
        } else if (shipChoice==1) {
            player.setCurrentShip(1);
            music.stop();
            gsm.setState(GameStateManager.LEVEL1STATE);
        } else if (shipChoice==2) {
            player.setCurrentShip(2);
            music.stop();
            gsm.setState(GameStateManager.LEVEL1STATE);
        }
    }

    @Override
    public void update() {
        debrisField.update();
    }

    @Override
    public void draw(Graphics2D g) {
        if (gsm.getCurrentPlayer() == 0) {
            playerString = "Player One";
        } else {
            playerString = "Player Two";
        }
        height = (float)((GamePanel.HEIGHT * 2 / 6));
        width = (float)((GamePanel.WIDTH / 2.0) - (g.getFontMetrics().stringWidth(playerString)) - 25);
        mainbg.draw(g);//draw the background
        debrisField.draw(g);//draw debris field
        title.draw(g);//draw title
        g.setFont(font);
        g.drawString(playerString, width, height);
        g.setFont(nav);
        g.setColor(new Color(232,255,125));
        g.drawString("(ESC) Back", 25, 25);
        g.drawString("(ENTER) Select", 650, 25);

        if (shipChoice==0) {
            red[1].draw(g);
        } else {
            red[0].draw(g);
        }

        if (shipChoice==1) {
            blue[1].draw(g);
        } else {
            blue[0].draw(g);
        }

        if (shipChoice==2) {
            green[1].draw(g);
        } else {
            green[0].draw(g);
        }

    }

    @Override
    public void keyPressed(int k) {
        switch (k) {
            case KeyEvent.VK_UP:
                if (--shipChoice < 0) shipChoice = 2;
                break;
            case KeyEvent.VK_DOWN:
                if (++shipChoice > 2) shipChoice = 0;
                break;
            case KeyEvent.VK_ESCAPE:
                music.stop();
                gsm.setState(GameStateManager.PLAYERSELECTSTATE);
                break;
            case KeyEvent.VK_ENTER:
                selection();
                return;
        }
    }

    @Override
    public void keyReleased(int k) {

    }
}
