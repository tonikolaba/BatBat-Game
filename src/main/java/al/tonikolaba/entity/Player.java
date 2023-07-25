package al.tonikolaba.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.entity.enemies.Enemy;
import al.tonikolaba.handlers.LoggingHelper;
import al.tonikolaba.tilemap.TileMap;

/**
 * @author ArtOfSoul
 */

public class Player extends MapObject {

	public static final int NONE_EMOTE = 0;
	public static final int CONFUSED_EMOTE = 1;
	public static final int SURPRISED_EMOTE = 2;
	private static final int[] NUMFRAMES = { 1, 8, 5, 3, 3, 5, 3, 8, 2, 1, 3 };
	private static final int[] FRAMEWIDTHS = { 40, 40, 80, 40, 40, 40, 80, 40, 40, 40, 40 };
	private static final int[] FRAMEHEIGHTS = { 40, 40, 40, 40, 40, 80, 40, 40, 40, 40, 40 };
	private static final int[] SPRITEDELAYS = { -1, 3, 2, 6, 5, 2, 2, 2, 1, -1, 1 };
	// animation actions
	private static final int IDLE_ANIM = 0;
	private static final int WALKING_ANIM = 1;
	public static final int ATTACKING_ANIM = 2;
	private static final int JUMPING_ANIM = 3;
	private static final int FALLING_ANIM = 4;
	private static final int UPATTACKING_ANIM = 5;
	private static final int CHARGING_ANIM = 6;
	private static final int DASHING_ANIM = 7;
	private static final int KNOCKBACK_ANIM = 8;
	private static final int DEAD_ANIM = 9;
	private static final int TELEPORTING_ANIM = 10;
	public final String PLAYERJUMP_MUSIC_NAME = "playerjump";
	public final String PLAYERATTACK_MUSIC_NAME = "playerattack";
	// references
	private ArrayList<Enemy> enemies;
	// player stuff
	private int lives;
	private int health;
	private int maxHealth;
	private int damage;
	private int chargeDamage;
	private boolean knockback;
	private boolean flinching;
	private long flinchCount;
	private int score;
	private boolean doubleJump;
	private boolean alreadyDoubleJump;
	private double doubleJumpStart;
	private ArrayList<EnergyParticle> energyParticles;
	private long time;
	// actions
	private boolean dashing;
	public boolean attacking;
	public boolean upattacking;
	private boolean charging;
	private int chargingTick;
	private boolean teleporting;
	// animations
	private ArrayList<BufferedImage[]> sprites;
	private Rectangle ar;
	private Rectangle aur;
	private Rectangle cr;
	// emotes
	private BufferedImage confused;
	private BufferedImage surprised;
	private int emote = NONE_EMOTE;

	public Player(TileMap tm) {

		super(tm);

		ar = new Rectangle(0, 0, 0, 0);
		ar.width = 30;
		ar.height = 20;
		aur = new Rectangle((int) x - 15, (int) y - 45, 30, 30);
		cr = new Rectangle(0, 0, 0, 0);
		cr.width = 50;
		cr.height = 40;

		width = 30;
		height = 30;
		cwidth = 15;
		cheight = 38;

		moveSpeed = 1.6;
		maxSpeed = 1.6;
		stopSpeed = 1.6;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		doubleJumpStart = -3;

		damage = 2;
		chargeDamage = 1;

		facingRight = true;

		lives = PlayerSave.getLives();
		health = maxHealth = PlayerSave.getHeath();

		// load sprites
		try {

			BufferedImage spritesheet = ImageIO
					.read(getClass().getResourceAsStream("/Sprites/Player/BatterySpirtes.gif"));

			int count = 0;
			sprites = new ArrayList<>();
			for (int i = 0; i < NUMFRAMES.length; i++) {
				BufferedImage[] bi = new BufferedImage[NUMFRAMES[i]];
				for (int j = 0; j < NUMFRAMES[i]; j++) {
					bi[j] = spritesheet.getSubimage(j * FRAMEWIDTHS[i], count, FRAMEWIDTHS[i], FRAMEHEIGHTS[i]);
				}
				sprites.add(bi);
				count += FRAMEHEIGHTS[i];
			}

			// emotes
			spritesheet = ImageIO.read(getClass().getResourceAsStream("/HUD/Emotes.gif"));
			confused = spritesheet.getSubimage(0, 0, 14, 17);
			surprised = spritesheet.getSubimage(14, 0, 14, 17);

		} catch (Exception e) {
			LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
		}

		energyParticles = new ArrayList<>();

		setAnimation(IDLE_ANIM);

		JukeBox.load("/SFX/playerlands.mp3", "playerlands");
		JukeBox.load("/SFX/playerattack.mp3", PLAYERATTACK_MUSIC_NAME);
		JukeBox.load("/SFX/playerhit.mp3", "playerhit");
		JukeBox.load("/SFX/playercharge.mp3", "playercharge");

	}

	public void init(List<Enemy> enemies, List<EnergyParticle> energyParticles) {
		this.enemies = (ArrayList<Enemy>) enemies;
		this.energyParticles = (ArrayList<EnergyParticle>) energyParticles;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int i) {
		health = i;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setEmote(int i) {
		emote = i;
	}

	public void setTeleporting(boolean b) {
		teleporting = b;
	}

	@Override
	public void setJumping(boolean b) {
		if (knockback)
			return;
		if (b && !jumping && falling && !alreadyDoubleJump) {
			doubleJump = true;
		}
		jumping = b;
	}

	public void setAttacking() {
		if (knockback)
			return;
		if (charging)
			return;
		if (up && !attacking)
			upattacking = true;
		else
			attacking = true;
	}

	public void setCharging() {
		if (knockback)
			return;
		if (!attacking && !upattacking && !charging) {
			charging = true;
			JukeBox.play("playercharge");
			chargingTick = 0;
		}
	}

	public boolean isDashing() {
		return dashing;
	}

	public void setDashing(boolean b) {
		if (!b)
			dashing = false;
		else if (!falling) {
			dashing = true;
		}
	}

	public void setDead() {
		health = 0;
		stop();
	}

	public String getTimeToString() {
		int minutes = (int) (time / 3600);
		int seconds = (int) ((time % 3600) / 60);
		return seconds < 10 ? minutes + ":0" + seconds : minutes + ":" + seconds;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long t) {
		time = t;
	}

	public void gainLife() {
		lives++;
	}

	public void loseLife() {
		lives--;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int i) {
		lives = i;
	}

	public void increaseScore(int score) {
		this.score += score;
	}

	public int getScore() {
		return score;
	}

	public void hit(int damage) {
		if (flinching)
			return;
		// JukeBox.load("/SFX/playerhit.mp3", "playerhit");
		// Clip c = clips.get(s);
		// JukeBox.play("playerhit", 2);
		stop();
		health -= damage;
		if (health < 0)
			health = 0;
		flinching = true;
		flinchCount = 0;
		if (facingRight)
			dx = -1;
		else
			dx = 1;
		dy = -3;
		knockback = true;
		falling = true;
		jumping = false;
	}

	public void reset() {
		health = maxHealth;
		facingRight = true;
		currentAction = -1;
		stop();
	}

	public void stop() {
		left = right = up = down = flinching = dashing = jumping = attacking = upattacking = charging = false;
	}

	public void getNextPosition() {

		if (knockback) {
			dy += fallSpeed * 2;
			knockback = !falling;
			return;
		}

		movement();

		jumpAndFall();

	}

	private void jumpAndFall() {
		// jumping
		if (jumping && !falling) {
			dy = jumpStart;
			falling = true;
			JukeBox.play(PLAYERJUMP_MUSIC_NAME);
		}

		if (doubleJump) {
			dy = doubleJumpStart;
			alreadyDoubleJump = true;
			doubleJump = false;
			JukeBox.play(PLAYERJUMP_MUSIC_NAME);
			for (int i = 0; i < 6; i++) {
				energyParticles.add(new EnergyParticle(tileMap, x, y + cheight / 4.0, EnergyParticle.ENERGY_DOWN));
			}
		}

		if (!falling)
			alreadyDoubleJump = false;

		// falling
		if (falling) {
			dy += fallSpeed;
			if (dy < 0 && !jumping)
				dy += stopJumpSpeed;
			if (dy > maxFallSpeed)
				dy = maxFallSpeed;
		}
	}

	private void movement() {
		double maxSpeed = this.maxSpeed;
		if (dashing)
			maxSpeed *= 1.75;

		// movement
		if (left) {
			dx = Math.max(-maxSpeed, dx - moveSpeed);
		} else if (right) {
			dx = Math.min(maxSpeed, dx + moveSpeed);
		} else {
			if (dx >= 0) {
				dx = Math.max(0, dx - stopSpeed);
			} else {
				dx = Math.min(0, dx + stopSpeed);
			}
		}

		// cannot move while attacking, except in air
		if ((attacking || upattacking || charging) && !(jumping || falling)) {
			dx = 0;
		}

		// charging
		if (charging) {
			chargingTick++;
			if (facingRight)
				dx = moveSpeed * (3 - chargingTick * 0.07);
			else
				dx = -moveSpeed * (3 - chargingTick * 0.07);
		}
	}

	private void setAnimation(int i) {
		if (currentAction != i) {
			currentAction = i;
			animation.setFrames(sprites.get(currentAction));
			animation.setDelay(SPRITEDELAYS[currentAction]);
			width = FRAMEWIDTHS[currentAction];
			height = FRAMEHEIGHTS[currentAction];
		}
	}

	public void update() {

		time++;

		// check teleporting
		if (teleporting) {
			energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.ENERGY_UP));
		}

		// update position
		boolean isFalling = falling;
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		if (isFalling && !falling) {
			JukeBox.play("playerlands");
		}
		if (dx == 0)
			x = (int) x;

		checkAttack();

		checkEnemyInteraction();

		checkAnimations();

		animation.update();

		// set direction
		if (!attacking && !upattacking && !charging && !knockback) {
			if (right)
				facingRight = true;
			if (left)
				facingRight = false;
		}

	}

	private void checkAttack() {
		// check done flinching
		if (flinching) {
			flinchCount++;
			flinching = flinchCount <= 120;
		}

		// energy particles
		ArrayList<EnergyParticle> particlesToRemove = new ArrayList<>();
		for (int i = 0; i < energyParticles.size(); i++) {
			energyParticles.get(i).update();
			if (energyParticles.get(i).shouldRemove()) {
				particlesToRemove.add(energyParticles.get(i));
			}
		}

		for (EnergyParticle e : particlesToRemove) {
			energyParticles.remove(e);
		}

		// check attack finished
		if ((currentAction == ATTACKING_ANIM || currentAction == UPATTACKING_ANIM) && animation.hasPlayedOnce()) {
			attacking = false;
			upattacking = false;
		}
		if (currentAction == CHARGING_ANIM) {
			if (animation.hasPlayed(5)) {
				charging = false;
			}
			cr.y = (int) y - 20;
			if (facingRight) {
				cr.x = (int) x - 15;
				energyParticles.add(new EnergyParticle(tileMap, x + 30, y, EnergyParticle.ENERGY_RIGHT));
			} else {
				cr.x = (int) x - 35;
				energyParticles.add(new EnergyParticle(tileMap, x - 30, y, EnergyParticle.ENERGY_LEFT));
			}

		}
	}

	private void checkEnemyInteraction() {
		// check enemy interaction
		for (int i = 0; i < enemies.size(); i++) {

			Enemy e = enemies.get(i);

			// check attack
			if (currentAction == ATTACKING_ANIM && animation.getFrame() == 3 && animation.getCount() == 0
					&& e.intersects(ar)) {
				e.hit(damage);
			}

			// check upward attack
			if (currentAction == UPATTACKING_ANIM && animation.getFrame() == 3 && animation.getCount() == 0
					&& e.intersects(aur)) {
				e.hit(damage);
			}

			// check charging attack
			if (currentAction == CHARGING_ANIM && animation.getCount() == 0 && e.intersects(cr)) {
				e.hit(chargeDamage);
			}

			// collision with enemy
			if (!e.isDead() && intersects(e) && !charging) {
				hit(e.getDamage());
			}

			if (e.isDead()) {
				JukeBox.play("explode", 2000);
			}

		}
	}

	private void checkAnimations() {
		// set animation, ordered by priority
		if (teleporting) {
			setAnimation(TELEPORTING_ANIM);
		} else if (knockback) {
			setAnimation(KNOCKBACK_ANIM);
		} else if (health == 0) {
			setAnimation(DEAD_ANIM);
		} else if (upattacking) {
			checkUpAttackingAnim();
		} else if (attacking) {
			checkAttackingAnim();
		} else if (charging) {
			setAnimation(CHARGING_ANIM);
		} else if (dy < 0) {
			setAnimation(JUMPING_ANIM);
		} else if (dy > 0) {
			setAnimation(FALLING_ANIM);
		} else if (dashing && (left || right)) {
			setAnimation(DASHING_ANIM);
		} else if (left || right) {
			setAnimation(WALKING_ANIM);
		} else {
			setAnimation(IDLE_ANIM);
		}
	}

	private void checkAttackingAnim() {
		if (currentAction != ATTACKING_ANIM) {
			JukeBox.play(PLAYERATTACK_MUSIC_NAME);
			setAnimation(ATTACKING_ANIM);
			ar.y = (int) y - 6;
			ar.x = facingRight ? (int) x + 10 : (int) x - 40;
		} else {
			if (animation.getFrame() == 4 && animation.getCount() == 0) {
				for (int c = 0; c < 3; c++) {
					if (facingRight)
						energyParticles.add(new EnergyParticle(tileMap, ar.x + ar.width - 4, ar.y + ar.height / 2,
								EnergyParticle.ENERGY_RIGHT));
					else
						energyParticles.add(new EnergyParticle(tileMap, ar.x + 4, ar.y + ar.height / 2,
								EnergyParticle.ENERGY_LEFT));
				}
			}
		}
	}

	private void checkUpAttackingAnim() {
		if (currentAction != UPATTACKING_ANIM) {
			JukeBox.play(PLAYERATTACK_MUSIC_NAME);
			setAnimation(UPATTACKING_ANIM);
			aur.x = (int) x - 15;
			aur.y = (int) y - 50;
		} else {
			if (animation.getFrame() == 4 && animation.getCount() == 0) {
				for (int c = 0; c < 3; c++) {
					energyParticles.add(
							new EnergyParticle(tileMap, aur.x + aur.width / 2, aur.y + 5, EnergyParticle.ENERGY_UP));
				}
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {

		// draw emote
		if (emote == CONFUSED_EMOTE) {
			g.drawImage(confused, (int) (x + xmap - cwidth / 2.0), (int) (y + ymap - 40), null);
		} else if (emote == SURPRISED_EMOTE) {
			g.drawImage(surprised, (int) (x + xmap - cwidth / 2.0), (int) (y + ymap - 40), null);
		}

		// draw energy particles
		for (int i = 0; i < energyParticles.size(); i++) {
			energyParticles.get(i).draw(g);
		}

		// flinch
		if (flinching && !knockback && flinchCount % 10 < 5) {
			return;
		}

		super.draw(g);

	}
}