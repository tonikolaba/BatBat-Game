package al.artofsoul.batbatgame.gamestate;

import al.artofsoul.batbatgame.audio.JukeBox;
import al.artofsoul.batbatgame.entity.Player;
import al.artofsoul.batbatgame.handlers.Keys;
import al.artofsoul.batbatgame.handlers.LoggingHelper;
import al.artofsoul.batbatgame.main.GamePanel;
import al.artofsoul.batbatgame.tilemap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

/**
 * Created by NdueKolaba on 03-Apr-18.
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

            // load sound fx
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
        // other
        g.setFont(font2);
        g.drawString("2017 © toni kolaba", 10, 232);
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

