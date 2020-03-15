package al.artofsoul.batbatgame.gamestate;

import al.artofsoul.batbatgame.handlers.Keys;
import al.artofsoul.batbatgame.main.GamePanel;

import java.awt.*;

/**
 * @author ArtOfSoul
 */

public class PauseState extends GameState {

    private Font pauseFont;

    public PauseState(GameStateManager gsm) {

        super(gsm);

        // fonts
        pauseFont = new Font("Arial", Font.PLAIN, 12);

    }

    @Override
    public void update() {
        handleInput();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(pauseFont);
        g.drawString("Game Paused", 110, 110);
        g.drawString("Press [Enter] to Exit Game", 80, 140);
    }

    @Override
    public void handleInput() {
        if (Keys.isPressed(Keys.ESCAPE))
            gsm.setPaused(false);
        if (Keys.isPressed(Keys.ENTER)) {
            gsm.setPaused(false);
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }

}
