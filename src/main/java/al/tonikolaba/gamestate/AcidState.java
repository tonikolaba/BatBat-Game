/** Copyright to N.Kolaba
All rights reserved ©.
**/

package al.tonikolaba.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.handlers.Keys;
import al.tonikolaba.main.GamePanel;

/**
 * @author N.Kolaba
 */

public class AcidState extends BasicState {

	public AcidState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void update() {
		handleInput();
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setFont(fontMenu);
		g.setColor(Color.WHITE);
		g.fillOval(185, 165, 250, 250); // draw a cycle OK
		g.setColor(Color.ORANGE);
		g.fillOval(180, 160, 260, 260); // draw a cycle
		// g.setColor(Color.ORANGE);
		g.drawRect(185, 165, 250, 250); // draw a square Katerori
		g.setColor(Color.WHITE);
		g.fillOval(190, 170, 240, 240); // Fills a square
		g.setColor(Color.YELLOW);
		g.fillOval(195, 175, 230, 230);
		g.setColor(Color.RED);
		g.drawString("Congratulation!", 240, 280);
		g.setFont(font);
		g.drawString("Press any key to Play Again", 215, 305);
	}

	@Override
	protected void select() {
		switch (currentChoice) {
		case 0:
			JukeBox.play("menuselect");
			gsm.setState(GameStateManager.MENUSTATE);
			break;
		default:
			JukeBox.play("menuselect");
			gsm.setState(GameStateManager.MENUSTATE);
			break;
		}
	}

	@Override
	public void handleInput() {
		if (Keys.isPressed(Keys.ENTER))
			select();
		if (Keys.isPressed(Keys.UP) && currentChoice > 0) {
			JukeBox.play("menuoption", 0);
			gsm.setState(GameStateManager.MENUSTATE);
			currentChoice--;
		}
	}
}