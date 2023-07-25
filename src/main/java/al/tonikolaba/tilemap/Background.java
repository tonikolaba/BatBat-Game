package al.tonikolaba.tilemap;

import al.tonikolaba.handlers.LoggingHelper;
import al.tonikolaba.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.logging.Level;

/**
 * @author ArtOfSoul
 */

public class Background {

    private BufferedImage image;

    private double x;
    private double y;
    private double dx;
    private double dy;

    private int width;
    private int height;

    private double xscale;
    private double yscale;

    public Background(String s) {
        this(s, 0.1);
    }

    public Background(String s, double d) {
        this(s, d, d);
    }

    public Background(String s, double d1, double d2) {
        try {
            image = ImageIO.read(
                    Objects.requireNonNull(getClass().getResourceAsStream(s))
            );
            width = image.getWidth();
            height = image.getHeight();
            xscale = d1;
            yscale = d2;
        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public Background(String s, double ms, int x, int y, int w, int h) {
        try {
            image = ImageIO.read(
                    Objects.requireNonNull(getClass().getResourceAsStream(s))
            );
            image = image.getSubimage(x, y, w, h);
            width = image.getWidth();
            height = image.getHeight();
            xscale = ms;
            yscale = ms;
        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public void setPosition(double x, double y) {
        this.x = (x * xscale) % width;
        this.y = (y * yscale) % height;
    }

    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void setScale(double xscale, double yscale) {
        this.xscale = xscale;
        this.yscale = yscale;
    }

    public void setDimensions(int i1, int i2) {
        width = i1;
        height = i2;
    }

    public double getx() {
        return x;
    }

    public double gety() {
        return y;
    }

    public void update() {
        x += dx;
        while (x <= -width) x += width;
        while (x >= width) x -= width;
        y += dy;
        while (y <= -height) y += height;
        while (y >= height) y -= height;
    }

    public void draw(Graphics2D g) {

        g.drawImage(image, (int) x, (int) y, null);

        if (x < 0) {
            g.drawImage(image, (int) x + GamePanel.WIDTH, (int) y, null);
        }
        if (x > 0) {
            g.drawImage(image, (int) x - GamePanel.WIDTH, (int) y, null);
        }
        if (y < 0) {
            g.drawImage(image, (int) x, (int) y + GamePanel.HEIGHT, null);
        }
        if (y > 0) {
            g.drawImage(image, (int) x, (int) y - GamePanel.HEIGHT, null);
        }
    }

}







