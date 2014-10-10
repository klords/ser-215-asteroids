package gamestate;

import entity.PlayerShip;
import main.GamePanel;
import tilemap.DebrisField;
import tilemap.HUD;
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
    private Images mainbg;
    private DebrisField debrisField;
    private PlayerShip player;
    private HUD hud;

    public ShipSelectState(GameStateManager gsm, PlayerShip player) {
        this.player = player;
        this.gsm = gsm;
        try {
            mainbg = new Images("/resources/backgrounds/mainbg.png");
            debrisField = new DebrisField();

            red = new Images[]{new Images("/resources/ships/redShip.png"), new Images("/resources/ships/redShipA3.png")};
            blue = new Images[]{new Images("/resources/ships/blueShip.png"), new Images("/resources/ships/blueShipA3.png")};
            green = new Images[]{new Images("/resources/ships/greenShip.png"), new Images("/resources/ships/greenShipA3.png")};

            // set position of images
            red[0].setPosition((GamePanel.WIDTH / 2) - (red[0].getWidth() / 2), (GamePanel.HEIGHT * 0.25) - (red[0].getHeight() / 2));
            red[1].setPosition((GamePanel.WIDTH / 2) - (red[0].getWidth() / 2), (GamePanel.HEIGHT * 0.25) - (red[0].getHeight() / 2));
            blue[0].setPosition((GamePanel.WIDTH / 2) - (blue[0].getWidth() / 2), (GamePanel.HEIGHT * 0.5) - (blue[0].getHeight() / 2));
            blue[1].setPosition((GamePanel.WIDTH / 2) - (blue[0].getWidth() / 2), (GamePanel.HEIGHT * 0.5) - (blue[0].getHeight() / 2));
            green[0].setPosition((GamePanel.WIDTH / 2) - (green[0].getWidth() / 2), (GamePanel.HEIGHT * 0.75) - (green[0].getHeight() / 2));
            green[1].setPosition((GamePanel.WIDTH / 2) - (green[0].getWidth() / 2), (GamePanel.HEIGHT * 0.75) - (green[0].getHeight() / 2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
        shipChoice = 0;
    }

    public void selection() {
        if (shipChoice==0) {
            player.setCurrentShip(0);
            gsm.setState(GameStateManager.LEVEL1STATE);
        } else if (shipChoice==1) {
            player.setCurrentShip(1);
            gsm.setState(GameStateManager.LEVEL1STATE);
        } else if (shipChoice==2) {
            player.setCurrentShip(2);
            gsm.setState(GameStateManager.LEVEL1STATE);
        }
    }

    @Override
    public void update() {
        debrisField.update();
    }

    @Override
    public void draw(Graphics2D g) {
        mainbg.draw(g);//draw the background
        debrisField.draw(g);//draw debris field

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
            case KeyEvent.VK_ENTER:
                selection();
                return;
        }
    }

    @Override
    public void keyReleased(int k) {

    }
}
