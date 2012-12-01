package marioRipoff;

import java.io.*;
import java.util.*;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.particles.*;
import org.newdawn.slick.SlickException;

public class MarioGame extends BasicGame {

	private int[][] levelData;
	public final int levelHeight = 16;
	public final int levelCols = 35;
	public final int tileSize = 32;
	
	public final int SCREEN_WIDTH = 1280;
	public final int SCREEN_HEIGHT = 768;
	
	Image grass = null;
	Image dirt = null;
	Image stone = null;
	Image player = null;
	
	private float playerX;
	private float playerY;
	private float playerOldX;
	private float playerOldY;

	public MarioGame() {
		super("zakk's mario ripoff (sorry nintendo plz no sue)");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		levelData = new int[levelHeight][levelCols];
		grass = new Image("tex/grass.png");
		stone = new Image("tex/stone.png");
		dirt = new Image("tex/dirt.png");
		player = new Image("tex/mario.png");

		
		Scanner input = null;
		try {
			input = new Scanner(new File("level2.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// take the first levelHeight no. of rows from the input file 
		for(int i=0; i<levelHeight; i++) {
			String line = input.nextLine();
			for(int j = 0; j<line.length(); j++) {
				levelData[i][j] = (int) (line.charAt(j) - '0');
			}
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// hang onto the last values in case we move to an invalid spot
		playerOldX = playerX;
		playerOldY = playerY;
		
		Input input = gc.getInput();

		if (input.isKeyDown(Input.KEY_A)) {
			playerX -= 0.3;
		}

		if (input.isKeyDown(Input.KEY_D)) {
			playerX += 0.3;
		}

		if (input.isKeyDown(Input.KEY_W)) {
			playerY -= 1.3;
		}
		
		// ensure valid player y position
		// if(playerY < 400)
		
		// gravity: always attempt to fall the player 1px 
			playerY += 0.7;
		
		// ensure valid player x position
		if(playerX < 0)
			playerX = 0;
		if(playerX > SCREEN_WIDTH)
			playerX = SCREEN_WIDTH;
		
		if(levelData.length < (int) playerY/tileSize || levelData[(int) playerY/tileSize].length < (int) playerX/tileSize)
			init(gc);
		
		if(levelData[(int) playerY/tileSize][(int) playerX/tileSize] != 0 || playerY > 600)
			playerY = playerOldY;
		
		if(levelData[(int) playerY/tileSize][(int) playerX/tileSize] != 0)
			playerX = playerOldX;
			
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		// draw world
		for(int i = 0; i < levelHeight; i++) {
			for(int j = 0; j < levelCols; j++) {
				switch(levelData[i][j]) {
				case 0:
					// scaling shiz: Image stone2 = stone.getScaledCopy(2);
					stone.draw((j*tileSize), (i*tileSize));
					break;
				case 1:
					// Image grass2 = grass.getScaledCopy(2);
					grass.draw((j*tileSize), (i*tileSize));
					break;
				case 2:
					// Image dirt2 = dirt.getScaledCopy(2);
					dirt.draw((j*tileSize), (i*tileSize));
					break;
				}
			}
		}
		
		// draw overlays (text etc)
		g.drawString("Mario Ripoff v0.0.0.1", 10, 30);
		
		player.draw(playerX, playerY);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new MarioGame());

		app.setDisplayMode(1280, 768, false);
		app.start();
	}
}