package al.artofsoul.batbatgame.entity.enemies;

import al.artofsoul.batbatgame.entity.Player;
import al.artofsoul.batbatgame.handlers.Content;
import al.artofsoul.batbatgame.main.GamePanel;
import al.artofsoul.batbatgame.tilemap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author ArtOfSoul
 */

public class XhelBat extends Flyer {

    private BufferedImage[] sprites;
    private Player player;
    private boolean active;

    public XhelBat(TileMap tm, Player p) {

        super(tm, FlyerType.XHEL_BAT);
        player = p;

        sprites = Content.getXhelbat()[0];

        animation.setFrames(sprites);
        animation.setDelay(4);

        left = true;
        facingRight = false;

    }

    @Override
    public void update() {

        if (!active) {
            if (Math.abs(player.getx() - x) < GamePanel.WIDTH)
                active = true;
            return;
        }

        // check if done flinching
        if (flinching) {
            flinchCount++;
            if (flinchCount == 6)
                flinching = false;
        }

        getNextPosition();
        checkTileMapCollision();
        calculateCorners(x, ydest + 1);
        if (!bottomLeft) {
            left = false;
            right = facingRight = true;
        }
        if (!bottomRight) {
            left = true;
            right = facingRight = false;
        }
        setPosition(xtemp, ytemp);

        if (dx == 0) {
            left = !left;
            right = !right;
            facingRight = !facingRight;
        }

        // update animation
        animation.update();

    }

    @Override
    public void draw(Graphics2D g) {

        if (flinching && (flinchCount == 0 || flinchCount == 2)) {
            return;
        }

        super.draw(g);

    }

}
