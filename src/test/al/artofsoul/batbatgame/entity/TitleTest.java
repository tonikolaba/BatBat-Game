package al.artofsoul.batbatgame.entity;

import al.artofsoul.batbatgame.handlers.LoggingHelper;
import al.artofsoul.batbatgame.tilemap.Tile;
import org.junit.Test;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.util.logging.Level;


import static org.junit.Assert.*;

public class TitleTest {

    @Test
    public void TestTitleStringTooLong(){
        String imageDir = "/Users/xiaoqinbai/Documents/GitHub/BatBat-Game/about/6.gif";

        try {
            Title t = new Title( imageDir );
        }catch (Exception e){
            System.out.println(e);
        }
//        String[] TitleStrings = {"test1", "test2", "this is a very long string so that I can check if it will work, otherwise it will give errs"};
//        for (int i = 0; i < TitleStrings.length; i++){
//            System.out.println(TitleStrings[i]);
//            try{
//                Title t = new Title( TitleStrings[i] );
//            }catch (Exception e){
//                System.out.println(e);
//            }
//        }
//        Title t = new Title( "sdfd" );
//        String ss = t.toString();
//        try {
//
//            BufferedImage image = ImageIO.read( getClass().getResourceAsStream( ss ) );
//
//        }
//        catch (Exception e) {
//            LoggingHelper.LOGGER.log( Level.SEVERE, e.getMessage());
//        }

    }

}