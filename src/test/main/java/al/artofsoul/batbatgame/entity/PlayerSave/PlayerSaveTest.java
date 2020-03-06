package main.java.al.artofsoul.batbatgame.entity.PlayerSave;



import org.junit.Test;

import static org.junit.Assert.*;


public class PlayerSaveTest {

    @Test
    public void PlayerSave(){

        al.artofsoul.batbatgame.entity.PlayerSave ps = new al.artofsoul.batbatgame.entity.PlayerSave();
        al.artofsoul.batbatgame.entity.PlayerSave ps1 = new al.artofsoul.batbatgame.entity.PlayerSave();

        al.artofsoul.batbatgame.entity.PlayerSave ps2 = new al.artofsoul.batbatgame.entity.PlayerSave();
        al.artofsoul.batbatgame.entity.PlayerSave ps3 = new al.artofsoul.batbatgame.entity.PlayerSave();
        al.artofsoul.batbatgame.entity.PlayerSave ps4 = new al.artofsoul.batbatgame.entity.PlayerSave();
        assertNotNull( ps );
        assertNotNull( ps1 );
        assertNotNull( ps2 );
        assertNotNull( ps3 );
        assertNotNull( ps4 );


    }
    @Test
    public void init() {
        al.artofsoul.batbatgame.entity.PlayerSave ps = new al.artofsoul.batbatgame.entity.PlayerSave();
        al.artofsoul.batbatgame.entity.PlayerSave ps1 = new al.artofsoul.batbatgame.entity.PlayerSave();
        assertNotNull( ps );
        assertNotNull( ps1 );

        ps.setLives(3);
        int expectLives = ps.getLives();
        assertEquals(expectLives,3 );

        ps.setHealth( 5 );
        int expectHealth = ps.getHealth();
        assertEquals(expectHealth,5 );


        ps.setTime( 0);
        long expectTime = ps.getTime();
        assertEquals(expectTime,0 );

    }

    @Test
    public void getLives() {
        al.artofsoul.batbatgame.entity.PlayerSave ps = new al.artofsoul.batbatgame.entity.PlayerSave();
        assertNotNull( ps );

        ps.setLives(3);
        int expectLives = ps.getLives();
        assertEquals(expectLives,3 );

        ps.setLives(2);
        int expectLives2 = ps.getLives();
        assertEquals(expectLives2,2 );

        ps.setLives(1);
        int expectLives3= ps.getLives();
        assertEquals(expectLives3,1 );

        ps.setLives(0);
        int expectLives4= ps.getLives();
        assertEquals(expectLives4,0 );

    }



    @Test
    public void getHealth() {
        al.artofsoul.batbatgame.entity.PlayerSave ps = new al.artofsoul.batbatgame.entity.PlayerSave();
        assertNotNull( ps );

        ps.setHealth(5);
        int expectHealth;
        expectHealth = ps.getHealth();
        assertEquals(expectHealth,5 );

        ps.setHealth(10);
        int expectHealth2 = ps.getHealth();
        assertEquals(expectHealth2,
                10 );

        ps.setHealth(15);
        int expectHealth3 = ps.getHealth();
        assertEquals(expectHealth3,15 );

        ps.setHealth(0);
        int expectHealth4 = ps.getHealth();
        assertEquals(expectHealth4,0 );

        ps.setHealth(3);
        int expectHealth5 = ps.getHealth();
        assertEquals(expectHealth5,3 );

    }


    @Test
    public void getTime() {
        al.artofsoul.batbatgame.entity.PlayerSave ps = new al.artofsoul.batbatgame.entity.PlayerSave();
        assertNotNull( ps );

        ps.setTime( 0);
        long expectTime = ps.getTime();
        assertEquals(expectTime,0 );

        ps.setTime( 1);
        long expectTime2;
        expectTime2 = ps.getTime();
        assertEquals(expectTime2,1 );



    }

}