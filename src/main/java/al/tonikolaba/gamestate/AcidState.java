/** Copyright to N.Kolaba
All rights reserved ©.
**/

package al.tonikolaba.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.handlers.Keys;

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
		g.setFont(font);
		g.setColor(Color.GRAY);
		g.fillOval(85, 65, 150, 150); // draw a cycle OK
		g.drawOval(80, 60, 160, 160); // draw a cycle
		g.drawRect(85, 65, 150, 150); // draw a square Katerori
		g.setColor(Color.WHITE);
		g.fillOval(90, 70, 140, 140); // Fills a square
		g.setColor(Color.DARK_GRAY);
		g.fillOval(95, 75, 130, 130);
		g.setColor(Color.GREEN);
		g.drawString("Congratulation!", 120, 133);
		g.setFont(font2);
		g.drawString("Press any key to Play Again", 100, 148);
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