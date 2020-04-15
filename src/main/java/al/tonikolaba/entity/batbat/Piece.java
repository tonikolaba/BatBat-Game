package al.tonikolaba.entity.batbat;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import al.tonikolaba.entity.MapObject;
import al.tonikolaba.handlers.LoggingHelper;
import al.tonikolaba.tilemap.TileMap;

/**
 * @author ArtOfSoul
 */

public class Piece extends MapObject {
	private BufferedImage[] sprites;

	public Piece(TileMap tm, int[] mapCoords) {
		super(tm);
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Other/ballBatBoss.gif"));
			sprites = new BufferedImage[1];
			width = height = 4;
			sprites[0] = spritesheet.getSubimage(mapCoords[0], mapCoords[1], mapCoords[2], mapCoords[3]);
			animation.setFrames(sprites);
			animation.setDelay(-1);
		} catch (Exception e) {
			LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	public void update() {
		x += dx;
		y += dy;
		animation.update();
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}
}
