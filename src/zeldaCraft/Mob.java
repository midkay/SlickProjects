package zeldaCraft;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


public class Mob extends Entity 
{
	private String mobType;
	
	
	public Mob (int entityX, int entityY, int mobWidth, int mobHeight, int mobHealth, int mobDamage, String type) 
	{
		super (entityX, entityY, mobWidth, mobHeight, mobHealth, mobDamage); //Sets all superclass fields
		
		setSpeed ((float) .1);
		mobType = type;
	}

	
	/*
	 * General update method for mobs. Will call all important functions pertaining to
	 * and affecting the player.
	 */
	public void mobUpdate (Entity passedEntity, int delta, Map curMap)
	{
		entityMapCollision (curMap);
		mobMovement (passedEntity, delta);
	}
	
	
	//needs to have move the mobs around randomly as well!!!
	/*
	 * Handles all mob movements. Determines when to chase player and how to wander.
	 */
	private void mobMovement (Entity passedEntity, int delta)
	{
		if (distanceMethod (passedEntity) < 500)
		{
			if ( (int) passedEntity.getX() < (int) getX())
			{
				setX (getX() - (getSpeed() * delta));
				getAniLeft().update(delta);
				setLastDirection ("left");
			}
			else if ( (int) passedEntity.getX() > (int) getX())
			{
				setX (getX() + (getSpeed() * delta));
				getAniRight().update(delta);
				setLastDirection ("right");
			}
			if ( (int) passedEntity.getY() < (int) getY())
			{
				setY (getY() - (getSpeed() * delta));
				getAniUp().update(delta);
				setLastDirection ("up");
			}
			else if ( (int) passedEntity.getY() > (int) getY())
			{
				setY (getY() + (getSpeed() * delta));
				getAniDown().update(delta);
				setLastDirection ("down");
			}
		}
	}

}
