package miniCraftTest;

import java.io.*;
import java.util.*;

import marioRipoff.MarioRipoff;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class MiniCraftTest extends BasicGame {
	
	private int[][] levelData;
	public final int levelHeight = 40;
	public final int levelCols = 112;
	public final int tileSize = 32;
	
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
	
	private float playerX;
	private float playerY;
	private float playerOldX;
	private float playerOldY;
	private float worldX;
	private float worldY;
	
	private boolean facingRight;

	public MiniCraftTest() {
		super("broth's minecraft, minicraft ripoff (sorry mojang plz no sue)");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		levelData = new int[levelHeight][levelCols];
		grass = new Image("res/miniCraftTest/grass.png");
		stone = new Image("res/miniCraftTest/stone.png");
		dirt = new Image("res/miniCraftTest/dirt.png");
		cobble = new Image("res/miniCraftTest/cobble.png");
		dirtyGrass = new Image("res/miniCraftTest/dirty_grass.png");
		player = new Image("res/miniCraftTest/link_front.gif");
		
		playerX = SCREEN_WIDTH / 2;
		playerY = SCREEN_HEIGHT / 2;
		playerOldX = playerX;
		playerOldY = playerY;
		worldX = 0;
		worldY = 0;
		//facingRight = true;
		
		Scanner input = null;
		try {
			input = new Scanner(new File("res/miniCraftTest/level.txt"));
		} catch (FileNotFoundException e) {
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
		
		//hang onto the most previous x/y values
		playerOldX = playerX;
		playerOldY = playerY;

		//start key input
		if (input.isKeyDown(Input.KEY_A)) {
			playerX -= 0.2 * delta;
			player = new Image("res/miniCraftTest/link_lside.gif");
		}

		if (input.isKeyDown(Input.KEY_D)) {
			playerX += 0.2 * delta;
			player = new Image("res/miniCraftTest/link_rside.gif");
		}

		if (input.isKeyDown(Input.KEY_W)) {
			playerY -= 0.2 * delta;
			player = new Image("res/miniCraftTest/link_back.gif");
		}
		
		if (input.isKeyDown(Input.KEY_S)){
			playerY += 0.2 * delta;
			player = new Image("res/miniCraftTest/link_front.gif");
		}
		
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			System.exit(0);
		}
		//end key input
		
		//ensure valid player x/y positions
		if(playerX < 0)//left side
			playerX = 0;
		
		if(playerY < 0)//top side
			playerY = 0;
		
		if(playerX > levelCols*tileSize)//right side
			playerX = levelCols*tileSize;
		
		if(playerY > SCREEN_HEIGHT - tileSize)//bottom side
			playerY = playerOldY;
		//end valid x/y
		
		// start scrolling
		worldX += (playerX - playerOldX);
		playerX = playerOldX;
		
		worldY += (playerY - playerOldY);
		playerY = playerOldY;
		// end scrolling
		
		float dx = playerX + worldX;
		
		//float dy = playerY + worldY;
		
		/*spike stuff?
		if(levelData[(int) playerY/tileSize+1][(int) dx/tileSize] == 6)
			init(gc);
		
		if(levelData[(int) dy/tileSize][(int) playerX/tileSize+1] == 6)
			init(gc);
		*/
		
		if(levelData[(int) playerY/tileSize+1][(int) dx/tileSize] != 0 ||
		   levelData[(int) playerY/tileSize+1][(int) dx/tileSize+1] != 0 ||
		   levelData[(int) playerY/tileSize][(int) dx/tileSize] != 0 ||
		   levelData[(int) playerY/tileSize][(int) dx/tileSize+1] != 0)
			playerY = playerOldY;
		
		if(levelData[(int) playerY/tileSize+1][(int) dx/tileSize] != 0 ||
		   levelData[(int) playerY/tileSize+1][(int) dx/tileSize+1] != 0 ||
	       levelData[(int) playerY/tileSize][(int) dx/tileSize] != 0 ||
		   levelData[(int) playerY/tileSize][(int) dx/tileSize+1] != 0)
			playerX = playerOldX;
		
		/*
		if(playerX < playerOldX)
			facingRight = false;
		else if(playerX > playerOldX)
			facingRight = true;
		*/
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		// draw world
		for(int i = 0; i < levelHeight; i++) {
			for(int j = 0; j < levelCols; j++) {
				int x = (int) ((j*tileSize)-worldX);
				int y = (int) ((i*tileSize)-worldY);
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
		g.drawString("MiniCraftTest v.CHoDE", 10, 30);
		
		//draw player
		player.draw((int) playerX, (int) playerY);
		
		/*
		if(facingRight)
			player.draw((int) playerX, (int) playerY);
		else {
			Image player2 = player.getFlippedCopy(true, false);
			player2.draw((int) playerX, (int) playerY);
		}
		*/
	}
	
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new MiniCraftTest());

		app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
		app.start();

	}

}