package marioRipoff;

import java.io.*;
import java.util.*;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class MarioRipoff extends BasicGame {

	private int[][] levelData;
	public final int levelHeight = 22;
	public final int levelCols = 74;
	public final int tileSize = 32;
	public final float playerSpeed = (float) 0.15;
	public final float gravSpeed = (float) 0.2;
	
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 704;
	
	Image grass = null;
	Image dirt = null;
	Image dirtyGrass = null;
	Image stone = null;
	Image cobble = null;
	Image spikes = null;
	Image arrowRight = null;
	Image player = null;
	
	private float worldX;
	
	private boolean facingRight;
	
	private Player p1;

	public MarioRipoff() {
		super("zakk's mario ripoff (sorry nintendo plz no sue)");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		levelData = new int[levelHeight][levelCols];
		grass = new Image("res/mario/grass.png");
		stone = new Image("res/mario/stone.png");
		dirt = new Image("res/mario/dirt.png");
		cobble = new Image("res/mario/cobble.png");
		dirtyGrass = new Image("res/mario/dirty_grass.png");
		spikes = new Image("res/mario/spikes.png");
		arrowRight = new Image("res/mario/arrow_right.png");
		player = new Image("res/mario/mario_debug.png");
		p1 = new Player(SCREEN_WIDTH/3, 100);

		worldX = 0;
		facingRight = true;
		
		Scanner input = null;
		try {
			input = new Scanner(new File("res/mario/level.txt"));
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
		
		// gravity: always attempt to fall the player 1px 
		p1.moveY(gravSpeed * delta, levelData, tileSize);

		if (input.isKeyDown(Input.KEY_A)) {
			p1.moveX(-playerSpeed * delta, levelData, tileSize);
			facingRight = false;
		}

		if (input.isKeyDown(Input.KEY_D)) {
			p1.moveX(playerSpeed * delta, levelData, tileSize);
			facingRight = true;
		}

		if (input.isKeyDown(Input.KEY_W)) {
			p1.moveY(-(playerSpeed + gravSpeed) * delta, levelData, tileSize);
		}
		
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			System.exit(0);
		}

		// ensure valid player x position
		if(p1.playerX < 0)
			p1.playerX = 0;
		if(p1.playerX > levelCols*tileSize)
			p1.playerX = levelCols*tileSize;
		
		if(p1.playerY < 0)
			p1.playerY = 0;
		if(p1.playerY > 600)
			p1.playerY = 600;
		
		// horizontal scrolling code
//		if(p1.playerX - worldX > (.8 * SCREEN_WIDTH) || p1.playerX - worldX < (.2 * SCREEN_WIDTH)) {
//			worldX = p1.playerX;
//			if(worldX < 0)
//				worldX = 0;
//			p1.playerX = p1.playerOldX;
//		}
		
		if(p1.playerX - worldX > (.8 * SCREEN_WIDTH))
			worldX = (float) (p1.playerX - (.8 * SCREEN_WIDTH));
		
		if(p1.playerX - worldX < (.2 * SCREEN_WIDTH))
			worldX = (float) (p1.playerX - (.2 * SCREEN_WIDTH));
		
		if(worldX < 0)
			worldX = 0;

		if(p1.playerY > SCREEN_HEIGHT - tileSize)
			p1.playerY = SCREEN_HEIGHT;
		
		p1.tick();
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		// draw world
		for(int i = 0; i < levelHeight; i++) {
			for(int j = (int) (worldX / tileSize); j <= (int) (worldX / tileSize + SCREEN_WIDTH / tileSize); j++) {
				int x = (int) ((j*tileSize)-worldX);
				int y = (i*tileSize);
				switch(levelData[i][j]) {
				case 0:
					// scaling shiz: Image stone2 = stone.getScaledCopy(2);
					stone.draw(x, y);
					break;
				case 1:
					grass.draw(x, y);
					break;
				case 2:
					dirt.draw(x, y);
					break;
				case 3:
					cobble.draw(x, y);
					break;
				case 4:
					arrowRight.draw(x, y);
					break;
				case 5:
					dirtyGrass.draw(x, y);
					break;
				case 6:
					spikes.draw(x, y);
					break;
				}
			}
		}
		
		// draw overlays (text etc)
		g.drawString("Mario Ripoff v0.0.0.2", 10, 30);
		
		if(facingRight)
			player.draw((int) (p1.posX() - worldX), (int) p1.posY());
		else {
			Image player2 = player.getFlippedCopy(true, false);
			player2.draw((int) (p1.posX() - worldX), (int) p1.posY());
		}
		
		// player coords (debug)
		g.drawString("playerX: " + p1.playerX, 10, 50);
		g.drawString("playerY: " + p1.playerY, 10, 70);
		g.drawString("worldX: " + worldX + "  % tileSize = " + worldX % tileSize, 10, 90);
		
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new MarioRipoff());

		app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
		app.start();
	}
}