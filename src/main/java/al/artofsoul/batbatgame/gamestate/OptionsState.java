package al.artofsoul.batbatgame.gamestate;

import al.artofsoul.batbatgame.audio.JukeBox;

import javax.swing.*;
import java.awt.*;

/**
 * @author ArtOfSoul
 */

public class OptionsState extends BasicState {

    final ImageIcon howTo = new ImageIcon(getClass().getResource("/Backgrounds/Instructions.gif"));

    public OptionsState(GameStateManager gsm) {

        super(gsm);
        options = new String[]{"How To Play", "Back"};
    }

    @Override
    public void update() {
        // check keys
        handleInput();
    }

    @Override
    public void draw(Graphics2D g) {

        super.draw(g);
        g.drawString("How To Play", 140, 133);
        g.drawString("Back", 140, 148);

    }

    public void siLuhet() {
        JOptionPane.showMessageDialog(null, "", "How To Play?", JOptionPane.INFORMATION_MESSAGE, howTo);
    }

    @Override
    protected void select() {
        if (currentChoice == 0) {
            JukeBox.play("menuselect");
            siLuhet();
        } else {
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }

}
