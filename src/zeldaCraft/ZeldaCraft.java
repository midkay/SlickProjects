package zeldaCraft;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class ZeldaCraft extends BasicGame {
	
	public static TiledMap map;
	public static int mapWidth;
	public static int mapHeight;
	public static int tileSize = 32;
	
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 576;
	
	public float worldX = SCREEN_WIDTH / 2;
	public float worldY = SCREEN_HEIGHT / 2;
	
	private PlayerZelda p1;
	Image player = null;
	public final float playerSpeed = (float) 0.2;
	
	public ZeldaCraft() {
		super("A minecraft, minicraft, zelda ripoff (sorry mojang/nintendo plz no sue)");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		map = new TiledMap ("res/zeldaCraft/level.tmx", "res/zeldaCraft/");
		mapWidth = map.getWidth() * map.getTileWidth();
		mapHeight = map.getHeight() * map.getTileHeight();
		
		player = new Image ("res/zeldaCraft/link_front.gif");
		p1 = new PlayerZelda (SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		//start key input
		if (input.isKeyDown(Input.KEY_W)) {
			player = new Image("res/zeldaCraft/link_back.gif");
			
			p1.moveY (-playerSpeed * delta);
		}
		
		if (input.isKeyDown(Input.KEY_A)) {
			player = new Image("res/zeldaCraft/link_lside.gif");
			
			p1.moveX (-playerSpeed * delta);
		}
		
		if (input.isKeyDown(Input.KEY_S)) {
			player = new Image("res/zeldaCraft/link_front.gif");
			
			p1.moveY (playerSpeed * delta);
		}
		
		if (input.isKeyDown(Input.KEY_D)) {
			player = new Image("res/zeldaCraft/link_rside.gif");
			
			p1.moveX (playerSpeed * delta);
		}
		
		if (input.isKeyDown(Input.KEY_ESCAPE))
			System.exit(0);
		//end key input
		
		/*
		// start scrolling
		worldX += (playerX - playerOldX);
		playerX = playerOldX;
		
		worldY += (playerY - playerOldY);
		playerY = playerOldY;
		// end scrolling
		*/
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		map.render (0, 0);
		
		// draw overlays (text etc)
		g.drawString("ZeldaCraft v.CHoDE", 10, 30);
		
		// player coords
		g.drawString("playerX: " + p1.posX(), 10, 50);
		g.drawString("playerY: " + p1.posY(), 10, 70);
		
		player.draw (p1.posX(), p1.posY());
	}

}
