package al.tonikolaba.entity.enemies;

import al.tonikolaba.entity.Player;
import al.tonikolaba.handlers.Content;
import al.tonikolaba.tilemap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ArtOfSoul
 */

public class Ufo extends Flyer {

    private static final int IDLE = 0;
    private static final int JUMPING = 1;
    private static final int ATTACKING = 2;
    private Player player;
    private ArrayList<Enemy> enemies;
    private BufferedImage[] idleSprites;
    private BufferedImage[] jumpSprites;
    private BufferedImage[] attackSprites;
    private int attackTick;
    private int attackDelay = 30;
    private int step;

    public Ufo(TileMap tm, Player p, List<Enemy> en) {

        super(tm, FlyerType.UFO);
        player = p;
        enemies = (ArrayList<Enemy>) en;

        idleSprites = Content.getUfo()[0];
        jumpSprites = Content.getUfo()[1];
        attackSprites = Content.getUfo()[2];

        animation.setFrames(idleSprites);
        animation.setDelay(-1);

        attackTick = 0;

    }

    @Override
    public void update() {

        // check if done flinching
        if (flinching) {
            flinchCount++;
            if (flinchCount == 6)
                flinching = false;
        }

        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        // update animation
        animation.update();

        facingRight = player.getx() >= x;

        switch (step) {
            case 0:
                updateIdle();
                break;
            case 1:
                updateJump();
                break;
            case 2:
                updateAttack();
                break;
            case 3:
                if (dy == 0)
                    step++;
                break;
            default:
                step = 0;
                left = right = jumping = false;
                break;

        }
    }

    private void updateAttack() {
        if (dy > 0 && currentAction != ATTACKING) {
            currentAction = ATTACKING;
            animation.setFrames(attackSprites);
            animation.setDelay(3);
            RedEnergy de = new RedEnergy(tileMap);
            de.setPosition(x, y);
            if (facingRight)
                de.setVector(3, 3);
            else
                de.setVector(-3, 3);
            enemies.add(de);
        }
        if (currentAction == ATTACKING && animation.hasPlayedOnce()) {
            step++;
            currentAction = JUMPING;
            animation.setFrames(jumpSprites);
            animation.setDelay(-1);
        }
    }

    private void updateJump() {
        if (currentAction != JUMPING) {
            currentAction = JUMPING;
            animation.setFrames(jumpSprites);
            animation.setDelay(-1);
        }
        jumping = true;
        if (facingRight)
            left = true;
        else
            right = true;
        if (falling) {
            step++;
        }
    }

    private void updateIdle() {
        if (currentAction != IDLE) {
            currentAction = IDLE;
            animation.setFrames(idleSprites);
            animation.setDelay(-1);
        }
        attackTick++;
        if (attackTick >= attackDelay && Math.abs(player.getx() - x) < 60) {
            step++;
            attackTick = 0;
        }
    }

    @Override
    public void draw(Graphics2D g) {

        if (flinching && (flinchCount == 0 || flinchCount == 2)) {
            return;
        }

        super.draw(g);

    }

}
