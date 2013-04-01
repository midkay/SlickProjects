package zeldacraft;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;


public class Map extends ZeldaCraft
{
	public static TiledMap map;
	private static String[][] tileProperty;
	private int mapWidth;
	private int mapHeight;

	
	/*
	 * Create a new map based on passed TiledMap file directory
	 */
	public Map (String mapDir) throws SlickException
	{
		map = new TiledMap (mapDir);
		mapWidth = map.getWidth() * tileSize;
	    mapHeight = map.getHeight() * tileSize;
	    
	    tileProperty = new String [map.getWidth()] [map.getHeight()];
	    
	    //fill the tileProperty 2d string array with all the tile properties in the map
  		for (int i = 0; i < map.getWidth(); i++) 
  		{
  			for (int j = 0; j < map.getHeight(); j++)
  			{
  				int tileID = map.getTileId (i, j, 0);

  				//collision property
  				if ("true".equals (map.getTileProperty(tileID, "blocked", "false")))
					tileProperty[i][j] = "blocked";
  				//spawn property
  				if ("true".equals (map.getTileProperty (tileID, "spawn", "false")))
  					tileProperty[i][j] = "spawn";
  			}
  		}
	}
	
	
	/*
	 * render method for use in rendering the map
	 */
	public void mapRender (GameContainer container, Graphics g, float cameraX, float cameraY)	
	{	
		//renders the map efficiently -- draws only a portion of the map around the player!
		//Params: x, y, sx, sy, width, height
		//x and y determine the screen location which to start rendering
		//sx and sy determine the tile location of the map to start rendering
		//width and height determine the section to render (in tiles)
		map.render (-tileSize + (int) (cameraX % tileSize), -tileSize + (int) (cameraY % tileSize), 
					(int) (-cameraX / tileSize) - 1, (int) (-cameraY / tileSize) - 1, 
					screenW / tileSize + 2, screenW / tileSize + 2);
	}
	
	
	/*
	 * returns true if a collision is occurring at the given coords
	 */
	public String collisionCheck (float x, float y)
	{
		return tileProperty [(int) (x / tileSize)] [(int) (y / tileSize)];
	}
	
	
	public int getMapWidth()
	{
		return mapWidth;
	}
	public void setMapWidth (int passedWidth) 
	{
		mapWidth = passedWidth;
	}
	
	
	public int getMapHeight()
	{
		return mapHeight;
	}
	public void setMapHeight (int passedHeight) 
	{
		mapHeight = passedHeight;
	}
	
	
	public TiledMap getTileMap()
	{
		return map;
	}
	public void setTileMap (TiledMap passedTiledMap)	
	{
		map = passedTiledMap;
	}
	
}
