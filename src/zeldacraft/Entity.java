package zeldaCraft;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;


public class Entity extends ZeldaCraft
{
	private float entityX; private float entityY;
	private int entityWidth; private int entityHeight;
	private Animation entityAniLeft; private Animation entityAniRight;
	private Animation entityAniDown; private Animation entityAniUp;
	private float entitySpeed;
	private Polygon entityPoly;
	private String entityLastDirection;
	private int entityHealth;
	private boolean entityAlive;
	private int entityDamage;
	
	
	/*
	 * Constructor
	 */
	public Entity (int initialX, int initialY, int width, int height, int initialHealth, int initialDamage)
	{
		entityX = initialX; entityY = initialY;
		entityWidth = width; entityHeight = height;
		
		entityPoly = new Polygon (new float[] {
				entityX, entityY, 
				entityX + entityWidth, entityY, 
				entityX + entityWidth, entityY + entityHeight,
				entityX, entityY + entityHeight});
		
		entityHealth = initialHealth;
		entityAlive = true;
		entityDamage = initialDamage;
	}
	
	
	/*
	 * Entity to Map CollisionChecking method. Called every update for Player and Mobs.
	 */
	public void entityMapCollision (Map curMap) 
	{
		// check left corners for collision. If colliding with a block, set X or Y to exact tile edge (ignores players input).
		if (curMap.collisionCheck (getX(), getY()) 
			|| curMap.collisionCheck (getX(), getY() + getHeight() - 1))
		{
			setX (((int) (getX() / tileSize) + 1) * tileSize);
		}
		// check right corners for collision
		if (curMap.collisionCheck (getX() + getWidth(), getY()) 
			|| curMap.collisionCheck (getX() + getWidth(), getY() + getHeight() - 1))
		{
			setX (((int) (getX() / tileSize)) * tileSize);
		}
		// check bottom corners for collision
		if (curMap.collisionCheck (getX(), getY() + getHeight()) 
			|| curMap.collisionCheck (getX() + getWidth() - 1, getY() + getHeight()))
		{
			setY (((int) (getY() / tileSize)) * tileSize);
		}
		// check top corners for collision
		if (curMap.collisionCheck (getX(), getY())
			|| curMap.collisionCheck (getX() + getWidth() - 1, getY()))
		{
			setY (((int) (getY() / tileSize) + 1) * tileSize);
		}
	}
	
	
	/*
	 * Entity to Entity CollisionChecking method. Called every update for Player and Mobs.
	 * NEEDS WORK
	 */
	public boolean entityToEntityCollision (Entity collidingEntity)
	{
		if (getPoly().intersects (collidingEntity.getPoly()))
		{
			setX(getX());
			setY(getY());
			collidingEntity.setX(collidingEntity.getX());
			collidingEntity.setY(collidingEntity.getY());
			
			return true;
		}
		
		return false;
	}
	
	
	/*
	 * Renders the appropriate entity animation based on direction and passed coords.
	 */
	public void entityAniRender (GameContainer container, Graphics g, float posX, float posY)
	{
		if (getLastDirection() == null) //before any input, just draw the ani facing down
			entityAniDown.draw (posX, posY);
		
		if (getLastDirection() == "left") //if last input was left, draw left ani
			entityAniLeft.draw (posX, posY);
		
		if (getLastDirection() == "right") //""
			entityAniRight.draw (posX, posY);
		
		if (getLastDirection() == "down") //""
			entityAniDown.draw (posX, posY);
		
		if (getLastDirection() == "up") //""
			entityAniUp.draw (posX, posY);
	}
	
	
	/*
	 * Animation method. Take the image directory and the type and make an appropriate animation.
	 */
	public void setAnimation (String aniDir, String aniType) throws SlickException
	{
		//make a spritesheet with the passed image; params (location, imageWidth, imageHeight)
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
		entityPoly.setX(newX);
	}
	public float getY() 
	{
		return entityY;
	}
	public void setY (float newY) 
	{
		entityY = newY;
		entityPoly.setY(newY);
	}
	
	
	public int getWidth() 
	{
		return entityWidth;
	}
	public void setWidth (int newWidth) 
	{
		entityWidth = newWidth;
	}
	public float getHeight() 
	{
		return entityHeight;
	}
	public void setHeight (int newHeight) 
	{
		entityHeight = newHeight;
	}
	
	
	public float getSpeed()
	{
		return entitySpeed;
	}
	public void setSpeed (float newSpeed)
	{
		entitySpeed = newSpeed;
	}
	
	
	public Polygon getPoly() {
		return entityPoly;
	}

	public void setPoly(Polygon entityPoly) {
		this.entityPoly = entityPoly;
	}
	
	
	public String getLastDirection()
	{
		return entityLastDirection;
	}
	public void setLastDirection (String direction)
	{
		entityLastDirection = direction;
	}
	
	
	public int getHealth()
	{
		return entityHealth;
	}
	public void setHealth (int newHealth)
	{
		entityHealth = newHealth;
	}
	
	
	public boolean getAlive()
	{
		return entityAlive;
	}
	public void setAlive (boolean newAlive)
	{
		entityAlive = newAlive;
	}
	
	
	public int getDamage()
	{
		return entityDamage;
	}
	public void setDamage (int newDamage)
	{
		entityDamage = newDamage;
	}
	
}
