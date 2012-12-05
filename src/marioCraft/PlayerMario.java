package marioCraft;

import org.newdawn.slick.SlickException;

import marioCraft.MarioCraft;

public class PlayerMario extends MarioCraft {
	protected float playerX;
	protected float playerY;
	protected float playerOldX;
	protected float playerOldY;

	public PlayerMario() throws SlickException {
		this(100, 100);
	}

	public PlayerMario(int startX, int startY) throws SlickException {
		playerX = startX;
		playerY = startY;
		playerOldX = playerX;
		playerOldY = playerY;
	}

	public void moveX(float delta) {
		playerX += delta;
		
		// check left corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX, playerY) == BLOCK_SOLID ||
		   checkCollision(playerX, playerY+tileSize-1) == BLOCK_SOLID) {
			playerX = ((int) (playerX/tileSize) + 1) * tileSize;
		}
		// check right corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX+tileSize, playerY) == BLOCK_SOLID ||
		   checkCollision(playerX+tileSize, playerY+tileSize-1) == BLOCK_SOLID) {
			playerX = ((int) (playerX/tileSize)) * tileSize;
		}
	}
	
	public void moveY(float delta) throws SlickException {
		playerY += delta;
		
		// check bottom corners for kill blocks. restart game if we hit one
		if(checkCollision(playerX+tileSize-1, playerY+tileSize) == BLOCK_KILL ||
		   checkCollision(playerX, playerY+tileSize) == BLOCK_KILL) {
			dead = true;
			return;
		}
		
		// check bottom corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX+tileSize-1, playerY+tileSize) == BLOCK_SOLID ||
		   checkCollision(playerX, playerY+tileSize) == BLOCK_SOLID) {
			playerY = ((int) (playerY/tileSize)) * tileSize;
			stopJump(true);
		}
		// check top corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX+tileSize-1, playerY) == BLOCK_SOLID ||
		   checkCollision(playerX, playerY) == BLOCK_SOLID) {
			playerY = ((int) (playerY/tileSize) + 1) * tileSize;
			stopJump(false);
		}
	}
	
	// returns true if a collision is occurring at the given x,y
	public int checkCollision(float x, float y) {
		return tileType[(int) (x/tileSize)][(int) (y/tileSize)];
	}
	
	// playerOldX & Y get assigned current (valid) playerX & Y values
	public void tick() {
		playerOldX = playerX;
		playerOldY = playerY;
	}
	
	public float posX() {
		return playerX;
	}
	
	public float posY() {
		return playerY;
	}
	
	public void setX(float pos) {
		playerX = pos;
		playerOldX = pos;
	}
	
	public void setY(float pos) {
		playerY = pos;
		playerOldY = pos;
	}
	
	public void setPos(float x, float y) {
		setX(x);
		setY(y);
	}
}
