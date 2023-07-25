package al.tonikolaba.entity.enemies;

import al.tonikolaba.handlers.Content;
import al.tonikolaba.tilemap.TileMap;

import java.awt.image.BufferedImage;

/**
 * @author ArtOfSoul
 */

public class RedEnergy extends Enemy {

    public static final int VECTOR = 0;
    public static final int GRAVITY = 1;
    public static final int BOUNCE = 2;
    private BufferedImage[] startSprites;
    private BufferedImage[] sprites;
    private boolean start;
    private boolean permanent;
    private int type = 0;
    private int bounceCount = 0;

    public RedEnergy(TileMap tm) {

        super(tm);

        health = maxHealth = 1;

        width = 20;
        height = 20;
        cwidth = 12;
        cheight = 12;

        damage = 1;
        moveSpeed = 5;

        startSprites = Content.getRedEnergy()[0];
        sprites = Content.getRedEnergy()[1];

        animation.setFrames(startSprites);
        animation.setDelay(2);

        start = true;
        flinching = true;
        permanent = false;

    }

    public void setType(int i) {
        type = i;
    }

    public void setPermanent(boolean b) {
        permanent = b;
    }

    @Override
    public void update() {

        if (start && animation.hasPlayedOnce()) {
            animation.setFrames(sprites);
            animation.setNumFrames(3);
            animation.setDelay(2);
            start = false;
        }

        if (type == VECTOR) {
            x += dx;
            y += dy;
        } else if (type == GRAVITY) {
            dy += 0.2;
            x += dx;
            y += dy;
        } else if (type == BOUNCE) {
            double dx2 = dx;
            double dy2 = dy;
            checkTileMapCollision();
            if (dx == 0) {
                dx = -dx2;
                bounceCount++;
            }
            if (dy == 0) {
                dy = -dy2;
                bounceCount++;
            }
            x += dx;
            y += dy;
        }

        // update animation
        animation.update();

        if (!permanent) {
            if (x < 0 || x > tileMap.getWidth() || y < 0 || y > tileMap.getHeight()) {
                remove = true;
            }
            if (bounceCount == 3) {
                remove = true;
            }
        }

    }

}
