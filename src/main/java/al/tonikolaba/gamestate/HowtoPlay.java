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
		// fontMenu = new Font("Arial", Font.BOLD, 20);
	}

	@Override
	public void draw(Graphics2D h) {
		super.draw(h);
		h.setFont(fontMenu);
		h.setColor(Color.YELLOW);
		h.fillRect(200, 160, 280, 200); // Fills a square
		h.setColor(Color.RED);
		h.drawString("< > - MOVE LEFT-RIGHT", 233, 200);
		h.drawString(" W+R  -  JUMP & HIT ", 235, 225);
		h.drawString(" R  -   SINGLE SHOOT ", 235, 250);
		h.drawString(" F -  BIG SHOOT ", 235, 275);
		h.drawString(" W -  JUMP UP ", 235, 300);
		h.setFont(font);
		h.drawString(" * Press any key to go Back ", 260, 335);
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
