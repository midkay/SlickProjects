package zeldaCraft;

import org.newdawn.slick.Animation;
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
	public static boolean[][] collideTiles;
	
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 576;
	public float worldX;
	public float worldY;

	private  PlayerZelda player;
	public final float playerSpeed = (float) 0.2;
	
	Animation link, movingUp, movingLeft, movingDown, movingRight;
	public static int[] animationDuration = {200, 200};

	
	public ZeldaCraft() {
		super("A minecraft, minicraft, zelda ripoff (sorry mojang/nintendo plz no sue)");
	}

	
	@Override
	public void init(GameContainer container) throws SlickException {
		//start animation init
		Image[] moveUp = {new Image ("res/zeldaCraft/linkBackOne.png"), new Image ("res/zeldaCraft/linkBackTwo.png")};
		Image[] moveLeft = {new Image ("res/zeldaCraft/linkLeftOne.png"), new Image ("res/zeldaCraft/linkLeftTwo.png")};
		Image[] moveDown = {new Image ("res/zeldaCraft/linkFrontOne.png"), new Image ("res/zeldaCraft/linkFrontTwo.png")};
		Image[] moveRight = {new Image ("res/zeldaCraft/linkRightOne.png"), new Image ("res/zeldaCraft/linkRightTwo.png")};
		
		movingUp = new Animation (moveUp, animationDuration, false);
		movingLeft = new Animation (moveLeft, animationDuration, false);
		movingDown = new Animation (moveDown, animationDuration, false);
		movingRight = new Animation (moveRight, animationDuration, false);
		
		link = movingUp;
		//end animation
		
		//start world init
		worldY = SCREEN_HEIGHT / 2;
		worldX = SCREEN_WIDTH / 2;
		
		map = new TiledMap("res/zeldaCraft/level.tmx", "res/zeldaCraft");
		collideTiles = new boolean[map.getWidth()][map.getHeight()];
		player = new PlayerZelda (map.getWidth() * tileSize / 2, map.getHeight() * tileSize / 2);

		//solidity
		for (int i = 0; i < map.getWidth(); i++) {
			for (int j = 0; j < map.getHeight(); j++) {
				int tileID = map.getTileId(i, j, 0);

				collideTiles[i][j] = "true".equals (map.getTileProperty(tileID,
						"blocked", "false"));

				if ("true".equals (map.getTileProperty (tileID, "spawn", "false")))
					player.setPos(i * tileSize, j * tileSize);
			}
		}
		//end world
	}

	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		// start key input
		if (input.isKeyDown(Input.KEY_W)) {
			link = movingUp;
			player.moveY (-playerSpeed * delta);
		}

		if (input.isKeyDown(Input.KEY_A)) {
			link = movingLeft;
			player.moveX (-playerSpeed * delta);
		}

		if (input.isKeyDown(Input.KEY_S)) {
			link = movingDown;
			player.moveY (playerSpeed * delta);
		}

		if (input.isKeyDown(Input.KEY_D)) {
			link = movingRight;
			player.moveX (playerSpeed * delta);
		}

		if (input.isKeyDown(Input.KEY_ESCAPE))
			System.exit(0);
		// end key input

		// WORLD SCROLLING CODE
		// move the world view left if player's global x coordinate nears the right edge
		worldX = (float) (SCREEN_WIDTH / 2 - player.playerX);
		worldY = (float) (SCREEN_HEIGHT / 2 - player.playerY);

		player.tick();
}

	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		//render the map efficiently -- draw only the tiles we need drawn
		//warning: this is confusing!!
		map.render (-tileSize + (int) (worldX % tileSize), -tileSize
				+ (int) (worldY % tileSize), (int) (-worldX / tileSize) - 1,
					(int) (-worldY / tileSize) - 1, SCREEN_WIDTH / tileSize + 2,
						SCREEN_HEIGHT / tileSize + 2);

		//draw overlay/title
		g.drawString ("ZeldaCraft v.CHoDE", 10, 30);

		//player coords
		g.drawString ("playerX: " + player.posX(), 10, 50);
		g.drawString ("playerY: " + player.posY(), 10, 70);

		link.draw (player.posX(), player.posY());
	}

	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer (new ZeldaCraft());

		app.setDisplayMode (SCREEN_WIDTH, SCREEN_HEIGHT, false);
		app.start();
	}

}
