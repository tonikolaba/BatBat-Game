package al.tonikolaba.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

/**
 * @author N.Kolaba
 *
 */

@RunWith(JUnitPlatform.class)
@DisplayName("Player Save")
public class PlayerSaveTest {

	@Test
	public void PlayerSave() {
		PlayerSave ps = new PlayerSave();
		PlayerSave ps1 = new PlayerSave();
		PlayerSave ps2 = new PlayerSave();
		PlayerSave ps3 = new PlayerSave();
		PlayerSave ps4 = new PlayerSave();

		assertNotNull(ps);
		assertNotNull(ps1);
		assertNotNull(ps2);
		assertNotNull(ps3);
		assertNotNull(ps4);

	}

	@Test
	public void init() {
		PlayerSave ps = new PlayerSave();
		PlayerSave ps1 = new PlayerSave();
		assertNotNull(ps);
		assertNotNull(ps1);

		PlayerSave.setLives(3);
		int expectLives = PlayerSave.getLives();
		assertEquals(expectLives, 3);

		PlayerSave.setHealth(5);
		int expectHealth = PlayerSave.getHealth();
		assertEquals(expectHealth, 5);

		PlayerSave.setTime(0);
		long expectTime = PlayerSave.getTime();
		assertEquals(expectTime, 0);

	}

	@Test
	public void getLives() {
		PlayerSave ps = new PlayerSave();
		assertNotNull(ps);

		PlayerSave.setLives(3);
		int expectLives = PlayerSave.getLives();
		assertEquals(expectLives, 3);

		PlayerSave.setLives(2);
		int expectLives2 = PlayerSave.getLives();
		assertEquals(expectLives2, 2);

		PlayerSave.setLives(1);
		int expectLives3 = PlayerSave.getLives();
		assertEquals(expectLives3, 1);

		PlayerSave.setLives(0);
		int expectLives4 = PlayerSave.getLives();
		assertEquals(expectLives4, 0);

	}

	@Test
	public void getHealth() {
		PlayerSave ps = new PlayerSave();
		assertNotNull(ps);

		PlayerSave.setHealth(5);
		int expectHealth;
		expectHealth = PlayerSave.getHealth();
		assertEquals(expectHealth, 5);

		PlayerSave.setHealth(10);
		int expectHealth2 = PlayerSave.getHealth();
		assertEquals(expectHealth2, 10);

		PlayerSave.setHealth(15);
		int expectHealth3 = PlayerSave.getHealth();
		assertEquals(expectHealth3, 15);

		PlayerSave.setHealth(0);
		int expectHealth4 = PlayerSave.getHealth();
		assertEquals(expectHealth4, 0);

		PlayerSave.setHealth(3);
		int expectHealth5 = PlayerSave.getHealth();
		assertEquals(expectHealth5, 3);

	}

	@Test
	public void getTime() {
		PlayerSave ps = new PlayerSave();
		assertNotNull(ps);

		PlayerSave.setTime(0);
		long expectTime = PlayerSave.getTime();
		assertEquals(expectTime, 0);

		PlayerSave.setTime(1);
		long expectTime2;
		expectTime2 = PlayerSave.getTime();
		assertEquals(expectTime2, 1);

	}
}