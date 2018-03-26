package al.artofsoul.batbatgame.gamestate;

import al.artofsoul.batbatgame.audio.JukeBox;
import al.artofsoul.batbatgame.entity.PlayerSave;
import al.artofsoul.batbatgame.handlers.Keys;

import java.awt.*;

/**
 * @author ArtOfSoul
 */

public class MenuState extends BasicState {

    public MenuState(GameStateManager gsm) {

        super(gsm);
        options = new String[]{"Play", "Options", "Quit"};
    }

    @Override
    public void draw(Graphics2D g) {

        super.draw(g);
        g.drawString("Play", 140, 133);
        g.drawString("Options", 140, 148);
        g.drawString("Quit", 140, 163);
    }

    @Override
    protected void select() {
        switch (currentChoice) {
            case 0:
                JukeBox.play("menuselect");
                PlayerSave.init();
                gsm.setState(GameStateManager.LEVEL1STATE); /// start this level entrance
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
