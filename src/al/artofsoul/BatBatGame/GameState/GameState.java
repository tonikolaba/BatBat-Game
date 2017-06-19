package al.artofsoul.BatBatGame.GameState;

import java.awt.*;


/**
 * @author ArtOfSoul
 *
 */

public abstract class GameState {
	
	protected GameStateManager gsm;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void handleInput();


}
