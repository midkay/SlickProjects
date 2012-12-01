package zeldaCraft;

import java.util.*;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class BlockMap extends ZeldaCraft {
	
	public static TiledMap tmap;
	public static int mapWidth;
	public static int mapHeight;
	
	//public static ArrayList<Object> entities;
	public static Map<Integer, Block> entities;
	
	//square shaped tile
	//Coords indicate top left, top right, bottom right, and bottom left respectively
	private int square[] = {0,0, 32,0, 32,32, 0,32};
	
	public BlockMap (String mapData) throws SlickException {
		//entities = new ArrayList<Object>();
		entities = new HashMap<Integer, Block>();
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
					//entities.add (new Block (x * 32, y * 32, square, "square"));
					int temp = x * 32 * 10000;
					temp +=  y * 32;
					
					System.out.println(temp);
					entities.put(temp, new Block (x * 32, y * 32, square, "square"));

					//System.out.println(x * 32 + " " + y * 32);
				}
				
			}
		}
		
	}

}
