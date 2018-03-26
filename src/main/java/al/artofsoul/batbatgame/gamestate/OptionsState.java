package al.artofsoul.batbatgame.gamestate;

import al.artofsoul.batbatgame.audio.JukeBox;

import javax.swing.*;
import java.awt.*;

/**
 * @author ArtOfSoul
 */

public class OptionsState extends BasicState {

    final ImageIcon howTo = new ImageIcon(getClass().getResource("/Backgrounds/howTo.gif"));

    public OptionsState(GameStateManager gsm) {

        super(gsm);
        options = new String[]{"HowTo Play", "Back"};
    }

    @Override
    public void update() {
        // check keys
        handleInput();
    }

    @Override
    public void draw(Graphics2D g) {

        super.draw(g);
        g.drawString("HowTo Play", 140, 133);
        g.drawString("Back", 140, 148);

    }

    public void siLuhet() {
        JOptionPane.showMessageDialog(null, "", "HowTo Paly?", JOptionPane.INFORMATION_MESSAGE, howTo);
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
