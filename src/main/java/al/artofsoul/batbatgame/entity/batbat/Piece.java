package al.artofsoul.batbatgame.entity.batbat;

import al.artofsoul.batbatgame.entity.MapObject;
import al.artofsoul.batbatgame.handlers.LoggingHelper;
import al.artofsoul.batbatgame.tilemap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

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
}
