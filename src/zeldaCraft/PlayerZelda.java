package zeldaCraft;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class PlayerZelda extends Entity
{
	private float cameraX; private float cameraY;
	

	public PlayerZelda (int entityX, int entityY) 
	{
		super (entityX, entityY); //Sets all superclass fields
		
		cameraX = screenW / 2; cameraY = screenH / 2;
		setEntitySpeed ((float) .2);
	}
	
	
	/*
	 * This method handles all player movements, inputs and adjusts the camera
	 */
	public void movementHandler (GameContainer container, int delta, Map curMap) throws SlickException 
	{
		Input input = container.getInput();
		
		if (input.isKeyDown (Input.KEY_A)) //Move Left
		{
			setX (getX() - (getEntitySpeed() * delta));
			getAniLeft().update(delta);
			setLastDirection ("left");
		}
		if (input.isKeyDown (Input.KEY_D)) //Move Right
		{
			setX (getX() + (getEntitySpeed() * delta));
			getAniRight().update(delta);
			setLastDirection ("right");
		}
		if (input.isKeyDown (Input.KEY_S)) //Move down
		{
			setY (getY() + (getEntitySpeed() * delta));
			getAniDown().update(delta);
			setLastDirection ("down");
		}
		if (input.isKeyDown (Input.KEY_W)) //Move up
		{
			setY (getY() - (getEntitySpeed() * delta));
			getAniUp().update(delta);
			setLastDirection ("up");
		}
		if (input.isKeyDown (Input.KEY_ESCAPE))
			System.exit(0);
		
		entityCollision (curMap);
		
		cameraX = screenW / 2 - getX();
		cameraY = screenH / 2 - getY();
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
	
}
