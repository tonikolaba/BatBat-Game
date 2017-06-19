package al.artofsoul.BatBatGame.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import al.artofsoul.BatBatGame.TileMap.TileMap;

/**
 * @author ArtOfSoul
 *
 */

public class Portal extends MapObject {
	
	private BufferedImage[] closedSprites;
	private BufferedImage[] openingSprites;
	private BufferedImage[] openedSprites;
	
	private boolean opened;
	private boolean opening;
	
	public Portal(TileMap tm) {
		
		super(tm);
		
		width = 81;
		height = 111;
		
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream("/Sprites/Other/Portal.gif")
			);
			
			closedSprites = new BufferedImage[1];
			closedSprites[0] = spritesheet.getSubimage(0, 0, width, height);
			
			openingSprites = new BufferedImage[6];
			for(int i = 0; i < openingSprites.length; i++) {
				openingSprites[i] = spritesheet.getSubimage(
					i * width, height, width, height
				);
			}
			
			openedSprites = new BufferedImage[3];
			for(int i = 0; i < openedSprites.length; i++) {
				openedSprites[i] = spritesheet.getSubimage(
					i * width, 2 * height, width, height
				);
			}
			
			animation.setFrames(closedSprites);
			animation.setDelay(-1);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setClosed() {
		animation.setFrames(closedSprites);
		animation.setDelay(-1);
		opened = false;
	}
	
	public void setOpening() {
		opening = true;
		animation.setFrames(openingSprites);
		animation.setDelay(2);
	}
	
	public void setOpened() {
		if(opened) return;
		animation.setFrames(openedSprites);
		animation.setDelay(2);
		opened = true;
	}
	
	public boolean isOpened() { return opened; }
	
	public void update() {
		animation.update();
		if(opening && animation.hasPlayedOnce()) {
			opened = true;
			animation.setFrames(openedSprites);
			animation.setDelay(2);
		}
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
}