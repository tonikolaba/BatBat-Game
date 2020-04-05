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
		options = new String[] { "< > - MOVE LEFT-RIGHT ", "W - JUMP UP", "R - SINGLE SHOOT", "W+R - JUMP & HIT",
				"F - BIG SHOOT", "MENU" };
	}

	@Override
	public void draw(Graphics2D h) {
		super.draw(h);
		h.setFont(font);
		h.setColor(Color.YELLOW);
		h.drawString("< > - MOVE LEFT-RIGHT ", 140, 133);
		h.drawString("W - JUMP UP", 140, 148);
		h.drawString("R - SINGLE SHOOT", 140, 163);
		h.drawString("W+R - JUMP & HIT", 140, 178);
		h.drawString("F - BIG SHOOT", 140, 193);
		h.drawString("MENU", 140, 208);
	}

	@Override
	protected void select() {
		switch (currentChoice) {
		case 0:
			JukeBox.play("menuselect");
			gsm.setState(GameStateManager.HOWTOPLAY);
			break;
		case 1:
			JukeBox.play("menuselect");
			gsm.setState(GameStateManager.HOWTOPLAY);
			break;
		case 2:
			JukeBox.play("menuselect");
			gsm.setState(GameStateManager.HOWTOPLAY);
			break;
		case 3:
			JukeBox.play("menuselect");
			gsm.setState(GameStateManager.HOWTOPLAY);
			break;
		case 4:
			JukeBox.play("menuselect");
			gsm.setState(GameStateManager.HOWTOPLAY);
			break;
		case 5:
			JukeBox.play("menuselect");
			// PlayerSave.init();
			gsm.setState(GameStateManager.MENUSTATE);
			break;
		default:
			JukeBox.play("menuselect");
			gsm.setState(GameStateManager.HOWTOPLAY);
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
