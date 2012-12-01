package marioRipoff;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tile {

	String filename;
	Image tile;
	
	public Tile(String filename) throws SlickException {
		this.filename = filename;
		tile = new Image(filename);
	}
	
	public void draw(float x, float y) {
		tile.draw(x, y);
	}
	
	public void draw(int x, int y) {
		tile.draw(x, y);
	}
}
