package al.tonikolaba.gamestate;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.main.GamePanel;

/**
 * @author tonikolaba
 */

public class GameStateManager {

	public static final int NUMGAMESTATES = 16;
	public static final int MENUSTATE = 0;
	public static final int OPTIONSSTATE = 1;
	public static final int LEVEL1STATE = 2;
	public static final int LEVEL2STATE = 3;
	public static final int LEVEL3STATE = 4;
	public static final int LEVEL4STATE = 5;
//	 public static final int LEVELTEST = 6;
	public static final int HOWTOPLAY = 7;
	public static final int ACIDSTATE = 15;
	public BasicState[] gameStates;
	private int currentState;
	private PauseState pauseState;
	private boolean paused;

	public GameStateManager() {

		JukeBox.init();

		gameStates = new BasicState[NUMGAMESTATES];

		pauseState = new PauseState(this);
		paused = false;

		currentState = MENUSTATE;
		loadState(currentState);

	}

	public void loadState(int state) {
		switch (state) {
			case (MENUSTATE):
				gameStates[state] = new MenuState(this);
				break;
			case (OPTIONSSTATE):
				gameStates[state] = new OptionsState(this);
				break;
			case(HOWTOPLAY):
				gameStates[state] = new HowtoPlay(this);
				break;
			case (LEVEL1STATE):
				gameStates[state] = new Level1State(this);
				break;
			case (LEVEL2STATE):
				gameStates[state] = new Level2State(this);
				break;
			case (LEVEL3STATE):
				gameStates[state] = new Level3State(this);
				break;
			case(LEVEL4STATE):
				gameStates[state] = new Level4State(this);
				break;
//			case(LEVELTEST):
//			gameStates[state] = new LevelTest(this);
//			break;
			case(ACIDSTATE):
				gameStates[state] = new AcidState(this);
				break;
		}
	}

	private void unloadState(int state) {
		gameStates[state] = null;
	}

	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	public void setPaused(boolean b) {
		paused = b;
	}

	public void update() {
		if (paused) {
			pauseState.update();
			return;
		}
		if (gameStates[currentState] != null)
			gameStates[currentState].update();
	}

	public void draw(java.awt.Graphics2D g) {
		if (paused) {
			pauseState.draw(g);
			return;
		}
		if (gameStates[currentState] != null)
			gameStates[currentState].draw(g);
		else {
			g.setColor(java.awt.Color.YELLOW);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}
}