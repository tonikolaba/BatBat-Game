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

public class HowtoPlay extends BasicState {

	public HowtoPlay(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void draw(Graphics2D h) {
		super.draw(h);
		h.setFont(font);
		h.setColor(Color.YELLOW);
		h.fillRect(200, 160, 280, 200); // Fills a square
		h.drawRoundRect(190, 150, 300, 220, 50, 50);
		h.setColor(Color.RED);
		h.drawString("<- ->      -   MOVE LEFT OR RIGHT", 230, 200);
		h.drawString("LEFT SHIFT   -   RUN", 230, 220);
		h.drawString("D         -   SINGLE HIT ", 230, 240);
		h.drawString("F         -   BIG HIT ", 230, 260);
		h.drawString("SPACE        -   JUMP UP ", 230, 280);
		h.drawString("ESC   -   PAUSE ", 230, 300);
		h.setFont(font);
		h.drawString(" * Press any key to go Back ", 240, 330);
	}

	@Override
	protected void select() {
		switch (currentChoice) {
		case 0:
			JukeBox.play("menuselect");
			gsm.setState(GameStateManager.OPTIONSSTATE);
			break;
		default:
			JukeBox.play("menuselect");
			gsm.setState(GameStateManager.OPTIONSSTATE);
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
