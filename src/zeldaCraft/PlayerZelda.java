package zeldaCraft;

public class PlayerZelda extends ZeldaCraft{
	protected float playerX;
	protected float playerY;
	protected float playerOldX;
	protected float playerOldY;

	public PlayerZelda() {
		this(100, 100);
	}

	public PlayerZelda(int startX, int startY) {
		playerX = startX;
		playerY = startY;
		playerOldX = playerX;
		playerOldY = playerY;
	}

	public void moveX(float delta) {
		playerX += delta;
		
		// check left corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX, playerY) || checkCollision(playerX, playerY+tileSize-1))
			playerX = ((int) (playerX/tileSize) + 1) * tileSize;
		
		// check right corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX+tileSize, playerY) || checkCollision(playerX+tileSize, playerY+tileSize-2))
			playerX = ((int) (playerX/tileSize)) * tileSize;
	}
	
	public void moveY(float delta) {
		playerY += delta;
		
		// check bottom corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX+tileSize-1, playerY+tileSize) || checkCollision(playerX, playerY+tileSize))
			playerY = ((int) (playerY/tileSize)) * tileSize;

		// check top corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX+tileSize-1, playerY) || checkCollision(playerX, playerY))
			playerY = ((int) (playerY/tileSize) + 1) * tileSize;
	}
	
	// returns true if a collision is occurring at the given x,y
	public boolean checkCollision(float x, float y) {
		return collideTiles[(int) (x/tileSize)][(int) (y/tileSize)];
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
