package al.artofsoul.batbatgame.gamestate;

import al.artofsoul.batbatgame.entity.Enemy.EnemyType;
import al.artofsoul.batbatgame.tilemap.Background;

/**
 * @author ArtOfSoul
 */

public class Level1State extends GameState {

	public Level1State(GameStateManager gsm) {
		super(gsm);
		init(GameStateManager.LEVEL2STATE);
	}

	@Override
	public void init(int nextLevel) {
		// backgrounds
		super.init(nextLevel);

		generateTileMap("/Maps/level1.map", 0, 120, true);

		setupGameObjects(140, 191, 3700, 131, false);

		setupMusic("level1", "/Music/level1.mp3", true);

		sky = new Background("/Backgrounds/qielli.gif", 0);
		clouds = new Background("/Backgrounds/rete.gif", 0.1);
		mountains = new Background("/Backgrounds/mali.gif", 0.2);
		enemyTypesInLevel = new EnemyType[] { EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT,
				EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT,
				EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.ZOGU, EnemyType.ZOGU };
		coords = new int[][] { new int[] { 1300, 100 }, new int[] { 1320, 100 }, new int[] { 1340, 100 },
				new int[] { 1660, 100 }, new int[] { 1680, 100 }, new int[] { 1700, 100 }, new int[] { 2177, 100 },
				new int[] { 2960, 100 }, new int[] { 2980, 100 }, new int[] { 3000, 100 }, new int[] { 2300, 100 },
				new int[] { 3500, 100 } };

		populateEnemies(enemyTypesInLevel, coords);
		setupTitle(new int[] { 0, 0, 178, 19 }, new int[] { 0, 20, 82, 13 });
	}

}
