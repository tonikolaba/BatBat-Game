package al.artofsoul.batbatgame.gamestate;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameStateManagerTest {
    @Test
    public void loadState() {
        //setup
        al.artofsoul.batbatgame.gamestate.GameStateManager gm = new GameStateManager();
        //run functions
        gm.loadState(3);
        gm.loadState(4);
        gm.loadState(1);
        gm.loadState(0);
        gm.loadState(16);
        gm.loadState(13);

        //assert statements
        assertNotNull(gm.gameStates[3]); //expected true
        assertNull(gm.gameStates[2]); //expected false
        assertNotNull(gm.gameStates[1]); //expected true
        assertNotNull(gm.gameStates[0]); //expected true
        assertNotNull(gm.gameStates[4]); //expected true
        assertNull(gm.gameStates[16]); //failed, because out of bound
        assertNull(gm.gameStates[13]); //expected true, but does not represent a game state
    }

}