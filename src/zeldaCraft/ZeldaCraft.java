package zeldaCraft;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class ZeldaCraft extends BasicGame
{
	public static final int tileSize = 32;
	public static final int screenW = 1280; public static final int screenH = 720;
	protected Map initialMap;
	protected PlayerZelda player;
	protected Mob mob; //this needs to be a list of mobs, not just one instance of mob!
	
	
	/**********************************************
	 * Main method for slick, doesn't do all that much. Lists project name, initializes fields.
	 */
	public ZeldaCraft()
	{
		super ("A minecraft, minicraft, zelda ripoff (sorry mojang/nintendo plz no sue)");
	}

	
	/**********************************************
	 * Initialization method for Slick. Allocates memory, creates objects.
	 * Run at the launch of the game and when manually called in the game.
	 */
	public void init (GameContainer container) throws SlickException
	{
		initialMap = new Map ("res/zeldaCraft/level.tmx");
		
		player = new PlayerZelda (initialMap.getMapWidth() / 2, initialMap.getMapHeight() / 2, 32, 32, 10, 2);
		player.setAnimation ("res/zeldaCraft/link/link_normal_noshield/link_left.png", "left");
		player.setAnimation ("res/zeldaCraft/link/link_normal_noshield/link_right.png", "right");
		player.setAnimation ("res/zeldaCraft/link/link_normal_noshield/link_down.png", "down");
		player.setAnimation ("res/zeldaCraft/link/link_normal_noshield/link_up.png", "up");
		
		mob = new Mob (1000, 1000, 32, 32, 5, 1, "linkMob");
		mob.setAnimation ("res/zeldaCraft/link/link_normal_noshield/link_left.png", "left");
		mob.setAnimation ("res/zeldaCraft/link/link_normal_noshield/link_right.png", "right");
		mob.setAnimation ("res/zeldaCraft/link/link_normal_noshield/link_down.png", "down");
		mob.setAnimation ("res/zeldaCraft/link/link_normal_noshield/link_up.png", "up");
	}
	
	
	/**********************************************
	 * Update method for Slick. Runs every frame.
	 * Listens for keypresses; Runs these internal methods.
	 */
	public void update (GameContainer container, int delta) throws SlickException
	{
		player.playerUpdate (container, delta, initialMap, mob);
		mob.mobUpdate (player, delta, initialMap);
		System.out.println(player.getHealth());
	}
	
	
	/**********************************************
	 * Render method for Slick. Runs every frame.
	 * Displays images, the map, and other information to the game window
	 */
	public void render (GameContainer container, Graphics g) throws SlickException
	{
		initialMap.mapRender (container, g, player.getCameraX(), player.getCameraY());
		
		player.entityAniRender (container, g, screenW / 2, screenH / 2);
		mob.entityAniRender (container, g, mob.getX() + player.getCameraX(), mob.getY() + player.getCameraY());
		
		g.drawString ("ZeldaCraft v.CHoDE", 10, 30);
		g.drawString ("playerX: " + player.getX(), 10, 50); 
		g.drawString ("playerY: " + player.getY(), 10, 70);
		g.drawString ("cameraX: " + player.getCameraX(), 10, 100);
		g.drawString ("cameraY: " + player.getCameraY(), 10, 120);
		g.drawString ("mobX: " + mob.getX(), 10, 150); 
		g.drawString ("mobY: " + mob.getY(), 10, 170);
		g.drawString ("playerPolyX: " + player.getPoly().getX(), 10, 200); 
		g.drawString ("playerPolyY: " + player.getPoly().getY(), 10, 220);
	}
	
	
	/**********************************************
	 * Main method for the game. Logistical use, executes main slick method.
	 */
	public static void main(String[] args) throws SlickException 
	{
		AppGameContainer app = new AppGameContainer (new ZeldaCraft());
		app.setDisplayMode (screenW, screenH, false);
		app.start();
	}

}
