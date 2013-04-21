package zeldaCraft;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class Entity extends ZeldaCraft
{
	private float entityX; private float entityY;
	private float entitySpeed;
	private Animation entityAniLeft; private Animation entityAniRight;
	private Animation entityAniDown; private Animation entityAniUp;
	private String entityLastDirection;
	
	
	/*
	 * Constructors...
	 */
	public Entity ()
	{
		this(100, 100);
	}
	
	public Entity (int initialX, int initialY)
	{
		entityX = initialX; entityY = initialY;
	}
	
	
	/*
	 * Animation method; take the image directory and the type and make an appropriate animation
	 */
	public void setAnimation (String aniDir, String aniType) throws SlickException
	{
		//make a spritesheet with the passed image; params (location, tileWidth, tileHeight)
		SpriteSheet entityCurrentSS = new SpriteSheet (aniDir, 30, 32);
		
		//run through the types and find the aniType passed in
		if (aniType == "left")
		{
			entityAniLeft = new Animation(false); //false arg so that the ani doesnt run when player is still
			for (int i = 0; i < 2; i++)
			{
				entityAniLeft.addFrame (entityCurrentSS.getSprite(i,0), 120); //the last param sets the speed of the ani
			}
		}
		else if (aniType == "right")
		{
			entityAniRight = new Animation(false);
			for (int i = 0; i < 2; i++)
			{
				entityAniRight.addFrame (entityCurrentSS.getSprite(i,0), 120);
			}
		}
		else if (aniType == "down")
		{
			entityAniDown = new Animation(false);
			for (int i = 0; i < 2; i++)
			{
				entityAniDown.addFrame (entityCurrentSS.getSprite(i,0), 120);
			}
		}
		else if (aniType == "up")
		{
			entityAniUp = new Animation(false);
			for (int i = 0; i < 2; i++)
			{
				entityAniUp.addFrame (entityCurrentSS.getSprite(i,0), 120);
			}
		}
	}
	public Animation getAniLeft() 
	{
		return entityAniLeft;
	}
	public Animation getAniRight()
	{
		return entityAniRight;
	}
	public Animation getAniDown()
	{
		return entityAniDown;
	}
	public Animation getAniUp()
	{
		return entityAniUp;
	}
	
	
	/*
	 * Entity CollisionChecking method
	 */
	public void entityCollision (Map curMap) 
	{
		// check left corners for collision. move back just as far as needed, if needed
		if (curMap.collisionCheck (getX(), getY()) == "blocked"
			|| curMap.collisionCheck (getX(), getY() + tileSize - 1) == "blocked")
		{
			setX (((int) (getX() / tileSize) + 1) * tileSize);
		}
		// check right corners for collision
		if (curMap.collisionCheck (getX() + tileSize, getY()) == "blocked"
			|| curMap.collisionCheck (getX() + tileSize, getY() + tileSize - 1) == "blocked")
		{
			setX (((int) (getX() / tileSize)) * tileSize);
		}
		// check bottom corners for collision
		if (curMap.collisionCheck (getX() + tileSize - 1, getY() + tileSize) == "blocked"
			|| curMap.collisionCheck (getX(), getY() + tileSize) == "blocked")
		{
			setY (((int) (getY() / tileSize)) * tileSize);
		}
		// check top corners for collision
		if (curMap.collisionCheck (getX() + tileSize - 1, getY()) == "blocked"
			|| curMap.collisionCheck (getX(), getY()) == "blocked")
		{	
			setY (((int) (getY() / tileSize) + 1) * tileSize);
		}
	}
	
	
	/*
	 * Renders the appropriate entity animation based on direction and passed coords
	 */
	public void entityAniRender (GameContainer container, Graphics g, float posX, float posY)
	{
		if (getLastDirection() == null) //before any input, just draw the ani facing down
			entityAniDown.draw (posX, posY);
		
		if (getLastDirection() == "left") //if last input was left, draw left ani
			entityAniLeft.draw (posX, posY);
		
		if (getLastDirection() == "right") //if last input was right, draw right ani
			entityAniRight.draw (posX, posY);
		
		if (getLastDirection() == "down") //if last input was down, draw down ani
			entityAniDown.draw (posX, posY);
		
		if (getLastDirection() == "up") //if last input was up, draw up ani
			entityAniUp.draw (posX, posY);
	}
	
	
	/*
	 * returns the distance between the entity and passed entity
	 */
	public double distanceMethod (Entity passedEntity)
	{
		double distance;

		distance = Math.sqrt (Math.pow ((passedEntity.getX() - entityX), 2) + 
			Math.pow ((passedEntity.getY() - entityY), 2));

		return distance;
	}
	
	
	public float getX() 
	{
		return entityX;
	}
	public void setX (float newX) 
	{
		entityX = newX;
	}
	public float getY() 
	{
		return entityY;
	}
	public void setY (float newY) 
	{
		entityY = newY;
	}
	
	
	public float getEntitySpeed()
	{
		return entitySpeed;
	}
	public void setEntitySpeed (float newSpeed)
	{
		entitySpeed = newSpeed;
	}
	
	
	public String getLastDirection()
	{
		return entityLastDirection;
	}
	public void setLastDirection (String direction)
	{
		entityLastDirection = direction;
	}
	
}
