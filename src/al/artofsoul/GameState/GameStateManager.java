package al.artofsoul.GameState;

import al.artofsoul.Audio.JukeBox;
import al.artofsoul.Main.GamePanel;

public class GameStateManager {
	
	public static final int NUMGAMESTATES = 16;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1ASTATE = 2;
	public static final int LEVEL1BSTATE = 3;
	public static final int LEVEL1CSTATE = 4;
	public static final int ACIDSTATE = 15;
    private GameState[] gameStates;
    private int currentState;
    private PauseState pauseState;
    private boolean paused;

    public GameStateManager() {
		
		JukeBox.init();
		
		gameStates = new GameState[NUMGAMESTATES];
		
		pauseState = new PauseState(this);
		paused = false;
		
		currentState = MENUSTATE;
		loadState(currentState);
		
	}
	
	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
        else if (state == OPTIONSSTATE)
            gameStates[state] = new OptionsState(this);
        else if (state == LEVEL1STATE)
            gameStates[state] = new Level1State(this);
        else if (state == LEVEL2STATE)
            gameStates[state] = new Level2State(this);
        else if (state == LEVEL3STATE)
            gameStates[state] = new Level3State(this);
        else if (state == LEVEL4STATE)
            gameStates[state] = new Level4State(this);
        else if(state == ACIDSTATE)
			gameStates[state] = new AcidState(this);
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	
	public void setPaused(boolean b) { paused = b; }
	
	public void update() {
		if(paused) {
			pauseState.update();
			return;
		}
		if(gameStates[currentState] != null) gameStates[currentState].update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		if(paused) {
			pauseState.draw(g);
			return;
		}
		if(gameStates[currentState] != null) gameStates[currentState].draw(g);
		else {
			g.setColor(java.awt.Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}
	
}