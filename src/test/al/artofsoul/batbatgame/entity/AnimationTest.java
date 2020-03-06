package al.artofsoul.batbatgame.entity;

import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

public class AnimationTest {
    @Test
    public void testUpdate() {
        Animation an = new Animation();
        assertNotNull( an );
        int count = an.getCount();
        assertEquals( count,0 );
        an.update();
        count = an.getCount();
        assertEquals( count,1 );
        an.update();
        count = an.getCount();
        assertEquals( count,2 );
        an.update();
        count = an.getCount();
        assertEquals( count,3 );
        an.update();
        count = an.getCount();
        assertEquals( count,4 );
        an.update();
        count = an.getCount();
        int f = an.getFrame();
        assertEquals( count,5 );
        assertEquals( f,0 );
    }


}