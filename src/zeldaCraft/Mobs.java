package zeldaCraft;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


public class Mobs extends Entity 
{
	private String mobType;
	
	
	public Mobs (int entityX, int entityY, String type) 
	{
		super (entityX, entityY); //Sets all superclass fields
		
		mobType = type;
		setEntitySpeed ((float) .1);
	}
	
	
	//need a method that will move the mobs around randomly, and then move closer to the player if 
		//within a certain distance
	public void mobMovement (Entity passedEntity, int delta, Map curMap)
	{
		if (distanceMethod (passedEntity) < 500)
		{
			if ( (int) passedEntity.getX() < (int) getX())
			{
				setX (getX() - (getEntitySpeed() * delta));
				getAniLeft().update(delta);
				setLastDirection ("left");
			}
			else if ( (int) passedEntity.getX() > (int) getX())
			{
				setX (getX() + (getEntitySpeed() * delta));
				getAniRight().update(delta);
				setLastDirection ("right");
			}
			if ( (int) passedEntity.getY() < (int) getY())
			{
				setY (getY() - (getEntitySpeed() * delta));
				getAniUp().update(delta);
				setLastDirection ("up");
			}
			else if ( (int) passedEntity.getY() > (int) getY())
			{
				setY (getY() + (getEntitySpeed() * delta));
				getAniDown().update(delta);
				setLastDirection ("down");
			}
		}
			
		entityCollision (curMap);
	}

}
