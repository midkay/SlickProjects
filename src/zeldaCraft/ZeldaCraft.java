package zeldaCraft;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class ZeldaCraft extends BasicGame {
	
	public static TiledMap map;
	public static int tileSize = 32;
	
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 576;
	
	public float worldX = SCREEN_WIDTH / 2;
	public float worldY = SCREEN_HEIGHT / 2;
	
	public static boolean[][] collideTiles;
	
	private static PlayerZelda p1;
	private static Image player = null;
	public final float playerSpeed = (float) 0.2;
	
	public ZeldaCraft() {
		super("A minecraft, minicraft, zelda ripoff (sorry mojang/nintendo plz no sue)");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		map = new TiledMap ("res/zeldaCraft/level.tmx", "res/zeldaCraft/");
		
		player = new Image ("res/zeldaCraft/link_front.gif");
		p1 = new PlayerZelda (map.getWidth() * tileSize / 2, map.getHeight() * tileSize / 2);		
		collideTiles = new boolean[map.getWidth()][map.getHeight()];
		
		for(int i=0; i<map.getWidth(); i++) {
			for(int j=0; j<map.getHeight(); j++) {
				int tileID = map.getTileId (i, j, 0);
	
				collideTiles[i][j] = "true".equals (map.getTileProperty (tileID, "blocked", "false"));
				
				if("true".equals (map.getTileProperty (tileID, "spawn", "false")))
					p1.setPos(i*tileSize, j*tileSize);
			}
		}
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
		
		// WORLD SCROLLING CODE
		// move the world view left if player's global x coordinate nears the right edge
		worldX = (float) (SCREEN_WIDTH/2 - p1.playerX);
		worldY = (float) (SCREEN_HEIGHT/2 - p1.playerY);
		
		p1.tick();
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		map.render ((int) worldX, (int) worldY);
		
		// draw overlays (text etc)
		g.drawString("ZeldaCraft v.CHoDE", 10, 30);
		
		// player coords
		g.drawString("playerX: " + p1.posX(), 10, 50);
		g.drawString("playerY: " + p1.posY(), 10, 70);
		
		player.draw (SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
	}	
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new ZeldaCraft());

		app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
		app.start();
	}

}
