package al.tonikolaba.entity;

/**
 * @author N.Kolaba
 *
 */

public class PlayerSave {

	private static int lives;
	private static int heath;
	private static long time;

	public PlayerSave() {
		// throw new IllegalStateException("Utility Class");
		lives = 3;
		heath = 20;
		time = 0;
	}

//	public static void init() {
//		LIVES = 3;
//		HEATH = 20;
//		TIME = 0;
//	}

	public static int getLives() {
		return lives;
	}

	public static void setLives(int lives) {
		PlayerSave.lives = lives;
	}

	public static int getHeath() {
		return heath;
	}

	public static void setHeath(int heath) {
		PlayerSave.heath = heath;
	}

	public static long getTime() {
		return time;
	}

	public static void setTime(long time) {
		PlayerSave.time = time;
	}

}
