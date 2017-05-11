package al.artofsoul.Main;

import javax.swing.JFrame;

import al.artofsoul.Main.GamePanel;

/**
 * @author ArtOfSoul
 *
 */

public class Game {
	
	public static void main(String[] args) {
		JFrame window = new JFrame("ANGELS POP");
		window.add(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
}
