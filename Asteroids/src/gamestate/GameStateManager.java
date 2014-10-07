package gamestate;

import java.awt.Graphics2D;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameStateManager {
	
	public static final int MENUSTATE=0;
	public static final int LEVEL1STATE=1;
	public static final int LEVEL2STATE=2;
	public static final int LEVEL3STATE=3;
	public static final int LEVEL4STATE=4;
	public static final int LEVEL5STATE=5;
	
	private ArrayList<GameState> states;
	//private static ActionListener actListener;
	private int currentState;
	
	public GameStateManager(){
		states = new ArrayList<GameState>();
		
		currentState = LEVEL1STATE; //MENUSTATE;
		
		states.add(new MenuState(this));
		states.add(new Level1State(this));
	}
	
	public void keyPressed(int k){
		states.get(currentState).keyPressed(k);
	}
	public void keyReleased(int k){
		states.get(currentState).keyReleased(k);
	}
	public void update(){
		states.get(currentState).update();
	}
	public void setState(int gs){
		currentState = gs;
		states.get(currentState).init();
	}
	public void draw(Graphics2D g){
		states.get(currentState).draw(g);
	}

	public void init() {}

}
