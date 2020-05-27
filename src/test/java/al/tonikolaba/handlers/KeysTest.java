package al.tonikolaba.handlers;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

/**
 * @author N.Kolaba
 *
 */

@RunWith(JUnitPlatform.class)
@DisplayName("Keys")
public class KeysTest {

	@Test
	@SuppressWarnings("unused")
	public void updateTest() {
		Keys keys = new Keys();
		Keys.keySet(1, true);
		Keys.getKeyState();
		Keys.update();
		assertEquals(Keys.prevKeyState[1], false);
	}

	@Test
	@SuppressWarnings("unused")
	public void isPressedTest() {
		Keys keys = new Keys();
		Keys.keySet(1, false);
		Keys.getKeyState();
		Keys.update();
		assertEquals(Keys.isPressed(1), false);
	}
}
