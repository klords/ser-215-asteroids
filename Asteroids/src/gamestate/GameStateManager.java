package gamestate;

import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameStateManager extends GameState{
	
	public static final int MENUSTATE=0;
	public static final int LEVEL1STATE=1;
	public static final int LEVEL2STATE=2;
	public static final int LEVEL3STATE=3;
	public static final int LEVEL4STATE=4;
	public static final int LEVEL5STATE=5;
	
	private ArrayList<GameState> states;
	private static ActionListener actListener;
	private int currentState;
	
	public GameStateManager(){
		states = new ArrayList<GameState>();
		
		currentState = MENUSTATE;
		
		states.add(new MenuState(this));
	}
	
	public void keyPressed(int i){
		states.get(currentState).update();
	}
	public void keyReleased(int i){
		states.get(currentState).update();
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

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}


}
