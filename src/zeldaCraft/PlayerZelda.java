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
		if(checkCollision(playerX, playerY) ||
		   checkCollision(playerX, playerY+ZeldaCraft.tileSize-1)) {
			playerX = ((int) (playerX/ZeldaCraft.tileSize) + 1) * ZeldaCraft.tileSize;
		}
		// check right corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX+ZeldaCraft.tileSize, playerY) ||
		   checkCollision(playerX+ZeldaCraft.tileSize, playerY+ZeldaCraft.tileSize-1)) {
			playerX = ((int) (playerX/ZeldaCraft.tileSize)) * ZeldaCraft.tileSize;
		}
	}
	
	public void moveY(float delta) {
		playerY += delta;
		
		// check bottom corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX+ZeldaCraft.tileSize-1, playerY+ZeldaCraft.tileSize) ||
		   checkCollision(playerX, playerY+ZeldaCraft.tileSize)) {
			playerY = ((int) (playerY/ZeldaCraft.tileSize)) * ZeldaCraft.tileSize;
		}
		// check top corners for collision. move back just as far as needed, if needed
		if(checkCollision(playerX+ZeldaCraft.tileSize-1, playerY) ||
		   checkCollision(playerX, playerY)) {
			playerY = ((int) (playerY/ZeldaCraft.tileSize) + 1) * ZeldaCraft.tileSize;
		}
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
