/** Copyright to N.Kolaba
All rights reserved ©.
**/

<<<<<<< HEAD:src/main/java/al/tonikolaba/gamestate/BasicState.java
package al.tonikolaba.gamestate;
=======
package al.artofsoul.batbatgame.gamestate;
>>>>>>> aae2fa52323674b457b6667781f55e1db62713db:src/main/java/al/artofsoul/batbatgame/gamestate/BasicState.java

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.entity.Player;
import al.tonikolaba.handlers.Keys;
import al.tonikolaba.handlers.LoggingHelper;
import al.tonikolaba.main.GamePanel;
import al.tonikolaba.tilemap.TileMap;

/**
 * Created by tonikolaba on 03-Jan-18.
 */

public abstract class BasicState {
	private static final String MENU_OPTION = "menuoption";
	protected GameStateManager gsm;
	protected Player player;
	protected TileMap tileMap;
	protected boolean blockInput = false;
	protected BufferedImage bg;
	protected BufferedImage head;
	protected int currentChoice = 0;
	protected String[] options;
	protected Font font;
	protected Font font2;

	public BasicState(GameStateManager gsm) {
		this.gsm = gsm;
		try {

			bg = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/sfondi.gif")).getSubimage(0, 0,
					GamePanel.WIDTH, GamePanel.HEIGHT);

			// load floating head
			head = ImageIO.read(getClass().getResourceAsStream("/HUD/Hud.gif")).getSubimage(0, 12, 12, 11);

			// titles and fonts

			font = new Font("Arial", Font.BOLD, 11);
			font2 = new Font("Arial", Font.PLAIN, 9);

			// load sound sfx
			JukeBox.load("/SFX/menuoption.mp3", MENU_OPTION);
			JukeBox.load("/SFX/menuselect.mp3", "menuselect");

		} catch (Exception e) {
			LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	public void update() {
		// check keys
		handleInput();
	}

	public void draw(Graphics2D g) {
		// draw bg
		g.drawImage(bg, 0, 0, null);
		// draw menu options
		g.setFont(font);
		g.setColor(Color.WHITE);
		// draw floating head

		if (currentChoice == 0)
			g.drawImage(head, 120, 123, null);
		else if (currentChoice == 1)
			g.drawImage(head, 120, 138, null);
		else if (currentChoice == 2)
			g.drawImage(head, 120, 153, null);
		else if (currentChoice == 3)
			g.drawImage(head, 120, 168, null);
		else if (currentChoice == 4)
			g.drawImage(head, 120, 183, null);
		else if (currentChoice == 5)
			g.drawImage(head, 120, 195, null);
		// other
		g.setFont(font2);
		g.drawString("tonikolaba \u00A9 \u00AE", 10, 232);
	}

	protected void select() {
		throw new IllegalStateException("Needs to be overwritten");
	}

	public void handleInput() {
		if (Keys.isPressed(Keys.ENTER))
			select();
		if (Keys.isPressed(Keys.UP) && currentChoice > 0) {
			JukeBox.play(MENU_OPTION, 0);
			currentChoice--;
		}
		if (Keys.isPressed(Keys.DOWN) && currentChoice < options.length - 1) {
			JukeBox.play(MENU_OPTION, 0);
			currentChoice++;
		}
	}
}