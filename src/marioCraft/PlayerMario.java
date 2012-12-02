package marioCraft;

public class PlayerMario {
	protected float playerX;
	protected float playerY;
	protected float playerOldX;
	protected float playerOldY;

	public PlayerMario() {
		this(100, 100);
	}

	public PlayerMario(int startX, int startY) {
		playerX = startX;
		playerY = startY;
		playerOldX = playerX;
		playerOldY = playerY;
	}

	// move the player in the X direction the specified amount, and check collisions
	public void moveX(float delta) {
		playerX += delta;
		
		// check left corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX, playerY) ||
		   checkCollision(playerX, playerY+MarioCraft.tileSize-1)) {
			playerX = ((int) (playerX/MarioCraft.tileSize) + 1) * MarioCraft.tileSize;
		}
		// check right corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX+MarioCraft.tileSize, playerY) ||
		   checkCollision(playerX+MarioCraft.tileSize, playerY+MarioCraft.tileSize-1)) {
			playerX = ((int) (playerX/MarioCraft.tileSize)) * MarioCraft.tileSize;
		}
	}

	// move the player in the Y direction the specified amount, and check collisions
	public void moveY(float delta) {
		playerY += delta;
		
		// check bottom corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX+MarioCraft.tileSize-1, playerY+MarioCraft.tileSize-1) ||
		   checkCollision(playerX, playerY+MarioCraft.tileSize-1)) {
			playerY = ((int) (playerY/MarioCraft.tileSize)) * MarioCraft.tileSize;
			MarioCraft.stopJump(true);
		}
		// check top corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX+MarioCraft.tileSize-1, playerY) ||
		   checkCollision(playerX, playerY)) {
			playerY = ((int) (playerY/MarioCraft.tileSize) + 1) * MarioCraft.tileSize;
			MarioCraft.stopJump(false);
		}
	}
	
	// returns true if a collision is occurring at the given x,y
	public boolean checkCollision(float x, float y) {
		if(x < 0 || x > (MarioCraft.levelData[0].length - 1) * MarioCraft.tileSize)
			return true;
		
		int tileX = (int) (x / MarioCraft.tileSize);
		int tileY = (int) (y / MarioCraft.tileSize);
		
		return MarioCraft.levelData[tileY][tileX] != 0;
	}
	
	// ensure we haven't gone off the edges of the screen. snap x/y to sane values if we have
	public void validateCoords() {
		// ensure valid player x position
		if(playerX < 0)
			playerX = 0;
		if(playerX > MarioCraft.levelCols*MarioCraft.tileSize)
			playerX = MarioCraft.levelCols*MarioCraft.tileSize;
		
		// ensure valid player y position
		if(playerY < 0)
			playerY = 0;
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