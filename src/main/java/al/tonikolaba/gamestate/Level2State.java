package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Enemy.EnemyType;
import al.tonikolaba.tilemap.Background;

/**
 * @author ArtOfSoul
 */

public class Level2State extends GameState {

    public Level2State(GameStateManager gsm) {
        super(gsm);
        init(GameStateManager.LEVEL3STATE);
    }

    @Override
    public void init(int nextLevel) {

        super.init(nextLevel);
        // backgrounds
        perendimi = new Background("/Backgrounds/perendimi.gif", 0.5, 0);
        mountains = new Background("/Backgrounds/mali2.gif", 0.2);

        // tilemap
        generateTileMap("/Maps/level2.map", 140, 0, true);

        setupGameObjects(300, 161, 3700, 131, false);
        setupTitle(new int[]{0, 0, 178, 20}, new int[]{0, 33, 91, 13});
        setupMusic("level1", "/Music/level1.mp3", true);

        enemyTypesInLevel = new EnemyType[]{EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT,
                EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT,
                EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.ZOGU, EnemyType.ZOGU};
        coords = new int[][]{new int[]{1300, 100}, new int[]{1320, 100}, new int[]{1340, 100},
                new int[]{1660, 100}, new int[]{1680, 100}, new int[]{1700, 100}, new int[]{2177, 100},
                new int[]{2960, 100}, new int[]{2980, 100}, new int[]{3000, 100}, new int[]{2600, 100},
                new int[]{3500, 100}};

        populateEnemies(enemyTypesInLevel, coords);
    }

}
