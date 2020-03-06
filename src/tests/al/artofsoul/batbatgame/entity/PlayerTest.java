package al.artofsoul.batbatgame.entity;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void TestSetAttacking() {
        al.artofsoul.batbatgame.tilemap.TileMap tm = new al.artofsoul.batbatgame.tilemap.TileMap(30);
        al.artofsoul.batbatgame.entity.Player play = new al.artofsoul.batbatgame.entity.Player(tm);
        assertNotNull(play);
        //case 1:
        play.attacking = false;
        play.upattacking = false;
        play.setAttacking();
        assertEquals(true, play.attacking);

        assertTrue(play.attacking);
        //case 2 :
        al.artofsoul.batbatgame.entity.Player play1 = new al.artofsoul.batbatgame.entity.Player(tm);
        play1.stop();//call stop here
        play.setAttacking();
        assertEquals(play1.attacking,false);
        //case 3:
        al.artofsoul.batbatgame.entity.Player play2 = new al.artofsoul.batbatgame.entity.Player(tm);
        play2.hit(6);
        play2.setAttacking();
        assertEquals(play2.attacking,false);
        //case 4:
        al.artofsoul.batbatgame.entity.Player play3 = new al.artofsoul.batbatgame.entity.Player(tm);
        play3.getNextPosition();
        play3.setAttacking();
        assertEquals(play3.attacking,true);
        //case 5:
        al.artofsoul.batbatgame.entity.Player play4 = new al.artofsoul.batbatgame.entity.Player(tm);
        play4.hit(5);
        play4.getNextPosition();
        play4.setAttacking();
        assertEquals(play4.attacking,true);
    }

    @Test
    public void TestGetTimeToString() {
        al.artofsoul.batbatgame.tilemap.TileMap tm = new al.artofsoul.batbatgame.tilemap.TileMap(30);
        al.artofsoul.batbatgame.entity.Player play6 = new al.artofsoul.batbatgame.entity.Player(tm);
        al.artofsoul.batbatgame.entity.Player play7 = new al.artofsoul.batbatgame.entity.Player(tm);
        al.artofsoul.batbatgame.entity.Player play8 = new al.artofsoul.batbatgame.entity.Player(tm);
        al.artofsoul.batbatgame.entity.Player play9 = new al.artofsoul.batbatgame.entity.Player(tm);
        al.artofsoul.batbatgame.entity.Player play10 = new al.artofsoul.batbatgame.entity.Player(tm);
        assertNotNull(play6);
        //case1:
        play6.setTime(7200);
        play6.getTimeToString();
        assertEquals("2:00",play6.getTimeToString());
        //case2:
        play6.setTime(0);
        play6.getTimeToString();
        assertEquals("0:00",play6.getTimeToString());
        //case3:
        play6.setTime(-7200);
        play6.getTimeToString();
        assertEquals("-2:00",play6.getTimeToString());
        //case4:
        play6.setTime(3600/10);
        play6.getTimeToString();
        assertEquals("0:06",play6.getTimeToString());
        case5:
        play6.setTime(9/0);
        play6.getTimeToString();
        assertEquals("0:00",play6.getTimeToString());
    }

}