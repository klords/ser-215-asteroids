package gamestate;

import java.awt.Graphics2D;
import java.util.ArrayList;

import entity.PlayerShip;
import tilemap.HUD;

public class GameStateManager {
	
	public static final int MENUSTATE=0;
	public static final int LEVEL1STATE=1;
	public static final int LEVEL2STATE=2;
	public static final int LEVEL3STATE=3;
	public static final int LEVEL4STATE=4;
	public static final int LEVEL5STATE=5;
	public static final int HIGHSCORESTATE=6;
    public static final int SHIPSELECTSTATE=7;
	public static final int GAMEOVERSTATE=8;
    public static final int PLAYERSELECTSTATE=9;

	private ArrayList<PlayerShip> players;
	private int currentPlayer;
    private int numPlayers;
	private ArrayList<GameState> states;
	private int currentState;
	private int prevState;
	
	private boolean debugCollision;
	
	public GameStateManager(){
		
		debugCollision = false;
		
		players = new ArrayList<PlayerShip>();
		currentPlayer = 0;
        players.add(new PlayerShip());

		states = new ArrayList<GameState>();
		
		states.add(new MenuState(this));
		states.add(new Level1State(this, players.get(currentPlayer)));
		states.add(new Level1State(this, players.get(currentPlayer)));
		states.add(new Level1State(this, players.get(currentPlayer)));
		states.add(new Level1State(this, players.get(currentPlayer)));
		states.add(new Level1State(this, players.get(currentPlayer)));
		states.add(new HighScoreState(this));
        states.add(new ShipSelectState(this, players.get(currentPlayer)));
		states.add(new GameOverState(this));
        states.add(new PlayerSelectState(this));
		
		setState(MENUSTATE);
		
	}
	
	public void setState(int gs){
		prevState = currentState;
		currentState = gs;
		init();
	}

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public GameState getState(int gs) {
		return states.get(gs);
	}
	
	public int getPrevState() {
		return prevState;
	}
	
	public void init() {
		states.get(currentState).init();
	}
	
	public void update(){
		states.get(currentState).update();
	}
	
	public void draw(Graphics2D g){
		states.get(currentState).draw(g);
	}
	
	public void keyPressed(int k){
		states.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k){
		states.get(currentState).keyReleased(k);
	}

	public boolean getDebugCollision() {
		return debugCollision;
	}
	
}
