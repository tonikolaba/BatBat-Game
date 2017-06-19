package al.artofsoul.BatBatGame.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/**
 * @author ArtOfSoul
 *
 */

public class HUD {
	
	private Player player;
	
	private BufferedImage heart;
	private BufferedImage life;
	
	public HUD(Player p) {
		player = p;
		try {
			BufferedImage image = ImageIO.read(
				getClass().getResourceAsStream(
					"/HUD/Hud.gif"
				)
			);
			heart = image.getSubimage(0, 0, 13, 12);
			life = image.getSubimage(0, 12, 12, 11);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		for(int i = 0; i < player.getHealth(); i++) {
			g.drawImage(heart, 10 + i * 15, 10, null);
		}
		for(int i = 0; i < player.getLives(); i++) {
			g.drawImage(life, 10 + i * 15, 25, null);
		}
		g.setColor(java.awt.Color.WHITE);
		g.drawString(player.getTimeToString(), 290, 15);
	}
	
}













