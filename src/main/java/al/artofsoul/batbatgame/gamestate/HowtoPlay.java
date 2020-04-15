/** Copyright to N.Kolaba
All rights reserved ©.
**/

package al.artofsoul.batbatgame.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import al.artofsoul.batbatgame.audio.JukeBox;
import al.artofsoul.batbatgame.handlers.Keys;

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
		h.fillRect(90, 70, 140, 140); // Fills a square
		h.setColor(Color.RED);
		h.drawString("< > - MOVE LEFT-RIGHT", 100, 100);
		h.drawString(" W + R  -  JUMP & HIT ", 100, 115);
		h.drawString(" R  -   SINGLE SHOOT ", 100, 130);
		h.drawString(" F -  BIG SHOOT ", 100, 145);
		h.drawString(" W -  JUMP UP ", 100, 160);
		h.setFont(font2);
		h.drawString(" * any key to go Back ", 110, 185);
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
