package zeldacraft;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class PlayerZelda extends Entity
{
	private float cameraX; private float cameraY;
	private float playerSpeed;
	private String lastInput;
	

	public PlayerZelda (int entityX, int entityY) 
	{
		super (entityX, entityY); //Sets all superclass fields
		
		cameraX = screenW / 2; cameraY = screenH / 2;
		playerSpeed = (float) .2;
	}
	
	
	/*
	 * This method handles all player movements, inputs and adjusts the camera
	 */
	public void movementHandler (GameContainer container, int delta, Map curMap) throws SlickException 
	{
		Input input = container.getInput();
		
		if (input.isKeyDown (Input.KEY_A)) //Move Left
		{
			setX (getX() - (playerSpeed * delta));
			getAniLeft().update(delta);
			setLastInput ("left");
		}
		if (input.isKeyDown (Input.KEY_D)) //Move Right
		{
			setX (getX() + (playerSpeed * delta));
			getAniRight().update(delta);
			setLastInput ("right");
		}
		if (input.isKeyDown (Input.KEY_S)) //Move down
		{
			setY (getY() + (playerSpeed * delta));
			getAniDown().update(delta);
			setLastInput ("down");
		}
		if (input.isKeyDown (Input.KEY_W)) //Move up
		{
			setY (getY() - (playerSpeed * delta));
			getAniUp().update(delta);
			setLastInput ("up");
		}
		if (input.isKeyDown (Input.KEY_ESCAPE))
			System.exit(0);
		
		entityCollision (curMap);
		
		setCameraX (screenW / 2 - getX());
		setCameraY (screenH / 2 - getY());
	}
	
	
	/*
	 * Renders the appropriate player animation based on input;
	 * Player will always appear to be in the middle of the screen
	 */
	public void playerAniRender (GameContainer container, Graphics g)
	{
		if (getLastInput() == null) //before any input, just draw the ani facing down
			getAniDown().draw (screenW / 2, screenH / 2);
		
		if (getLastInput() == "left") //if last input was left, draw left ani
			getAniLeft().draw (screenW / 2, screenH / 2);
		
		if (getLastInput() == "right") //if last input was right, draw right ani
			getAniRight().draw (screenW / 2, screenH / 2);
		
		if (getLastInput() == "down") //if last input was down, draw down ani
			getAniDown().draw (screenW / 2, screenH / 2);
		
		if (getLastInput() == "up") //if last input was up, draw up ani
			getAniUp().draw (screenW / 2, screenH / 2);
	}
	
	
	public float getCameraX()
	{
		return cameraX;
	}
	public void setCameraX (float newCameraX)
	{
		cameraX = newCameraX;
	}
	
	
	public float getCameraY()
	{
		return cameraY;
	}
	public void setCameraY (float newCameraY)
	{
		cameraY = newCameraY;
	}
	
	
	public float getPlayerSpeed()
	{
		return playerSpeed;
	}
	public void setPlayerSpeed (float newSpeed)
	{
		playerSpeed = newSpeed;
	}
	
	
	public String getLastInput()
	{
		return lastInput;
	}
	public void setLastInput (String input)
	{
		lastInput = input;
	}
	
}
