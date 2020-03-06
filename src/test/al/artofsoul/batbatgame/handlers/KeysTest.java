package al.artofsoul.batbatgame.handlers;

import org.junit.Test;

import static org.junit.Assert.*;

public class KeysTest {
    @Test
    public void updateTest() {
        al.artofsoul.batbatgame.handlers.Keys keys = new Keys();
        keys.keySet(1,true);
        keys.getKeyState();
        keys.update();
        assertEquals(keys.prevKeyState[1],false);
    }

    @Test
    public void isPressedTest() {
        al.artofsoul.batbatgame.handlers.Keys keys = new Keys();
        keys.keySet(1,true);
        keys.getKeyState();
        keys.update();
        assertEquals(keys.isPressed(1),false);
    }
}