/**
 * 
 */
package al.tonikolaba.gamestate;

import java.util.ArrayList;

import al.tonikolaba.entity.EnergyParticle;
import al.tonikolaba.entity.Player;
import al.tonikolaba.entity.enemies.Spirit;
import al.tonikolaba.entity.enemies.Enemy.EnemyType;
import al.tonikolaba.entity.batbat.Piece;
import al.tonikolaba.tilemap.Background;

/**
 * @author N.Kolaba
 *
 */
public class LevelTest extends GameState {

	private static final String LEVEL_BOSS_MUSIC_NAME = "level1boss";
	private ArrayList<EnergyParticle> energyParticles;
	private Piece tlp;
	private Piece trp;
	private Piece blp;
	private Piece brp;
	private Spirit spirit;
	private boolean flash;
	private boolean eventBossDead;
	private boolean eventQuake;

	public LevelTest(GameStateManager gsm) {
		super(gsm);
		init(GameStateManager.ACIDSTATE);
	}

	@Override
	public void init(int nextLevel) { // fillon niveli tj

		super.init(nextLevel);
		// backgrounds
		temple = new Background("/Backgrounds/temple.gif", 0.5, 0);

		// tilemap
		generateTileMap("/Maps/level4.map", 140, 0, false);

		setupGameObjects(80, 131, 2850, 120, false);
		setupTitle(new int[] { 0, 0, 178, 20 }, new int[] { 0, 33, 91, 13 });
		setupMusic("level4", "/Music/level1boss.mp3", true);

		enemyTypesInLevel = new EnemyType[] { EnemyType.SPIRIT };

		coords = new int[][] { new int[] { 150, 100 } };

		populateEnemies(enemyTypesInLevel, coords);
	}

	@Override
	public void update() {

		super.update();
		// eventStartFunc();
		// check if quake event should start

		if (player.getx() > 100 && !tileMap.isShaking()) {
			eventQuake = blockInput = true;
			eventCount++;
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
			player.setPosition(120, player.gety());
			player.setEmote(Player.CONFUSED_EMOTE);
			player.setEmote(Player.NONE_EMOTE);
			player.setEmote(Player.SURPRISED_EMOTE);
			tileMap.setShaking(true, 10);
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
