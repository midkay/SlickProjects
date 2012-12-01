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

	public void moveX(float delta) {
		playerX += delta;
		
		// check left corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX, playerY) ||
		   checkCollision(playerX, playerY+MarioRipoff.tileSize-1)) {
			playerX = ((int) (playerX/MarioRipoff.tileSize) + 1) * MarioRipoff.tileSize;
		}
		// check right corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX+MarioRipoff.tileSize, playerY) ||
		   checkCollision(playerX+MarioRipoff.tileSize, playerY+MarioRipoff.tileSize-1)) {
			playerX = ((int) (playerX/MarioRipoff.tileSize)) * MarioRipoff.tileSize;
		}
	}
	
	public void moveY(float delta) {
		playerY += delta;
		
		// check bottom corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX+MarioRipoff.tileSize-1, playerY+MarioRipoff.tileSize-1) ||
		   checkCollision(playerX, playerY+MarioRipoff.tileSize-1)) {
			playerY = ((int) (playerY/MarioRipoff.tileSize)) * MarioRipoff.tileSize;
			MarioRipoff.stopJump(true);
		}
		// check top corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX+MarioRipoff.tileSize-1, playerY) ||
		   checkCollision(playerX, playerY)) {
			playerY = ((int) (playerY/MarioRipoff.tileSize) + 1) * MarioRipoff.tileSize;
			MarioRipoff.stopJump(false);
		}
	}
	
	// returns true if a collision is occurring at the given x,y
	public boolean checkCollision(float x, float y) {
		if(x < 0 || x > (MarioRipoff.levelData[0].length - 1) * MarioRipoff.tileSize)
			return true;
		
		int tileX = (int) (x / MarioRipoff.tileSize);
		int tileY = (int) (y / MarioRipoff.tileSize);
		
		return MarioRipoff.levelData[tileY][tileX] != 0;
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