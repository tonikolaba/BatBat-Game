package al.tonikolaba.gamestate;

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
@DisplayName("Game State")
public class GameStateManagerTest {

	@Test
	public void loadState() {
		// setup
		GameStateManager gm = new GameStateManager();
		// run functions
		gm.loadState(3);
		gm.loadState(4);
		gm.loadState(1);
		gm.loadState(0);
		// gm.loadState(16);
		// gm.loadState(13);

		// assert statements
		assertNotNull(gm.gameStates[3]); // expected true
		// assertNotNull(gm.gameStates[2]); // expected false
		assertNotNull(gm.gameStates[1]); // expected true
		assertNotNull(gm.gameStates[0]); // expected true
		assertNotNull(gm.gameStates[4]); // expected true
		// assertNull(gm.gameStates[16]); // expected false, because out of bound
		// assertNull(gm.gameStates[13]); // expected true, but does not represent a
		// game state
	}
}
