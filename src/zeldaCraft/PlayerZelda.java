package zeldaCraft;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class PlayerZelda extends Entity
{
	private float cameraX; private float cameraY; //shifting values for the map
	

	public PlayerZelda (int entityX, int entityY, int playerWidth, int playerHeight, int playerHealth, int mobDamage) 
	{
		super (entityX, entityY, playerWidth, playerHeight, playerHealth, mobDamage); //Sets all superclass fields
		
		cameraX = screenW / 2; cameraY = screenH / 2;
		setSpeed ((float) .2);
	}
	
	
	/*
	 * General update method for the player. Will call all important functions pertaining to
	 * and affecting the player.
	 */
	public void playerUpdate (GameContainer container, int delta, Map curMap, Entity mob) throws SlickException 
	{
		playerMovement (container, delta, curMap, mob); //player should move first in update (for collision)
		entityMapCollision(curMap);
		entityToEntityCollision(mob);
		playerAttacked (mob);
	}
	
	
	/*
	 * Handles all player movements, inputs and sets the offset for the camera coords
	 */
	private void playerMovement (GameContainer container, int delta, Map curMap, Entity mob)
	{
		Input input = container.getInput();
		
		//entityToEntityCollision(mob);
		
		if (input.isKeyDown (Input.KEY_A)) //Move Left
		{
			setX (getX() - (getSpeed() * delta));
			getAniLeft().update(delta);
			setLastDirection ("left");
		}
		if (input.isKeyDown (Input.KEY_D)) //Move Right
		{
			setX (getX() + (getSpeed() * delta));
			getAniRight().update(delta);
			setLastDirection ("right");
		}
		if (input.isKeyDown (Input.KEY_S)) //Move down
		{
			setY (getY() + (getSpeed() * delta));
			getAniDown().update(delta);
			setLastDirection ("down");
		}
		if (input.isKeyDown (Input.KEY_W)) //Move up
		{
			setY (getY() - (getSpeed() * delta));
			getAniUp().update(delta);
			setLastDirection ("up");
		}
		if (input.isKeyDown (Input.KEY_ESCAPE))
			System.exit(0);
		
		cameraX = (float) (screenW / 2 - getX());
		cameraY = (float) (screenH / 2 - getY());
	}
	
	
	//needs work, need to check for collision between entities and not collide if true.
	//player needs to take damage when colliding with a mob
	//player needs to 'jump' away from entity when hit
	//player needs a moment of invulnerability after hit
	//player needs to be able to attack
	private void playerAttacked (Entity mob)
	{
		//take damage when 'hit' by mob if polys intersect
		if (getPoly().intersects (mob.getPoly()))
		{
			setHealth (getHealth() - mob.getDamage());
			
			
		}
		
		//knock back a bit when hit by the mob
		
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
