/** Copyright to N.Kolaba
All rights reserved ©.
**/

package al.tonikolaba.gamestate;

import al.tonikolaba.entity.enemies.Enemy.EnemyType;
import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.Background;

/**
 * @author ArtOfSoul
 */

public class Level3State extends GameState {

	private boolean eventQuake;

	public Level3State(GameStateManager gsm) {
		super(gsm);
		init(GameStateManager.LEVEL4STATE);
	}

	@Override
	public void init(int nextLevel) {

		super.init(nextLevel);
		// backgrounds
		temple = new Background("/Backgrounds/temple1.gif", 0.5, 0);

		// tilemap
		generateTileMap("/Maps/level3.map", 140, 0, false);

		setupGameObjects(300, 131, 2850, 371, false);
		setupTitle(new int[] { 0, 0, 178, 20 }, new int[] { 0, 33, 91, 13 });
		setupMusic("level2", "/Music/level1v2.mp3", true);

		enemyTypesInLevel = new EnemyType[] { EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT,
				EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT,
				EnemyType.ZOGU, EnemyType.ZOGU, EnemyType.ZOGU, EnemyType.ZOGU, EnemyType.UFO, EnemyType.UFO,
				EnemyType.UFO, EnemyType.UFO };
		coords = new int[][] { new int[] { 750, 100 }, new int[] { 900, 150 }, new int[] { 1320, 250 },
				new int[] { 1570, 160 }, new int[] { 1590, 160 }, new int[] { 2600, 370 }, new int[] { 2620, 370 },
				new int[] { 2640, 370 }, new int[] { 904, 130 }, new int[] { 1080, 270 }, new int[] { 1200, 270 },
				new int[] { 1704, 300 }, new int[] { 1900, 580 }, new int[] { 2330, 550 }, new int[] { 2400, 490 },
				new int[] { 2457, 430 } };

		populateEnemies(enemyTypesInLevel, coords);
	}

	@Override
	public void update() {

		super.update();

		// check if quake event should start
		if (player.getx() > 2175 && !tileMap.isShaking()) {
			eventQuake = blockInput = true;
		}

		if (eventQuake)
			eventQuake();

	}

	///////////////////////////////////////////////////////
	//////////////////// EVENTS
	///////////////////////////////////////////////////////

	// earthquake
	private void eventQuake() {
		eventCount++;
		if (eventCount == 1) {
			player.stop();
			player.setPosition(2175, player.gety());
		}
		if (eventCount == 60) {
			player.setEmote(Player.CONFUSED_EMOTE);
		}
		if (eventCount == 120)
			player.setEmote(Player.NONE_EMOTE);
		if (eventCount == 150)
			tileMap.setShaking(true, 10);
		if (eventCount == 180)
			player.setEmote(Player.SURPRISED_EMOTE);
		if (eventCount == 300) {
			player.setEmote(Player.NONE_EMOTE);
			eventQuake = blockInput = false;
			eventCount = 0;
		}
	}
}