package al.artofsoul.batbatgame.entity.enemies;

import al.artofsoul.batbatgame.entity.Enemy;
import al.artofsoul.batbatgame.tilemap.TileMap;

/**
 * Created by NdueKolaba on 03-Apr-18.
 */

public class Flyer extends Enemy {


    private double[][] initValues = new double[][]{new double[]{4, 30, 30, 20, 26, 1, 1.5, 0.15, 4.0, -5},
            new double[]{1, 25, 25, 20, 18, 1, 0.8, 0.15, 4.0, -5}};

    public Flyer(TileMap tm, FlyerType type) {
        super(tm);
        health = maxHealth = (int) initValues[type.value][0];

        width = (int) initValues[type.value][1];
        height = (int) initValues[type.value][2];
        cwidth = (int) initValues[type.value][3];
        cheight = (int) initValues[type.value][4];

        damage = (int) initValues[type.value][5];
        moveSpeed = initValues[type.value][6];
        fallSpeed = initValues[type.value][7];
        maxFallSpeed = initValues[type.value][8];
        jumpStart = initValues[type.value][9];
    }

    protected void getNextPosition() {
        if (left)
            dx = -moveSpeed;
        else if (right)
            dx = moveSpeed;
        else
            dx = 0;
        if (falling) {
            dy += fallSpeed;
            if (dy > maxFallSpeed)
                dy = maxFallSpeed;
        }
        if (jumping && !falling) {
            dy = jumpStart;
        }
    }

    public enum FlyerType {
        UFO(0), XHEL_BAT(1);

        public final int value;

        FlyerType(int val) {
            value = val;
        }
    }
}
