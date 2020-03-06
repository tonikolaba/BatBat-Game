package al.artofsoul.batbatgame.entity;

import al.artofsoul.batbatgame.tilemap.TileMap;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void testCreation() {
        TileMap tm = new TileMap(20 );
        Player p;
        p = new Player( tm );
        assertNotNull( p );
    }
    @Test
    public void testJumping() {
        int tileSize = 20;
        TileMap tm = new TileMap( tileSize );
        Player p;
        p = new Player( tm );
        assertNotNull( p );
        p.setJumping(true);
        boolean jumping_status = p.jumping;

        assertFalse(!jumping_status);
        assertTrue( jumping_status);

        // set stop() so that flinching = False, then knockback =  true, and jumping = false, causes no update to jumping
        p.stop(); // so p.knockback=true, p.jumping=false
        p.setJumping( true );
        assertTrue( p.jumping );
        p.setJumping( false );
        assertFalse( p.jumping );


    }

    @Test
    public void testPositionOutOfMap(){
        /*

         */
        int mapsize = 20;
        TileMap tm = new TileMap( mapsize );
        tm.setPosition(10, mapsize);
        Player p = new Player(tm);
        try{
            p.update();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void testingHealthfromdamage() {
        al.artofsoul.batbatgame.tilemap.TileMap tm = new al.artofsoul.batbatgame.tilemap.TileMap(30);
        al.artofsoul.batbatgame.entity.Player testPlayer1 = new al.artofsoul.batbatgame.entity.Player(tm);
        al.artofsoul.batbatgame.entity.Player testPlayer2 = new al.artofsoul.batbatgame.entity.Player(tm);
        al.artofsoul.batbatgame.entity.Player testPlayer3 = new al.artofsoul.batbatgame.entity.Player(tm);
        al.artofsoul.batbatgame.entity.Player testPlayer4 = new al.artofsoul.batbatgame.entity.Player(tm);
        al.artofsoul.batbatgame.entity.Player testPlayer5 = new al.artofsoul.batbatgame.entity.Player(tm);
        assertNotNull(testPlayer1);
        //player health starts at 5
        assertEquals(5, testPlayer1.getHealth());
        testPlayer1.hit(3);
        assertEquals(2, testPlayer1.getHealth());
        //test 1 - damage = 3, health should return 2
        testPlayer2.hit(0);
        assertEquals(5, testPlayer2.getHealth());
        //test 2 - damage = 0, health should return 5
        testPlayer3.hit(-1);
        assertEquals(6, testPlayer3.getHealth());
        //test 3 - damage = -1, this should not be allowed (user would be gaining a life)
        testPlayer4.hit(5000);
        assertEquals(0, testPlayer4.getHealth());
        //test 4 - damage = 5000, positive boundary checking on health, if no other problem, health = 0
        testPlayer5.hit(-5000);
        assertEquals(5005, testPlayer5.getHealth());
        //test 5 - damage = -5000, negative boundary checking on health, this should throw error
    }
    @Test
    public void checkingConditionalEffectOnChange(){
        al.artofsoul.batbatgame.tilemap.TileMap tm = new al.artofsoul.batbatgame.tilemap.TileMap(30);
        al.artofsoul.batbatgame.entity.Player chargingPlayer = new al.artofsoul.batbatgame.entity.Player(tm);
        assertNotNull(chargingPlayer);
        //Regularly the chargingTick should be 0 if, attacking, upattacking, and charging are set to true -PASSED
        chargingPlayer.attacking = true;
        chargingPlayer.upattacking = true;
        chargingPlayer.charging = true;
        chargingPlayer.setCharging();
        assertEquals(0, chargingPlayer.chargingTick);
        //if the charge is set to 5 before this function, it should be set to 0 after it enters the second condition -FAILED (actual 5)
        chargingPlayer.chargingTick = 5;
        chargingPlayer.setCharging();
        assertEquals(0, chargingPlayer.chargingTick);
        //second if condition should be false therefore charging should stay the same - PASSED
        chargingPlayer.charging = false;
        chargingPlayer.chargingTick = 1;
        chargingPlayer.setCharging();
        assertEquals(1,chargingPlayer.chargingTick);
    }
    @Test
    public void checkingBooleanInSetDashing(){
        al.artofsoul.batbatgame.tilemap.TileMap tm = new al.artofsoul.batbatgame.tilemap.TileMap(30);
        al.artofsoul.batbatgame.entity.Player dashingPlayer = new al.artofsoul.batbatgame.entity.Player(tm);
        //test 1
        dashingPlayer.falling = false;
        dashingPlayer.setDashing(true);
        assertEquals(false, dashingPlayer.dashing);
        //test 2
        dashingPlayer.falling = true;
        dashingPlayer.setDashing(false);
        assertEquals(true, dashingPlayer.dashing);
        //test 3
        dashingPlayer.falling = true;
        dashingPlayer.setDashing(true);
        assertEquals(true, dashingPlayer.dashing);
    }
    @Test
    public void TestSetAttacking() {
        al.artofsoul.batbatgame.tilemap.TileMap tm = new al.artofsoul.batbatgame.tilemap.TileMap(30);
        al.artofsoul.batbatgame.entity.Player play = new Player(tm);
        assertNotNull(play);
        //case 1:
        play.attacking = false;
        play.upattacking = false;
        play.setAttacking();
        assertEquals(true, play.attacking);
        assertTrue(play.attacking);
        //case 2 :
        al.artofsoul.batbatgame.entity.Player play1 = new Player(tm);
        play1.stop();//call stop here
        play.setAttacking();
        assertEquals(play1.attacking,false);
        //case 3:
        al.artofsoul.batbatgame.entity.Player play2 = new Player(tm);
        play2.hit(6);
        play2.setAttacking();
        assertEquals(play2.attacking,false);
        //case 4:
        al.artofsoul.batbatgame.entity.Player play3 = new Player(tm);
        play3.getNextPosition();
        play3.setAttacking();
        assertEquals(play3.attacking,true);
        //case 5:
        al.artofsoul.batbatgame.entity.Player play4 = new Player(tm);
        play4.hit(5);
        play4.getNextPosition();
        play4.setAttacking();
        assertEquals(play4.attacking,true);
    }

    @Test
    public void TestSetAttacking1() {
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
    public void TestGetTimeToString2() {
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
        play6.setTime(9/4);
        play6.getTimeToString();
        assertEquals("0:00",play6.getTimeToString());
    }

}