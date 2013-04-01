package zeldacraft;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class Entity extends ZeldaCraft
{
	private float entityX; private float entityY;
	private Animation entityAniLeft; private Animation entityAniRight;
	private Animation entityAniDown; private Animation entityAniUp;
	
	
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
	
	
	public float getX() 
	{
		return entityX;
	}
	public void setX (float posX) 
	{
		entityX = posX;
	}
	
	
	public float getY() 
	{
		return entityY;
	}
	public void setY (float posY) 
	{
		entityY = posY;
	}
	
	
	public void setCoords (float posX, float posY)
	{
		entityX = posX;
		entityY = posY;
	}
	
}
