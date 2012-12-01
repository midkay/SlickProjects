package zeldaCraft;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

public class ZeldaCraft extends BasicGame {
	
	public BlockMap map;
	
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 576;
	
	
	public float worldX = SCREEN_WIDTH / 2;
	public float worldY = SCREEN_HEIGHT / 2;
	
	private PlayerZelda p1;
	Image player = null;
	private Polygon playerPoly;
	public final float playerSpeed = (float) 0.2;
	
	public ZeldaCraft() {
		super("A minecraft, minicraft, zelda ripoff (sorry mojang/nintendo plz no sue)");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		map = new BlockMap("res/zeldaCraft/level.tmx");
		player = new Image ("res/zeldaCraft/link_front.gif");
		p1 = new PlayerZelda (SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
		
		playerPoly = new Polygon(new float[]{
				p1.posX(), p1.posY(),
				p1.posX()+32, p1.posY(),
				p1.posX()+32, p1.posY()+32,
				p1.posX(), p1.posY()+32
		});
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		//start key input
		if (input.isKeyDown(Input.KEY_W)) {
			player = new Image("res/zeldaCraft/link_back.gif");
			
			p1.moveY (-playerSpeed * delta);
			playerPoly.setY (p1.posY());
			
			if (entityCollisionWith()) {
				p1.moveY (playerSpeed * delta);
				playerPoly.setY (p1.posY());
			}
		}
		
		if (input.isKeyDown(Input.KEY_A)) {
			player = new Image("res/zeldaCraft/link_lside.gif");
			
			p1.moveX (-playerSpeed * delta);
			playerPoly.setX (p1.posX());
			
			if (entityCollisionWith()) {
				p1.moveX (playerSpeed * delta);
				playerPoly.setX (p1.posX());
			}
		}
		
		if (input.isKeyDown(Input.KEY_S)) {
			player = new Image("res/zeldaCraft/link_front.gif");
			
			p1.moveY (playerSpeed * delta);
			playerPoly.setY (p1.posY());
			
			if (entityCollisionWith()) {
				p1.moveY (-playerSpeed * delta);
				playerPoly.setY (p1.posY());
			}
		}
		
		if (input.isKeyDown(Input.KEY_D)) {
			player = new Image("res/zeldaCraft/link_rside.gif");
			
			p1.moveX (playerSpeed * delta);
			playerPoly.setX (p1.posX());
			
			if (entityCollisionWith()) {
				p1.moveX (-playerSpeed * delta);
				playerPoly.setX (p1.posX());
			}
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
	
	public boolean entityCollisionWith() throws SlickException {
		for (int i = 0; i < BlockMap.entities.size(); i++) {
			Block entity1 = (Block) BlockMap.entities.get(i);
			if (playerPoly.intersects (entity1.poly))
				return true;
		}     
		return false;
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		BlockMap.tmap.render (0, 0);
		
		// draw overlays (text etc)
		g.drawString("ZeldaCraft v.CHoDE", 10, 30);
		
		
		// player coords
		g.drawString("playerX: " + p1.posX(), 10, 50);
		g.drawString("playerY: " + p1.posY(), 10, 70);
		//player polygon box
		g.draw (playerPoly);
		
		
		player.draw (p1.posX(), p1.posY());
	}

}
