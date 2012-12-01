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
	}
	
	public void moveY(float delta) {
		playerY += delta;
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
