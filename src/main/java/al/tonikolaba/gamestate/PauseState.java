package al.tonikolaba.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import al.tonikolaba.handlers.Keys;
import al.tonikolaba.main.GamePanel;

/**
 * @author N. Kolaba
 */

public class PauseState extends GameState {

	public PauseState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void update() {
		handleInput();
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		g.fillRoundRect(180, 130, 300, 220, 50, 50);
		g.setColor(Color.YELLOW);
		g.fillRect(190, 140, 280, 200); // Fills a square
		g.setColor(Color.RED);
		g.setFont(fontMenu);
		g.drawString("Game Paused", 280, 230);
		g.setFont(font);
		g.drawString("* press ESC to continue", 250, 255);
	}

	@Override
	public void handleInput() {
		if (Keys.isPressed(Keys.ESCAPE))
			gsm.setPaused(false);
		if (Keys.isPressed(Keys.BUTTON1)) {
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}

}
