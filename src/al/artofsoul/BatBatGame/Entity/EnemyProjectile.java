package al.artofsoul.BatBatGame.Entity;

import java.awt.Graphics2D;

import al.artofsoul.BatBatGame.TileMap.TileMap;

/**
 * @author ArtOfSoul
 *
 */

public abstract class EnemyProjectile extends MapObject {
	
	protected boolean hit;
	protected boolean remove;
	protected int damage;
	
	public EnemyProjectile(TileMap tm) {
		super(tm);
	}
	
	public int getDamage() { return damage; }
	public boolean shouldRemove() { return remove; }
	
	public abstract void setHit();
	
	public abstract void update();
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
}
