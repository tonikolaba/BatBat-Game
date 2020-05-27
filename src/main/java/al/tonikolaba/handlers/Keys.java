package al.tonikolaba.handlers;

import java.awt.event.KeyEvent;

// this class contains a boolean array of current and previous key states
// for the 10 keys that are used for this game.
// a key k is down when keyState[k] is true.

public class Keys {

	public static final int NUM_KEYS = 16;
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	public static final int BUTTON1 = 4;
	public static final int BUTTON2 = 5;
	public static final int BUTTON3 = 6;
	public static final int BUTTON4 = 7;
	public static final int ENTER = 8;
	public static final int ESCAPE = 9;
	private static final boolean[] KEY_STATE = new boolean[NUM_KEYS];
	public static boolean[] prevKeyState = new boolean[NUM_KEYS];

	public Keys() {
		// throw new IllegalStateException("Utility Class");
	}

	public static void keySet(int i, boolean b) {
		if (i == KeyEvent.VK_UP)
			getKeyState()[UP] = b;
		else if (i == KeyEvent.VK_LEFT)
			getKeyState()[LEFT] = b;
		else if (i == KeyEvent.VK_DOWN)
			getKeyState()[DOWN] = b;
		else if (i == KeyEvent.VK_RIGHT)
			getKeyState()[RIGHT] = b;
		else if (i == KeyEvent.VK_W)
			getKeyState()[BUTTON1] = b;
		else if (i == KeyEvent.VK_E)
			getKeyState()[BUTTON2] = b;
		else if (i == KeyEvent.VK_R)
			getKeyState()[BUTTON3] = b;
		else if (i == KeyEvent.VK_F)
			getKeyState()[BUTTON4] = b;
		else if (i == KeyEvent.VK_ENTER)
			getKeyState()[ENTER] = b;
		else if (i == KeyEvent.VK_ESCAPE)
			getKeyState()[ESCAPE] = b;
	}

	public static void update() {
		for (int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = getKeyState()[i];
		}
	}

	public static boolean isPressed(int i) {
		return getKeyState()[i] && !prevKeyState[i];
	}

	public static boolean anyKeyPress() {
		for (int i = 0; i < NUM_KEYS; i++) {
			if (getKeyState()[i])
				return true;
		}
		return false;
	}

	public static boolean[] getKeyState() {
		return KEY_STATE;
	}

}
