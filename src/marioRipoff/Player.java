package marioRipoff;

public class Player {
	protected float playerX;
	protected float playerY;
	protected float playerOldX;
	protected float playerOldY;

	public Player() {
		this(100, 100);
	}

	public Player(int startX, int startY) {
		playerX = startX;
		playerY = startY;
		playerOldX = playerX;
		playerOldY = playerY;
	}

	public void moveX(float delta, int[][] levelData, int tileSize) {
		playerX += delta;
		
		// check left corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX, playerY, levelData, tileSize) ||
		   checkCollision(playerX, playerY+tileSize-1, levelData, tileSize)) {
			playerX = ((int) (playerX/tileSize) + 1) * tileSize;
		}
		// check right corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX+tileSize, playerY, levelData, tileSize) ||
		   checkCollision(playerX+tileSize, playerY+tileSize-1, levelData, tileSize)) {
			playerX = ((int) (playerX/tileSize)) * tileSize;
		}
	}
	
	public void moveY(float delta, int[][] levelData, int tileSize) {
		playerY += delta;
		
		// check bottom corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX+tileSize-1, playerY+tileSize-1, levelData, tileSize) ||
		   checkCollision(playerX, playerY+tileSize-1, levelData, tileSize)) {
			playerY = ((int) (playerY/tileSize)) * tileSize;
			MarioRipoff.stopJump(true);
		}
		// check top corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX+tileSize-1, playerY, levelData, tileSize) ||
		   checkCollision(playerX, playerY, levelData, tileSize)) {
			playerY = ((int) (playerY/tileSize) + 1) * tileSize;
			MarioRipoff.stopJump(false);
		}
	}
	
	// returns true if a collision is occurring at the given x,y
	public boolean checkCollision(float x, float y, int[][] levelData, int tileSize) {
		if(x < 0 || x > (levelData[0].length - 1) * tileSize)
			return true;
		
		int tileX = (int) (x / tileSize);
		int tileY = (int) (y / tileSize);
		
		return levelData[tileY][tileX] != 0;
	}

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
}