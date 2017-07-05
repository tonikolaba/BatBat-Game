package al.artofsoul.BatBatGame.GameState;

import al.artofsoul.BatBatGame.Audio.JukeBox;
import al.artofsoul.BatBatGame.Entity.*;
import al.artofsoul.BatBatGame.Entity.Enemies.XhelBat;
import al.artofsoul.BatBatGame.Entity.Enemies.Zogu;
import al.artofsoul.BatBatGame.Handlers.Keys;
import al.artofsoul.BatBatGame.Main.GamePanel;
import al.artofsoul.BatBatGame.TileMap.Background;
import al.artofsoul.BatBatGame.TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author ArtOfSoul
 */

public class Level1State extends GameState {

    private Background sky;
    private Background clouds;
    private Background mountains;

    private Player player;
    private TileMap tileMap;
    private ArrayList<Enemy> enemies;
    private ArrayList<EnemyProjectile> eprojectiles;
    private ArrayList<EnergyParticle> energyParticles;
    private ArrayList<Explosion> explosions;

    private HUD hud;
    private BufferedImage batBatStart;
    private Title title;
    private Title subtitle;
    private Teleport teleport;

    // events
    private boolean blockInput = false;
    private int eventCount = 0;
    private boolean eventStart;
    private ArrayList<Rectangle> tb;
    private boolean eventFinish;
    private boolean eventDead;

    public Level1State(GameStateManager gsm) {
        super(gsm);
        init();
    }

    public void init() {

        // backgrounds
        sky = new Background("/Backgrounds/qielli.gif", 0);
        clouds = new Background("/Backgrounds/rete.gif", 0.1);
        mountains = new Background("/Backgrounds/mali.gif", 0.2);

        // tilemap
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/ruinstileset.gif");
        tileMap.loadMap("/Maps/level1.map");
        tileMap.setPosition(0, 120);
        tileMap.setBounds(
                tileMap.getWidth() - 1 * tileMap.getTileSize(),
                tileMap.getHeight() - 2 * tileMap.getTileSize(),
                0, 0
        );
        tileMap.setTween(1);

        // player
        player = new Player(tileMap);
        player.setPosition(140, 191);
        player.setHealth(PlayerSave.getHealth());
        player.setLives(PlayerSave.getLives());
        player.setTime(PlayerSave.getTime());

        // enemies
        enemies = new ArrayList<Enemy>();
        eprojectiles = new ArrayList<EnemyProjectile>();
        populateEnemies();

        // energy particle
        energyParticles = new ArrayList<EnergyParticle>();

        // init player
        player.init(enemies, energyParticles);

        // explosions
        explosions = new ArrayList<Explosion>();

        // hud
        hud = new HUD(player);

        // title and subtitle
        try {
            batBatStart = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/batbat.gif")
            );
            title = new Title(batBatStart.getSubimage(0, 0, 178, 19));
            title.sety(60);
            subtitle = new Title(batBatStart.getSubimage(0, 20, 82, 13));
            subtitle.sety(85);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // teleport
        teleport = new Teleport(tileMap);
        teleport.setPosition(3700, 131);

        // start event
        eventStart = true;
        tb = new ArrayList<Rectangle>();
        eventStart();

        // sfx
        JukeBox.load("/SFX/teleport.mp3", "teleport");
        JukeBox.load("/SFX/explode.mp3", "explode");
        JukeBox.load("/SFX/enemyhit.mp3", "enemyhit");

        // music
        JukeBox.load("/Music/level1.mp3", "level1");
        JukeBox.loop("level1", 600, JukeBox.getFrames("level1") - 2200);

    }

    private void populateEnemies() {
        enemies.clear();
        /*Ufo t = new Ufo(tileMap, player, enemies);
		t.setPosition(1300, 100);
		enemies.add(t);
		t = new Ufo(tileMap, player, enemies);
		t.setPosition(1330, 100);
		enemies.add(t);
		t = new Ufo(tileMap, player, enemies);
		t.setPosition(1360, 100);
		enemies.add(t);*/
        XhelBat gp;
        Zogu g;

        gp = new XhelBat(tileMap, player);
        gp.setPosition(1300, 100);
        enemies.add(gp);
        gp = new XhelBat(tileMap, player);
        gp.setPosition(1320, 100);
        enemies.add(gp);
        gp = new XhelBat(tileMap, player);
        gp.setPosition(1340, 100);
        enemies.add(gp);
        gp = new XhelBat(tileMap, player);
        gp.setPosition(1660, 100);
        enemies.add(gp);
        gp = new XhelBat(tileMap, player);
        gp.setPosition(1680, 100);
        enemies.add(gp);
        gp = new XhelBat(tileMap, player);
        gp.setPosition(1700, 100);
        enemies.add(gp);
        gp = new XhelBat(tileMap, player);
        gp.setPosition(2177, 100);
        enemies.add(gp);
        gp = new XhelBat(tileMap, player);
        gp.setPosition(2960, 100);
        enemies.add(gp);
        gp = new XhelBat(tileMap, player);
        gp.setPosition(2980, 100);
        enemies.add(gp);
        gp = new XhelBat(tileMap, player);
        gp.setPosition(3000, 100);
        enemies.add(gp);

        g = new Zogu(tileMap);
        g.setPosition(2300, 100);
        enemies.add(g);
        g = new Zogu(tileMap);
        g.setPosition(3500, 100);
        enemies.add(g);
    }

    public void update() {

        // check keys
        handleInput();

        // check if end of level event should start
        if (teleport.contains(player)) {
            eventFinish = blockInput = true;
        }

        // check if player dead event should start
        if (player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
            eventDead = blockInput = true;
        }

        // play events
        if (eventStart) eventStart();
        if (eventDead) eventDead();
        if (eventFinish) eventFinish();

        // move title and subtitle
        if (title != null) {
            title.update();
            if (title.shouldRemove()) title = null;
        }
        if (subtitle != null) {
            subtitle.update();
            if (subtitle.shouldRemove()) subtitle = null;
        }

        // move backgrounds
        clouds.setPosition(tileMap.getx(), tileMap.gety());
        mountains.setPosition(tileMap.getx(), tileMap.gety());

        // update player
        player.update();

        // update tilemap
        tileMap.setPosition(
                GamePanel.WIDTH / 2 - player.getx(),
                GamePanel.HEIGHT / 2 - player.gety()
        );
        tileMap.update();
        tileMap.fixBounds();

        // update enemies
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if (e.isDead()) {
                enemies.remove(i);
                i--;
                explosions.add(new Explosion(tileMap, e.getx(), e.gety()));
            }
        }

        // update enemy projectiles
        for (int i = 0; i < eprojectiles.size(); i++) {
            EnemyProjectile ep = eprojectiles.get(i);
            ep.update();
            if (ep.shouldRemove()) {
                eprojectiles.remove(i);
                i--;
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

        // update teleport
        teleport.update();

    }

    public void draw(Graphics2D g) {

        // draw background
        sky.draw(g);
        clouds.draw(g);
        mountains.draw(g);

        // draw tilemap
        tileMap.draw(g);

        // draw enemies
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }

        // draw enemy projectiles
        for (int i = 0; i < eprojectiles.size(); i++) {
            eprojectiles.get(i).draw(g);
        }

        // draw explosions
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).draw(g);
        }

        // draw player
        player.draw(g);

        // draw teleport
        teleport.draw(g);

        // draw hud
        hud.draw(g);

        // draw title
        if (title != null) title.draw(g);
        if (subtitle != null) subtitle.draw(g);

        // draw transition boxes
        g.setColor(java.awt.Color.BLACK);
        for (int i = 0; i < tb.size(); i++) {
            g.fill(tb.get(i));
        }

    }

    public void handleInput() {
        if (Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
        if (blockInput || player.getHealth() == 0) return;
        player.setUp(Keys.keyState[Keys.UP]);
        player.setLeft(Keys.keyState[Keys.LEFT]);
        player.setDown(Keys.keyState[Keys.DOWN]);
        player.setRight(Keys.keyState[Keys.RIGHT]);
        player.setJumping(Keys.keyState[Keys.BUTTON1]);
        player.setDashing(Keys.keyState[Keys.BUTTON2]);
        if (Keys.isPressed(Keys.BUTTON3)) player.setAttacking();
        if (Keys.isPressed(Keys.BUTTON4)) player.setCharging();
    }

///////////////////////////////////////////////////////
//////////////////// EVENTS
///////////////////////////////////////////////////////

    // reset level
    private void reset() {
        player.reset();
        player.setPosition(140, 191);
        populateEnemies();
        blockInput = true;
        eventCount = 0;
        tileMap.setShaking(false, 0);
        eventStart = true;
        eventStart();
        title = new Title(batBatStart.getSubimage(0, 0, 178, 20));
        title.sety(60);
        subtitle = new Title(batBatStart.getSubimage(0, 33, 91, 13));
        subtitle.sety(85);
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
        }
        if (eventCount > 1 && eventCount < 60) {
            tb.get(0).height -= 4;
            tb.get(1).width -= 6;
            tb.get(2).y += 4;
            tb.get(3).x += 6;
        }
        if (eventCount == 30) title.begin();
        if (eventCount == 60) {
            eventStart = blockInput = false;
            eventCount = 0;
            subtitle.begin();
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
            tb.add(new Rectangle(
                    GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
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
            JukeBox.play("teleport");
            player.setTeleporting(true);
            player.stop();
        } else if (eventCount == 120) {
            tb.clear();
            tb.add(new Rectangle(
                    GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
        } else if (eventCount > 120) {
            tb.get(0).x -= 6;
            tb.get(0).y -= 4;
            tb.get(0).width += 12;
            tb.get(0).height += 8;
            JukeBox.stop("teleport");
        }
        if (eventCount == 180) {
            PlayerSave.setHealth(player.getHealth());
            PlayerSave.setLives(player.getLives());
            PlayerSave.setTime(player.getTime());
            gsm.setState(GameStateManager.LEVEL3STATE);
        }

    }

}
