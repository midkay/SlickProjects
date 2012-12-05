package marioCraft;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.TiledMap;

public class MarioCraft extends BasicGame {

	protected static TiledMap map;
	protected static final int tileSize = 32;
	protected final float playerSpeed = (float) 0.15;
	protected final float gravSpeed = (float) 0.24;
	protected final String resLocation = "res/mario";
	protected static int[][] tileType;
	
	protected static final int SCREEN_WIDTH = 1280;
	protected static final int SCREEN_HEIGHT = 704;
	protected static final int BLOCK_SOLID = 1;
	protected static final int BLOCK_KILL  = -1;
	protected static boolean dead = false;

	Image playerLeft;
	Image playerRight;

	private float worldX;
	private float worldY;
	
	private boolean facingRight;
	private static boolean jumping;
	private static float jumpSpeed;
	
	private PlayerMario p1;

	public MarioCraft() throws SlickException {
		super("zakk's mario ripoff (sorry nintendo plz no sue)");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		map = new TiledMap(resLocation + "/mariocraft.tmx", resLocation);
		
		// spawn player into center of the map by default (placing a spawn point will change this)
		p1 = new PlayerMario(map.getWidth() / 2, map.getHeight() / 2);
		
		playerLeft = new Image("res/mario/mario.png");
		playerRight = playerLeft.getFlippedCopy(true, false);

		worldX = 0;
		worldY = 0;
		facingRight = true;
		jumping = false;
		jumpSpeed = 0;
		dead = false;
		
		// load collision tile properties into 2D boolean array for faster access
		tileType = new int[map.getWidth()][map.getHeight()];
		for(int layer=0; layer < map.getLayerCount(); layer++) {
			for (int i = 0; i < map.getWidth(); i++) {
				for (int j = 0; j < map.getHeight(); j++) {
					int tileID = map.getTileId(i, j, layer);
					
					// 'collide' tiles, on only the world layer, are considered solid blocks
					if(layer == 0 && "true".equals (map.getTileProperty(tileID, "collide", "false")))
						tileType[i][j] = BLOCK_SOLID;
					
					// 'kill' tiles on any layer count as deadly
					if("true".equals (map.getTileProperty(tileID, "kill", "false")))
						tileType[i][j] = BLOCK_KILL;
	
					// if we find a spawn point, set player coords there
					if ("true".equals (map.getTileProperty (tileID, "spawn", "false")))
						p1.setPos(i * tileSize, j * tileSize);
				}
			}
		}

		// initially set the world camera to be centered on the player
		worldX = (float) (p1.playerX - SCREEN_WIDTH * .2);
		worldY = (float) (p1.playerY - SCREEN_HEIGHT / 2);

	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
		
		// gravity: always attempt to fall the player an appropriate amount
		p1.moveY(gravSpeed * delta);

		if (input.isKeyDown(Input.KEY_A)) { // A = move left
			p1.moveX(-playerSpeed * delta);
			facingRight = false;
		}

		if (input.isKeyDown(Input.KEY_D)) { // D = move right
			p1.moveX(playerSpeed * delta);
			facingRight = true;
		}

		if (input.isKeyDown(Input.KEY_W)) { // W = jump
			if(!jumping) {
				jumping = true;
				jumpSpeed = (float) ((playerSpeed + gravSpeed) * 1.5);
			}
		}
		
		if (input.isKeyDown(Input.KEY_ESCAPE)) // ESC = exit the game
			System.exit(0);
		
		// jumping calculations
		if(jumping) {
			p1.moveY(-jumpSpeed * delta * (float) 1.8); // move up if jumping
			jumpSpeed *= 1 - (0.0045 * delta); // scale down jumping speed				
		}
		
		// WORLD SCROLLING CODE
		// move the world view left if player's global x coordinate nears the right edge
		if(p1.playerX - worldX > (.8 * SCREEN_WIDTH))
			worldX = (float) (p1.playerX - (.8 * SCREEN_WIDTH));
		// move the world view right if player nears left edge
		if(p1.playerX - worldX < (.2 * SCREEN_WIDTH))
			worldX = (float) (p1.playerX - (.2 * SCREEN_WIDTH));
		// move the world view down if player nears the bottom edge
		if(p1.playerY - worldY > (.7 * SCREEN_HEIGHT))
			worldY = (float) (p1.playerY - (.7 * SCREEN_HEIGHT));
		// move the world view up if player nears top edge
		if(p1.playerY - worldY < (.2 * SCREEN_HEIGHT))
			worldY = (float) (p1.playerY - (.2 * SCREEN_HEIGHT));
		
		// if we're about to fall off the screen... don't
		if(p1.playerY > map.getHeight() * tileSize - tileSize * 2)
			init(gc);
		
		if(dead) // died at some point during this update .. RIP
			init(gc);
		
		p1.tick(); // update playerOldX & Y
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		drawLayer(map.getLayerIndex("world"));    // draw the world
		drawLayer(map.getLayerIndex("underlay")); // draw any 'overlay' tiles below the player
		
		// draw mario facing the right direction
		if(facingRight)
			playerLeft.draw((int) (p1.posX() - worldX), (int) (p1.posY() - worldY));
		else
			playerRight.draw((int) (p1.posX() - worldX), (int) (p1.posY() - worldY));
		
		// draw any 'overlay' tiles above the player
		drawLayer(map.getLayerIndex("overlay"));
		
		// draw text overlays (coords, debug etc)
		g.drawString("Mario Ripoff v0.0.0.3", 10, 30);
		g.drawString("playerX: " + p1.playerX, 10, 50);
		g.drawString("playerY: " + p1.playerY, 10, 70);
		g.drawString("worldX: " + worldX, 10, 90);
		g.drawString("worldY: " + worldY, 10, 110);
	}
	
	// sets jumping speed to 0 and optionally re-allows jumping
	public static void stopJump(boolean landed) {
		jumpSpeed = 0;
		if(landed)
			jumping = false;
	}
	
	// render the given layer, efficiently -- draw only the tiles we need drawn
	// warning: this is confusing!!
	public void drawLayer(int layer) {
		map.render(-tileSize + (int) (-worldX % tileSize), -tileSize
				+ (int) (-worldY % tileSize), (int) (worldX / tileSize) - 1,
					(int) (worldY / tileSize) - 1, SCREEN_WIDTH / tileSize + 2,
						SCREEN_HEIGHT / tileSize + 2, layer, true);
	}

	// launch the friggin game
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new MarioCraft());

		app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
		app.start();
	}
}