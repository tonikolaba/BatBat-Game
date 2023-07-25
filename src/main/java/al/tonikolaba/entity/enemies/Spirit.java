package al.tonikolaba.entity.enemies;

import al.tonikolaba.entity.Explosion;
import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author N. Kolaba
 */

public class Spirit extends Enemy {

	public BufferedImage[] sprites;
	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;

	private boolean active;
	private boolean finalAttack;

	private int step;
	private int stepCount;

	// attack pattern
	private int[] steps = { 0, 1, 0, 1, 2, 1, 0, 2, 1, 2 };

	//// attacks:
	// fly around throwing dark energy (0)
	// floor sweep (1)
	// crash down on floor to create shockwave (2)
	//// special:
	// after half hp, create shield
	// after quarter hp, bullet hell

	private RedEnergy[] shield;
	private double ticks;

	public Spirit(TileMap tm, Player p, ArrayList<Enemy> enemies, ArrayList<Explosion> explosions) {

		super(tm);
		player = p;
		this.enemies = enemies;
		this.explosions = explosions;

		width = 40;
		height = 40;
		cwidth = 30;
		cheight = 30;

		health = maxHealth = 80;

		moveSpeed = 1.4;

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/Spirit.gif"));
			sprites = new BufferedImage[4];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		damage = 1;

		animation.setFrames(sprites);
		animation.setDelay(1);

		shield = new RedEnergy[2];

		step = 0;
		stepCount = 0;

	}

	public void setActive() {
		active = true;
	}

	@Override
	public void update() {

		if (health == 0)
			return;

		// restart attack pattern
		if (step == steps.length) {
			step = 0;
		}

		ticks++;

		if (flinching) {
			flinchCount++;
			if (flinchCount == 8)
				flinching = false;
		}

		x += dx;
		y += dy;

		animation.update();

		if (!active)
			return;

		////////////
		// special
		////////////
		if (health <= maxHealth / 2) {
			if (shield[0] == null) {
				shield[0] = new RedEnergy(tileMap);
				shield[0].setPermanent(true);
				enemies.add(shield[0]);
			}
			if (shield[1] == null) {
				shield[1] = new RedEnergy(tileMap);
				shield[0].setPermanent(true);
				enemies.add(shield[1]);
			}
			double pos = ticks / 32;
			shield[0].setPosition(x + 30 * Math.sin(pos), y + 30 * Math.cos(pos));
			pos += 3.1415;
			shield[1].setPosition(x + 30 * Math.sin(pos), y + 30 * Math.cos(pos));
		}

		if (!finalAttack && health <= maxHealth / 4) {
			stepCount = 0;
			finalAttack = true;
		}

		if (finalAttack) {
			stepCount++;
			if (stepCount == 1) {
				explosions.add(new Explosion(tileMap, (int) x, (int) y));
				x = -9000;
				y = 9000;
				dx = dy = 0;
			}
			if (stepCount == 60) {
				x = tileMap.getWidth() / 2;
				y = tileMap.getHeight() / 2;
				explosions.add(new Explosion(tileMap, (int) x, (int) y));
			}
			if (stepCount >= 90 && stepCount % 30 == 0) {
				RedEnergy de = new RedEnergy(tileMap);
				de.setPosition(x, y);
				de.setVector(3 * Math.sin(stepCount / 32), 3 * Math.cos(stepCount / 32));
				de.setType(RedEnergy.BOUNCE);
				enemies.add(de);
			}
			return;
		}

		////////////
		// attacks
		////////////

		// fly around dropping bombs
		if (steps[step] == 0) {
			stepCount++;
			if (y > 60) {
				dy = -4;
			}
			if (y < 60) {
				dy = 0;
				y = 60;
				dx = -1;
			}
			if (y == 60) {
				if (dx == -1 && x < 60) {
					dx = 1;
				}
				if (dx == 1 && x > tileMap.getWidth() - 60) {
					dx = -1;
				}
			}
			if (stepCount % 10 == 0) { // 60 per te hedhur me shpesh bombat
				RedEnergy de = new RedEnergy(tileMap);
				de.setType(RedEnergy.GRAVITY);
				de.setPosition(x, y);
				int dir = Math.random() < 0.5 ? 1 : -1;
				de.setVector(dir, 0);
				enemies.add(de);
			}
			if (stepCount == 559) {
				step++;
				stepCount = 0;
				right = left = false;
			}
		}
		// floor sweep
		else if (steps[step] == 1) {
			stepCount++;
			if (stepCount == 1) {
				explosions.add(new Explosion(tileMap, (int) x, (int) y));
				x = -9000;
				y = 9000;
				dx = dy = 0;
			}
			if (stepCount == 60) {
				if (player.getx() > tileMap.getWidth() / 2) {
					x = 30;
					y = tileMap.getHeight() - 60;
					dx = 4;
				} else {
					x = tileMap.getWidth() - 30;
					y = tileMap.getHeight() - 60;
					dx = -4;
				}
				explosions.add(new Explosion(tileMap, (int) x, (int) y));
			}
			if ((dx == -4 && x < 30) || (dx == 4 && x > tileMap.getWidth() - 30)) {
				stepCount = 0;
				step++;
				dx = dy = 0;
			}

		}
		// shockwave
		else if (steps[step] == 2) {
			stepCount++;
			if (stepCount == 1) {
				x = tileMap.getWidth() - 50; // zbret poshte dhe gjuan topat ne drejtim horzontal-poshte
				y = 60; // 40 nise nga pika lart duke zbrit poshte
			}
			if (stepCount == 30) { // 60 // per te shtuar hedhjen a bombave nag e majta ne te djatht
				dy = 73; // 7 leviz hedhjen e batbat ne drejtim me poshte
			}
			if (y >= tileMap.getHeight() - 260) { // per te gjuartur kur arrin ne fund afer Batbat-it
				dy = 0;
			}
			if (stepCount > 60 && stepCount < 120 && stepCount % 2 == 0 && dy == 0) { // %5
				RedEnergy de = new RedEnergy(tileMap);
				de.setType(RedEnergy.GRAVITY);
				de.setPosition(x, y);
				de.setVector(-12, 0); // -3 //per te hedhur topat majtas djathats me shpejt
				enemies.add(de);
				de = new RedEnergy(tileMap);
				de.setType(RedEnergy.GRAVITY);
				de.setPosition(x, y);
				de.setVector(9, 0); // 3
				enemies.add(de);
			}
			if (stepCount == 420) {
				stepCount = 0;
				step++;
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		if (flinching) {
			if (flinchCount % 4 < 2)
				return;
		}
		super.draw(g);
	}
}