package gamestate;

import entity.Sounds;
import main.GamePanel;
import tilemap.DebrisField;
import tilemap.Images;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by gareth on 10/10/14.
 */
public class PlayerSelectState extends GameState {
    private int numPlayers;
    private float oneW;
    private float twoW;
    private float oneH;
    private float twoH;

    // fonts
    private Font unselect;
    private Font select;
    private Font nav;

    // background images
    private Images mainbg;
    private DebrisField debrisField;
    private Images title;

    // menu icons and selection
    private Images[] one;
    private Images[] two;

    private Sounds music;

    public PlayerSelectState(GameStateManager gsm) {
        this.gsm = gsm;
        unselect = new Font("Courier", Font.BOLD, 40);
        select = new Font("Courier", Font.BOLD, 43);
        nav = new Font("Courier", Font.PLAIN, 15);

        try {
            music = new Sounds("/Resources/sounds/menumusic.wav");
            mainbg = new Images("/Resources/backgrounds/mainbg.png");
            debrisField = new DebrisField();
            title = new Images("/Resources/backgrounds/asteroidsTitle.png");
            title.setPosition((GamePanel.WIDTH / 2) - (title.getWidth() / 2), (GamePanel.HEIGHT * 0.25) - (title.getHeight() / 2));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selection() {
        if (numPlayers == 0) {
            gsm.setNumPlayers(1);
            music.stop();
            gsm.setState(GameStateManager.SHIPSELECTSTATE);
        } else if (numPlayers == 1) {
            gsm.setNumPlayers(2);
            music.stop();
            gsm.setState(GameStateManager.SHIPSELECTSTATE);
        }
    }

    @Override
    public void init() {
        numPlayers = 0;
        music.loop();

    }

    @Override
    public void update() {
        debrisField.update();
    }

    @Override
    public void draw(Graphics2D g) {
        oneW = (float)((GamePanel.WIDTH / 2.0) - g.getFontMetrics().stringWidth("One Player") / 2);
        twoW = (float)((GamePanel.WIDTH / 2.0) - g.getFontMetrics().stringWidth("Two Player") / 2);
        oneH = (float)((GamePanel.HEIGHT * 0.5));
        twoH = (float)((GamePanel.HEIGHT * 0.75));
        mainbg.draw(g);//draw the background
        debrisField.draw(g);//draw debris field
        title.draw(g);//draw title
        g.setColor(new Color(232,255,125));
        g.setFont(nav);
        g.drawString("(ESC) Back", 25, 25);
        g.drawString("(ENTER) Select", 650, 25);

        if (numPlayers==0) {
            g.setFont(select);
            g.drawString("One Player", oneW, oneH);
        } else {
            g.setFont(unselect);
            g.drawString("One Player", oneW, oneH);
        }

        if (numPlayers==1) {
            g.setFont(select);
            g.drawString("Two Player", twoW, twoH);
        } else {
            g.setFont(unselect);
            g.drawString("Two Player", twoW, twoH);
        }

    }

    @Override
    public void keyPressed(int k) {
        switch (k) {
            case KeyEvent.VK_UP:
                if (--numPlayers < 0) numPlayers = 1;
                break;
            case KeyEvent.VK_DOWN:
                if (++numPlayers > 1) numPlayers = 0;
                break;
            case KeyEvent.VK_ESCAPE:
                music.stop();
                gsm.setState(GameStateManager.MENUSTATE);
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
