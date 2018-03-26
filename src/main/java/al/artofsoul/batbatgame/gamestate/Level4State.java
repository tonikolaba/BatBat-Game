package al.artofsoul.batbatgame.gamestate;

import al.artofsoul.batbatgame.audio.JukeBox;
import al.artofsoul.batbatgame.entity.Enemy.EnemyType;
import al.artofsoul.batbatgame.entity.*;
import al.artofsoul.batbatgame.entity.batbat.Piece;
import al.artofsoul.batbatgame.entity.enemies.RedEnergy;
import al.artofsoul.batbatgame.main.GamePanel;
import al.artofsoul.batbatgame.tilemap.Background;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author ArtOfSoul
 */

public class Level4State extends GameState {

    private static final String LEVEL_BOSS_MUSIC_NAME = "level1boss";
    private ArrayList<EnergyParticle> energyParticles;
    private Piece tlp;
    private Piece trp;
    private Piece blp;
    private Piece brp;
    private Spirit spirit;
    private boolean flash;
    private boolean eventBossDead;

    public Level4State(GameStateManager gsm) {
        super(gsm);
        init(GameStateManager.ACIDSTATE);
    }

    @Override
    public void init(int nextLevel) {

        super.init(nextLevel);
        // backgrounds
        temple = new Background("/Backgrounds/temple.gif", 0.5, 0);

        // tilemap
        generateTileMap("/Maps/level4.map", 140, 0, false);

        setupGameObjects(50, 190, 160, 154, true);
        setupMusic(LEVEL_BOSS_MUSIC_NAME, "/Music/level1boss.mp3", false);

        energyParticles = new ArrayList<>();
        // angelspop
        tlp = new Piece(tileMap, new int[]{0, 0, 10, 10});
        trp = new Piece(tileMap, new int[]{10, 0, 10, 10});
        blp = new Piece(tileMap, new int[]{0, 10, 10, 10});
        brp = new Piece(tileMap, new int[]{10, 10, 10, 10});
        tlp.setPosition(152, 102);
        trp.setPosition(162, 102);
        blp.setPosition(152, 112);
        brp.setPosition(162, 112);

        enemyTypesInLevel = new EnemyType[]{EnemyType.SPIRIT};
        coords = new int[][]{new int[]{-9000, 9000}};
        populateEnemies(enemyTypesInLevel, coords);
    }

    @Override
    public void update() {

        super.update();
        if (eventPortal)
            eventPortal();
        if (eventBossDead)
            eventBossDead();
        // update portal
        portal.update();

        // update BATBAT
        tlp.update();
        trp.update();
        blp.update();
        brp.update();

    }

    @Override
    public void draw(Graphics2D g) {

        super.draw(g);
        // draw angelspop
        tlp.draw(g);
        trp.draw(g);
        blp.draw(g);
        brp.draw(g);

        // flash
        if (flash) {
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        }

    }

    ///////////////////////////////////////////////////////
    //////////////////// EVENTS
    ///////////////////////////////////////////////////////

    // level started
    @Override
    protected void eventStartFunc() {
        super.eventStartFunc();
        if (eventCount == 1) {
            if (!portal.isOpened())
                tileMap.setShaking(true, 10);
            JukeBox.stop("level1");
        }
    }

    // finished level
    @Override
    protected void eventFinishFunc() {
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
            PlayerSave.setHealth(player.getHealth());
            PlayerSave.setLives(player.getLives());
            PlayerSave.setTime(player.getTime());
            gsm.setState(GameStateManager.ACIDSTATE);
        }

    }

    private void eventPortal() {
        eventCount++;
        if (eventCount == 1 && portal.isOpened()) {
            eventCount = 360;

        }
        if (eventCount > 60 && eventCount < 180) {
            Random rnd = new Random();
            energyParticles.add(new EnergyParticle(tileMap, 157, 107, rnd.nextInt() * 4));
        }
        if (eventCount >= 160 && eventCount <= 180) {
            flash = eventCount % 4 == 0 || eventCount % 4 == 1;
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
            spirit.setPosition(160, 160);
            RedEnergy de;
            for (int i = 0; i < 20; i++) {
                de = new RedEnergy(tileMap);
                de.setPosition(160, 160);
                de.setVector(Math.random() * 10 - 5, Math.random() * -2 - 3);
                enemies.add(de);
            }
        }
        if (eventCount == 362) {
            flash = false;
            JukeBox.loop(LEVEL_BOSS_MUSIC_NAME, 0, 60000, JukeBox.getFrames(LEVEL_BOSS_MUSIC_NAME) - 4000);
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
            JukeBox.stop(LEVEL_BOSS_MUSIC_NAME);
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