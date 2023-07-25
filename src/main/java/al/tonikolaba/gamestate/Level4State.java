package al.tonikolaba.gamestate;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.entity.enemies.Enemy;
import al.tonikolaba.entity.EnergyParticle;
import al.tonikolaba.entity.Explosion;
import al.tonikolaba.entity.HUD;
import al.tonikolaba.entity.Player;
import al.tonikolaba.entity.PlayerSave;
import al.tonikolaba.entity.Portal;
import al.tonikolaba.entity.enemies.Spirit;
import al.tonikolaba.entity.Teleport;
import al.tonikolaba.entity.batbat.Piece;
import al.tonikolaba.entity.enemies.RedEnergy;
import al.tonikolaba.handlers.Keys;
import al.tonikolaba.main.GamePanel;
import al.tonikolaba.tilemap.Background;

/**
 * @author N.Kolaba
 */

public class Level4State extends GameState {

	private static final String LEVEL_BOSS_MUSIC_NAME = "level1boss";
	private ArrayList<EnergyParticle> energyParticles;
	private Piece tlp;
	private Piece trp;
	private Piece blp;
	private Piece brp;

	private Spirit spirit;

	// events
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private ArrayList<Rectangle> tb;
	private boolean eventFinish;
	private boolean eventDead;
	private boolean eventPortal;
	private boolean flash;
	private boolean eventBossDead;
	protected Portal portal;

	public Level4State(GameStateManager gsm) {
		super(gsm);
		init(GameStateManager.ACIDSTATE);
	}

	@Override
	public void init(int nextLevel) {

		super.init(nextLevel);
		// backgrounds
		temple = new Background("/Backgrounds/temple1.gif", 0.5, 0);

		// tilemap
		generateTileMap("/Maps/level4.map", 0, 140, false);

		setupGameObjects(50, 190, 160, 154, true);
		setupMusic(LEVEL_BOSS_MUSIC_NAME, "/Music/level1boss.mp3", true);

		// player
		player = new Player(tileMap);
		player.setPosition(50, 190);
		player.setHealth(PlayerSave.getHeath());
		player.setLives(PlayerSave.getLives());
		player.setTime(PlayerSave.getTime());

		// player
//		player = new Player(tileMap);
//		player.setPosition(50, 190);
//		player.setHealth(PlayerSave.getHealth());
//		player.setLives(PlayerSave.getLives());
//		player.setTime(PlayerSave.getTime());

		// explosions
		explosions = new ArrayList<Explosion>();

		// enemies
		enemies = new ArrayList<Enemy>();
		populateEnemies();

		// energy particle
		energyParticles = new ArrayList<EnergyParticle>();

		// init player
		player.init(enemies, energyParticles);

		// hud
		hud = new HUD(player);

		// portal
		portal = new Portal(tileMap);
		portal.setPosition(270, 395);
		teleport = new Teleport(tileMap);
		teleport.setPosition(480, 40);
		// teleport.setPosition(260, 395);

		// vendi ku duhet te dal Spirit
		tlp = new Piece(tileMap, new int[] { 0, 0, 10, 10 });
		trp = new Piece(tileMap, new int[] { 10, 0, 10, 10 });
		blp = new Piece(tileMap, new int[] { 0, 10, 10, 10 });
		brp = new Piece(tileMap, new int[] { 10, 10, 10, 10 });
		tlp.setPosition(260, 345);
		trp.setPosition(270, 345);
		blp.setPosition(260, 355);
		brp.setPosition(270, 355);

		// start event
		eventStart = blockInput = true;
		tb = new ArrayList<Rectangle>();
		eventStart();
	}

	private void populateEnemies() {
		enemies.clear();
		spirit = new Spirit(tileMap, player, enemies, explosions);
		spirit.setPosition(-9000, 9000);
		enemies.add(spirit);
	}

	@Override
	public void update() {
		// check keys
		handleInput();

		// check if boss dead event should start
		if (!eventFinish && spirit.isDead()) {
			eventBossDead = blockInput = true;
		}

		// check if player dead event should start
		if (player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true;
		}

		// play events
		if (eventStart)
			eventStart();
		if (eventDead)
			eventDead();
		if (eventFinish)
			eventFinish();
		if (eventPortal)
			eventPortal();
		if (eventBossDead)
			eventBossDead();

		// move backgrounds
		temple.setPosition(tileMap.getx(), tileMap.gety());

		// update player
		player.update();

		// update tilemap
		tileMap.setPosition(GamePanel.WIDTH - player.getx(), GamePanel.HEIGHT - player.gety());
		tileMap.update();
		tileMap.fixBounds();

		// update enemies
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if (e.isDead() || e.shouldRemove()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, e.getx(), e.gety()));
			}
		}

		// update explosions
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if (explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}

		// update portal
		portal.update();

		// update artfact
		tlp.update();
		trp.update();
		blp.update();
		brp.update();
	}

	@Override
	public void draw(Graphics2D g) {
		// draw background
		temple.draw(g);

		// draw tilemap
		tileMap.draw(g);

		// draw portal
		portal.draw(g);

		// draw enemies
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}

		// draw explosions
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).draw(g);
		}

		// draw artifact
		tlp.draw(g);
		trp.draw(g);
		blp.draw(g);
		brp.draw(g);

		// draw player
		player.draw(g);

		// draw hud
		hud.draw(g);

		// draw transition boxes
		g.setColor(java.awt.Color.YELLOW);
		for (int i = 0; i < tb.size(); i++) {
			g.fill(tb.get(i));
		}

		// flash
		if (flash) {
			g.setColor(java.awt.Color.WHITE);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}

	@Override
	public void handleInput() {
		if (Keys.isPressed(Keys.ESCAPE))
			gsm.setPaused(true);
		if (blockInput || player.getHealth() == 0)
			return;
		player.setUp(Keys.getKeyState()[Keys.UP]);
		player.setLeft(Keys.getKeyState()[Keys.LEFT]);
		player.setDown(Keys.getKeyState()[Keys.DOWN]);
		player.setRight(Keys.getKeyState()[Keys.RIGHT]);
		player.setJumping(Keys.getKeyState()[Keys.BUTTON1]);
		player.setDashing(Keys.getKeyState()[Keys.BUTTON2]);
		if (Keys.isPressed(Keys.BUTTON3))
			player.setAttacking();
		if (Keys.isPressed(Keys.BUTTON4))
			player.setCharging();
	}

///////////////////////////////////////////////////////
//////////////////// EVENTS
///////////////////////////////////////////////////////

	// reset level
	@Override
	protected void reset() {
		player.reset();
		player.setPosition(50, 190);
		populateEnemies();
		eventStart = blockInput = true;
		eventCount = 0;
		eventStart();
	}

	// level started
	private void eventStart() {
		eventCount++;
		if (eventCount == 1) {
			tb.clear();
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
			tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
			if (!portal.isOpened())
				tileMap.setShaking(true, 10);
			JukeBox.stop("level1");
		}
		if (eventCount > 1 && eventCount < 60) {
			tb.get(0).height -= 4;
			tb.get(1).width -= 6;
			tb.get(2).y += 4;
			tb.get(3).x += 6;
		}
		if (eventCount == 60) {
			eventStart = blockInput = false;
			eventCount = 0;
			eventPortal = blockInput = true;
			tb.clear();

		}
	}

	// player has died
	private void eventDead() {
		eventCount++;
		if (eventCount == 1) {
			player.setDead();
			player.stop();
		}
		if (eventCount == 60) {
			tb.clear();
			tb.add(new Rectangle(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		} else if (eventCount > 60) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if (eventCount >= 120) {
			if (player.getLives() == 0) {
				gsm.setState(GameStateManager.MENUSTATE);
			} else {
				eventDead = blockInput = false;
				eventCount = 0;
				player.loseLife();
				reset();
			}
		}
	}

	// finished level
	private void eventFinish() {
		eventCount++;
		if (eventCount == 1) {
			tb.clear();
			tb.add(new Rectangle(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		} else if (eventCount > 1) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if (eventCount == 60) {
			PlayerSave.setHeath(player.getHealth());
			PlayerSave.setLives(player.getLives());
			PlayerSave.setTime(player.getTime());
			gsm.setState(GameStateManager.ACIDSTATE);
		}

	}

	private void eventPortal() {
		eventCount++;
		if (eventCount == 1) {
			if (portal.isOpened()) {
				eventCount = 360;
			}
		}
		if (eventCount > 60 && eventCount < 180) {
			energyParticles.add(new EnergyParticle(tileMap, 270, 353, (int) (Math.random() * 4))); // vendi ku fillon te
																									// dalin drita
		}
		if (eventCount >= 160 && eventCount <= 180) {
			if (eventCount % 4 == 0 || eventCount % 4 == 1)
				flash = true;
			else
				flash = false;
		}
		if (eventCount == 181) {
			tileMap.setShaking(false, 0);
			flash = false;
			tlp.setVector(-0.3, -0.3);
			trp.setVector(0.3, -0.3);
			blp.setVector(-0.3, 0.3);
			brp.setVector(0.3, 0.3);
			player.setEmote(Player.SURPRISED_EMOTE);
		}
		if (eventCount == 240) {
			tlp.setVector(0, -5);
			trp.setVector(0, -5);
			blp.setVector(0, -5);
			brp.setVector(0, -5);
		}
		if (eventCount == 300) {
			player.setEmote(Player.NONE_EMOTE);
			portal.setOpening();
		}
		if (eventCount == 360) {
			flash = true;
			spirit.setPosition(270, 395);
			RedEnergy de;
			for (int i = 0; i < 20; i++) {
				de = new RedEnergy(tileMap);
				de.setPosition(270, 395);
				de.setVector(Math.random() * 10 - 5, Math.random() * -2 - 3);
				enemies.add(de);
			}
		}
		if (eventCount == 362) {
			flash = false;
			JukeBox.loop("level1boss", 0, 60000, JukeBox.getFrames("level1boss") - 4000);
		}
		if (eventCount == 420) {
			eventPortal = blockInput = false;
			eventCount = 0;
			spirit.setActive();
		}

	}

	public void eventBossDead() {
		eventCount++;
		if (eventCount == 1) {
			player.stop();
			JukeBox.stop("level1boss");
			enemies.clear();
		}
		if (eventCount <= 120 && eventCount % 15 == 0) {
			explosions.add(new Explosion(tileMap, spirit.getx(), spirit.gety()));
			JukeBox.play("explode");
		}
		if (eventCount == 180) {
			JukeBox.play("fanfare");
		}
		if (eventCount == 390) {
			eventBossDead = false;
			eventCount = 0;
			eventFinish = true;
		}
	}
}