/** Copyright to N.Kolaba
All rights reserved ©.
**/

package al.tonikolaba.gamestate;

import java.awt.Graphics2D;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.entity.PlayerSave;
import al.tonikolaba.handlers.Keys;

/**
 * @author tonikolaba
 */

public class MenuState extends BasicState {

	public MenuState(GameStateManager gsm) {
		super(gsm);
		options = new String[] { "Play", "Options", "Quit" };
	}

	@Override
	public void draw(Graphics2D i) {

		super.draw(i);
		// titles and fonts
		i.setFont(fontMenu);
		i.drawRect(230, 190, 210, 100); // Fills a square
		i.drawString("Play", 300, 223); // 25
		i.drawString("Options", 300, 248);
		i.drawString("Quit", 300, 273);
	}

	@Override
	protected void select() {
		switch (currentChoice) {
		case 0:
			JukeBox.play("menuselect");
			PlayerSave.init();
			gsm.setState(GameStateManager.LEVEL1STATE); // start this level entrance
			break;
		case 1:
			gsm.setState(GameStateManager.OPTIONSSTATE);
			break;
		default:
			System.exit(0);
			break;
		}
	}

	@Override
	public void handleInput() {
		if (Keys.isPressed(Keys.ENTER))
			select();
		if (Keys.isPressed(Keys.UP) && currentChoice > 0) {
			JukeBox.play("menuoption", 0);
			currentChoice--;
		}
		if (Keys.isPressed(Keys.DOWN) && currentChoice < options.length - 1) {
			JukeBox.play("menuoption", 0);
			currentChoice++;
		}
	}

}
