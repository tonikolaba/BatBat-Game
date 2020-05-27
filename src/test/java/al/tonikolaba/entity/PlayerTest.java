package al.tonikolaba.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import al.tonikolaba.tilemap.TileMap;

/**
 * @author N.Kolaba
 *
 */

@RunWith(JUnitPlatform.class)
@DisplayName("Player")
public class PlayerTest {

	@Test
	// @Ignore // to disable test
	public void TestSetAttacking() {
		TileMap tm = new TileMap(30);
		Player play = new Player(tm);
		assertNotNull(play);
		// case 1:
		play.attacking = false;
		play.upattacking = false;
		play.setAttacking();
		assertEquals(true, play.attacking);
		assertTrue(play.attacking);
		// case 2 :
		Player play1 = new Player(tm);
		play1.stop(); // call stop here
		play.setAttacking();
		assertEquals(play1.attacking, false);
		// case 3:
		Player play2 = new Player(tm);
		play2.setAttacking();
		play2.hit(4);
		assertEquals(play2.attacking, false);
		// case 4:
		Player play3 = new Player(tm);
		play3.getNextPosition();
		play3.setAttacking();
		assertEquals(play3.attacking, true);
		// case 5:
		Player play4 = new Player(tm);
		play4.hit(5);
		play4.getNextPosition();
		play4.setAttacking();
		assertEquals(play4.attacking, true);

	}

	@Test
	public void TestGetTimeToString() {
		TileMap tm = new TileMap(30);
		Player play6 = new Player(tm); //
		@SuppressWarnings("unused")
		Player play7 = new Player(tm); //
		@SuppressWarnings("unused")
		Player play8 = new Player(tm); //
		@SuppressWarnings("unused")
		Player play9 = new Player(tm); //
		@SuppressWarnings("unused")
		Player play10 = new Player(tm);
		assertNotNull(play6);
		// case1:
		play6.setTime(7200);
		play6.getTimeToString();
		assertEquals("2:00", play6.getTimeToString());
		// case2:
		play6.setTime(0);
		play6.getTimeToString();
		assertEquals("0:00", play6.getTimeToString());
		// case3:
		play6.setTime(-7200);
		play6.getTimeToString();
		assertEquals("-2:00", play6.getTimeToString());
		// case4:
		play6.setTime(3600 / 10);
		play6.getTimeToString();
		assertEquals("0:06", play6.getTimeToString());
		// case5:
		// play6.setTime(9 / 0); //Test fail, Numbers can't division by zero
		// play6.getTimeToString();
		// assertEquals("0:00", play6.getTimeToString());
	}
}
