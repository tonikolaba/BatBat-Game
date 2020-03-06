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

}