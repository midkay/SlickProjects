package marioCraft;

import java.io.*;
import java.util.*;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class MarioCraft extends BasicGame {

	protected static int[][] levelData;
	protected static final int levelHeight = 22;
	protected static final int levelCols = 74;
	protected static final int tileSize = 32;
	protected final float playerSpeed = (float) 0.15;
	protected final float gravSpeed = (float) 0.2;
	protected final String resLocation = "res/mario/";
	protected final String[] tileNames = {"stone.png", "grass.png", "dirt.png", "cobble.png", "arrow_right.png", "dirty_grass.png", "spikes.png", "obsidian.png"};
	
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 704;

	Image playerLeft;
	Image playerRight;
	
	Image[] tiles;
	
	private float worldX;
	
	private boolean facingRight;
	private static boolean jumping;
	private static float jumpSpeed;
	
	private PlayerMario p1;

	public MarioCraft() throws SlickException {
		super("zakk's mario ripoff (sorry nintendo plz no sue)");


	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		levelData = new int[levelHeight][levelCols];
		p1 = new PlayerMario(SCREEN_WIDTH/3, 100);
		
		playerLeft = new Image("res/mario/mario.png");
		playerRight = playerLeft.getFlippedCopy(true, false);
		
		tiles = new Image[tileNames.length];
		for(int i = 0; i < tileNames.length; i++) {
			tiles[i] = new Image(resLocation + tileNames[i]);
		}

		worldX = 0;
		facingRight = true;
		jumping = false;
		jumpSpeed = 0;
		
		Scanner input = null;
		try {
			input = new Scanner(new File(resLocation + "level.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// take the first levelHeight no. of rows from the input file 
		for(int i = 0; i < levelHeight; i++) {
			String line = input.nextLine();
			for(int j = 0; j<line.length(); j++) {
				levelData[i][j] = (int) (line.charAt(j) - '0');
			}
		}
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
			p1.moveY(-jumpSpeed * delta); // move up if jumping
			jumpSpeed *= 0.997; // scale down jumping speed				
		}
		
		// ensure we haven't walked off the map in all this mayhem
		p1.validateCoords();
		
		// WORLD SCROLLING CODE
		// move the world view left if player's global x coordinate nears the right edge
		if(p1.playerX - worldX > (.8 * SCREEN_WIDTH))
			worldX = (float) (p1.playerX - (.8 * SCREEN_WIDTH));
		// move the world view right if player nears left edge
		if(p1.playerX - worldX < (.2 * SCREEN_WIDTH))
			worldX = (float) (p1.playerX - (.2 * SCREEN_WIDTH));
		// make sure we haven't gone too far in either direction (into null space)
		if(worldX < 0)
			worldX = 0;
		if(worldX > (levelCols - 1) * tileSize - SCREEN_WIDTH)
			worldX = (levelCols - 1) * tileSize - SCREEN_WIDTH;

		// if we're about to fall off the screen... don't
		if(p1.playerY > SCREEN_HEIGHT - tileSize)
			init(gc);
		
		p1.tick(); // playerOldX gets assigned current (valid) playerX/playerY values
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		// draw world
		for(int i = 0; i < levelHeight; i++) { // every row
			// draw only the currently visible world tiles. efficiency ftw
			for(int j = (int) (worldX / tileSize); j <= (int) (worldX / tileSize + SCREEN_WIDTH / tileSize); j++) {
				// coords we're gonna draw the current tile at
				int x = (int) ((j*tileSize)-worldX);
				int y = (i*tileSize);
				
				tiles[levelData[i][j]].draw(x, y);
			}
		}
		
		// draw overlays (text etc)
		g.drawString("Mario Ripoff v0.0.0.2", 10, 30);
		
		// draw mario facing the right direction
		if(facingRight)
			playerLeft.draw((int) (p1.posX() - worldX), (int) p1.posY());
		else
			playerRight.draw((int) (p1.posX() - worldX), (int) p1.posY());
		
		// player coords (debug)
		g.drawString("playerX: " + p1.playerX, 10, 50);
		g.drawString("playerY: " + p1.playerY, 10, 70);
		g.drawString("worldX: " + worldX + "  % tileSize = " + worldX % tileSize, 10, 90);
		
	}
	
	// sets jumping speed to 0 and optionally re-enables jumping ability
	public static void stopJump(boolean landed) {
		jumpSpeed = 0;
		if(landed)
			jumping = false;
	}

	// launch the friggin game
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new MarioCraft());

		app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
		app.start();
	}
}