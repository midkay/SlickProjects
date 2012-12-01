package zeldaCraft;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class BlockMap extends ZeldaCraft {
	
	public static TiledMap tmap;
	public static int mapWidth;
	public static int mapHeight;
	
	public static ArrayList<Object> entities;
	
	//square shaped tile
	//Coords indicate top left, top right, bottom right, and bottom left respectively
	private int square[] = {0,0, 31,0, 31,31, 0,31};
	
	public BlockMap (String mapData) throws SlickException {
		entities = new ArrayList<Object>();
		tmap = new TiledMap (mapData, "res/zeldaCraft/");
		mapWidth = tmap.getWidth() * tmap.getTileWidth();
		mapHeight = tmap.getHeight() * tmap.getTileHeight();
		
		for (int x = 0; x < tmap.getWidth(); x++) {
			for (int y = 0; y < tmap.getHeight(); y++) {
				
				//collision checking
				int tileID = tmap.getTileId (x, y, 0);
				boolean blocked = "true".equals (tmap.getTileProperty (tileID, "blocked", "false"));
				
				//if there is collision, create a poly around it
				if (blocked == true) {
					entities.add (new Block (x * 32, y * 32, square, "square"));
				}
			}
		}
		
	}

}
